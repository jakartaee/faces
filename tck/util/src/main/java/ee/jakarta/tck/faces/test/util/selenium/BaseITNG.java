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

import static java.lang.Boolean.parseBoolean;
import static java.lang.System.getProperty;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.jupiter.api.extension.ConditionEvaluationResult.disabled;
import static org.junit.jupiter.api.extension.ConditionEvaluationResult.enabled;

import java.io.File;
import java.net.URL;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.openqa.selenium.WebElement;

/**
 * Use this for Selenium based tests.
 */
@ExtendWith(ArquillianExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public abstract class BaseITNG implements ExecutionCondition {

    private static final Logger logger = Logger.getLogger(BaseITNG.class.getName());

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
    void afterAll() {
        driverPool.quitAll();
    }

    protected WebPage getPage(String page) {
        return getPage(page, 0);
    }

    private WebPage getPage(String page, int retryAttempt) {
        String existingWindowHandle = webDriver.getWindowHandle();
        String url = webUrl.toString() + page;
        webDriver.get(url);

        if (!webDriver.getWindowHandles().contains(existingWindowHandle)) {
            updatePage();
        }

        WebPage webPage = new WebPage(webDriver);
        webPage.waitForPageToLoad();

        if (webPage.isEmpty()) {
            // For some reason the page is sometimes completely empty even when webPage.waitForPageToLoad() returns document.readyState==complete.
            // Most likely some weird bug in Chrome driver. For now simply retry (max 6 times).
            Level level = retryAttempt++ < 3 ? Level.FINE : Level.WARNING;
            logger.log(level, "Empty page returned?! Retry attempt #" + retryAttempt + " on " + url);
            
            if (retryAttempt < 6) {
                return getPage(page, retryAttempt);
            }
        }

        return webPage;
    }

    /**
     * Selenium does not automatically update the window handles if a link is clicked without ajax
     */
    protected void updatePage() {
        Set<String> windowHandles = webDriver.getWindowHandles();

        if (windowHandles.isEmpty()) {
            throw new IllegalStateException("No browser windows available");
        }

        webDriver.switchTo().window(windowHandles.iterator().next());
    }

    protected int getStatusCode(String page) {
        return getPage(page).getResponseStatus();
    }

    public ExtendedWebDriver getWebDriver() {
        return webDriver;
    }

    protected String getHrefURI(WebElement link) {
        String uri = link.getDomProperty("href").substring(webUrl.toExternalForm().length());
        String uriWithoutJsessionId = uri.split(";jsessionid=", 2)[0];
        String[] uriAndQueryString = uri.split(Pattern.quote("?"), 2);

        if (uriAndQueryString.length == 2) {
            uriWithoutJsessionId += "?" + uriAndQueryString[1];
        }

        return uriWithoutJsessionId;
    }
}
