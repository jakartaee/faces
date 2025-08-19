/*
 * Copyright (c) 2023, 2025 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the Apache License, Version 2.0
 * which is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GPL-2.0 with Classpath-exception-2.0 which
 * is available at https://openjdk.java.net/legal/gplv2+ce.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0 or Apache-2.0
 */
package ee.jakarta.tck.faces.test.util.selenium;

import java.io.OutputStream;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Credentials;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Pdf;
import org.openqa.selenium.ScriptKey;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumNetworkConditions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v139.network.Network;
import org.openqa.selenium.devtools.v139.network.model.Headers;
import org.openqa.selenium.devtools.v139.network.model.MonotonicTime;
import org.openqa.selenium.devtools.v139.network.model.Request;
import org.openqa.selenium.devtools.v139.network.model.RequestId;
import org.openqa.selenium.devtools.v139.network.model.ResourceType;
import org.openqa.selenium.devtools.v139.network.model.ResponseReceived;
import org.openqa.selenium.devtools.v139.network.model.TimeSinceEpoch;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.logging.EventType;
import org.openqa.selenium.print.PrintOptions;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.CommandPayload;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.ErrorHandler;
import org.openqa.selenium.remote.FileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.virtualauthenticator.VirtualAuthenticator;
import org.openqa.selenium.virtualauthenticator.VirtualAuthenticatorOptions;

import static ee.jakarta.tck.faces.test.util.selenium.WebPage.STD_TIMEOUT;
import static java.util.Comparator.comparing;
import static java.util.Comparator.naturalOrder;
import static java.util.Comparator.nullsFirst;
import static java.util.Optional.empty;
import static java.util.function.Predicate.not;
import static java.util.logging.Level.FINE;
import static java.util.logging.Level.FINEST;
import static java.util.logging.Level.INFO;
import static java.util.logging.Level.WARNING;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

/**
 * Extended driver which we need for getting the http response code and the http response without having to revert to proxy solutions
 *
 * <p>
 * We need access top the response body and response code from always the last access
 *
 * <p>
 * We use the chrome dev tools to access the data but we isolate the new functionality in an interface, so other drivers must apply something different to get
 * the results
 *
 * @see also https://medium.com/codex/selenium4-a-peek-into-chrome-devtools-92bca6de55e0
 */
public class ChromeDevtoolsDriver extends RemoteWebDriver implements ExtendedWebDriver {

    private static final Logger LOG = Logger.getLogger(ChromeDevtoolsDriver.class.getName());
    private static final Comparator<HttpCycleData> RESPONSE_TIME_COMPARATOR = comparing(HttpCycleData::getResponseTime,
        nullsFirst(naturalOrder()));

    /**
     * We only want the cdp version warning once, now matter how often the driver is called if not wanted at all the
     * selenium webdriver version must match the browser version
     */
    private static AtomicBoolean firstLog = new AtomicBoolean(Boolean.TRUE);

    private ChromeDriver delegate;
    private ChromeDriverWait facesContentWait;
    private ConcurrentLinkedQueue<HttpCycleData> cycleData = new ConcurrentLinkedQueue<>();
    private ReentrantLock cycleDataWriteLock = new ReentrantLock();
    private boolean firstRequest = true;
    private String lastGet;

    public static ExtendedWebDriver stdInit() {
        Locale.setDefault(new Locale("en", "US"));
        initCDPVersionMessageFilter();

        ChromeOptions options = new ChromeOptions();

        String chromedriverVersion = System.getProperty("chromedriver.version");
        if (chromedriverVersion != null && !chromedriverVersion.isEmpty()) {
            options.setBrowserVersion(chromedriverVersion);
        }

        // We can turn on a visual browser by
        // adding chromedriver.headless = false to our properties
        // default is headless = true
        String headless = System.getProperty("chromedriver.headless");
        if (headless == null || "true".equals(headless)) {
            options.addArguments("--headless");
        }

        options.addArguments("--no-sandbox");
        options.addArguments("--disable-web-security");
        options.addArguments("--allow-insecure-localhost");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--ignore-urlfetcher-cert-requests");
        if (Boolean.getBoolean("chromedriver.auto-open-devtools-for-tabs")) {
            LOG.log(WARNING, "The --auto-open-devtools-for-tabs can cause test instability.");
            options.addArguments("--auto-open-devtools-for-tabs");
        }
        options.addArguments("--disable-gpu");
        options.setExperimentalOption("prefs", Map.of("intl.accept_languages", "en"));
        options.addArguments("--lang=en");

        ExtendedWebDriver driver = new ChromeDevtoolsDriver(options);
        driver.manage().timeouts().implicitlyWait(STD_TIMEOUT);
        driver.manage().timeouts().pageLoadTimeout(STD_TIMEOUT);
        driver.manage().timeouts().scriptTimeout(STD_TIMEOUT);

        return driver;
    }

    private static void initCDPVersionMessageFilter() {
        Logger logger = Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder");

        logger.setFilter(record -> {
            // report the match warning only once, this suffices
            boolean isMatchWarning = record.getMessage().contains("Unable to find an exact match for CDP version");
            if (isMatchWarning && !firstLog.get()) {
                return false;
            }
            if (isMatchWarning) {
                firstLog.set(false);
            }
            return true;
        });
    }

    public ChromeDevtoolsDriver(ChromeOptions options) {
        ChromeDriverService chromeDriverService = new ChromeDriverService.Builder().build();

        chromeDriverService.sendOutputTo(OutputStream.nullOutputStream());
        delegate = new ChromeDriver(chromeDriverService, options);
        facesContentWait = new ChromeDriverWait(delegate, STD_TIMEOUT, Duration.ofMillis(100L))
            .ignoring(WebDriverException.class);
        setCommandExecutor(delegate.getCommandExecutor());
    }

    /**
     * Initializes the extended functionality
     */
    @Override
    public void postInit() {
        DevTools devTools = getDevTools();

        // We always store the last request for further reference
        initNetworkListeners(devTools);
        initDevTools(devTools);
        disableCache(devTools);
    }

    private static void disableCache(DevTools devTools) {
        devTools.send(Network.setCacheDisabled(true));
    }

    private static void initDevTools(DevTools devTools) {
        try {
            devTools.createSession();
            devTools.send(Network.clearBrowserCache());
        } catch (TimeoutException ex) {
            LOG.warning("Init timeout error, can happen, if the driver already has been used, can be safely ignore");
        }
        devTools.send(Network.enable(empty(), empty(), empty(), empty()));
    }

    private void initNetworkListeners(DevTools devTools) {
        devTools.addListener(Network.requestWillBeSent(), request -> {
            if (isIgnored(request.getType().orElse(null))) {
                return;
            }
            cycleDataWriteLock.lock();
            try {
                LOG.log(INFO, () -> "Recording request: " + reflectionToString(request));
                HttpCycleData data = findOrCreate(request.getRequestId());
                data.request = request.getRequest();
                cycleData.add(data);
            } finally {
                cycleDataWriteLock.unlock();
            }
        });

        // Sometimes response event comes even before request event.
        devTools.addListener(Network.responseReceived(), response -> {
            if (isIgnored(response.getType())) {
                return;
            }
            cycleDataWriteLock.lock();
            try {
                LOG.log(INFO, () -> "Recording response: " + reflectionToString(response));
                HttpCycleData data = findOrCreate(response.getRequestId());
                data.responseReceived = response;
                cycleData.add(data);
            } finally {
                cycleDataWriteLock.unlock();
            }
        });
    }

    /**
     * Interesting for us are just {@link ResourceType#XHR} and {@link ResourceType#DOCUMENT} types.
     */
    private boolean isIgnored(ResourceType resourceType) {
        return resourceType != ResourceType.XHR && resourceType != ResourceType.DOCUMENT;
    }

    private HttpCycleData findOrCreate(RequestId requestId) {
        return cycleData.stream().filter(data -> data.isRequest(requestId)).findFirst()
            .orElse(new HttpCycleData(requestId));
    }

    public Capabilities getCapabilities() {
        return delegate.getCapabilities();
    }

    public void setFileDetector(FileDetector detector) {
        delegate.setFileDetector(detector);
    }

    public <X> void onLogEvent(EventType<X> kind) {
        delegate.onLogEvent(kind);
    }

    public void register(Predicate<URI> whenThisMatches, Supplier<Credentials> useTheseCredentials) {
        delegate.register(whenThisMatches, useTheseCredentials);
    }

    public void launchApp(String id) {
        delegate.launchApp(id);
    }

    @Override
    protected Response execute(CommandPayload payload) {
        // Session can change, there's a command for that.
        setSessionId(delegate.getSessionId().toString());
        Response response = super.execute(payload);
        if (DriverCommand.CLICK_ELEMENT.equals(payload.getName())) {
            LOG.log(FINEST, "Some element click was executed, waiting for Faces for sure ...");
            waitForFaces(STD_TIMEOUT);
            LOG.log(FINEST, "Waiting finished.");
        }
        return response;
    }

    public Map<String, Object> executeCdpCommand(String commandName, Map<String, Object> parameters) {
        return delegate.executeCdpCommand(commandName, parameters);
    }

    public Optional<DevTools> maybeGetDevTools() {
        return delegate.maybeGetDevTools();
    }

    public List<Map<String, String>> getCastSinks() {
        return delegate.getCastSinks();
    }

    public String getCastIssueMessage() {
        return delegate.getCastIssueMessage();
    }

    public void selectCastSink(String deviceName) {
        delegate.selectCastSink(deviceName);
    }

    public void startDesktopMirroring(String deviceName) {
        delegate.startDesktopMirroring(deviceName);
    }

    public void startTabMirroring(String deviceName) {
        delegate.startTabMirroring(deviceName);
    }

    public void stopCasting(String deviceName) {
        delegate.stopCasting(deviceName);
    }

    public void setPermission(String name, String value) {
        delegate.setPermission(name, value);
    }

    public ChromiumNetworkConditions getNetworkConditions() {
        return delegate.getNetworkConditions();
    }

    public void setNetworkConditions(ChromiumNetworkConditions networkConditions) {
        delegate.setNetworkConditions(networkConditions);
    }

    public void deleteNetworkConditions() {
        delegate.deleteNetworkConditions();
    }

    public SessionId getSessionId() {
        return delegate.getSessionId();
    }

    public ErrorHandler getErrorHandler() {
        return delegate.getErrorHandler();
    }

    public void setErrorHandler(ErrorHandler handler) {
        delegate.setErrorHandler(handler);
    }

    public CommandExecutor getCommandExecutor() {
        return delegate.getCommandExecutor();
    }

    @Override
    public void get(String url) {
        LOG.log(INFO, "Opening URL {0}", url);
        int questionMark = url.indexOf('?');
        lastGet = questionMark > 0 ? url.substring(0, questionMark) : url;
        if (firstRequest) {
            firstRequest = false;
            getAndWaitForWindowAndFaces(url, Duration.ofSeconds(60));
        } else {
            getAndWaitForFaces(url, Duration.ofSeconds(10));
        }
    }

    /**
     * Navigates the current window to the url and waits until the retrieved page is settled down,
     * which means all additional XHR requests were processed.
     * If the window was switched somehow, tries to find the right window again.
     *
     * @param url target link
     * @param timeout time to wait.
     */
    protected void getAndWaitForWindowAndFaces(String url, Duration timeout) {
        WebDriverWait waitForWindow = new WebDriverWait(delegate, timeout, Duration.ofSeconds(5));
        WebDriverWait waitForJs = new WebDriverWait(delegate, Duration.ofSeconds(10L), Duration.ofMillis(10));
        waitForWindow.until(d -> {
            try {
                d.get(url);
            } catch (NoSuchWindowException e) {
                switchToWindowWithUrl(lastGet);
                return false;
            }
            try {
                waitForJs.until(e -> !cycleData.isEmpty());
            } catch (TimeoutException e) {
                // Will reload the page again
                return false;
            }
            return true;
        });
        LOG.log(FINEST, "Communication with Faces started!");
        waitForFaces(timeout);
    }

    /**
     * Navigates the current window to the url and waits until the retrieved page is settled down,
     * which means all additional XHR requests were processed.
     *
     * @param url target link
     * @param timeout time to wait.
     */
    protected void getAndWaitForFaces(String url, Duration timeout) {
        delegate.get(url);
        waitForFaces(timeout);
    }

    @Override
    public void waitForFaces(Duration timeout) {
        Duration pause = Duration.ofMillis(100);
        // Some tests click a button and immediately check the result.
        // As they probably produced a XHR request immediately,
        // we should allow it to make it to the cycleData.
        try {
            Thread.sleep(pause.toMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        ChromeDriverWait wait = new ChromeDriverWait(delegate, timeout, pause);
        wait.until(d -> {
            if (cycleData.isEmpty()) {
                LOG.log(FINEST, "Waiting for communication with Faces server ...");
                return false;
            }
            HttpCycleData data = getLastGetData();
            LOG.log(FINEST, "LastGetData: {0}", data);
            if (data == null) {
                return false;
            }
            JavascriptExecutor jsExecutor = getJSExecutor();
            String jsCommand = "return document.readyState";
            Object result = jsExecutor.executeScript(jsCommand);
            LOG.log(FINE, () -> jsCommand + " returned " + result);
            // See JavaScript specifications for readyState values
            return "complete".equals(result);
        });
    }

    @Override
    public void switchToWindowWithUrl(String url) {
        for (String handle : getWindowHandles()) {
            delegate.switchTo().window(handle);
            if (delegate.getCurrentUrl().equals(url)) {
                LOG.log(INFO, "Switched to open window with the url: {0}", delegate.getCurrentUrl());
                return;
            }
        }
        for (String handle : getWindowHandles()) {
            delegate.switchTo().window(handle);
            if (delegate.getCurrentUrl().startsWith("http")) {
                LOG.log(WARNING, "Switched to random window using any http url. URL: {0}", delegate.getCurrentUrl());
                return;
            }
        }
        throw new IllegalStateException("Failed to find usable window.");
    }

    @Override
    public String getTitle() {
        return delegate.getTitle();
    }

    @Override
    public String getCurrentUrl() {
        return delegate.getCurrentUrl();
    }

    @Override
    public WebElement findElement(By locator) {
        return facesContentWait.until(driver -> {
            RemoteWebElement element = (RemoteWebElement) driver.findElement(locator);
            // element.getText will use our execute method.
            // This is a temporary workaround for Spec1263IT and others which doesn't wait
            // after click() until the element is redrawn.
            element.setParent(this);
            return element;
        });
    }

    @Override
    public List<WebElement> findElements(By locator) {
        return facesContentWait.until(driver -> driver.findElements(locator));
    }

    @Override
    public String getPageSource() {
        return facesContentWait.until(driver -> driver.getPageSource());
    }

    @Override
    public String getPageText() {
        return facesContentWait.until(driver -> {
            String head = driver.findElement(By.tagName("head")).getAttribute("innerText").replaceAll("[\\s\\n ]", " ");
            String body = driver.findElement(By.tagName("body")).getAttribute("innerText").replaceAll("[\\s\\n ]", " ");
            return head + " " + body;
        });
    }

    @Override
    public String getPageTextReduced() {
        return facesContentWait.until(driver -> {
            String head = driver.findElement(By.tagName("head")).getAttribute("innerText");
            String body = driver.findElement(By.tagName("body")).getAttribute("innerText");
            // handle blanks and nbsps
            return (head + " " + body).replaceAll("[\\s\\u00A0]+", " ");
        });
    }

    /**
     * resets the current tab and gives it a clean slate that way we do not have to build up the entire tab again
     */
    @Override
    public void reset() {
        DevTools devTools = delegate.getDevTools();
        devTools.clearListeners();
        devTools.send(Network.clearBrowserCookies()); // just to be sure w clear out all cookies
        devTools.disconnectSession(); // This kills off the existing session

        cycleData.clear();
        lastGet = null;
        firstRequest = false;
    }

    /**
     * closes the current tab for good, this has been problematic when the last or only tab was closes for recycling apparently we run into dev tools timeouts
     * then hence reset now is the preferred way to recycle tabs instead of using close
     */
    @Override
    public void close() {
        reset();
        delegate.close();
    }

    /**
     * quits the driver entirely
     */
    @Override
    public void quit() {
        delegate.quit();
    }

    @Override
    public Set<String> getWindowHandles() {
        return delegate.getWindowHandles();
    }

    @Override
    public String getWindowHandle() {
        return delegate.getWindowHandle();
    }

    @Override
    public TargetLocator switchTo() {
        return delegate.switchTo();
    }

    @Override
    public Navigation navigate() {
        return delegate.navigate();
    }

    @Override
    public Options manage() {
        return delegate.manage();
    }

    @Override
    public int getResponseStatus() {
        HttpCycleData data = getLastGetData();
        if (data == null || !data.hasResponse()) {
            return -1;
        }
        return data.responseReceived.getResponse().getStatus();
    }

    @Override
    public String getResponseBody() {
        HttpCycleData data = getLastGetData();
        if (data == null) {
            return null;
        }
        return delegate.getDevTools().send(Network.getResponseBody(data.requestId)).getBody();
    }

    @Override
    public String getRequestData() {
        HttpCycleData data = getLastGetData();
        if (data == null) {
            return null;
        }
        return data.request.getPostData().orElse("");
    }

    public <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException {
        return delegate.getScreenshotAs(outputType);
    }

    public Pdf print(PrintOptions printOptions) throws WebDriverException {
        return delegate.print(printOptions);
    }

    public List<WebElement> findElements(SearchContext context, BiFunction<String, Object, CommandPayload> findCommand, By locator) {
        return facesContentWait.until(driver -> driver.findElements(context, findCommand, locator));
    }

    public Object executeScript(String script, Object... args) {
        return delegate.executeScript(script, args);
    }

    public Object executeAsyncScript(String script, Object... args) {
        return delegate.executeAsyncScript(script, args);
    }

    public void setLogLevel(Level level) {
        delegate.setLogLevel(level);
    }

    public void perform(Collection<Sequence> actions) {
        delegate.perform(actions);
    }

    public void resetInputState() {
        delegate.resetInputState();
    }

    public VirtualAuthenticator addVirtualAuthenticator(VirtualAuthenticatorOptions options) {
        return delegate.addVirtualAuthenticator(options);
    }

    public void removeVirtualAuthenticator(VirtualAuthenticator authenticator) {
        delegate.removeVirtualAuthenticator(authenticator);
    }

    public FileDetector getFileDetector() {
        return delegate.getFileDetector();
    }

    public ScriptKey pin(String script) {
        return delegate.pin(script);
    }

    public void unpin(ScriptKey key) {
        delegate.unpin(key);
    }

    public Set<ScriptKey> getPinnedScripts() {
        return delegate.getPinnedScripts();
    }

    public Object executeScript(ScriptKey key, Object... args) {
        return delegate.executeScript(key, args);
    }

    public void register(Supplier<Credentials> alwaysUseTheseCredentials) {
        delegate.register(alwaysUseTheseCredentials);
    }

    public DevTools getDevTools() {
        return delegate.getDevTools();
    }

    public String[] getRequestDataAsArray() {
        String requestData = getRequestData();
        String[] splitData = requestData.split("&");

        return Stream.of(splitData).map((String keyVal) -> URLDecoder.decode(keyVal, StandardCharsets.UTF_8)).toArray(String[]::new);
    }

    private HttpCycleData getLastGetData() {
        if (lastGet == null) {
            return null;
        }

        return cycleData.stream().filter(item -> item.hasBaseUrl(lastGet)).sorted(RESPONSE_TIME_COMPARATOR.reversed())
            .findFirst().orElse(null);
    }

    @Override
    public void printProcessedResponses() {
        cycleData.stream().filter(HttpCycleData::hasResponse).sorted(RESPONSE_TIME_COMPARATOR)
                // Missing last api
                .forEach(item -> {
                    System.out.println("Url: " + item.request.getUrl());
                    System.out.println("RequestId: " + item.requestId.toJson());
                    Optional<TimeSinceEpoch> responseTime = item.responseReceived.getResponse().getResponseTime();
                    System.out.println("ResponseTime: " + responseTime.orElse(new TimeSinceEpoch(-1)));
                    System.out.println("ResponseStatus: " + item.responseReceived.getResponse().getStatus());
                });
    }

    @Override
    public ChromeDriver getDelegate() {
        return delegate;
    }

    @Override
    public JavascriptExecutor getJSExecutor() {
        return this;
    }

    @Override
    public void addRequestHeader(String name, String value) {
        getDevTools().send(Network.setExtraHTTPHeaders(new Headers(Map.of(name, value))));
    }

    private class ChromeDriverWait extends FluentWait<ChromeDriver> {
        ChromeDriverWait(ChromeDriver input, Duration timeout, Duration pause) {
            super(input);
            withTimeout(timeout);
            pollingEvery(pause);
        }

        public <V> V until(Function<? super ChromeDriver, V> isTrue) {
            // Block if there is anything what could result in changes.
            super.until(d -> !isCommunicationInProgress());
            return super.until(isTrue);
        }

        public ChromeDriverWait ignoring(Class<? extends Throwable> exceptionType) {
            return ignoreAll(List.of(exceptionType));
        }

        public <K extends Throwable> ChromeDriverWait ignoreAll(Collection<Class<? extends K>> types) {
            super.ignoreAll(types);
            return this;
        }

        private boolean isCommunicationInProgress() {
            Optional<HttpCycleData> incomplete = cycleData.stream().filter(not(HttpCycleData::hasResponse)).findAny();
            if (incomplete.isPresent()) {
                LOG.log(FINE, "Still waiting for response for {0}.", incomplete.get());
                return true;
            }
            return false;
        }
    }
}

class HttpCycleData {
    public final RequestId requestId;
    public Request request;
    public ResponseReceived responseReceived;

    public HttpCycleData(RequestId requestId) {
        this.requestId = requestId;
    }

    public boolean isRequest(RequestId id) {
        return requestId.toJson().equals(id.toJson());
    }

    public boolean hasBaseUrl(String urlBase) {
        String url = request == null ? null : request.getUrl();
        return url != null && url.startsWith(urlBase);
    }

    public boolean hasResponse() {
        return responseReceived != null;
    }

    public Integer getResponseTime() {
        if (responseReceived == null) {
            return null;
        }
        MonotonicTime timestamp = responseReceived.getTimestamp();
        if (timestamp == null) {
            return null;
        }
        return timestamp.toJson().intValue();
    }

    @Override
    public String toString() {
        return reflectionToString(this);
    }
}
