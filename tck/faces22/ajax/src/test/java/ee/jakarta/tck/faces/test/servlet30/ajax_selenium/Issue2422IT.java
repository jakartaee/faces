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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.component.behavior.AjaxBehavior;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;


class Issue2422IT extends BaseITNG {

  /**
   * This test verifies correct function of SelectManyCheckbox in a Composite
   * Component over Ajax. 
   * 
   * @see AjaxBehavior
     * @see WebElementManyCheckbox
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2425
   */
  @Test
  void selectBooleanCheckbox() throws Exception {
        WebPage page = getPage("selectBooleanCheckbox.xhtml");
        WebElement cbox = page.findElement(By.id("checkbox"));
      assertFalse(cbox.isSelected());

        page.guardAjax(cbox::click);
        assertTrue(cbox.isSelected());

    }
}
