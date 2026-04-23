# TCK Migration TODO

## Status

- **Done:** `old-tck/source/src/com/sun/ts/tests/jsf/spec/` → migrated to `tck/faces20/` in 3 batches (Arquillian + Selenium, 65 IT classes, 188 tests).
- **Done:** `old-tck/source/src/com/sun/ts/tests/jsf/api/` → migrated across Waves 1-5. Destructive tests (`Application`-, `Lifecycle`-, `RenderKit`-mutating + `FactoryFinder.releaseFactories`) live in isolated WARs under `faces20/api/` with `reuseForks=false`. FactoryFinder variants live as sibling WARs under `faces20/webapp/`. Remaining failures are genuine test bugs (84 across all api modules), not cross-contamination.

## What `api/` tests (14 sub-areas)

Each old package follows the same pattern: `URLClient.java` (client runs test), `TestServlet.java` (deployed to container, invokes API and prints PASS/FAIL), `web.xml`, `build.xml`.

| Area | What it tests |
|---|---|
| `component/html/*` (25 dirs) | Getters/setters on HtmlCommandButton, HtmlInputText, HtmlDataTable, etc. |
| `component/ui*` (17 dirs) | UIComponent subclasses: UIInput, UIOutput, UIData, UIViewRoot, UISelectOne/Many, UIViewParameter, UIViewAction |
| `component/behavior/*` | ClientBehavior, AjaxBehavior, ClientBehaviorContext |
| `component/annotation` | @FacesComponent / @ResourceDependency scanning |
| `convert/*` (17 dirs) | Converter ctor + getAsObject/getAsString for Boolean, Integer, DateTime, LocalDate, BigDecimal, Enum, … |
| `validator/*` (6 dirs) | LengthValidator, LongRangeValidator, RegexValidator, BeanValidator, … |
| `model/*` (9 dirs) | ListDataModel, ArrayDataModel, ResultSetDataModel, IterableDataModel, DataModelEvent, SelectItem, SelectItemGroup |
| `event/*` (22 dirs) | SystemEvent subclasses, PhaseEvent, ActionEvent, ExceptionQueuedEvent, pre/postRender, pre/postValidate, flash events |
| `el/*` | Faces EL resolvers, CompositeComponentELResolver, ManagedBeanELResolver |
| `application/*` (17 dirs) | Application, ApplicationFactory, Resource, ResourceHandler, ViewHandler, StateManager, NavigationHandler, FacesMessage, NavigationCase, ViewExpiredException, ProtectedViewException |
| `context/*` (6 dirs) | FacesContext, ExternalContext, their factories and wrappers |
| `lifecycle/*` (3 dirs) | Lifecycle, LifecycleFactory, LifecycleWrapper |
| `view/*` (5 dirs) | ViewDeclarationLanguage, StateManagementStrategy, Location, components |
| `render/*` (3 dirs) | RenderKit, RenderKitFactory, ClientBehaviorRenderer (API-side; distinct from `spec/render` per-tag tests) |
| `flow/*` | FlowHandler |
| `factoryfinder/*` (7 dirs) | FactoryFinder resolution with META-INF, WEB-INF, @FacesConfig variants |
| `factoryfinderrelease` | `FactoryFinder.releaseFactories()` behavior |
| `facesexception` | FacesException ctors/messages |

## Approach: single WAR, Arquillian suite deployment

Same shape as the already-migrated `renderkit` module. One war, many IT classes, shared deployment. **No unit/integration split, no Maven profiles.**

- Faithful to old-tck: every old package already has `TestServlet.java` — port it into the war, add a thin `@WebServlet` mapping, and write a JUnit 5 + Arquillian IT that hits the endpoint and asserts `PASS` in the response body.
- Portability for free: `jakarta.faces:jakarta.faces-api` as `provided`. Container ships API+impl. Same war runs on GlassFish/Mojarra, WildFly/MyFaces, Liberty — the container decides the impl, not the TCK.
- One deployment across all ITs (Arquillian suite deployment, already proven in `renderkit` with 27 IT classes).

### Module layout

```
tck/faces20/api/
├── pom.xml                     # war, <finalName>test-faces20-api</finalName>
├── src/main/java/              # test servlets + TCK helper beans ported from old-tck/common
│   └── .../api/jakarta_faces/
│       ├── validator/lengthvalidator/LengthValidatorTestServlet.java
│       ├── model/selectitem/SelectItemTestServlet.java
│       ├── application/application/ApplicationTestServlet.java
│       └── …
├── src/main/webapp/
│   ├── WEB-INF/web.xml         # or @WebServlet per servlet
│   └── WEB-INF/beans.xml
└── src/test/java/              # JUnit 5 + Arquillian ITs
    └── .../api/jakarta_faces/
        ├── validator/lengthvalidator/LengthValidatorIT.java
        └── …
```

Mirror old-tck subdir names (`validator/lengthvalidator/`, `model/selectitem/`, …) inside the module to preserve 1:1 lineage and make diff/delete of old sources trivial.

### Servlet shape

One `TestServlet` per old package, each annotated `@WebServlet("/api/<area>/<name>/*")`. IT dispatches test methods via a query param: `getPage("faces/api/validator/lengthvalidator?test=lengthValidatorCtor1Test")`, assert response contains `PASS`. This matches how `HttpTCKServlet` in old-tck already dispatches by `?test=` — only the transport changes from harness-driven `invoke()` to Selenium's `WebPage`.

### Test code discipline

Test code (both servlet-side and IT-side) imports only `jakarta.faces.*` and `jakarta.el.*` — never `com.sun.faces.*` or `org.apache.myfaces.*`. Consider an ArchUnit / Maven enforcer rule to enforce.

### Port `common/` helpers

Port `old-tck/source/src/com/sun/ts/tests/jsf/common/*` (`TCKBehavior`, `TCKNavigationHandler`, `TCKResourceHandler`, `TCKSystemEvent`, `TCKELResolver`, `TCKStateManager`, `TCKViewHandler`, `TCKActionListener`, `HttpTCKServlet`, `JSFTestUtil`, …) directly into `tck/faces20/api/src/main/java/.../api/jakarta_faces/common/`. They're only used inside this one war.

## Migration order

Smallest/cleanest first to validate the pattern, then the heavy areas.

- **Wave 1 (pilot):** `facesexception` + `model/selectitem` + one `validator/lengthvalidator` — end-to-end test-servlet + IT pattern, smallest surface.
- **Wave 2:** `model/*`, `selectitem`, remaining `component/html/*`, `component/ui*`, `component/behavior/*` — bulk of the POJO-ish tests.
- **Wave 3:** `convert/*`, `validator/*`, `event/*` — API-plus-lifecycle tests.
- **Wave 4:** `application/*`, `context/*`, `lifecycle/*`, `view/*`, `render/*`, `el/*`, `flow/*`, `component/annotation` — container-backed areas.
- **Wave 5:** `factoryfinder/*` + `factoryfinderrelease` — may need distinct sub-war or separate module because each test needs its own `web.xml` / `faces-config.xml`/`META-INF` layout. Decide at the time.

## Gotchas learned on renderkit migration (carry forward)

- Old TCK's `IncludingId` assertion was substring match. Selenium tests wrapping outputs in `h:form` get prefixed IDs → use xpath ends-with, not equals.
- CDI `@Named` bean names must be unique across the whole war. Two beans sharing `@Named("status")` silently broke Weld bootstrap in renderkit (`beanManager` null in `WeldInitialListener`), which surfaced as `URLResourceProvider returned null value` for every test. Grep `@Named` across the war before merging waves.
- `mvn verify` on `tck/faces20/*` needs `-Dglassfish.home=/home/bauke/Java/glassfish9`; the local GlassFish is patched for the `com.sun.faces` → `org.glassfish.mojarra` package migration. Without it, failsafe unpacks a fresh GlassFish whose `weld-integration.jar` requires `com.sun.faces.spi 5.0.0` which no bundle exports.
- Java 21 required (class file major 65). Run `java21` (shell function) before `mvn verify`.
- Don't over-assert rendered HTML shape. The spec (see `api/src/main/renderkitdoc/standard-html-renderkit.xml`) prescribes which attributes land on which element — e.g. SelectManyCheckbox puts `style`/`styleClass` on the outer `<table>`, not on each `<input>`. Verify against renderkitdoc before copying an old assertion literally.

## Cleanup after migration complete

- Delete `tck/old-tck/source/src/com/sun/ts/tests/jsf/api/` after all waves are green.
- Delete `tck/old-tck-selenium/` (only `commandLink` left after spec/render cleanup in prior commit).
- Update `tck/old-tck/build.xml` (or remove `old-tck/` entirely if nothing else depends on it).
