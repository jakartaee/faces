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

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * High-level facade over {@link ExtendedWebDriver} for integration tests.
 * Adds settle/poll primitives ({@link #guardHttp}, {@link #guardAjax},
 * {@link #waitForCondition}) and assertion-friendly content checks
 * ({@link #containsText}, {@link #containsSource}, {@link #matchesText}).
 */
public class WebPage {

    static final Duration STD_TIMEOUT = Duration.ofMillis(Integer.parseInt(System.getProperty("ee.jakarta.tck.faces.timeout", "10000")));

    private ExtendedWebDriver webDriver;

    public WebPage(ExtendedWebDriver webDriver) {
        this.webDriver = webDriver;
    }

    /**
     * Polls until {@code isTrue} returns truthy or {@link #STD_TIMEOUT} elapses.
     * Throws {@link org.openqa.selenium.TimeoutException} on timeout. Tests
     * that need a non-default timeout should construct {@link WebDriverWait}
     * directly off the inherited {@code getWebDriver()}.
     */
    public <V> void waitForCondition(Function<? super WebDriver, V> isTrue) {
        new WebDriverWait(webDriver, STD_TIMEOUT).until(isTrue);
    }

    /**
     * Run {@code action} and block until the resulting non-Ajax navigation has
     * completed (i.e. the new page's {@code document.readyState} is {@code complete}).
     *
     * @param action the non-ajax action to execute, usually {@code WebElement::click}
     */
    public void guardHttp(Runnable action) {
        action.run();
        new WebDriverWait(webDriver, STD_TIMEOUT).until(
                $ -> "complete".equals(executeScript("return document.readyState")));
    }

    /**
     * wait until the current Ajax request targeting the same page has completed
     *
     * @param the ajax action to execute, usually {@code WebElement::click}
     */
    public void guardAjax(Runnable action) {
        var uuid = UUID.randomUUID().toString();
        executeScript("window.$ajax=true;"
                + "faces.ajax.addOnEvent(data=>{if(data.status=='success')window.$ajax='" + uuid + "'});"
                + "faces.ajax.addOnError(()=>window.$ajax='" + uuid + "')");
        action.run();
        webDriver.waitForFaces(STD_TIMEOUT);
        waitForCondition($ -> executeScript("return window.$ajax=='" + uuid + "' || (!window.$ajax && document.readyState=='complete')"));
    }

    /**
     * Run {@code action} and verify that it does NOT trigger an Ajax request within a short window.
     *
     * @return {@code true} if no Ajax fired (the expected case for negative-path assertions),
     *         {@code false} if an Ajax response did complete.
     */
    public boolean assertNoAjax(Runnable action) {
        executeScript("window.$ajaxFired=false;faces.ajax.addOnEvent(()=>window.$ajaxFired=true)");
        action.run();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return Boolean.FALSE.equals(executeScript("return window.$ajaxFired"));
    }

    /**
     * Returns true if the page's visible text (whitespace-collapsed) contains
     * the given text. Synchronous: assumes the caller already settled the page
     * via guardHttp, guardAjax, or getPage. If you need to wait for the text
     * to appear, use {@link #waitForCondition} explicitly.
     */
    public boolean containsText(String text) {
        return getText().contains(text);
    }

    /**
     * Returns true if the page's full HTML markup contains the given text.
     * Synchronous; same precondition as {@link #containsText}. Use this only
     * when the asserted text lives in markup-only context (HTML attributes,
     * encoded entities, script content) — for visible page content prefer
     * {@link #containsText}.
     */
    public boolean containsSource(String text) {
        return getSource().contains(text);
    }

    /**
     * Returns true if the page's visible text (whitespace-collapsed) matches
     * the given regex. Synchronous; same precondition as {@link #containsText}.
     */
    public boolean matchesText(String regex) {
        return getText().matches(regex);
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

    public JavascriptExecutor getJSExecutor() {
        return webDriver.getJSExecutor();
    }

    /**
     * Convenience shorthand for {@code getJSExecutor().executeScript(script, args)}.
     */
    public Object executeScript(String script, Object... args) {
        return webDriver.getJSExecutor().executeScript(script, args);
    }

    public String getCurrentUrl() {
        return webDriver.getCurrentUrl();
    }

    public String getTitle() {
        return webDriver.getTitle();
    }

    /**
     * Returns the full HTML markup of the current page. Escape hatch for
     * tests that need to fetch the page once and run multiple operations
     * (regex, substring, repeated lookups) on the same snapshot. For simple
     * "does X appear?" checks use {@link #containsSource}.
     */
    public String getSource() {
        return webDriver.getPageSource();
    }

    /**
     * Returns the page's visible text with all whitespace collapsed to single
     * spaces (head + body innerText, runs of whitespace and non-breaking
     * spaces normalised). Callers should prefer {@link #containsText} and {@link #matchesText}.
     */
    private String getText() {
        return webDriver.getPageTextReduced();
    }

    /**
     * Convenience method to get all anchor elmements
     *
     * @return a list of a hrefs as WebElements
     */
    public List<WebElement> getAnchors() {
        return webDriver.findElements(By.cssSelector("a[href]"));
    }

    /**
     * Returns inline (non-{@code src}) {@code <script>} elements whose textContent
     * mentions the given input's id (single- or double-quoted) — i.e. the JSF
     * client-behavior scripts wired up to that input.
     */
    public List<WebElement> getBehaviorScriptElements(WebElement input) {
        var id = input.getAttribute("id");
        var elements = new ArrayList<WebElement>();
        for (var script : findElements(By.tagName("script"))) {
            var src = script.getAttribute("src");
            if (src == null || src.isEmpty()) {
                var content = script.getDomProperty("textContent");
                if (content.contains("'" + id + "'") || content.contains("\"" + id + "\"")) {
                    elements.add(script);
                }
            }
        }
        return elements;
    }

    /**
     * Same as {@link #getBehaviorScriptElements} but returns the script bodies as strings.
     */
    public List<String> getBehaviorScripts(WebElement input) {
        return getBehaviorScriptElements(input).stream().map(script -> script.getDomProperty("textContent")).toList();
    }

    /**
     * Returns the first script element wired to {@code input}, or {@code null} if none.
     */
    public WebElement getBehaviorScriptElement(WebElement input) {
        var elements = getBehaviorScriptElements(input);
        return elements.isEmpty() ? null : elements.get(0);
    }

    /**
     * Returns the body of the first script wired to {@code input}, or {@code null} if none.
     */
    public String getBehaviorScript(WebElement input) {
        var scripts = getBehaviorScripts(input);
        return scripts.isEmpty() ? null : scripts.get(0);
    }
}
