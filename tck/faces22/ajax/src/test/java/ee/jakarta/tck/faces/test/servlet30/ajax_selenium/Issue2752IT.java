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

class Issue2752IT extends BaseITNG {

  /**
   * @see AjaxBehavior
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2756
   */
  @Test
  void insertFromResponse() throws Exception {
        WebPage page = getPage("insertElement.xhtml");
         WebElement beforeButton = page.findElement(By.id("beforeButton"));
         page.guardAjax(beforeButton::click);
        assertTrue(page.isInPageText("This is before textalpha"));
        WebElement afterButton = page.findElement(By.id("afterButton"));
        page.guardAjax(afterButton::click);
        assertTrue(page.isInPageText("This is before textalphaThis is after text"));
    }

}
