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

package ee.jakarta.tck.faces.test.javaee8.searchExpression_selenium;


import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.ExtendedWebDriver;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;
import jakarta.faces.component.search.SearchKeywordResolver;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;

public class Spec1238IT extends BaseITNG {

    /**
     * @see SearchKeywordResolver
     * @see https://github.com/jakartaee/faces/issues/1238
     */
    @Test
    public void test() throws Exception {
        testSearchExpression();
    }

    public void testSearchExpression() throws Exception {
        WebPage page = getPage("spec1238.xhtml");
        page.wait(Duration.ofMillis(60000));

        ExtendedWebDriver webDriver = getWebDriver();
        WebElement label = webDriver.findElement(By.id("label"));
        WebElement input = webDriver.findElement(By.id("spec1238ITinput1"));
        
        Assert.assertEquals(label.getAttribute("for"), input.getAttribute("id"));
        
        String onchange = input.getAttribute("onchange");

        if (onchange.contains("@this")) {
            Assert.assertFalse(onchange.contains("spec1238ITinput1"));
        }
        else {
            Assert.assertTrue(onchange.contains("spec1238ITinput1"));
        }
        Assert.assertTrue(onchange.contains("spec1238ITinput2"));
    }
}
