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

import static java.lang.System.getProperty;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;

import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.util.regex.Pattern;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

/**
 * Base class for tests, which provides a certain infrastructure can be used, but does not have to be
 */
@ExtendWith(SeleniumArquilianRunner.class)
public class BaseITNG {

    @ArquillianResource
    protected URL webUrl;

    private ExtendedWebDriver webDriver;

    static DriverPool driverPool = new DriverPool();

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return create(ZipImporter.class, getProperty("finalName") + ".war")
                .importFrom(new File("target/" + getProperty("finalName") + ".war"))
                .as(WebArchive.class);
    }

  @BeforeEach
  void setUp() {
        webDriver = driverPool.getOrNewInstance();
    }

  @AfterEach
  void tearDown() {
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
        ExtendedWebDriver webDriver = getWebDriver();
        for (String windowHandle : webDriver.getWindowHandles()) {
            webDriver.switchTo().window(windowHandle);
        }
    }

    protected int getStatusCode(String page) {
        webDriver.get(webUrl.toString() + page);
        WebPage webPage = new WebPage(webDriver);
        webPage.waitForPageToLoad();

        return webPage.getResponseStatus();
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
}
