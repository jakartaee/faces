<!---
[//]: # " Copyright (c) 2026 Contributors to the Eclipse foundation. All rights reserved. "
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

# Contributing to the Jakarta Faces TCK

Conventions every TCK test/module must follow. If a rule below is broken,
the test isn't ready to merge — fix the test, not the rule. The rules exist
because each one has burned us in the past.

> The TCK is **distributable without the API module**. Tests live under
> `tck/`, never under `api/src/test/`. Don't put TCK fixtures in the API
> module's source tree.

## 1. Where does my test go?

Pick the **lowest** Faces version where the spec/issue applies:

- New spec issue → `tck/facesNN/<module>/` matching the version it shipped in (e.g. issue #1590 shipped in Faces 4.0 → `tck/faces40/...`).
- New mojarra-impl issue → same logic, version where the fix landed.
- Adding a sibling test to an existing module? Reuse the module — same `web.xml`, `beans.xml`, and beans/companion artifacts.

## 2. Naming conventions

### Module / directory / artifactId

- `lowercase-with-hyphens`. The directory name **is** the Maven `artifactId`.
- GroupId is per Faces version: `org.eclipse.ee4j.tck.faces.facesNN`. This makes module artifactIds unambiguous across versions.

### Java packages

- Hyphens in the module name → **underscores** in the Java package, **never periods**. `ajax-inputs` → `ee.jakarta.tck.faces.faces22.ajax_inputs`. A period would imply a sub-package and reads incorrectly for a single compound concept.
- Shape: `ee.jakarta.tck.faces.facesNN.<module_snake>`. **Drop the historical `.test.` segment.** For nested submodules: `ee.jakarta.tck.faces.facesNN.<group>.<sub>` where the leaf segment matches the module's leaf name.

### Test class prefix — Spec wins over Issue

| Source | Class name | Issue link |
| --- | --- | --- |
| Spec issue (jakartaee/faces) | `Spec123IT` | `https://github.com/jakartaee/faces/issues/123` |
| Mojarra impl issue | `Issue234IT` | `https://github.com/eclipse-ee4j/mojarra/issues/234` |
| Both apply | **`Spec123IT`**; mojarra issue goes as additional `@see` | — |

A test class covers **one** ticket. If you need to cover several different ticket numbers, split into separate IT classes by ticket — don't shoe-horn unrelated `@Test` methods together.

### Companion artifacts (xhtml / bean / servlet / filter / entity / service)

- Same prefix as the test class: `Spec123IT.java` ↔ `spec123.xhtml` ↔ `Spec123Bean.java`.
- Beans use no explicit `@Named` value — let EL infer it: `#{spec123Bean}`.
- **Beans must have a scope annotation** alongside `@Named` (`@RequestScoped`, `@ViewScoped`, `@ApplicationScoped`, `@SessionScoped`, or the `@Model` stereotype which combines `@Named + @RequestScoped`). `@Named` alone makes the class *nameable* but not a *bean* under CDI 4.0+'s `bean-discovery-mode="annotated"` default — EL resolution returns null ("Target Unreachable, identifier 'spec123Bean' resolved to null") and the page fails with HTTP 500 before the test even gets a chance to interact with it.
- If the artifact is **shared** across multiple tests in the same module, prefix with the module name instead.

### Welcome-file

Don't put `index.xhtml` as a welcome-file in `web.xml` unless the test specifically exercises welcome-file behavior. Tests must `getPage("Spec123.xhtml")` explicitly, not `getPage("")`.

## 3. Module skeleton

A minimal new module looks like this:

```
faces50/my-feature/
├── pom.xml
└── src/
    ├── main/
    │   ├── java/ee/jakarta/tck/faces/faces50/my_feature/Spec123Bean.java
    │   └── webapp/
    │       ├── spec123.xhtml
    │       └── WEB-INF/
    │           ├── web.xml
    │           ├── beans.xml
    │           └── faces-config.xml      (only if needed)
    └── test/
        └── java/ee/jakarta/tck/faces/faces50/my_feature/Spec123IT.java
```

### `pom.xml`

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" ...>
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.eclipse.ee4j.tck.faces.faces50</groupId>
        <artifactId>pom</artifactId>
        <version>5.0.0-SNAPSHOT</version>
    </parent>

    <artifactId>my-feature</artifactId>
    <packaging>war</packaging>
    <name>Jakarta Faces TCK ${project.version} - ${test.moduleName} - ${project.artifactId}</name>

    <build>
        <finalName>test-${test.moduleName}-${project.artifactId}</finalName>
    </build>
</project>
```

That's it. The parent supplies dependencies, surefire/failsafe config, and the GlassFish pool wiring.

Don't forget to add `<module>my-feature</module>` to the parent `pom.xml`'s `<modules>` block.

### `web.xml` — every war must have one

Use the canonical 4 context-param block, in this exact order, parameterized:

```xml
<web-app xmlns="https://jakarta.ee/xml/ns/jakartaee" ... version="6.1">
    <context-param>
        <param-name>jakarta.faces.PROJECT_STAGE</param-name>
        <param-value>${webapp.projectStage}</param-value>
    </context-param>
    <context-param>
        <param-name>jakarta.faces.PARTIAL_STATE_SAVING</param-name>
        <param-value>${webapp.partialStateSaving}</param-value>
    </context-param>
    <context-param>
        <param-name>jakarta.faces.STATE_SAVING_METHOD</param-name>
        <param-value>${webapp.stateSavingMethod}</param-value>
    </context-param>
    <context-param>
        <param-name>jakarta.faces.SERIALIZE_SERVER_STATE</param-name>
        <param-value>${webapp.serializeServerState}</param-value>
    </context-param>
</web-app>
```

The `${webapp.*}` placeholders default to `Production`/`true`/`server`/`false` (set in the parent pom) and let CI sweep the suite under different state-saving configurations via `-Dwebapp.xxx=…`.

**No need to declare `<servlet>` for `FacesServlet` unless you need a non-default mapping** — Jakarta Faces auto-maps `*.xhtml` since 4.0. Only add explicit mapping when the test needs it.

```xml
<servlet>
    <servlet-name>facesServlet</servlet-name>
    <servlet-class>jakarta.faces.webapp.FacesServlet</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>facesServlet</servlet-name>
    <url-pattern>*.foo</url-pattern>
</servlet-mapping>
```

### `beans.xml`

```xml
<beans xmlns="https://jakarta.ee/xml/ns/jakartaee" ... version="4.1">
</beans>
```

An empty `beans.xml` (0 bytes) is also valid as a CDI marker, unless an explicit `bean-discovery-mode` is relevant to the test.

### `faces-config.xml`

**Always include one when `web.xml` has no `FacesServlet`**. The presence of `WEB-INF/faces-config.xml` is what triggers Jakarta Faces' auto-registration of `FacesServlet`. Add real content (custom factories, navigation rules, `facelets-processing`, etc.) only when the test needs it.

```xml
<faces-config xmlns="https://jakarta.ee/xml/ns/jakartaee" ... version="4.1">
</faces-config>
```

## 4. The xhtml fixture

```xhtml
<!DOCTYPE html>
<!--
    Copyright (c) Contributors to Eclipse Foundation.
    ... license header ...
-->
<html xmlns:h="jakarta.faces.html"
      xmlns:f="jakarta.faces.core"
      xmlns:ui="jakarta.faces.facelets">
    <h:head>
        <title>Spec123IT</title>
    </h:head>
    <h:body>
        <h:form id="form">
            <h:commandButton id="submit" action="#{spec123Bean.submit}" />
            <h:outputText id="status" value="#{spec123Bean.status}" />
        </h:form>
    </h:body>
</html>
```

Rules:

- **`<!DOCTYPE html>`** (HTML5). No legacy XHTML 1.0 doctypes, unless relevant to the test — they require a DTD that introduces named entities (`&nbsp;`) and renderer differences that `ScriptStyleBaseRenderer` checks.
- **No `xmlns="http://www.w3.org/1999/xhtml"`** on `<html>` — unnecessary under HTML5 + Faces 4.0.
- **No `<?xml ... ?>"`** XML prolog, unless relevant to the test.
- **No `<meta http-equiv="Content-Type">`** — Faces emits the content-type header from `<f:view contentType="…">` or by default.
- **Use Jakarta namespace URNs**: `jakarta.faces.html`, `jakarta.faces.core`, `jakarta.faces.facelets`, `jakarta.faces.composite`, `jakarta.faces.composite/<lib>`, `jakarta.tags.core`. **Not** `http://java.sun.com/jsf/*` or `http://xmlns.jcp.org/jsf/*` — those are only used in `tck/faces40/...spec1553.xhtml`, which specifically tests legacy-namespace backwards compatibility.

## 5. The test class

```java
package ee.jakarta.tck.faces.faces50.my_feature;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.faces.application.ResourceHandler;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Spec123IT extends BaseITNG {

    /**
     * @see ResourceHandler#getCurrentNonce
     * @see https://github.com/jakartaee/faces/issues/123
     */
    @Test
    void submitsForm() {
        WebPage page = getPage("spec123.xhtml");
        page.guardHttp(page.findElement(By.id("form:submit"))::click);
        assertEquals("done", page.findElement(By.id("form:status")).getText());
    }
}
```

Rules:

- **Always extend `BaseITNG`** — it wires Arquillian deploy/undeploy, the GlassFish pool slot lease, the Chrome WebDriver pool, and a one-shot warm-up GET to the deployed app's root.
- **Per `@Test` javadoc must include**:
  - `@see <issue URL>` — the GitHub issue (spec or mojarra impl).
  - `@see <API artifact>` — `jakarta.faces.*` class/method exercised. Use an impl-class `@see` (`org.glassfish.mojarra.*`) only when no API artifact applies (rare — usually internal Facelets bits).
- **Always `getPage("Spec123.xhtml")` explicitly**, not `getPage("")`. We don't rely on welcome-files.
- **Assert on an explicit result element, not on whole-page text.** Render the outcome the test cares about into a dedicated component with a fixed `id` — typically `<h:outputText id="status" value="#{bean.status}"/>` — then assert `page.findElement(By.id("form:status")).getText()` equals the expected value. This pins the assertion to one known element, so it can't accidentally pass on text that happens to appear elsewhere on the page (a label, another field, a stale value) and it fails with a clear "expected X but was Y" instead of a bare `false`. Put the value the test verifies into the bean and surface it through that element.
- Fall back to scanning page text **only** when the result genuinely can't be pinned to one element — e.g. a framework-generated `h:messages` entry, the dev-stage error page, or a raw markup/attribute/entity check. Then use the right `WebPage` helper:
  - `containsText(s)` — visible text only (`body.innerText`). Use this for what the user sees.
  - `containsSource(s)` — full HTML markup. Use this for content inside hidden DOM (e.g. inside the dev-stage error page's collapsed stack trace), HTML attributes, encoded entities, or script bodies. Prefer this over `getSource().contains(s)`; reserve `getSource()` for fetching one snapshot you then run several checks against.
  - `matchesText(regex)` — visible text against a regex.
  - `guardHttp(action)` — runs a non-ajax click and waits for full navigation.
  - `guardAjax(action)` — runs a click and waits for the matching ajax response to finish processing.
  - `guardNoAjax(action)` — asserts the action does NOT trigger ajax.
  - `waitForCondition(predicate)` — explicit poll with the standard timeout.

## 6. When a test pins a context-param value

Some tests genuinely need a specific configuration and must ignore `-Dwebapp.xxx`. Hardcode the value and explain why in a `DO NOT PARAMETERIZE` comment immediately above the `<context-param>`. Pattern:

```xml
<!-- DO NOT PARAMETERIZE PROJECT_STAGE, Spec1568IT asserts dev-stage error-page behavior -->
<context-param>
    <param-name>jakarta.faces.PROJECT_STAGE</param-name>
    <param-value>Development</param-value>
</context-param>
```

The comment must:

1. Begin with `DO NOT PARAMETERIZE <PARAM_NAME>` so a `grep` over the corpus finds it.
2. Name the specific test class (or classes) that depend on the value.
3. Briefly say why a different value would break the test.

Common reasons we pin:

- `PROJECT_STAGE=Development` — exercises dev-only validators (e.g. `f:validateWholeBean` precondition checks) or asserts the Mojarra dev error page.
- `PROJECT_STAGE=Production` — asserts the spec default (`Application.getProjectStage()` returning `Production` when no override is configured) or refresh-period production semantics.
- `PARTIAL_STATE_SAVING=true` — exercises `<ui:repeat>` row-state preservation, ajax+commandLink-in-repeat, or composite-component re-render. These are textbook FSS corner cases (FSS itself is deprecated in Faces 4.1).
- `STATE_SAVING_METHOD=server`/`client` — the test specifically validates that state-saving mode, **or** the test implicitly depends on the HTTP session existing.

## 7. Before you push

1. `mvn clean install -am -pl <your-module> -Dit.test=Spec123IT` passes locally.
2. The module also passes when the user cycles through the four `webapp.*` flags; if it doesn't, either fix the test or pin the param with a `DO NOT PARAMETERIZE` comment per section 6.
3. New `web.xml` has all four canonical context-params; new `pom.xml` has the standard parent + `<name>` + `<finalName>`.
4. New xhtml fixtures use HTML5 doctype, Jakarta URNs, fixed IDs on NamingContainers, and numeric entity refs.
5. New test class extends `BaseITNG`, has per-method `@see` javadoc, and uses `getPage("Spec123.xhtml")` explicitly.
6. Assertions target an explicit result element by id (`findElement(By.id(...)).getText()`), not whole-page `containsText`/`containsSource`, except where the result genuinely can't be pinned to one element.
