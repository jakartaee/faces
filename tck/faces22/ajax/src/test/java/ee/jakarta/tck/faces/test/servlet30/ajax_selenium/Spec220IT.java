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
import ee.jakarta.tck.faces.test.util.selenium.ExtendedTextInput;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;
import jakarta.faces.component.behavior.AjaxBehavior;
import jakarta.faces.render.ResponseStateManager;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static junit.framework.Assert.assertTrue;

public class Spec220IT extends BaseITNG {

    /**
     * @see AjaxBehavior
     * @see ResponseStateManager#VIEW_STATE_PARAM
     * @see https://github.com/jakartaee/faces/issues/220
     */
    @Test
    public void testViewState() throws Exception {
        WebPage page = getPage("viewState1.xhtml");
        ExtendedTextInput textField = new ExtendedTextInput(getWebDriver(), page.findElement(By.id("firstName")));
        textField.setValue("ajaxFirstName");
        
        WebElement button = page.findElement(By.id("submitAjax"));
        button.click();
        page.waitReqJs();
        String pageText = page.getPageSource();
        assertTrue(pageText.contains("|ajaxFirstName|"));

        textField = new ExtendedTextInput(getWebDriver(), page.findElement(By.id("firstName")));
        textField.setValue("nonAjaxFirstName");
        
        button = new ExtendedTextInput(getWebDriver(), page.findElement(By.id("submitNonAjax")));
        button.click();
        page.waitReqJs();
        pageText = page.getPageSource();
        assertTrue(pageText.contains("|nonAjaxFirstName|"));
    }
}
