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
shared **pool**, leased per-module by an Arquillian extension running inside
each failsafe-forked test JVM. The pool lives entirely under `target/pool/` at
the reactor root and is wiped by `mvn clean`.

- `glassfish-pool-maven-plugin` (sibling of OmniFish's other Arquillian
  GlassFish containers, in
  [arquillian-container-glassfish](https://github.com/OmniFish-EE/arquillian-container-glassfish)):
  bound to `initialize` per test module via the parent profile. On the first
  call it stages the dist (resolves the GlassFish zip from Maven Central,
  unpacks it under `target/dist/glassfish9`, overlays the Mojarra build
  under test into its `modules/`), then clones that install into `slot-N/`
  directories (hardlinked via `Files.createLink`, the `cp -al` equivalent),
  patches each slot's port range, and starts the initial slot's domain. Its
  `up()` is JVM-wide synchronized + idempotent and the dist unpack is
  marker-guarded by GAV, so concurrent test modules under `-TN` converge on
  one provisioning and the unpack only re-runs when the dist version
  changes.
- `arquillian-glassfish-server-pool` (the runtime jar, on every test
  module's classpath via the `glassfish-ci-managed` profile): an Arquillian
  `LoadableExtension` whose `DeployableContainer` acquires a `FileLock` on
  `slot-N/lock` at container start, reads the slot's published
  `ports.properties`, configures the deploy, and holds the lock for the JVM
  lifetime (released automatically on JVM exit). Also exposes the slot
  index as the `gf.pool.slot` system property so the test ProgressListener
  can tag output with `[SLOTn]`.
- Grow-on-demand: if every existing slot is leased, the leaser provisions
  another slot from the test JVM (using the `gf.pool.source` system property
  the failsafe config forwards). Concurrent demand naturally caps growth
  at the running `-T` value (each test JVM only ever leases one slot), so a
  single `mvn -Dit.test=…` boots one server, `mvn clean verify -T4` grows to
  four, `mvn clean verify -T16` grows to sixteen.
- Shutdown hook: `glassfish-pool:up` arms a JVM shutdown hook in the Maven
  process that stops every running slot at session end — both on normal
  completion and on `Ctrl+C`.

## Running the tests

Full suite, sequentially:

```bash
mvn clean verify
```

To actually exercise the pool's parallelism, pass `-TN`:

```bash
mvn clean verify -T4
```

The pool starts with one slot and grows on demand to match concurrent
demand, so `-T4` ramps to four slots and `-T8` to eight. Pick `-T` based
on host capacity.

A single test module — just `cd` into it and run `mvn`. The `.mvn/`
marker at the tck root pins `${maven.multiModuleProjectDirectory}`
there, so the pool dir resolves to the same shared location even when
Maven is invoked from a subdirectory:

```bash
cd faces50/ajax
mvn clean verify -T4
```

A whole faces version — same idea, descend into the aggregator:

```bash
cd faces50
mvn clean verify -T4
```

A single test class or method:

```bash
cd faces50/ajax
mvn clean verify -Dit.test=Issue5594IT
mvn clean verify -Dit.test=Issue5594IT#someMethod
```

If you'd rather stay at the tck root, the `-pl` form still works
(use `-am` to also pull in upstream `util`):

```bash
mvn clean verify -am -pl faces50/ajax -T4
mvn clean verify -am -pl faces50/ajax -Dit.test=Issue5594IT -Dfailsafe.failIfNoSpecifiedTests=false
```

Note: `-Dfailsafe.failIfNoSpecifiedTests=false` is required whenever you
combine `-am` with `-Dit.test=…`, because failsafe fails the build by
default if the test filter matches nothing in any module — and upstream
modules like `util` carry no integration tests.

Against an existing GlassFish install (skips the zip download; that
install becomes the pool's clone source — the Mojarra build under test
is overlaid into `${glassfish.home}/glassfish/modules/` and every slot
is `cp -al`-cloned from it, with `applications/`, `generated/`,
`logs/` cleared per slot). Add `-Dmojarra.noupdate=true` to also skip
the overlay:

```bash
mvn clean verify -T4 -Dglassfish.home=/path/to/glassfish
```

## Common overrides

| Flag | Effect |
| --- | --- |
| `-T1` | Sequential build, single slot. This is Maven's default when `-T` is omitted. |
| `-T8` | Eight Maven threads. Pool grows on demand to match. |
| `-Dgf.pool.size=N` | Pre-provision N slots at build time instead of the default 1. |
| `-Dglassfish.home=/path/to/glassfish9` | Skip the GlassFish download; clone slots from the existing install at the given path. The Mojarra overlay still applies (writes `mojarra.jar` into `${glassfish.home}/glassfish/modules/`); combine with `-Dmojarra.noupdate=true` to skip that too. |
| `-DskipTests` / `-Dmaven.test.skip` | Skip the entire pool lifecycle (dist unpack + Mojarra overlay + slot bootstrap), in addition to skipping tests. Use when you only want a clean compile/package without paying the pool cost. |
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
mvn clean verify -Dwebapp.projectStage=Development -Dwebapp.stateSavingMethod=client
```

Or sweep one configuration matrix axis for a single module:

```bash
cd faces50/ajax
mvn clean verify -Dwebapp.partialStateSaving=false
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
mvn clean verify -T8 -Dgf.pool.size=8
```

## Pool lifecycle helpers

The pool comes up automatically at the start of any test build and is
stopped by a JVM shutdown hook at session end. For ad-hoc lifecycle
control there are direct goals on `glassfish-pool-maven-plugin`:

```bash
mvn -N glassfish-pool:up        # provision and start the pool
mvn -N glassfish-pool:status    # live top-style table (idx, port, alive, leased)
mvn -N glassfish-pool:provision -Dgf.pool.slot=N   # add slot N to a running pool
mvn -N glassfish-pool:down      # stop every slot's domain
mvn -N glassfish-pool:nuke      # stop + wipe the pool dir entirely
```

`-N` (non-recursive) keeps the goal from firing once per module — it only
needs to run on the reactor root, since the pool dir, source, and port
layout are all resolved from root-level properties.

`glassfish-pool:up` resolves the GlassFish dist + Mojarra overlay from
Maven, unpacks them under `target/dist/`, and provisions slots:

```bash
mvn -N glassfish-pool:up

# Or skip the download entirely with an existing install:
mvn -N glassfish-pool:up -Dglassfish.home=/path/to/glassfish9
```

The shutdown hook fires when the Maven session that spawned the slots exits.
Aborting with Ctrl+C still triggers it; only a `kill -9` of the Maven
process itself can leave slot JVMs orphaned. If that happens (rare),
`mvn -N glassfish-pool:nuke` reaps everything and resets the pool dir.

The plugin prefix `glassfish-pool:` resolves out of the box from any
directory in this repo because the parent pom registers the plugin in
`<pluginManagement>`. Outside this repo, add `ee.omnifish.arquillian` to
`<pluginGroups>` in `~/.m2/settings.xml` or use the long form
`mvn ee.omnifish.arquillian:glassfish-pool-maven-plugin:<goal>`.

To watch how many surefire-forked test JVMs are running concurrently
during a build (each one leases a slot, so this is also the live slot
lease count):

```bash
watch -n 1 'pgrep -fa "java.*surefirebooter" | grep -v "/bin/sh" | wc -l'
```

### Testing local Mojarra changes in a sub-module

The Mojarra jar is hardlinked into each slot at pool provisioning time. TCK
runs where the pool is already up reuse whatever jar is already in the slot.
Editing files under `~/git/mojarra` and re-running a TCK sub-module against a
still-running pool will silently test against the stale jar.

`mvn clean verify` from the TCK **root** picks up the fresh Mojarra
automatically: the root pom is in the reactor, so `clean` wipes
`<root>/target/` — including `target/pool` and `target/dist` — and the
next build re-overlays + re-clones from scratch. The cost is that you
build and test every module.

```bash
cd ~/git/mojarra/impl
mvn clean install
cd ~/git/faces/tck
mvn clean verify -am -pl faces50/facelets
```

The fast iteration path is `mvn clean verify -am -pl <module>`, which
restricts the reactor to that module + its upstream deps. The TCK root
pom is **not** in this reactor (`-am` pulls in dependency modules, not
the parent), so `clean` doesn't touch `<root>/target` and the existing
slots — hardlinked to the *old* Mojarra jar — survive. `pool-up`'s
"is the pool already healthy?" fast-path then skips re-cloning, so the
sub-reactor build silently uses the stale jar. To force a refresh, run
`glassfish-pool:nuke` first to wipe `target/pool`:

```bash
mvn -N glassfish-pool:nuke
mvn clean verify -am -pl faces23/cdi -Dit.test=Issue4551IT -Dfailsafe.failIfNoSpecifiedTests=false
```

## Testing against other servers (WIP)

Other Arquillian-supported servers can be selected via profile:

```bash
mvn clean verify -Ppiranha-embedded-micro
mvn clean verify -Ptomcat-ci-managed
```

Coverage and Mojarra-version handling for non-GlassFish servers is still
work-in-progress.

## Running tests manually

Each test module produces a plain WAR (e.g. `faces50/ajax/target/test-faces50-ajax.war`).
Deploy it to any Jakarta EE compatible server; the test code itself is documented
inline and identifies the URLs to request via a browser.
