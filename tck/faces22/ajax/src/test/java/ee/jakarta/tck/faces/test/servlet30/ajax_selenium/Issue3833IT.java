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


import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.faces.component.behavior.AjaxBehavior;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

@Disabled("ignored at the request by the myfaces community -- See https://github.com/jakartaee/faces/issues/1757")
class Issue3833IT extends BaseITNG {

  /**
   * @see AjaxBehavior
     * @see com.sun.faces.facelets.component.UIRepeat
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3837
   */
  @Test
  void uIRepeatStateSaving() throws Exception {
        WebPage page = getPage("issue3833.xhtml");

        page.guardAjax(() -> new Select(page.findElement(By.id("form:repeat:0:list"))).selectByIndex(0));
      assertEquals("null -> 1", page.findElement(By.id("form:message")).getText());

        page.guardAjax(() -> new Select(page.findElement(By.id("form:repeat:1:list"))).selectByIndex(0));
      assertEquals("null -> 3", page.findElement(By.id("form:message")).getText());

        page.guardAjax(() -> new Select(page.findElement(By.id("form:repeat:0:list"))).selectByIndex(1));
      assertEquals("1 -> 2", page.findElement(By.id("form:message")).getText());

        page.guardAjax(() -> new Select(page.findElement(By.id("form:repeat:1:list"))).selectByIndex(1));
      assertEquals("3 -> 4", page.findElement(By.id("form:message")).getText());
    }
}
