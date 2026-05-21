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
package ee.jakarta.tck.faces.util.selenium;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
     * Returns the first inline (non-{@code src}) {@code <script>} element whose textContent
     * mentions {@code input}'s id (single- or double-quoted) — i.e. the JSF client-behavior
     * script wired up to that input. Returns {@code null} if none.
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

    private List<WebElement> getBehaviorScriptElements(WebElement input) {
        var quotedId = quoted(input.getAttribute("id"));
        var elements = new ArrayList<WebElement>();
        for (var script : findElements(By.tagName("script"))) {
            var src = script.getAttribute("src");
            if (src != null && !src.isEmpty()) {
                continue;
            }
            var content = script.getDomProperty("textContent");
            if (content != null && containsAny(content, quotedId)) {
                elements.add(script);
            }
        }
        return elements;
    }

    private List<String> getBehaviorScripts(WebElement input) {
        return getBehaviorScriptElements(input).stream().map(script -> script.getDomProperty("textContent")).toList();
    }

    /**
     * Returns true if {@code element} effectively has {@code attrName} set to {@code expectedValue}.
     * For {@code on*} event-handler attributes this is implementation-agnostic per Faces 5.0
     * (jakartaee/faces#2167): the value may either be rendered as an inline {@code on*} attribute on
     * the element, or be wired at runtime via a {@code <script>} block that mentions the element's
     * id, the event name, and the expected value. For all other attributes only the inline form
     * is accepted.
     */
    public boolean hasAttributeValue(WebElement element, String attrName, String expectedValue) {
        if (Objects.equals(expectedValue, element.getDomAttribute(attrName))) {
            return true;
        }
        return expectedValue != null && isOnEventScripted(element, attrName, expectedValue);
    }

    /**
     * Returns true if {@code element} has {@code attrName} wired in any form. For {@code on*}
     * event-handler attributes this is implementation-agnostic per Faces 5.0
     * (jakartaee/faces#2167): either a non-empty inline {@code on*} attribute is present on the
     * element, or some {@code <script>} block on the page mentions the element's id and the event
     * name. For all other attributes only a non-empty inline form is accepted. Unlike
     * {@link #hasAttributeValue} this does not check the handler value.
     */
    public boolean isAttributeWired(WebElement element, String attrName) {
        var inline = element.getDomAttribute(attrName);
        if (inline != null && !inline.isEmpty()) {
            return true;
        }
        return isOnEventScripted(element, attrName, null);
    }

    /**
     * Searches inline {@code <script>} blocks wired to {@code element}'s id for one that mentions
     * the DOM event derived from {@code attrName} (the substring after {@code "on"}). If
     * {@code expectedValue} is non-null the script must additionally contain that value as a JS
     * string literal (raw or backslash-escaped, single- or double-quoted). Returns false for
     * non-{@code on*} attribute names.
     */
    private boolean isOnEventScripted(WebElement element, String attrName, String expectedValue) {
        if (!attrName.startsWith("on") || attrName.length() <= 2) {
            return false;
        }
        var quotedEvent = quoted(attrName.substring(2));
        var quotedValue = expectedValue == null ? null : quoted(expectedValue);
        for (var content : getBehaviorScripts(element)) {
            if (content == null || !containsAny(content, quotedEvent)) {
                continue;
            }
            if (quotedValue == null || containsAny(content, quotedValue)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns four search candidates for {@code s}: the raw value wrapped in single and double
     * quotes, and the JS-string-literal-escaped value (backslashes and single quotes escaped)
     * wrapped in single and double quotes. The escaped variants are necessary because impls that
     * defer {@code on*} attributes through an {@code addEventListener} chain typically emit the
     * handler script as a JS string literal whose embedded quotes must be backslash-escaped.
     */
    private static String[] quoted(String string) {
        var jsEscaped = string.replace("\\", "\\\\").replace("'", "\\'");
        return new String[] {
            "'" + string + "'", "\"" + string + "\"",
            "'" + jsEscaped + "'", "\"" + jsEscaped + "\""
        };
    }

    private static boolean containsAny(String haystack, String[] needles) {
        for (var needle : needles) {
            if (haystack.contains(needle)) {
            	return true;
            }
        }
        return false;
    }
}
