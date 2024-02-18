/*
 * Copyright (c) 2021, 2022 Contributors to the Eclipse Foundation.
 * Copyright (c) 1997, 2021 Oracle and/or its affiliates. All rights reserved.
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
package ee.jakarta.tck.faces.test.javaee8.websocket;

import static java.time.Duration.ofSeconds;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;
import jakarta.faces.component.UIWebsocket;

public class Spec1396IT extends BaseITNG {

    /**
     * @see UIWebsocket
     * @see https://github.com/jakartaee/faces/issues/1396
     */
    @Test
    public void testEnableWebsocketEndpoint() throws Exception {
        WebPage page = getPage("spec1396EnableWebsocketEndpoint.xhtml");
        assertEquals("true", page.findElement(By.id("param")).getText());
    }

    /**
     * @see UIWebsocket
     * @see https://github.com/jakartaee/faces/issues/1396
     */
    @Test
    public void testDefaultWebsocket() throws Exception {
        WebPage page = getPage("spec1396DefaultWebsocket.xhtml");

        String pageSource = page.getPageSource();
        assertTrue(pageSource.contains("faces.push.init("));
        assertTrue(pageSource.contains("/jakarta.faces.push/push?"));

        waitUntilWebsocketIsOpened(getWebDriver(), page);

        WebElement button = page.findElement(By.id("form:button"));
        button.click();

        waitUntilWebsocketIsPushed(getWebDriver(), page);
    }

    /**
     * @see UIWebsocket#setUser(java.io.Serializable)
     * @see https://github.com/jakartaee/faces/issues/1396
     */
    @Test
    public void testUserScopedWebsocket() throws Exception {
        WebPage page = getPage("spec1396UserScopedWebsocket.xhtml");

        String pageSource = page.getPageSource();
        assertTrue(pageSource.contains("faces.push.init("));
        assertTrue(pageSource.contains("/jakarta.faces.push/user?"));

        waitUntilWebsocketIsOpened(getWebDriver(), page);

        WebElement button = page.findElement(By.id("form:button"));
        button.click();

        waitUntilWebsocketIsPushed(getWebDriver(), page);
    }

    /**
     * @see UIWebsocket#setScope(String)
     * @see https://github.com/jakartaee/faces/issues/1396
     */
    @Test
    public void testViewScopedWebsocket() throws Exception {
        WebPage page = getPage("spec1396ViewScopedWebsocket.xhtml");

        String pageSource = page.getPageSource();
        assertTrue(pageSource.contains("faces.push.init("));
        assertTrue(pageSource.contains("/jakarta.faces.push/view?"));

        waitUntilWebsocketIsOpened(getWebDriver(), page);

        WebElement button = page.findElement(By.id("form:button"));
        button.click();

        waitUntilWebsocketIsPushed(getWebDriver(), page);
    }

    /**
     * HtmlUnit is not capable of waiting until WS is opened. Hence this work around.
     */
    static void waitUntilWebsocketIsOpened(WebDriver browser, WebPage page) throws Exception {
        new WebDriverWait(browser, ofSeconds(3)).until($ -> "yes".equals(page.findElement(By.id("opened")).getText()));
    }

    /**
     * HtmlUnit is not capable of waiting until WS is pushed. Hence this work around.
     */
    static void waitUntilWebsocketIsPushed(WebDriver browser, WebPage page) throws Exception {
        new WebDriverWait(browser, ofSeconds(3)).until($ -> "pushed!".equals(page.findElement(By.id("message")).getText()));
    }

}
