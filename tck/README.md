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
each failsafe-forked test JVM. The pool lives under `target/pool/` of the
project Maven runs in, next to the staged dist in `target/dist/`.

- `glassfish-pool-maven-plugin` (sibling of OmniFish's other Arquillian
  GlassFish containers, in
  [arquillian-container-glassfish](https://github.com/OmniFish-EE/arquillian-container-glassfish)):
  declared as a build extension and bound to `initialize` per test module via
  the parent profile. The extension shares one pool across all modules of the
  build, forwards the pool layout to failsafe, and stops the pool once the
  build ends. On the first call it stages the dist (resolves the GlassFish
  zip from Maven Central, unpacks it under `target/dist/glassfish9`,
  overlays the Mojarra build under test into its `modules/`), then clones
  that install into `slot-N/` directories (hardlinked via
  `Files.createLink`, the `cp -al` equivalent), patches each slot's port
  range, and starts the initial slot's domain. Its `up()` is JVM-wide
  synchronized + idempotent, so concurrent test modules under `-TN`
  converge on one provisioning. The staged dist carries a fingerprint of
  the dist, the overlays and the `poolSource`, so the unpack only re-runs
  when one of those changes, e.g. after a `mvn install` of Mojarra.
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
  the plugin's extension forwards to failsafe). Concurrent demand naturally
  caps growth at the running `-T` value (each test JVM only ever leases one
  slot), so a single `mvn -Dit.test=…` boots one server,
  `mvn clean verify -T4` grows to four, `mvn clean verify -T16` grows to
  sixteen.
- Shutdown: the extension stops every slot after the build, also when the
  build failed. `glassfish-pool:up` also arms a JVM shutdown hook in the
  Maven process, which stops the slots on `Ctrl+C`.

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

A single test module — just `cd` into it and run `mvn`. The pool and the
staged dist then live in that module's own `target/`:

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
| `-Dgf.pool.size=N` | Pre-provision N slots at build time instead of the default 1. Every module waits at `initialize` until all N are up, so a full build is slower than with on-demand growth. |
| `-Dglassfish.home=/path/to/glassfish9` | Skip the GlassFish download; clone slots from the existing install at the given path. The Mojarra overlay still applies (writes `mojarra.jar` into `${glassfish.home}/glassfish/modules/`); combine with `-Dmojarra.noupdate=true` to skip that too. |
| `-DskipTests` / `-Dmaven.test.skip` | Skip the entire pool lifecycle (dist unpack + Mojarra overlay + slot bootstrap), in addition to skipping tests. Use when you only want a clean compile/package without paying the pool cost. |
| `-Dmojarra.version=X.Y.Z` | Test against a specific Mojarra release/snapshot. |
| `-Dmojarra.noupdate=true` | Skip the Mojarra overlay; test the build that ships with GlassFish. Set `-Dsigtest.api.version=` to match the API GlassFish ships. |
| `-Dee.jakarta.tck.faces.timeout=N` | Selenium wait timeout in milliseconds (default `10000`). Bump on old, slow, or overloaded systems where the default 10 s isn't enough for HTTP navigations or ajax requests to settle, e.g. `-Dee.jakarta.tck.faces.timeout=30000` for 30 s. |
| `-Dwebapp.projectStage=…` | `jakarta.faces.PROJECT_STAGE` for every test webapp (default `Production`). Set to `Development` to exercise dev-stage code paths. |
| `-Dwebapp.stateSavingMethod=…` | `jakarta.faces.STATE_SAVING_METHOD` (default `server`). Set to `client` to run the suite under client-side state saving. |
| `-Dwebapp.serializeServerState=…` | `jakarta.faces.SERIALIZE_SERVER_STATE` (default `false`). Set to `true` to force server-state serialization. |

A handful of test modules pin a specific webapp context parameter value and ignore
the `-Dwebapp.*` flags — those carry a `DO NOT PARAMETERIZE` comment in their `web.xml`
along with reason.

The three `webapp.*` flags can be combined; for example, run the whole suite under
client-side state saving in development stage:

```bash
mvn clean verify -Dwebapp.projectStage=Development -Dwebapp.stateSavingMethod=client
```

Or sweep one configuration matrix axis for a single module:

```bash
cd faces50/ajax
mvn clean verify -Dwebapp.serializeServerState=true
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

The pool grows on demand to match concurrent demand, so `-TN` naturally caps growth at N (each test JVM only ever leases one slot).
On-demand growth overlaps the build: slot 1 boots while the first modules compile, and the remaining slots boot in parallel while those modules package.
Pre-provisioning with `-Dgf.pool.size=N` does not overlap: the pool comes up at `initialize`, and every module waits there until all N slots are up.
So leave `gf.pool.size` at its default for a full build.

## Pool lifecycle helpers

The pool comes up automatically at the start of any test build and is
stopped after the build. For ad-hoc lifecycle control there are direct
goals on `glassfish-pool-maven-plugin`:

```bash
mvn -N glassfish-pool:up        # stage the dist, boot a slot, stop on exit
mvn -N glassfish-pool:status    # live top-style table (idx, port, alive, leased)
mvn -N glassfish-pool:provision -Dgf.pool.slot=N   # add slot N to a running pool
mvn -N glassfish-pool:down      # stop every slot's domain
mvn -N glassfish-pool:nuke      # stop + wipe the pool dir entirely
```

`status`, `provision` and `down` act on a running pool, which exists only
while a test build runs. Run them from a second shell during that build,
in the same directory.

`-N` (non-recursive) keeps the goal from firing once per module — it only
needs to run on the reactor root, since the pool dir and the staged dist
default to the `target/` of the project Maven runs in.

`glassfish-pool:up` resolves the GlassFish dist + Mojarra overlay from
Maven, unpacks them under `target/dist/`, and provisions slots:

```bash
mvn -N glassfish-pool:up

# Or skip the download entirely with an existing install:
mvn -N glassfish-pool:up -Dglassfish.home=/path/to/glassfish9
```

The slots a standalone `up` starts stop when that `mvn` run exits, so it
only stages the dist and checks that a slot boots. Only a `kill -9` of the
Maven process can leave slot JVMs orphaned. If that happens (rare),
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

The staged dist carries a fingerprint of the Mojarra jar it overlays (its
size and modification time). After a `mvn install` of Mojarra, the next TCK
build stages the dist afresh and clones the slots from it, whether it runs
from the root, with `-pl`, or inside a module:

```bash
cd ~/git/mojarra/impl
mvn clean install
cd ~/git/faces/tck
mvn clean verify -am -pl faces23/cdi -Dit.test=Issue4551IT -Dfailsafe.failIfNoSpecifiedTests=false
```

`up` reuses slots that are still running as they are. A pool outlives its
build only when Maven was killed, so run `mvn -N glassfish-pool:nuke`
before testing a new jar against such a pool.

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
