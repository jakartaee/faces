/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2022 Contributors to Eclipse Foundation.
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

package ee.jakarta.tck.faces.test.servlet40.facelets_selenium;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIViewRoot;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.ExtendedWebDriver;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class Issue5078IT extends BaseITNG {

  /**
   * @see com.sun.faces.facelets.component.UIRepeat
     * @see UIComponent#visitTree(jakarta.faces.component.visit.VisitContext, jakarta.faces.component.visit.VisitCallback)
     * @see UIViewRoot#processApplication(jakarta.faces.context.FacesContext)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/5078
   */
  @Test
  void uIRepeatVisitTreeDuringInvokeApplication() throws Exception {
        WebPage page = getPage("faces/issue5078.xhtml");
        ExtendedWebDriver webDriver = getWebDriver();
        WebElement button = webDriver.findElement(By.id("form:repeat:1:button"));
        button.click();
        page.waitForCondition(webDriver1 -> {
            try {
                WebElement element = webDriver.findElement(By.id("form:value"));
                return element != null && element.getText().equals("2");
            } catch (StaleElementReferenceException ex) {
                //element has been replaced mid check
                return false;
            }
        });
    }

}
