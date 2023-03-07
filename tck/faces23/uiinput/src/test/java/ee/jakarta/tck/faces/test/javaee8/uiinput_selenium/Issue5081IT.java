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

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.ExtendedWebDriver;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;
import jakarta.faces.component.UIInput;
import jakarta.faces.component.UISelectMany;
import jakarta.faces.component.behavior.AjaxBehavior;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Issue5081IT extends BaseITNG {

    /**
     * @see UISelectMany
     * @see AjaxBehavior
     * @see UIInput#EMPTY_STRING_AS_NULL_PARAM_NAME
     * @see https://github.com/eclipse-ee4j/mojarra/issues/5081
     */
    @Test
    public void testIssue4734() throws Exception {
        WebPage page = getPage("issue5081.xhtml");

        ExtendedWebDriver webDriver = getWebDriver();

        WebElement input = webDriver.findElement(By.id("form:input"));
        input.sendKeys("text");
        // \t key equals to blur
        input.sendKeys("\t");
        page.waitReqJs(Duration.ofMillis(3000));
        WebElement submit = webDriver.findElement(By.id("form:submit"));
        submit.click();
        page.waitForCondition(webDriver1 ->
                page.getPageSource().contains("Validation Error"));
        WebElement message = webDriver.findElement(By.id("form:message_for_selectmany"));
        assertEquals("There is a required message", "form:selectmany: Validation Error: Value is required.", message.getText());

        input = webDriver.findElement(By.id("form:input"));
        input.sendKeys("more");
        input.sendKeys("\t"); // Before the fix, the second blur failed with java.lang.ClassCastException: class java.lang.String cannot be cast to class [Ljava.lang.Object;
        page.waitReqJs(Duration.ofMillis(3000));
        submit = webDriver.findElement(By.id("form:submit"));
        submit.click();
        page.waitReqJs(Duration.ofMillis(3000));
        message = webDriver.findElement(By.id("form:message_for_selectmany"));
        assertEquals("There is a still required message", "form:selectmany: Validation Error: Value is required.", message.getText());
    }

}
