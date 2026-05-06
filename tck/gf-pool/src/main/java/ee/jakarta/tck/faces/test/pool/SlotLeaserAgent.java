/*
 * Copyright (c) 2026 Contributors to Eclipse Foundation.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */
package ee.jakarta.tck.faces.test.pool;

import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.Instrumentation;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

/**
 * Java agent attached to the failsafe-forked test JVM. At {@code premain} it leases
 * one of the GlassFish pool slots managed by the {@code gf-pool} module by
 * acquiring an exclusive {@link FileLock} on {@code ${gf.pool.dir}/slot-N/lock}.
 * The lock is held in static fields so it survives for the JVM lifetime; JVM exit
 * releases it automatically. Once a slot is leased the agent loads
 * {@code slot-N/ports.properties} and exposes its keys (notably
 * {@code arquillian.adminPort}, {@code arquillian.httpPort},
 * {@code arquillian.httpsPort}) as system properties so the arquillian
 * remote container picks them up.
 *
 * <p>If every existing slot is leased the agent grows the pool: it acquires
 * a global grow-lock, computes the next slot index, execs
 * {@code provision-slot.sh} with the slot's port range, and leases the new
 * slot. Growth is unbounded — concurrent demand naturally caps it at the
 * Maven {@code -T} value, since each test JVM only ever leases one slot.
 */
public final class SlotLeaserAgent {

    private static final String POOL_DIR_PROP = "gf.pool.dir";
    private static final String LEASE_TIMEOUT_PROP = "gf.pool.leaseTimeoutSeconds";
    private static final String DEFAULT_LEASE_TIMEOUT_SECONDS = "600";
    private static final long POLL_INTERVAL_MILLIS = 500L;
    private static final int HEALTH_PROBE_TIMEOUT_MILLIS = 1000;

    @SuppressWarnings("unused") // anchors the lock for JVM lifetime
    private static FileChannel heldChannel;
    @SuppressWarnings("unused")
    private static FileLock heldLock;

    private SlotLeaserAgent() {
    }

    public static void premain(String args, Instrumentation instrumentation) throws Exception {
        leaseSlot();
    }

    public static void premain(String args) throws Exception {
        leaseSlot();
    }

    private static void leaseSlot() throws Exception {
        Path poolDir = Paths.get(requiredProperty(POOL_DIR_PROP));
        long timeoutSeconds = Long.parseLong(System.getProperty(LEASE_TIMEOUT_PROP, DEFAULT_LEASE_TIMEOUT_SECONDS));
        long deadline = System.currentTimeMillis() + (timeoutSeconds * 1000L);
        PoolConfig config = PoolConfig.load(poolDir);

        while (System.currentTimeMillis() < deadline) {
            int discovered = scanAndTryAcquire(poolDir);
            if (discovered < 0) {
                return; // success
            }
            int grown = tryGrow(poolDir, config);
            if (grown >= 0 && tryAcquire(poolDir, grown)) {
                return;
            }
            Thread.sleep(POLL_INTERVAL_MILLIS);
        }
        throw new IllegalStateException("Could not lease a GlassFish pool slot within "
                + timeoutSeconds + "s (pool=" + poolDir + ")");
    }

    /**
     * Enumerate every leasable slot under {@code poolDir} (gap-tolerant:
     * a {@code slot-N/} without {@code ports.properties} from a crashed
     * provision is skipped, not used as a stopping condition), then try to
     * acquire one in least-recently-used order. The lock file's mtime is
     * touched on every successful acquire (because we open it WRITE), so
     * sorting ascending by mtime biases toward the slot that has been idle
     * the longest — which evens out load when tests have wildly different
     * durations and a strict scan order would let fast-test JVMs cycle
     * through one slot while another holds a long test.
     *
     * @return -1 if a slot was leased; otherwise the count of leasable slots
     *         observed (all busy).
     */
    private static int scanAndTryAcquire(Path poolDir) throws IOException {
        List<Integer> candidates = new ArrayList<>();
        try (java.util.stream.Stream<Path> entries = Files.list(poolDir)) {
            for (Path entry : (Iterable<Path>) entries::iterator) {
                String name = entry.getFileName().toString();
                if (!name.startsWith("slot-")) {
                    continue;
                }
                if (!Files.exists(entry.resolve("ports.properties"))) {
                    continue;
                }
                try {
                    candidates.add(Integer.parseInt(name.substring("slot-".length())));
                } catch (NumberFormatException ignored) {
                    // Not a numeric slot dir — ignore.
                }
            }
        }
        candidates.sort(Comparator.comparingLong(slot -> lockMtime(poolDir, slot)));
        for (int slot : candidates) {
            if (tryAcquire(poolDir, slot)) {
                return -1;
            }
        }
        return candidates.size();
    }

    private static Integer parsePort(String raw) {
        if (raw == null || raw.isEmpty()) {
            return null;
        }
        try {
            return Integer.parseInt(raw.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static boolean isAdminPortHealthy(int adminPort) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("localhost", adminPort), HEALTH_PROBE_TIMEOUT_MILLIS);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private static long lockMtime(Path poolDir, int slot) {
        // Catch covers both missing file (NoSuchFileException) and unreadable
        // mtime; in either case treat as oldest so this slot is tried first.
        try {
            return Files.getLastModifiedTime(poolDir.resolve("slot-" + slot).resolve("lock")).toMillis();
        } catch (IOException e) {
            return 0L;
        }
    }

    private static boolean tryAcquire(Path poolDir, int slot) throws IOException {
        Path slotDir = poolDir.resolve("slot-" + slot);
        Path portsFile = slotDir.resolve("ports.properties");
        if (!Files.exists(portsFile)) {
            return false;
        }
        FileChannel channel = FileChannel.open(slotDir.resolve("lock"),
                StandardOpenOption.CREATE,
                StandardOpenOption.READ,
                StandardOpenOption.WRITE);
        FileLock lock = null;
        try {
            lock = channel.tryLock();
            if (lock == null) {
                channel.close();
                return false;
            }
            Properties properties = new Properties();
            try (InputStream in = Files.newInputStream(portsFile)) {
                properties.load(in);
            }
            // Health check: a free lock file does not imply a live GlassFish.
            // The previous owner's GF may have OOM-crashed, been killed
            // externally, or been mid-shutdown when we picked up the lock.
            // Probe the admin port before committing the lease so a phantom
            // slot returns false here and the scan continues.
            // Also tolerate a half-written ports.properties (provision-slot.sh
            // crashed mid-write): treat missing/non-numeric adminPort as a
            // failed health check, not a hard error.
            String adminPortStr = properties.getProperty("arquillian.adminPort");
            Integer adminPort = parsePort(adminPortStr);
            if (adminPort == null || !isAdminPortHealthy(adminPort)) {
                lock.release();
                channel.close();
                return false;
            }
            properties.forEach((k, v) -> System.setProperty(k.toString(), v.toString()));
            System.setProperty("gf.pool.slot", String.valueOf(slot));
            heldChannel = channel;
            heldLock = lock;
            // Don't println — surefire/failsafe owns the forked JVM's stdout as a
            // control channel and reports "Corrupted channel" on direct writes.
            // Slot identity is observable via the gf.pool.slot system property.
            return true;
        } catch (RuntimeException | IOException e) {
            // Anything between open() and the assignment to heldChannel/heldLock
            // must release the channel; otherwise a transient failure leaks an FD
            // every poll cycle.
            if (lock != null) {
                try { lock.release(); } catch (IOException ignored) { /* nothing useful */ }
            }
            try { channel.close(); } catch (IOException ignored) { /* nothing useful */ }
            throw e;
        }
    }

    /**
     * Grows the pool by one slot. Synchronised across concurrent test JVMs
     * via a blocking file lock on {@code grow.lock} so two JVMs can't pick
     * the same next index. Slot indices are 1-based for human readability;
     * scans from 1 and fills the lowest-unused slot (gap-aware).
     */
    private static int tryGrow(Path poolDir, PoolConfig config) throws Exception {
        Path growLockFile = poolDir.resolve("grow.lock");
        try (FileChannel fc = FileChannel.open(growLockFile,
                StandardOpenOption.CREATE,
                StandardOpenOption.READ,
                StandardOpenOption.WRITE);
             FileLock ignored = fc.lock()) {
            int next = 1;
            while (Files.exists(poolDir.resolve("slot-" + next).resolve("ports.properties"))) {
                next++;
            }
            ProcessBuilder pb = new ProcessBuilder("bash",
                    config.scriptDir.resolve("provision-slot.sh").toString());
            pb.environment().put("SLOT_IDX", String.valueOf(next));
            pb.environment().put("POOL_DIR", poolDir.toString());
            pb.environment().put("SOURCE_HOME", config.source.toString());
            pb.environment().put("ADMIN_BASE", String.valueOf(config.adminBase));
            pb.environment().put("PORT_STRIDE", String.valueOf(config.portStride));
            pb.inheritIO();
            int exit = pb.start().waitFor();
            if (exit != 0) {
                throw new IllegalStateException(
                        "provision-slot.sh failed (slot=" + next + ", exit=" + exit + ")");
            }
            return next;
        }
    }

    private static String requiredProperty(String name) {
        String value = System.getProperty(name);
        if (value == null || value.isEmpty()) {
            throw new IllegalStateException("System property " + name + " is required");
        }
        return value;
    }

    private static final class PoolConfig {
        final Path source;
        final int adminBase;
        final int portStride;
        final Path scriptDir;

        private PoolConfig(Properties p) {
            this.source = Paths.get(require(p, "source"));
            this.adminBase = Integer.parseInt(require(p, "adminBase"));
            this.portStride = Integer.parseInt(require(p, "portStride"));
            this.scriptDir = Paths.get(require(p, "scriptDir"));
        }

        private static String require(Properties p, String key) {
            String value = p.getProperty(key);
            if (value == null || value.isEmpty()) {
                throw new IllegalStateException("Pool config key '" + key + "' is missing");
            }
            return value;
        }

        static PoolConfig load(Path poolDir) throws IOException {
            Path file = poolDir.resolve("config.properties");
            if (!Files.exists(file)) {
                throw new IllegalStateException("Pool config not found at " + file
                        + " — has the gf-pool module run?");
            }
            Properties p = new Properties();
            try (InputStream in = Files.newInputStream(file)) {
                p.load(in);
            }
            return new PoolConfig(p);
        }
    }
}
