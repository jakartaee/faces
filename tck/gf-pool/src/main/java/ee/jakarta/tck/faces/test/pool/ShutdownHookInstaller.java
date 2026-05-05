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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/**
 * Custom Ant task that installs a JVM shutdown hook on Maven's own JVM so the
 * GlassFish pool stops when the build completes (or is killed via {@code Ctrl+C}).
 *
 * <p>Loaded by the {@code gf-pool} antrun via {@code <taskdef>}; runs in-process
 * inside the {@code maven-antrun-plugin}'s embedded Ant project, which shares
 * the Maven JVM. The hook persists for the lifetime of that JVM, so it fires
 * at session end after every test module has finished.
 *
 * <p>Avoids {@code <java fork="false">} which on JDK 17+ throws
 * {@code UnsupportedOperationException: The Security Manager is deprecated}.
 *
 * <p>The actual stop logic delegates to {@code pool-down.sh} so the script
 * remains the single source of truth for teardown.
 */
public final class ShutdownHookInstaller extends Task {

    private static final AtomicBoolean installed = new AtomicBoolean(false);
    private static final AtomicBoolean stopped = new AtomicBoolean(false);

    private File poolDir;
    private File scriptDir;

    public void setPoolDir(File poolDir) {
        this.poolDir = poolDir;
    }

    public void setScriptDir(File scriptDir) {
        this.scriptDir = scriptDir;
    }

    @Override
    public void execute() {
        if (poolDir == null || scriptDir == null) {
            throw new IllegalStateException("poolDir and scriptDir attributes are required");
        }
        if (!installed.compareAndSet(false, true)) {
            return;
        }
        File pool = poolDir;
        File scripts = scriptDir;
        Runtime.getRuntime().addShutdownHook(new Thread(() -> stopPool(pool, scripts), "gf-pool-shutdown"));
        log("shutdown hook armed (will stop pool when this Maven JVM exits)");
        warnIfSequential();
    }

    /**
     * If Maven was invoked without {@code -T}, log a banner reminding the user
     * that the pool's parallelism is unused. The {@code -T} value comes from
     * {@code MavenSession.getRequest().getDegreeOfConcurrency()}, which the
     * maven-antrun-plugin exposes through its property helper chain — we reach
     * it reflectively to avoid a hard dependency on maven-core in this jar.
     */
    private void warnIfSequential() {
        Integer threads = readDegreeOfConcurrency();
        if (threads == null || threads > 1) {
            return;
        }
        // One log() call per line so each gets its own [WARNING] prefix from
        // the Maven CLI logger; a single multi-line string only prefixes the
        // first line and the rest are emitted unprefixed by Ant.
        String[] banner = {
                "",
                "==========================================================================",
                "  WARNING: gf-pool is provisioned, but the build is running SEQUENTIALLY.",
                "  Re-run with -T <N> to actually exercise the pool, e.g.:",
                "",
                "      mvn clean install -T 4",
                "",
                "  The pool grows on demand up to max(4, cores/2).",
                "==========================================================================",
                ""
        };
        for (String line : banner) {
            log(line, Project.MSG_WARN);
        }
    }

    private Integer readDegreeOfConcurrency() {
        // maven-antrun-plugin doesn't expose MavenSession to Ant references,
        // so we infer -T from sun.java.command — the system property HotSpot
        // populates with the launcher class + Maven CLI args. Look for any of
        // the recognised forms ("-T 4", "-T4", "-T 1C", "--threads 4").
        String cmd = System.getProperty("sun.java.command", "");
        if (cmd.isEmpty()) {
            return null;
        }
        // Tokenise on whitespace; reject the matches when the next token starts
        // with a digit or 'C' (-T 1C means 1 per core).
        String[] tokens = cmd.split("\\s+");
        for (int i = 0; i < tokens.length; i++) {
            String t = tokens[i];
            if (("-T".equals(t) || "--threads".equals(t)) && i + 1 < tokens.length) {
                return parseThreads(tokens[i + 1]);
            }
            if (t.startsWith("-T") && t.length() > 2) {
                return parseThreads(t.substring(2));
            }
            if (t.startsWith("--threads=")) {
                return parseThreads(t.substring("--threads=".length()));
            }
        }
        // No -T detected — sequential.
        return 1;
    }

    private static Integer parseThreads(String value) {
        // The "C" suffix means "per-core" — `-T 1C` on an N-core host = N threads,
        // which is always parallel on multi-core hardware. Treat any *C form as
        // parallel regardless of multiplier.
        boolean perCore = value.endsWith("C") || value.endsWith("c");
        String stripped = perCore ? value.substring(0, value.length() - 1) : value;
        try {
            float n = Float.parseFloat(stripped);
            if (n <= 0) {
                return 1; // -T 0 / -T 0C / -T -1 — sequential by Maven semantics.
            }
            if (perCore) {
                return Integer.MAX_VALUE; // any positive *C multiplier on multi-core = parallel.
            }
            return n <= 1 ? 1 : Integer.MAX_VALUE;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static void stopPool(File poolDir, File scriptDir) {
        if (!stopped.compareAndSet(false, true)) {
            return;
        }
        File stopSlotScript = new File(scriptDir, "stop-slot.sh");
        if (!poolDir.isDirectory() || !stopSlotScript.isFile()) {
            return;
        }
        // Iterate slots in Java (rather than shelling out to pool-down.sh)
        // because we need to test each slot's lock with FileChannel.tryLock().
        // bash's flock(1) and Java's FileLock use *different* OS lock spaces
        // on Linux (flock(2) vs fcntl()), so a flock-based check from bash
        // cannot see Java agent locks. Holding the lock here while we run
        // stop-slot.sh also prevents a new test JVM from leasing the slot
        // mid-shutdown.
        File[] slotDirs = poolDir.listFiles((dir, name) -> name.startsWith("slot-"));
        if (slotDirs == null) {
            return;
        }
        // Stop slots concurrently — each asadmin stop-domain takes ~500 ms and the
        // calls are independent. The FileLock probe inside stopSlotIfIdle is safe
        // across threads because each slot has its own lock file.
        List<Thread> stoppers = new ArrayList<>(slotDirs.length);
        for (File slotDir : slotDirs) {
            int slotIdx;
            try {
                slotIdx = Integer.parseInt(slotDir.getName().substring("slot-".length()));
            } catch (NumberFormatException e) {
                continue;
            }
            Thread t = new Thread(
                () -> stopSlotIfIdle(slotDir.toPath(), slotIdx, stopSlotScript, poolDir),
                "gf-pool-stop-slot-" + slotIdx);
            t.start();
            stoppers.add(t);
        }
        for (Thread t : stoppers) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    private static void stopSlotIfIdle(Path slotDir, int slotIdx, File stopSlotScript, File poolDir) {
        Path lockFile = slotDir.resolve("lock");
        try (FileChannel fc = FileChannel.open(lockFile,
                StandardOpenOption.CREATE,
                StandardOpenOption.READ,
                StandardOpenOption.WRITE)) {
            FileLock lock = fc.tryLock();
            if (lock == null) {
                System.err.println("[gf-pool] slot " + slotIdx + " is still leased by another process; skip stop");
                return;
            }
            try {
                runStopSlot(stopSlotScript, poolDir, slotIdx);
            } finally {
                lock.release();
            }
        } catch (IOException e) {
            System.err.println("[gf-pool] couldn't probe slot " + slotIdx + ": " + e);
        }
    }

    private static void runStopSlot(File script, File poolDir, int slotIdx) {
        ProcessBuilder pb = new ProcessBuilder("bash", script.getAbsolutePath());
        pb.environment().put("POOL_DIR", poolDir.getAbsolutePath());
        pb.environment().put("SLOT_IDX", String.valueOf(slotIdx));
        pb.redirectErrorStream(true);
        try {
            Process process = pb.start();
            // Buffer the entire per-slot output and emit it as one block so the
            // concurrent slots' lines don't interleave mid-line on the console.
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            process.getInputStream().transferTo(buf);
            process.waitFor();
            String out = buf.toString();
            if (!out.isEmpty() && !out.endsWith("\n")) {
                out += "\n";
            }
            synchronized (ShutdownHookInstaller.class) {
                System.out.print(out);
                System.out.flush();
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("[gf-pool] failed to stop slot " + slotIdx + ": " + e);
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
