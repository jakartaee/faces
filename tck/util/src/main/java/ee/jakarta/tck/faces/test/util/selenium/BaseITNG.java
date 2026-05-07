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
import java.net.http.HttpClient;
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
        String url = webUrl.toString() + page;
        try {
            webDriver.get(url);
        } catch (WebDriverException ex) {
            // CDP session can die between @BeforeEach and the first navigation
            // (Chrome crash, paged-out, dropped WebSocket). DriverPool only retries
            // on postInit failure; here we cover the post-postInit gap.
            webDriver = driverPool.replace(webDriver, "navigation failed (" + ex.getClass().getSimpleName() + ")");
            webDriver.postInit();
            webDriver.get(url);
        }
        WebPage webPage = new WebPage(webDriver);
        PageFactory.initElements(webDriver, this);
        return webPage;
    }

    protected int getStatusCode(String page) {
        webDriver.get(webUrl.toString() + page);
        return new WebPage(webDriver).getResponseStatus();
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

}
