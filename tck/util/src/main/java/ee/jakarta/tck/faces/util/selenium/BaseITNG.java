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
import java.io.UncheckedIOException;
import java.net.URL;
import java.net.http.HttpClient;
import java.util.regex.Pattern;

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
import org.openqa.selenium.WebDriverException;
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
    
    private static final HttpClient HTTP = newHttpClient();

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return create(ZipImporter.class, getProperty("finalName") + ".war").importFrom(new File("target/" + getProperty("finalName") + ".war"))
                .as(WebArchive.class);
    }

    private boolean warmedUp;

    @BeforeEach
    void setUp() {
        warmUpOnce();
    }

    /**
     * Lazily acquires a pooled WebDriver the first time a test actually drives the
     * browser. HTTP-only tests (those using only {@link #getResponseBody}) never
     * trigger acquisition, so they skip Chrome/CDP session setup entirely.
     */
    private ExtendedWebDriver webDriver() {
        if (webDriver == null) {
            webDriver = driverPool.getOrNewInstance();
        }
        return webDriver;
    }

    /**
     * One cheap GET to the deployed app's root before the first test runs, to pay
     * first-request lazy-init tax (resource scan, view-handler init,
     * dev-mode validators) up front instead of on whichever @Test happens to run
     * first. Especially relevant under PROJECT_STAGE=Development where the first
     * ajax round-trip can otherwise exceed Selenium's wait timeout.
     */
    private void warmUpOnce() {
        if (warmedUp) {
            return;
        }
        warmedUp = true;
        try {
            HTTP.send(newBuilder(create(webUrl.toString())).build(), ofString());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (IOException ignore) {
            // Warm-up is best-effort; a non-200 (404 on welcome file, ConnectException
            // mid-deploy, etc.) is fine — the side effect of touching the app is what
            // matters.
        }
    }

    @AfterEach
    protected void tearDown() {
        if (webDriver != null) {
            driverPool.returnInstance(webDriver);
            webDriver = null;
        }
    }

    /**
     * Discards the current test's WebDriver (quit and removed from the pool) instead
     * of returning it for reuse. For tests whose per-test driver state cannot be
     * cleared by {@link DriverPool#returnInstance} — e.g. additive request headers.
     */
    protected void discardWebDriver() {
        if (webDriver != null) {
            driverPool.quitInstance(webDriver);
            webDriver = null;
        }
    }

    @AfterAll
    static void afterAll() {
        driverPool.quitAll();
    }

    protected WebPage getPage(String page) {
        ExtendedWebDriver driver = webDriver();
        String url = webUrl.toString() + page;
        try {
            driver.get(url);
        } catch (WebDriverException ex) {
            // CDP session can die between acquisition and the first navigation
            // (Chrome crash, paged-out, dropped WebSocket). DriverPool only retries
            // on postInit failure; here we cover the post-postInit gap.
            driver = webDriver = driverPool.replace(driver, "navigation failed (" + ex.getClass().getSimpleName() + ")");
            driver.postInit();
            driver.get(url);
        }
        WebPage webPage = new WebPage(driver);
        PageFactory.initElements(driver, this);
        return webPage;
    }

    protected int getStatusCode(String page) {
        ExtendedWebDriver driver = webDriver();
        driver.get(webUrl.toString() + page);
        return new WebPage(driver).getResponseStatus();
    }

    protected String getResponseBody(String resource) {
        try {
            return HTTP.send(newBuilder(create(webUrl + resource)).build(), ofString()).body();
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(e);
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
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
            throw new UncheckedIOException(e);
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
            throw new UncheckedIOException(e);
        }
    }

    public ExtendedWebDriver getWebDriver() {
        return webDriver();
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

}
