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

import static java.lang.Boolean.parseBoolean;
import static java.lang.System.getProperty;
import static java.net.URI.create;
import static java.net.http.HttpClient.newHttpClient;
import static java.net.http.HttpRequest.newBuilder;
import static java.net.http.HttpResponse.BodyHandlers.ofString;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.jupiter.api.extension.ConditionEvaluationResult.disabled;
import static org.junit.jupiter.api.extension.ConditionEvaluationResult.enabled;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.exception.UncheckedException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

/**
 * Use this for Selenium based tests.
 */
@ExtendWith(ArquillianExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public abstract class BaseITNG implements ExecutionCondition {

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        if (parseBoolean(System.getProperty("test.selenium"))) {
            return enabled("Test enabled because 'test.selenium' system property is set to 'true'");
        }

        return disabled("Test disabled because 'test.selenium' system property is not set to 'true'");
    }

    @ArquillianResource
    protected URL webUrl;

    private ExtendedWebDriver webDriver;

    protected static final DriverPool driverPool = new DriverPool();

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return create(ZipImporter.class, getProperty("finalName") + ".war").importFrom(new File("target/" + getProperty("finalName") + ".war"))
                .as(WebArchive.class);
    }

    @BeforeEach
    void setUp() {
        webDriver = driverPool.getOrNewInstance();
    }

    @AfterEach
    protected void tearDown() {
        driverPool.returnInstance(webDriver);
    }

    @AfterAll
    static void afterAll() {
        driverPool.quitAll();
    }

    protected WebPage getPage(String page) {
        webDriver.get(webUrl.toString() + page);
        WebPage webPage = new WebPage(webDriver);
        // Sometimes it takes longer until the first page is loaded after container startup
        webPage.waitForPageToLoad(Duration.ofSeconds(120));
        PageFactory.initElements(webDriver, this);
        return webPage;
    }

    /**
     * Selenium does not automatically update the page handles if a link is clicked without ajax
     */
    protected void updatePage() {
        webDriver.switchToWindowWithUrl(webDriver.getCurrentUrl());
    }

    protected int getStatusCode(String page) {
        webDriver.get(webUrl.toString() + page);
        WebPage webPage = new WebPage(webDriver);
        webPage.waitForPageToLoad();

        return webPage.getResponseStatus();
    }

    protected String getResponseBody(String resource) {
        try {
            return newHttpClient().send(newBuilder(create(webUrl + resource)).build(), ofString()).body();
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(e);
        }
        catch (IOException e) {
            throw new UncheckedException(e);
        }
    }

    /**
     * Send a GET to {@code resource} with the given request headers (including, if desired, a Cookie header)
     * and return the response body as a String. Useful for tests that exercise request headers, cookies, or
     * similar metadata.
     */
    protected String getResponseBody(String resource, java.util.Map<String, String> headers) {
        try {
            var builder = newBuilder(create(webUrl + resource));
            if (headers != null) {
                for (var entry : headers.entrySet()) {
                    builder.header(entry.getKey(), entry.getValue());
                }
            }
            return newHttpClient().send(builder.build(), ofString()).body();
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(e);
        }
        catch (IOException e) {
            throw new UncheckedException(e);
        }
    }

    /**
     * Send a GET to {@code resource} WITHOUT following redirects and return the 'Location' header (null if absent).
     * Useful for tests that verify a 3xx redirect target.
     */
    protected String getResponseLocation(String resource) {
        try {
            java.net.http.HttpClient client = java.net.http.HttpClient.newBuilder()
                    .followRedirects(java.net.http.HttpClient.Redirect.NEVER).build();
            java.net.http.HttpResponse<String> response = client.send(
                    newBuilder(create(webUrl + resource)).build(), ofString());
            return response.headers().firstValue("Location").orElse(null);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(e);
        }
        catch (IOException e) {
            throw new UncheckedException(e);
        }
    }

    public ExtendedWebDriver getWebDriver() {
        return webDriver;
    }

    protected String getHrefURI(WebElement link) {
        String uri = link.getAttribute("href").substring(webUrl.toExternalForm().length());
        String uriWithoutJsessionId = uri.split(";jsessionid=", 2)[0];
        String[] uriAndQueryString = uri.split(Pattern.quote("?"), 2);

        if (uriAndQueryString.length == 2) {
            uriWithoutJsessionId += "?" + uriAndQueryString[1];
        }

        return uriWithoutJsessionId;
    }

    protected String getContextPath() {
        return webUrl.getPath().replaceAll("/$", "");
    }

    protected List<WebElement> getBehaviorScriptElements(WebPage page, WebElement input) {
        var id = input.getAttribute("id");
        var elements = new ArrayList<WebElement>();

        for (var script : page.findElements(By.tagName("script"))) {
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

    protected List<String> getBehaviorScripts(WebPage page, WebElement input) {
        return getBehaviorScriptElements(page, input).stream().map(script -> script.getDomProperty("textContent")).toList();
    }

    protected WebElement getBehaviorScriptElement(WebPage page, WebElement input) {
        var elements = getBehaviorScriptElements(page, input);
        return elements.isEmpty() ? null : elements.get(0);
    }

    protected String getBehaviorScript(WebPage page, WebElement input) {
        var scripts = getBehaviorScripts(page, input);
        return scripts.isEmpty() ? null : scripts.get(0);
    }
}
