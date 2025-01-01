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


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import jakarta.faces.component.search.SearchKeywordResolver;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.ExtendedWebDriver;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

public class Spec1238IT extends BaseITNG {

  /**
   * @see SearchKeywordResolver
     * @see https://github.com/jakartaee/faces/issues/1238
   */
  @Test
  void test() throws Exception {
        testSearchExpression();
    }

    public void testSearchExpression() throws Exception {
        WebPage page = getPage("spec1238.xhtml");
        page.wait(Duration.ofMillis(3000));

        ExtendedWebDriver webDriver = getWebDriver();
        WebElement label = webDriver.findElement(By.id("label"));
        WebElement input = webDriver.findElement(By.id("spec1238ITinput1"));
        
        assertEquals(label.getDomAttribute("for"), input.getDomAttribute("id"));
        
        String onchange = input.getDomAttribute("onchange");

        if (onchange.contains("@this")) {
            assertFalse(onchange.contains("spec1238ITinput1"));
        }
        else {
            assertTrue(onchange.contains("spec1238ITinput1"));
        }
        assertTrue(onchange.contains("spec1238ITinput2"));
    }
}
