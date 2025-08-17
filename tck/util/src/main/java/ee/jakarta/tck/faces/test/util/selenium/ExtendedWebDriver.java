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

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/**
 * an extended web driver interface which takes the response into consideration selenium does not have an official api
 * but several webdrivers allow access to the data via various means
 *
 * Another possibility would have been a proxy, but I could not find any properly working proxy for selenium
 */
public interface ExtendedWebDriver extends WebDriver {

    /**
     * gets the response status of the last response (of the last triggered request against get)
     *
     * @return
     */
    int getResponseStatus();

    /**
     * gets the last response as body
     *
     * @return
     */
    String getResponseBody();

    /**
     * @return the request post data as string
     */
    String getRequestData();

    /**
     * postinit call for tests
     */
    void postInit();

    /**
     * gets the internal webdriver delegate
     *
     * @return
     */
    WebDriver getDelegate();

    /**
     * returns a reference to the Selenium JS Executor of this webdriver
     *
     * @return
     */
    JavascriptExecutor getJSExecutor();

    /**
     * @return the innerText of the Page
     */
    String getPageText();

    /**
     * returns the innerText of the page in a blank reduced state (more than one blank is reduced to one invisible blanks
     * like nbsp are replaced by normal blanks)
     *
     * @return
     */
    String getPageTextReduced();

    /**
     * debugging helper which allows to look into the processed response data
     */
    void printProcessedResponses();


    /**
     * Waits for until the communication with Faces server is completed.
     *
     * @param timeout
     */
    void waitForFaces(Duration timeout);

    /**
     * Takes current windows handles and switches the driver to the one with the given url.
     * If such window was not found, result is undefined.
     *
     * @param url
     */
    void switchToWindowWithUrl(String url);

    /**
     * quits the driver engine
     */
    @Override
    void quit();

    /**
     * closes the current tab
     */
    @Override
    void close();

    /**
     * resets the current page to its initial stage before the page load without dropping the engine or closing it
     */
    void reset();
}
