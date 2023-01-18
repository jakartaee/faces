/*
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

package ee.jakarta.tck.faces.test.servlet30.ajax_selenium;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;
import jakarta.faces.component.behavior.AjaxBehavior;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertTrue;

public class Issue2767IT extends BaseITNG {

    /**
     * @see AjaxBehavior
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2771
     */
    @Test
    public void testCommandLinkRadio() throws Exception {
        WebPage page = getPage("issue2767.xhtml");
        WebElement anchor = page.findElement(By.id("testLink"));
        // This will ensure JavaScript finishes before evaluating the page.
        page.waitReqJs();
        anchor.click();
        page.waitReqJs();
        page.waitReqJs();
        assertTrue(page.isInPage("LINK ACTION"));
        WebElement radio1 = page.findElement(By.id("testRadio:0"));
        WebElement radio2 = page.findElement(By.id("testRadio:1"));
        WebElement radio3 = page.findElement(By.id("testRadio:2"));

        radio1.click();
        page.waitReqJs();
        page.waitReqJs();
        assertTrue(page.isInPage("RADIO:red"));

        radio2.click();
        page.waitReqJs();
        page.waitReqJs();
        assertTrue(page.isInPage("RADIO:blue"));

        radio3.click();
        page.waitReqJs();
        page.waitReqJs();
        assertTrue(page.isInPage("RADIO:white"));
    }
}
