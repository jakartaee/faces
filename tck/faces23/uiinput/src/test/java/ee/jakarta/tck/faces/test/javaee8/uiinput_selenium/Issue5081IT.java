/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2022 Contributors to the Eclipse Foundation.
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
package ee.jakarta.tck.faces.test.javaee8.uiinput_selenium;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.component.UIInput;
import jakarta.faces.component.UISelectMany;
import jakarta.faces.component.behavior.AjaxBehavior;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class Issue5081IT extends BaseITNG {

  /**
   * @see UISelectMany
     * @see AjaxBehavior
     * @see UIInput#EMPTY_STRING_AS_NULL_PARAM_NAME
     * @see https://github.com/eclipse-ee4j/mojarra/issues/5081
   */
  @Test
  void issue4734() throws Exception {
        WebPage page = getPage("issue5081.xhtml");


        WebElement input1 = page.findElement(By.id("form:input"));
        page.guardAjax(() -> {
            input1.sendKeys("text");
            // \t key equals to blur
            input1.sendKeys("\t");
        });
        WebElement submit = page.findElement(By.id("form:submit"));
        page.guardHttp(submit::click);
        assertTrue(page.containsText("Validation Error"));
        WebElement message = page.findElement(By.id("form:message_for_selectmany"));
        assertEquals("form:selectmany: Validation Error: Value is required.", message.getText(), "There is a required message");

        WebElement input2 = page.findElement(By.id("form:input"));
        page.guardAjax(() -> {
            input2.sendKeys("more");
            input2.sendKeys("\t"); // Before the fix, the second blur failed with java.lang.ClassCastException: class java.lang.String cannot be cast to class [Ljava.lang.Object;
        });
        submit = page.findElement(By.id("form:submit"));
        page.guardAjax(submit::click);
        message = page.findElement(By.id("form:message_for_selectmany"));
        assertEquals("form:selectmany: Validation Error: Value is required.", message.getText(), "There is a still required message");
    }

}
