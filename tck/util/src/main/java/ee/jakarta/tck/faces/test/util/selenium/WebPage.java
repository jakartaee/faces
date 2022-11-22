/*
 * Copyright (c) 2021 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */
package ee.jakarta.tck.faces.test.util.selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * Mimics the html unit webpage
 */
public class WebPage {

    public static final Duration STD_TIMEOUT = Duration.ofMillis(3000);

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
     * implemented helper to wait for the background javascript
     *
     * @param timeout the standard timeout to wait in case the condition is not executed
     */
    public void waitForBackgroundJavascript(Duration timeout) {
        WebDriverWait wait = new WebDriverWait(webDriver, timeout);
        double rand = Math.random();
        @SuppressWarnings("UnnecessaryLabelJS")
        final String identifier = "__insert__:" + rand;
        webDriver.manage().timeouts().scriptTimeout(timeout);
        //We use a trick here, javascript  is cooperative multitasking
        //so we defer into a time when the script is executed
        //and then check for the new element
        webDriver.getJSExecutor().executeAsyncScript("let [resolve] = arguments; setTimeout(function() { var insert__ = document.createElement('div');" +
                "insert__.id = '" + identifier + "';" +
                "insert__.innerHTML = 'done';" +
                "document.body.append(insert__); resolve()}, 0);");
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.id(identifier), 0));
        webDriver.getJSExecutor().executeScript("document.body.removeChild(document.getElementById('" + identifier + "'));");
    }

    /**
     * waits for a certain condition is met until a timeout is hit
     * in case of exceeding the condition a runtime Exception is thrown
     * @param isTrue the condition lambda to check
     * @param timeout timeout duration
     */
    public <V> void waitForCondition(Function<? super WebDriver, V> isTrue, Duration timeout) {
        WebDriverWait wait = new WebDriverWait(webDriver, timeout);
        wait.until(isTrue);
    }

    /**
     * waits until no more running requests are present
     * in case of exceeding our STD_TIMEOUT, a runtime exception is thrown
     */
    public void waitForCurrentRequestEnd() {
        waitForCondition(webDriver1 -> webDriver.getResponseStatus() != -1, STD_TIMEOUT);
    }

    /**
     * wait until the current ajax request targeting the same page
     * has stopped and then tests for blocking running scripts
     * still running.
     * A small initial delay cannot hurt either
     */
    public void waitReqJs() {
        this.waitReqJs(STD_TIMEOUT);
    }

    /**
     * same as before but with a decdicated timeout
     * @param timeout the timeout duration after that the wait is cancelled
     *                and an exceotion is thrown
     */
    public void waitReqJs(Duration timeout) {
        try {
            Thread.sleep(100);
            waitForCurrentRequestEnd();
            waitForBackgroundJavascript(timeout);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * waits for backgrounds processes on the browser to complete
     *
     * @param timeOut the timeout duration until the wait can proceed before being interupopted
     */
    public void waitForPageToLoad(Duration timeOut) {
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<>() {
            public Boolean apply(WebDriver driver) {
                return webDriver.getJSExecutor().executeScript("return document.readyState").equals("complete");
            }
        };
        try {
            WebDriverWait wait = new WebDriverWait(webDriver, timeOut);
            wait.until(expectation);
        } catch (Throwable error) {
            error.printStackTrace();
            throw new RuntimeException(error);
        }
    }

    /**
     * wait until the page load is finished
     */
    public void waitForPageToLoad() {
        waitForPageToLoad(STD_TIMEOUT);
    }

    /**
     * conditional waiter and checker which checks whether the page text is present
     * we add our own waiter internally, because pageSource always delivers
     * @param text to check
     * @return true in case of found false in case of found after our standard timeout is reached
     */
    public boolean isInPageText(String text) {
        try {
            waitForCondition(webDriver1 -> webDriver.getPageText().contains(text), STD_TIMEOUT);
            return true;
        } catch (TimeoutException ex) {
            //timeout is wanted in this case and should result in a false
            return false;
        }
    }

    public boolean isInPageTextReduced(String text) {
        try {
            waitForCondition(webDriver1 -> webDriver.getPageTextReduced().contains(text), STD_TIMEOUT);
            return true;
        } catch (TimeoutException ex) {
            //timeout is wanted in this case and should result in a false
            return false;
        }
    }

    public boolean matchesPageText(String regexp) {
        try {
            waitForCondition(webDriver1 -> webDriver.getPageText().matches(regexp), STD_TIMEOUT);
            return true;
        } catch (TimeoutException ex) {
            //timeout is wanted in this case and should result in a false
            return false;
        }
    }
    /**
     * conditional waiter and checker which checks whether a text is not in the page
     * we add our own waiter internally, because pageSource always delivers
     * @param text to check
     * @return true in case of found false in case of found after our standard timeout is reached
     */
    public boolean isNotInPageText(String text) {
        try {
            waitForCondition(webDriver1 -> !webDriver.getPageText().contains(text), STD_TIMEOUT);
            return true;
        } catch (TimeoutException ex) {
            //timeout is wanted in this case and should result in a false
            return false;
        }
    }

    /**
     * conditional waiter and checker which checks whether a text is  in the page
     * we add our own waiter internally, because pageSource always delivers
     * this version of isInPage checks explicitly the full markup not only the text
     * @param text to check
     * @return true in case of found false in case of found after our standard timeout is reached
     */
    public boolean isInPage(String text) {
        try {
            waitForCondition(webDriver1 -> webDriver.getPageSource().contains(text), STD_TIMEOUT);
            return true;
        } catch (TimeoutException ex) {
            //timeout is wanted in this case and should result in a false
            return false;
        }
    }
    /**
     * conditional waiter and checker which checks whether a text is not in the page
     * we add our own waiter internally, because pageSource always delivers
     * we need to add two different condition checkers herte because
     * a timeout automatically throws internally an error which is mapped to false
     * We therefore cannot simply wait for the condition either being met or timeout
     * with one method
     * @param text to check
     * @return true in case of found false in case of found after our standard timeout is reached
     */
    public boolean isNotInPage(String text) {
        try {
            waitForCondition(webDriver1 -> !webDriver.getPageSource().contains(text), STD_TIMEOUT);
            return true;
        } catch (TimeoutException ex) {
            //timeout is wanted in this case and should result in a false
            return false;
        }
    }

    /**
     * conditional waiter and checker which checks whether a text is in the page
     * we add our own waiter internally, because pageSource always delivers
     * we need to add two different condition checkers herte because
     * a timeout automatically throws internally an error which is mapped to false
     * We therefore cannot simply wait for the condition either being met or timeout
     * with one method
     * @param text to check
     * @return true in case of found false in case of found after our standard timeout is reached
     */
    public boolean isInPage(String text, boolean allowExceptions) {
        try {
            waitForCondition(webDriver1 -> webDriver.getPageSource().contains(text), STD_TIMEOUT);
            return true;
        } catch (TimeoutException exception) {
            if(allowExceptions) {
                throw exception;
            }
            exception.printStackTrace();
            return false;
        }
    }

    /**
     * is condition reached or not reached after until a STD_TIMEOUT is reached
     * if the timeout is exceeded the condition is not met
     * @param isTrue the isTrue condition lambda
     * @return true if it is met until STD_TIMEOUT, otherwise false
     */
    public <V> boolean isCondition(Function<? super WebDriver, V> isTrue) {
        try {
            waitForCondition(isTrue, STD_TIMEOUT);
            return true;
        } catch (TimeoutException ex) {
            //timeout is wanted in this case and should result in a false
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
     * @return a list of a hrefs as WebElements
     */
    public List<WebElement> getAnchors() {
        return webDriver.findElements(By.cssSelector("a[href]"));
    }


}
