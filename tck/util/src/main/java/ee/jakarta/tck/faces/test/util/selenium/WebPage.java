/*
 * Copyright (c) 2023 Contributors to the Eclipse Foundation.
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

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Mimics the html unit webpage
 */
public class WebPage {

    public static final Duration STD_TIMEOUT = Duration.ofMillis(8000);
    public static final Duration LONG_TIMEOUT = Duration.ofMillis(16000);

    protected ExtendedWebDriver webDriver;

    public WebPage(ExtendedWebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public ExtendedWebDriver getWebDriver() {
        return webDriver;
    }

    public void setWebDriver(ExtendedWebDriver webDriver) {
        this.webDriver = webDriver;
    }

    /**
     * waits for a certain condition is met, until a timeout is hit. In case of exceeding the condition, a runtime exception
     * is thrown!
     *
     * @param isTrue the condition lambda to check
     * @param timeout timeout duration
     */
    public <V> void waitForCondition(Function<? super WebDriver, V> isTrue, Duration timeout) {
        synchronized (webDriver) {
            WebDriverWait wait = new WebDriverWait(webDriver, timeout);
            wait.until(isTrue);
        }
    }

    /**
     * The same as before, but with the long default timeout of LONG_TIMEOUT (16000ms)
     *
     * @param isTrue condition lambda
     */
    public <V> void waitForCondition(Function<? super WebDriver, V> isTrue) {
        synchronized (webDriver) {
            WebDriverWait wait = new WebDriverWait(webDriver, LONG_TIMEOUT);
            wait.until(isTrue);
        }
    }

    /**
     * Wait for a certain period of time
     *
     * @param timeout the timeout to wait (note due to the asynchronous nature of the web drivers, any code running on the
     * browser itself will proceed (aka javascript) only the client operations are stalled.
     */
    public void wait(Duration timeout) {
        synchronized (webDriver) {
            try {
                webDriver.wait(timeout.toMillis());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * wait until the current non-Ajax request targeting the same page has completed
     * 
     * @param the non-ajax action to execute, usually {@code WebElement::click}
     */
    public void guardHttp(Runnable action) {
        action.run();
        waitForPageToLoad();
    }

    /**
     * wait until the current Ajax request targeting the same page has completed
     * 
     * @param the ajax action to execute, usually {@code WebElement::click}
     */
    public void guardAjax(Runnable action) {
        var uuid = UUID.randomUUID().toString();
        executeScript("window.$ajax=true;faces.ajax.addOnEvent(data=>{if(data.status=='complete')window.$ajax='" + uuid + "'})");
        action.run();
        waitForCondition($ -> executeScript("return window.$ajax=='" + uuid + "' || (!window.$ajax && document.readyState=='complete')"));
    }

    /**
     * waits for backgrounds processes on the browser to complete
     *
     * @param timeOut the timeout duration until the wait can proceed before being interupopted
     */
    public void waitForPageToLoad(Duration timeOut) {
        synchronized (webDriver) {
            waitForCondition($ -> executeScript("return document.readyState=='complete'"), timeOut);
        }
    }

    /**
     * Returns true if the document.body is completely empty or if an exception has been thrown which indicates that the page is unusable somehow.
     */
    protected boolean isEmpty() {
        try {
            return findElement(By.tagName("body")) == null || (boolean) executeScript("return !document.body.innerHTML.length");
        } catch (NoSuchElementException | NoSuchWindowException | UnreachableBrowserException theseExceptionsAlsoIndicateEmptyPage) {
            return true;
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T executeScript(String script) {
        return (T) webDriver.getJSExecutor().executeScript(script);
    }

    /**
     * wait until the page load is finished
     */
    public void waitForPageToLoad() {
        waitForPageToLoad(STD_TIMEOUT);
    }

    /**
     * conditional waiter and checker which checks whether the page text is present we add our own waiter internally,
     * because pageSource always delivers
     *
     * @param text to check
     * @return true in case of found false in case of found after our standard timeout is reached
     */
    public boolean isInPageText(String text) {
        try {
            // values are not returned by getPageText
            String values = getInputValues();

            waitForCondition(webDriver1 -> (webDriver.getPageText() + values).contains(text), STD_TIMEOUT);
            return true;
        } catch (TimeoutException ex) {
            // timeout is wanted in this case and should result in a false
            return false;
        }
    }

    public boolean isInPageTextReduced(String text) {
        try {
            String values = getInputValues();
            waitForCondition(webDriver1 -> (webDriver.getPageTextReduced() + values.replaceAll("\\s+", " ")).contains(text), STD_TIMEOUT);
            return true;
        } catch (TimeoutException ex) {
            // timeout is wanted in this case and should result in a false
            return false;
        }
    }

    public boolean matchesPageText(String regexp) {
        try {
            waitForCondition(webDriver1 -> webDriver.getPageText().matches(regexp), STD_TIMEOUT);
            return true;
        } catch (TimeoutException ex) {
            // timeout is wanted in this case and should result in a false
            return false;
        }
    }

    /**
     * adds the reduced page text functionality to the regexp match
     *
     * @param regexp
     * @return
     */
    public boolean matchesPageTextReduced(String regexp) {
        try {
            waitForCondition(webDriver1 -> webDriver.getPageTextReduced().matches(regexp), STD_TIMEOUT);
            return true;
        } catch (TimeoutException ex) {
            // timeout is wanted in this case and should result in a false
            return false;
        }
    }

    /**
     * conditional waiter and checker which checks whether a text is not in the page we add our own waiter internally,
     * because pageSource always delivers
     *
     * @param text to check
     * @return true in case of found false in case of found after our standard timeout is reached
     */
    public boolean isNotInPageText(String text) {
        try {
            String values = getInputValues();
            waitForCondition(webDriver1 -> !(webDriver.getPageText() + values).contains(text), STD_TIMEOUT);
            return true;
        } catch (TimeoutException ex) {
            // timeout is wanted in this case and should result in a false
            return false;
        }
    }

    /**
     * conditional waiter and checker which checks whether a text is in the page we add our own waiter internally, because
     * pageSource always delivers this version of isInPage checks explicitly the full markup not only the text
     *
     * @param text to check
     * @return true in case of found false in case of found after our standard timeout is reached
     */
    public boolean isInPage(String text) {
        try {
            String values = getInputValues();
            waitForCondition(webDriver1 -> (webDriver.getPageSource() + values).contains(text), STD_TIMEOUT);
            return true;
        } catch (TimeoutException ex) {
            // timeout is wanted in this case and should result in a false
            return false;
        }
    }

    /**
     * conditional waiter and checker which checks whether a text is not in the page we add our own waiter internally,
     * because pageSource always delivers we need to add two different condition checkers herte because a timeout
     * automatically throws internally an error which is mapped to false We therefore cannot simply wait for the condition
     * either being met or timeout with one method
     *
     * @param text to check
     * @return true in case of found false in case of found after our standard timeout is reached
     */
    public boolean isNotInPage(String text) {
        try {
            String values = getInputValues();
            waitForCondition(webDriver1 -> !(webDriver.getPageSource() + values).contains(text), STD_TIMEOUT);
            return true;
        } catch (TimeoutException ex) {
            // timeout is wanted in this case and should result in a false
            return false;
        }
    }

    /**
     * conditional waiter and checker which checks whether a text is in the page we add our own waiter internally, because
     * pageSource always delivers we need to add two different condition checkers herte because a timeout automatically
     * throws internally an error which is mapped to false We therefore cannot simply wait for the condition either being
     * met or timeout with one method
     *
     * @param text to check
     * @return true in case of found false in case of found after our standard timeout is reached
     */
    public boolean isInPage(String text, boolean allowExceptions) {
        try {
            String values = getInputValues();
            waitForCondition(webDriver1 -> (webDriver.getPageSource() + values).contains(text), STD_TIMEOUT);
            return true;
        } catch (TimeoutException exception) {
            if (allowExceptions) {
                throw exception;
            }
            exception.printStackTrace();
            return false;
        }
    }

    /**
     * is condition reached or not reached after until a STD_TIMEOUT is reached if the timeout is exceeded the condition is
     * not met
     *
     * @param isTrue the isTrue condition lambda
     * @return true if it is met until STD_TIMEOUT, otherwise false
     */
    public <V> boolean isCondition(Function<? super WebDriver, V> isTrue) {
        try {
            waitForCondition(isTrue, STD_TIMEOUT);
            return true;
        } catch (TimeoutException ex) {
            // timeout is wanted in this case and should result in a false
            return false;
        }
    }

    public WebElement findElement(By by) {
        return webDriver.findElement(by);
    }

    public List<WebElement> findElements(By by) {
        return webDriver.findElements(by);
    }

    public int getResponseStatus() {
        return webDriver.getResponseStatus();
    }

    public String getResponseBody() {
        return webDriver.getResponseBody();
    }

    public String getRequestData() {
        return webDriver.getRequestData();
    }

    public void postInit() {
        webDriver.postInit();
    }

    public JavascriptExecutor getJSExecutor() {
        return webDriver.getJSExecutor();
    }

    public void printProcessedResponses() {
        webDriver.printProcessedResponses();
    }

    public void get(String url) {
        webDriver.get(url);
    }

    public String getCurrentUrl() {
        return webDriver.getCurrentUrl();
    }

    public String getTitle() {
        return webDriver.getTitle();
    }

    public String getPageSource() {
        return webDriver.getPageSource();
    }

    public void close() {
        webDriver.close();
    }

    public void quit() {
        webDriver.quit();
    }

    public Set<String> getWindowHandles() {
        return webDriver.getWindowHandles();
    }

    public String getWindowHandle() {
        return webDriver.getWindowHandle();
    }

    public WebDriver.TargetLocator switchTo() {
        return webDriver.switchTo();
    }

    public WebDriver.Navigation navigate() {
        return webDriver.navigate();
    }

    public WebDriver.Options manage() {
        return webDriver.manage();
    }

    /**
     * Convenience method to get all anchor elmements
     *
     * @return a list of a hrefs as WebElements
     */
    public List<WebElement> getAnchors() {
        return webDriver.findElements(By.cssSelector("a[href]"));
    }

    private String getInputValues() {
        return webDriver.findElements(By.cssSelector("input, textarea, select")).stream()
                .map(webElement -> webElement.getDomProperty("value")).reduce("", (str1, str2) -> str1 + " " + str2);
    }
}
