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

import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.component.behavior.AjaxBehavior;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class Issue2439IT extends BaseITNG {

  /**
   * @see AjaxBehavior#isDisabled()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2443
   */
  @Test
  void disabledBehaviors() throws Exception {
        WebPage page = getPage("disabledBehaviors.xhtml");
        
        WebElement input1 = page.findElement(By.id("form1:input1"));
        WebElement input2 = page.findElement(By.id("form1:input2"));
        WebElement input3 = page.findElement(By.id("form1:input3"));
        assertTrue(input1.getAttribute("onchange") == null || input1.getAttribute("onchange").isEmpty(), "input1 has no onchange attribute");
        assertTrue(input2.getAttribute("onchange") != null && !input2.getAttribute("onchange").isEmpty(), "input2 has onchange attribute");
        assertTrue(input3.getAttribute("onchange") != null && !input3.getAttribute("onchange").isEmpty(), "input3 has onchange attribute");
    }
}

