<!---
[//]: # " Copyright (c) 2021, 2026 Contributors to the Eclipse foundation. All rights reserved. "
[//]: # "  "
[//]: # " This program and the accompanying materials are made available under the "
[//]: # " terms of the Eclipse Public License v. 2.0, which is available at "
[//]: # " http://www.eclipse.org/legal/epl-2.0. "
[//]: # "  "
[//]: # " This Source Code may also be made available under the following Secondary "
[//]: # " Licenses when the conditions for such availability set forth in the "
[//]: # " Eclipse Public License v. 2.0 are satisfied: GNU General Public License, "
[//]: # " version 2 with the GNU Classpath Exception, which is available at "
[//]: # " https://www.gnu.org/software/classpath/license.html. "
[//]: # "  "
[//]: # " SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0 "
-->

# Jakarta Faces TCK

Integration tests that assert the Faces API and reference implementation behave
as the spec mandates. Tests are JUnit + Selenium driving real HTTP requests
against a deployed WAR; Arquillian only handles deploy/undeploy. No in-container
test transfer is used — all assertions run client-side.

## Architecture: the GlassFish pool

The TCK runs every test module against a pre-started GlassFish instance from a
shared **pool**, leased per-module by a Java agent attached to each
failsafe-forked test JVM. The pool lives entirely under
`gf-pool/target/pool/` and is wiped by `mvn clean`.

- `gf-pool` module: unpacks GlassFish, overlays the Mojarra build under test,
  clones the install into `slot-N/` directories (hardlinked via `cp -al`),
  patches each slot's port range, and starts the initial slot's domain.
- `SlotLeaserAgent` (Java agent, `-javaagent:slot-leaser-agent.jar`):
  acquires a `FileLock` on `slot-N/lock`, publishes the slot's
  `arquillian.adminPort` / `httpPort` / `httpsPort` as system properties for
  the Arquillian remote container, and holds the lock for the JVM lifetime
  (released automatically on JVM exit).
- Grow-on-demand: if every existing slot is leased, the agent provisions
  another slot. Concurrent demand naturally caps growth at the running
  `-T` value (each test JVM only ever leases one slot), so a single
  `mvn -Dit.test=…` boots one server, `mvn -T4 install` grows to four,
  `mvn -T16 install` grows to sixteen.
- `ShutdownHookInstaller`: a custom Ant task registered by the `gf-pool`
  antrun. Runs in the Maven JVM and installs a JVM shutdown hook that stops
  every running slot at session end — both on normal completion and on
  `Ctrl+C`.

## Running the tests

Full suite, sequentially:

```bash
mvn clean install
```

To actually exercise the pool's parallelism, pass `-TN`:

```bash
mvn clean install -T4
```

The pool starts with one slot and grows on demand to match concurrent
demand, so `-T4` ramps to four slots and `-T8` to eight. Pick `-T` based
on host capacity.

A single test module — just `cd` into it and run `mvn`. The `.mvn/`
marker at the tck root pins `${maven.multiModuleProjectDirectory}`
there, so the gf-pool javaagent path resolves correctly even when
Maven is invoked from a subdirectory:

```bash
cd faces50/ajax
mvn clean install -T4
```

A whole faces version — same idea, descend into the aggregator:

```bash
cd faces50
mvn clean install -T4
```

A single test class or method:

```bash
cd faces50/ajax
mvn clean install -Dit.test=Issue5594IT
mvn clean install -Dit.test=Issue5594IT#someMethod
```

If you'd rather stay at the tck root, the `-pl` form still works
(use `-am` to also pull in upstream `util` / `gf-pool`):

```bash
mvn -pl faces50/ajax -am clean install -T4
mvn -pl faces50/ajax -am clean install -Dit.test=Issue5594IT
```

Against an existing GlassFish install (skips the zip download and the
Mojarra overlay; that install becomes the pool's clone source — every
slot is `cp -al`-cloned from it, with `applications/`, `generated/`,
`logs/` cleared per slot):

```bash
mvn clean install -T4 -Dglassfish.home=/path/to/glassfish
```

## Common overrides

| Flag | Effect |
| --- | --- |
| `-T1` | Sequential build, single slot. This is Maven's default when `-T` is omitted. |
| `-T8` | Eight Maven threads. Pool grows on demand to match. |
| `-Dgf.pool.size=N` | Pre-provision N slots at build time instead of the default 1. |
| `-Dglassfish.home=/path/to/glassfish9` | Skip the GlassFish download; clone slots from an existing install. Each slot still gets a fresh copy of `domains/` with `applications/`, `generated/`, `logs/` cleared. |
| `-DskipTests` / `-Dmaven.test.skip` | Skip the entire pool lifecycle (GlassFish unpack + Mojarra overlay + slot bootstrap), in addition to skipping tests. Use when you only want a clean compile/package without paying the pool cost. |
| `-Dmojarra.version=X.Y.Z` | Test against a specific Mojarra release/snapshot. |
| `-Dmojarra.noupdate=true` | Skip the Mojarra overlay; test the build that ships with GlassFish. Set `-Dsigtest.api.version=` to match the API GlassFish ships. |
| `-Dee.jakarta.tck.faces.timeout=N` | Selenium wait timeout in milliseconds (default `10000`). Bump on old, slow, or overloaded systems where the default 10 s isn't enough for HTTP navigations or ajax requests to settle, e.g. `-Dee.jakarta.tck.faces.timeout=30000` for 30 s. |
| `-Dwebapp.projectStage=…` | `jakarta.faces.PROJECT_STAGE` for every test webapp (default `Production`). Set to `Development` to exercise dev-stage code paths. |
| `-Dwebapp.partialStateSaving=…` | `jakarta.faces.PARTIAL_STATE_SAVING` (default `true`). Set to `false` to run the suite under full-state-saving. |
| `-Dwebapp.stateSavingMethod=…` | `jakarta.faces.STATE_SAVING_METHOD` (default `server`). Set to `client` to run the suite under client-side state saving. |
| `-Dwebapp.serializeServerState=…` | `jakarta.faces.SERIALIZE_SERVER_STATE` (default `false`). Set to `true` to force server-state serialization. |

A handful of test modules pin a specific webapp context parameter value and ignore
the `-Dwebapp.*` flags — those carry a `DO NOT PARAMETERIZE` comment in their `web.xml`
along with reason.

The four `webapp.*` flags can be combined; for example, run the whole suite under
client-side state saving in development stage:

```bash
mvn clean install -Dwebapp.projectStage=Development -Dwebapp.stateSavingMethod=client
```

Or sweep one configuration matrix axis for a single module:

```bash
cd faces50/ajax
mvn clean install -Dwebapp.partialStateSaving=false
```

### Sizing `-T` and the pool for the host

Each parallel test module spawns a failsafe-fork JVM, drives a Chrome
instance, and pushes deploy/HTTP traffic at its leased GlassFish JVM. So
**every `-TN` slot keeps roughly three hot processes busy** (test JVM +
browser + GF), plus the Maven master and OS overhead, and each GF JVM
holds ~1 GB resident. Pick `-T` to leave both CPU and memory headroom:

| Host | RAM | Recommended `-T` |
| --- | --- | --- |
| 4-core / 8-thread laptop | 16 GB | `-T4` |
| 10-core / 20-thread workstation | 16 GB | `-T5`–`-T6` (memory-bound, see below) |
| 10-core / 20-thread workstation | 32 GB+ | `-T8` (`-T10` saturates and Selenium ajax timeouts start firing) |
| 16-core / 32-thread CI | 32 GB+ | `-T12`–`-T16` |
| 32-core / 64-thread server | 64 GB+ | `-T20`–`-T24` |

Going higher than these guidelines tends to *slow* the build on Selenium-
heavy modules: per-request latency stretches past the test's 30-second
ajax wait, modules like `faces23/ajax` start failing intermittently, and
the failure cost outweighs the parallelism gain. If you must push higher,
set `-Dfailsafe.rerunFailingTestsCount=2` to absorb pure flakes.

Each `-T` slot keeps roughly **1.5 GB resident**: a GlassFish JVM (~1 GB),
a Chrome process plus its helpers (~300 MB), and the failsafe-fork test JVM
(~256 MB). At `-TN` the build needs about `N × 1.5 GB` of test infra plus
~2 GB for Maven and the OS. Pick the **lower** of the CPU recommendation
above and `(RAM_in_GB − 4) / 1.5`. Going over that line pushes Chrome and
GlassFish into swap, CDP WebSocket round-trips start timing out at 60 s,
and the failsafe log cascades with
`WARNING: Driver postInit failed (TimeoutException); replacing with a fresh instance`.
The pool's per-test recovery absorbs single failures, but back-to-back
exhaustion will error individual ITs.

The pool grows on demand to match concurrent demand, so `-TN` naturally
caps growth at N (each test JVM only ever leases one slot). To skip the
warm-up cost of growing one slot at a time on cold builds, pre-provision
the pool with `gf.pool.size`:

```bash
mvn clean install -T8 -Dgf.pool.size=8
```

## Pool lifecycle helpers

The pool is normally stopped automatically at the end of every Maven session
by `ShutdownHookInstaller`. Two convenience scripts are provided for running the
pool independently of a build:

```bash
./start-pool.sh                                 # provision and start the pool
./start-pool.sh -Dglassfish.home=/path/to/gf    # pass through gf-pool overrides
./stop-pool.sh                                  # stop everything
```

Both scripts are idempotent: they `pkill` any slot JVMs whose `--module-path`
points into `gf-pool/target/pool/` before doing their main work. This matters
because `ShutdownHookInstaller` only fires when the Maven session that spawned
the slots exits cleanly **and** has `gf-pool` in its reactor. Running from a
sub-reactor (`cd faces22 && mvn install`) or aborting with Ctrl+C leaves the
slot JVMs alive holding their ports, which would collide with the next
bootstrap. Re-running `./start-pool.sh` reaps the stragglers automatically;
explicit `./stop-pool.sh` is only needed when you want the host released.

To watch how many surefire-forked test JVMs are running concurrently
during a build (each one leases a slot, so this is also the live slot
lease count):

```bash
watch -n 1 'pgrep -fa "java.*surefirebooter" | grep -v "/bin/sh" | wc -l'
```

### Testing local Mojarra changes in a sub-module

The Mojarra overlay is performed **once per slot** during `gf-pool` provisioning.
TCK runs that don't include `gf-pool` in the reactor (typically sub-module runs
like `cd faces50/facelets && mvn install`) do **not** re-resolve Mojarra — they
reuse whatever jar is already in the slot. Editing files under `~/git/mojarra` and
re-running the TCK sub-module alone will silently test against the stale jar and the
changes won't take effect.

To exercise local Mojarra changes end-to-end re-run from the tck root with `-am`
so `gf-pool`'s clean wipes the pool and the next bootstrap re-overlays the fresh
Mojarra jar from `~/.m2`:

```bash
cd ~/git/mojarra/impl
mvn clean install
cd ~/git/faces/tck
mvn clean install -am -pl faces50/facelets
```

## Testing against other servers (WIP)

Other Arquillian-supported servers can be selected via profile:

```bash
mvn clean install -Ppiranha-embedded-micro
mvn clean install -Ptomcat-ci-managed
```

Coverage and Mojarra-version handling for non-GlassFish servers is still
work-in-progress.

## Running tests manually

Each test module produces a plain WAR (e.g. `faces50/ajax/target/test-faces50-ajax.war`).
Deploy it to any Jakarta EE compatible server; the test code itself is documented
inline and identifies the URLs to request via a browser.
