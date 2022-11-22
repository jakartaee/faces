/*
 * Copyright (c) 2021 Contributors to the Eclipse Foundation.
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

package ee.jakarta.tck.faces.test.servlet30.ajax_new;


import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;
import jakarta.faces.component.behavior.AjaxBehavior;
import jakarta.faces.context.PartialViewContext;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertEquals;

public class Issue1284IT extends BaseITNG {

    /**
     * @see AjaxBehavior
     * @see PartialViewContext#getEvalScripts()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/1289
     */
    @Test
    public void testCdataEscape5() throws Exception {
        WebPage page = getPage("issue1284.xhtml");
        assertEquals("", page.findElement(By.id("form1:out1")).getText());
        assertEquals("", page.findElement(By.id("form1:in1")).getAttribute("value"));

        WebElement in1 = page.findElement(By.id("form1:in1"));
        in1.sendKeys("[");

        // Submit the ajax request
        WebElement button1 = page.findElement(By.id("form1:button1"));
        button1.click();
        Thread.sleep(3000);

        // Check that the ajax request succeeds
        assertEquals("[", page.findElement(By.id("form1:out1")).getText());
    }

    /**
     * @see AjaxBehavior
     * @see PartialViewContext#getEvalScripts()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/1289
     */
    @Test
    public void testCdataEscape6() throws Exception {
        WebPage page = getPage("issue1284.xhtml");
        assertEquals("", page.findElement(By.id("form1:out1")).getText());
        assertEquals("", page.findElement(By.id("form1:in1")).getAttribute("value"));

        WebElement in1 = page.findElement(By.id("form1:in1"));
        in1.sendKeys("var a=[");

        // Submit the ajax request
        WebElement button1 = page.findElement(By.id("form1:button1"));
        button1.click();
        Thread.sleep(3000);

        // Check that the ajax request succeeds
        assertEquals("var a=[", page.findElement(By.id("form1:out1")).getText());
    }
}
