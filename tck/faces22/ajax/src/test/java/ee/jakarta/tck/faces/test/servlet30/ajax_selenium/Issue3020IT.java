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

class Issue3020IT extends BaseITNG {

  /**
   * @see AjaxBehavior
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3024
   */
  @Test
  void delayPositive() throws Exception {
        WebPage page = getPage("issue3020Positive.xhtml");

        WebElement in1 = page.findElement(By.id("input"));
        in1.sendKeys("a");

        Thread.sleep(3000);

        // Check that the ajax request succeeds
        assertTrue(page.findElement(By.id("result")).getText().contains("aaaaaaaaaa"));
    }

  /**
   * @see AjaxBehavior
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3024
   */
  @Test
  void delayNegative() throws Exception {
        WebPage page = getPage("issue3020Negative.xhtml");

        WebElement in1 = page.findElement(By.id("input"));
        in1.sendKeys("a");

        Thread.sleep(3000);

        // Check that the ajax request does not succeed, change from the original which had direct access to the js exceptions
        WebElement errorHolder = page.findElement(By.id("windowError"));
        assertTrue(errorHolder.getText().contains("NaN"));
    }
    

}
