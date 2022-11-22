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
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertEquals;

/**
 * Demo port to showcase how to implement
 * an ajax test with Selenium web drivers
 */
public class Issue939IT extends BaseITNG {

    @Test
    public void testCdataEscape1() throws Exception {
        WebPage page = getPage("issue939.xhtml");
        WebElement out1 = page.findElement(By.id("form1:out1"));
        WebElement in1 = page.findElement(By.id("form1:in1"));
        assertEquals("", out1.getText().trim());
        assertEquals("", in1.getAttribute("value").trim());
        in1.sendKeys("]]>");
        WebElement button1 = page.findElement(By.id("form1:button1"));
        button1.click();
        Thread.sleep(1000);
        // Check that the ajax request succeeds
        assertEquals("]]>", page.findElement(By.id("form1:out1")).getText().trim());
    }

    @Test
    public void testCdataEscape2() throws Exception {
        WebPage page = getPage("issue939.xhtml");
        assertEquals("", page.findElement(By.id("form1:out1")).getText());
        assertEquals("", page.findElement(By.id("form1:in1")).getAttribute("value"));

        WebElement in1 = page.findElement(By.id("form1:in1"));
        in1.sendKeys("<!");

        // Submit the ajax request
        WebElement button1 = page.findElement(By.id("form1:button1"));
        button1.click();
        Thread.sleep(3000);

        // Check that the ajax request succeeds
        assertEquals("<!", page.findElement(By.id("form1:out1")).getText());
    }

    @Test
    public void testCdataEscape3() throws Exception {
        Thread.sleep(3000);
        WebPage page = getPage("issue939.xhtml");
        assertEquals("", page.findElement(By.id("form1:out1")).getText());
        assertEquals("", page.findElement(By.id("form1:in1")).getAttribute("value"));

        WebElement in1 = page.findElement(By.id("form1:in1"));
        in1.sendKeys("]");

        // Submit the ajax request
        WebElement button1 = page.findElement(By.id("form1:button1"));
        button1.click();
        Thread.sleep(3000);

        // Check that the ajax request succeeds
        assertEquals("]", page.findElement(By.id("form1:out1")).getText());
    }

    @Test
    public void testCdataEscape4() throws Exception {
        Thread.sleep(3000);
        WebPage page = getPage("issue939.xhtml");
        assertEquals("", page.findElement(By.id("form1:out1")).getText());
        assertEquals("", page.findElement(By.id("form1:in1")).getAttribute("value"));

        WebElement in1 = page.findElement(By.id("form1:in1"));
        in1.sendKeys("<![CDATA[ ]]>");

        // Submit the ajax request
        WebElement button1 = page.findElement(By.id("form1:button1"));
        button1.click();
        Thread.sleep(3000);

        // Check that the ajax request succeeds
        assertEquals("<![CDATA[ ]]>", page.findElement(By.id("form1:out1")).getText());
    }
}
