/*
 * Copyright (c) 2021 Contributors to the Eclipse Foundation.
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
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

package ee.jakarta.tck.faces.test.javaee8.commandScript_selenium;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.ExtendedWebDriver;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;
import jakarta.faces.component.html.HtmlCommandScript;
import org.junit.Test;
import org.openqa.selenium.By;

import java.time.Duration;

import static org.junit.Assert.assertTrue;


public class Spec613IT extends BaseITNG {

    /**
     * @see HtmlCommandScript
     * @see https://github.com/jakartaee/faces/issues/613
     */
    @Test
    public void test() throws Exception {
        testCommandScript();
    }

    public void testCommandScript() throws Exception {
        WebPage page = getPage("spec613.xhtml");
        page.wait(Duration.ofMillis(3000));
        ExtendedWebDriver webDriver = getWebDriver();
        assertTrue(webDriver.findElement(By.id("result")).getText().equals("foo"));

        webDriver.getJSExecutor().executeScript("bar()");
        page.wait(Duration.ofMillis(3000));
        assertTrue(webDriver.findElement(By.id("result")).getText().equals("bar"));
    }
}
