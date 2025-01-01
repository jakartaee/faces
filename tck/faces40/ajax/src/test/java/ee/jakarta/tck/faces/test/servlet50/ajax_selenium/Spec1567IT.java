/*
 * Copyright (c) 2021 Contributors to the Eclipse Foundation.
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

package ee.jakarta.tck.faces.test.servlet50.ajax_selenium;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.faces.component.UIComponent;
import jakarta.faces.component.behavior.AjaxBehavior;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.ExtendedTextInput;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class Spec1567IT extends BaseITNG {


  /**
   * @see AjaxBehavior#getExecute()
     * @see UIComponent#getCompositeComponentParent(UIComponent)
     * @see https://github.com/jakartaee/faces/issues/1567
   */
  @Test
  void test() throws Exception {
        WebPage page = getPage("spec1567IT.xhtml");

        // fill form1 input1
        page.guardAjax(() -> {
            ExtendedTextInput input = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form1:inputs:input1")));
            input.setValue("1");
            input.fireEvent("change");
        });

        ExtendedTextInput input1 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form1:inputs:input1")));
        ExtendedTextInput input2 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form1:inputs:input2")));
        ExtendedTextInput input3 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form1:inputs:input3")));

        String messages = page.findElement(By.id("form1:messages")).getText();
        assertEquals("1", input1.getValue(), "input1 is filled with 1");
        assertEquals("", input2.getValue(), "input2 is empty");
        assertEquals("", input3.getValue(), "input3 is empty");
        assertEquals("setForm1input1:1\nsetForm1input2:", messages, "input1 is filled and input2 is empty");

        // fill form1 input2
        page.guardAjax(() -> {
            ExtendedTextInput input = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form1:inputs:input2")));
            input.setValue("1");
            input.fireEvent("change");
        });

        input1 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form1:inputs:input1")));
        input2 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form1:inputs:input2")));
        input3 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form1:inputs:input3")));
        messages = page.findElement(By.id("form1:messages")).getText();
        assertEquals("1", input1.getValue(), "input1 is filled with 1");
        assertEquals("1", input2.getValue(), "input2 is filled with 1");
        assertEquals("", input3.getValue(), "input3 is empty");
        assertEquals("setForm1input1:1\nsetForm1input2:1", messages, "input1 is filled and input2 is filled");

        // fill form1 input3
        page.guardAjax(() -> {
            ExtendedTextInput input = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form1:inputs:input3")));
            input.setValue("1");
            input.fireEvent("change");
        });

        input1 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form1:inputs:input1")));
        input2 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form1:inputs:input2")));
        input3 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form1:inputs:input3")));
        messages =  page.findElement(By.id("form1:messages")).getText();
        assertEquals("", input1.getValue(), "input1 is refreshed to empty string");
        assertEquals("", input2.getValue(), "input2 is refreshed to empty string");
        assertEquals("1x", input3.getValue(), "input3 is filled and refreshed");
        assertEquals("setForm1input3:1", messages, "input3 is filled");

        // fill form2 input1
        page.guardAjax(() -> {
            ExtendedTextInput input = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form2:inputs:input1")));
            input.setValue("1");
            input.fireEvent("change");
        });

        input1 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form2:inputs:input1")));
        input2 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form2:inputs:input2")));
        input3 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form2:inputs:input3")));

        messages =  page.findElement(By.id("form2:messages")).getText();
        assertEquals("1", input1.getValue(), "input1 is filled with 1");
        assertEquals("", input2.getValue(), "input2 is empty");
        assertEquals("", input3.getValue(), "input3 is empty");
        assertEquals("setForm2input1:1\nsetForm2input2:", messages, "input1 is filled and input2 is empty");

        // fill form2 input2
        page.guardAjax(() -> {
            ExtendedTextInput input = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form2:inputs:input2")));
            input.setValue("1");
            input.fireEvent("change");
        });

        input1 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form2:inputs:input1")));
        input2 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form2:inputs:input2")));
        input3 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form2:inputs:input3")));
        messages = page.findElement(By.id("form2:messages")).getText();
        assertEquals("1", input1.getValue(), "input1 is filled with 1");
        assertEquals("1", input2.getValue(), "input2 is filled with 1");
        assertEquals("", input3.getValue(), "input3 is empty");
        assertEquals("setForm2input1:1\nsetForm2input2:1", messages, "input1 is filled and input2 is filled");

        // fill form2 input3
        page.guardAjax(() -> {
            ExtendedTextInput input = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form2:inputs:input3")));
            input.setValue("1");
            input.fireEvent("change");
        });

        input1 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form2:inputs:input1")));
        input2 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form2:inputs:input2")));
        input3 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form2:inputs:input3")));
        messages =  page.findElement(By.id("form2:messages")).getText();
        assertEquals("", input1.getValue(), "input1 is refreshed to empty string");
        assertEquals("", input2.getValue(), "input2 is refreshed to empty string");
        assertEquals("1x", input3.getValue(), "input3 is filled and refreshed");
        assertEquals("setForm2input3:1", messages, "input3 is filled");

        // fill form3 input1
        page.guardAjax(() -> {
            ExtendedTextInput input = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form3:inputs:input1")));
            input.setValue("1");
            input.fireEvent("change");
        });

        input1 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form3:inputs:input1")));
        input2 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form3:inputs:input2")));
        input3 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form3:inputs:input3")));

        messages =  page.findElement(By.id("form3:messages")).getText();
        assertEquals("1x", input1.getValue(), "input1 is refreshed to 1x");
        assertEquals("x", input2.getValue(), "input2 is refreshed to x");
        assertEquals("", input3.getValue(), "input3 is empty");
        assertEquals("setForm3input1:1\nsetForm3input2:", messages, "input1 is filled and input2 is empty");

        // fill form3 input2
        page.guardAjax(() -> {
            ExtendedTextInput input = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form3:inputs:input2")));
            input.setValue("1");
            input.fireEvent("change");
        });

        input1 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form3:inputs:input1")));
        input2 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form3:inputs:input2")));
        input3 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form3:inputs:input3")));
        messages = page.findElement(By.id("form3:messages")).getText();
        assertEquals("1xx", input1.getValue(), "input1 is refreshed to 1xx");
        assertEquals("1x", input2.getValue(), "input2 is refreshed to 1x");
        assertEquals("", input3.getValue(), "input3 is empty");
        assertEquals("setForm3input1:1x\nsetForm3input2:1", messages, "input1 is filled and input2 is filled");

        // fill form3 input3
        page.guardAjax(() -> {
            ExtendedTextInput input = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form3:inputs:input3")));
            input.setValue("1");
            input.fireEvent("change");
        });

        input1 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form3:inputs:input1")));
        input2 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form3:inputs:input2")));
        input3 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form3:inputs:input3")));
        messages =  page.findElement(By.id("form3:messages")).getText();
        assertEquals("", input1.getValue(), "input1 is refreshed to empty string");
        assertEquals("", input2.getValue(), "input2 is refreshed to empty string");
        assertEquals("1x", input3.getValue(), "input3 is filled and refreshed");
        assertEquals("setForm3input3:1", messages, "input3 is filled");
    }

}
