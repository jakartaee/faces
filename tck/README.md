<!---
[//]: # " Copyright (c) 2021 Contributors to the Eclipse foundation. All rights reserved.
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

# Faces "new" tests

The tests in this folder assert that functionality is working as it should. Test are junit tests
that use Arquillian to start/stop a server and to deploy/undeploy. The (plain) junit tests then do
HTTP requests to the server. No Arquillian "magic" is being used, and specifically no magic in-container
transfer of test code happens. All test code runs client-side in regular junit tests.

## NOTE

This is a relatively new effort (started in Mojarra 4.0). Not all functionality is covered yet.

## Running the tests

A default run of the tests can be started using:

```bash
mvn clean install
```

This will transparently download and unzip GlassFish into the root of this `test2` folder and update it with the build
of Mojarra produced by this project. The tests are then run against this server.

### Running the tests with a specific version of Mojarra

Tests can be run with any version of Mojarra supported by GlassFish (7.x the moment):

```bash
mvn clean install -pl :childCountTest -Dmojarra.version=4.0.0
```

```bash
mvn clean install -pl :childCountTest -Dmojarra.version=4.0.5-SNAPSHOT
```

etc

### Running the tests with the supplied version of Mojarra using the default GlassFish server

Tests can be run without updating Mojarra and using the version that ships with GlassFish (7.x the moment):

```bash
mvn clean install -pl :childCountTest -Dmojarra.noupdate=true
```


### Debugging using the default GlassFish server

GlassFish can be switched to debugging mode and suspend right at the start using:

```bash
mvn clean install -Dsuspend=true
```

This will suspend the JVM running GlassFish and wait for a debug connection on port `9009`.

### Debugging a specific test

A specific test module can be tested using the normal Maven method of building a single module:

```bash
mvn -pl :childCountTest  clean install -Dsuspend=true
```

A specific test class or even test method within such test class can be done via the normal Maven failsafe way:


```bash
mvn clean install -pl :childCountTest -Dit.test=ChildCountTestIT#testChildCountTest -Dsuspend=true
```

### Testing against other server (WIP)

The tests support being run against any server for which there is an Arquillian connector available via profiles:

```bash
mvn clean install -Ppiranha-embedded-micro
```

Note: Since this is a brand new project, support for other servers, specifically updating them to use specific Faces builds is WIP.

## Running the test manually

Every test module produces a plain war. This war can be deployed to any server supporting wars, which is at least every Jakarta EE compatible server. Look at the test code
for hints on which URLs to request manually using e.g. a web browser.

# Faces "old" tests

A subset of the tests in this folder are from the "old tck", which is the TCK based on the JavaTest harness and Apache Ant. These tests are run as part of the overall TCK run. E.g. they will be executed when running: 

```bash
mvn clean install
```

## Running only the old tests

The module containing the old tests can be executed separately using the normal Maven method of building a single module:

```bash
mvn clean install -pl :old-tck-build,old-tck-run
```

When the old TCK has already been build once for this release of the TCK, subsequent invocations can be done by omitting the build step:


```bash
mvn clean install -pl :old-tck-run
```

## Running a single old tests

Executing a single old test is different from running a single new test. It is done by using the `run.test` parameter and the full URL of the test (URLs are printed after the old test executed, and can also be found in the `ts-all-platform.jtx` and `ts-all-standalone.jtx` files). 

For example:


```bash
mvn clean install -pl :old-tck-run -Drun.test="com/sun/ts/tests/jsf/api/jakarta_faces/validator/doublerangevalidator/URLClient.java#stateHolderSaveStateNPETest"
```


## Running multiple old tests

Using the `run.test.pattern` a regexp can be specified that runs all tests matching that.

For example, the following runs all component tests:

```bash
mvn clean install -pl :old-tck-run -Drun.test.pattern='^com/sun/ts/tests/jsf/api/jakarta_faces/component'
```

The following runs all tests except the component tests:


```bash
mvn clean install -pl :old-tck-run -Drun.test.pattern='^((?!com/sun/ts/tests/jsf/api/jakarta_faces/component).)*$'
```


## Debugging old tests

GlassFish can be switched to debugging mode and suspend right at the start using:

```bash
mvn clean install -glassfish.suspend=true
```

This will suspend the JVM running GlassFish and wait for a debug connection on port `9009`.











