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

public class Issue939IT extends BaseITNG {

    /**
     * @see AjaxBehavior
     * @see https://github.com/eclipse-ee4j/mojarra/issues/943
     */
    @Test
    public void testCdataEscape1() throws Exception {
        WebPage page = getPage("issue939.xhtml");
        assertTrue(page.findElement(By.id("form1:out1")).getText().equals("")); 
        assertTrue(page.findElement(By.id("form1:in1")).getText().equals("")); 

        WebElement in1 = page.findElement(By.id("form1:in1"));
        in1.sendKeys("]]>");

        // Submit the ajax request
        WebElement button1 = page.findElement(By.id("form1:button1"));
        page.guardAjax(button1::click);
        

        // Check that the ajax request succeeds
        assertTrue(page.findElement(By.id("form1:out1")).getText().equals("]]>")); 
    }

    @Test
    public void testCdataEscape2() throws Exception {
        WebPage page = getPage("issue939.xhtml");
        assertTrue(page.findElement(By.id("form1:out1")).getText().equals("")); 
        assertTrue(page.findElement(By.id("form1:in1")).getText().equals("")); 

        WebElement in1 = page.findElement(By.id("form1:in1"));
        in1.sendKeys("<!");

        // Submit the ajax request
        WebElement button1 = page.findElement(By.id("form1:button1"));
        page.guardAjax(button1::click);
        

        // Check that the ajax request succeeds
        assertTrue(page.findElement(By.id("form1:out1")).getText().equals("<!")); 
    }

    @Test
    public void testCdataEscape3() throws Exception {
        WebPage page = getPage("issue939.xhtml");
        assertTrue(page.findElement(By.id("form1:out1")).getText().equals("")); 
        assertTrue(page.findElement(By.id("form1:in1")).getText().equals("")); 

        WebElement in1 = page.findElement(By.id("form1:in1"));
        in1.sendKeys("]");

        // Submit the ajax request
        WebElement button1 = page.findElement(By.id("form1:button1"));
        page.guardAjax(button1::click);
        

        // Check that the ajax request succeeds
        assertTrue(page.findElement(By.id("form1:out1")).getText().equals("]")); 
    }

    @Test
    public void testCdataEscape4() throws Exception {
        WebPage page = getPage("issue939.xhtml");
        assertTrue(page.findElement(By.id("form1:out1")).getText().equals("")); 
        assertTrue(page.findElement(By.id("form1:in1")).getText().equals("")); 

        WebElement in1 = page.findElement(By.id("form1:in1"));
        in1.sendKeys("<![CDATA[ ]]>");

        // Submit the ajax request
        WebElement button1 = page.findElement(By.id("form1:button1"));
        page.guardAjax(button1::click);
        

        // Check that the ajax request succeeds
        assertTrue(page.findElement(By.id("form1:out1")).getText().equals("<![CDATA[ ]]>")); 
    }
}
