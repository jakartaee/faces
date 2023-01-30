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

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.ExtendedTextInput;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.behavior.AjaxBehavior;
import org.junit.Test;
import org.openqa.selenium.By;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Spec1567IT extends BaseITNG {



    /**
     * @see AjaxBehavior#getExecute()
     * @see UIComponent#getCompositeComponentParent(UIComponent)
     * @see https://github.com/jakartaee/faces/issues/1567
     */
    @Test
    public void test() throws Exception {
        WebPage page = getPage("spec1567IT.xhtml");

        // fill form1 input1
        ExtendedTextInput input1 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form1:inputs:input1")));

        input1.setValue("1");
        input1.fireEvent("change");
        page.waitReqJs();

        input1 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form1:inputs:input1")));
        ExtendedTextInput input2 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form1:inputs:input2")));
        ExtendedTextInput input3 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form1:inputs:input3")));

        String messages = page.findElement(By.id("form1:messages")).getText();
        assertEquals("input1 is filled with 1", "1", input1.getValue());
        assertEquals("input2 is empty", "", input2.getValue());
        assertEquals("input3 is empty", "", input3.getValue());
        assertEquals("input1 is filled and input2 is empty", "setForm1input1:1\nsetForm1input2:", messages);

        // fill form1 input2
        input2 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form1:inputs:input2")));
        input2.setValue("1");
        input2.fireEvent("change");
        page.waitReqJs();

        input1 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form1:inputs:input1")));
        input2 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form1:inputs:input2")));
        input3 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form1:inputs:input3")));
        messages = page.findElement(By.id("form1:messages")).getText();
        assertEquals("input1 is filled with 1", "1", input1.getValue());
        assertEquals("input2 is filled with 1", "1", input2.getValue());
        assertEquals("input3 is empty", "", input3.getValue());
        assertEquals("input1 is filled and input2 is filled", "setForm1input1:1\nsetForm1input2:1", messages);

        // fill form1 input3
        input3 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form1:inputs:input3")));
        input3.setValue("1");
        input3.fireEvent("change");
        page.waitReqJs();

        input1 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form1:inputs:input1")));
        input2 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form1:inputs:input2")));
        input3 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form1:inputs:input3")));
        messages =  page.findElement(By.id("form1:messages")).getText();
        assertEquals("input1 is refreshed to empty string", "", input1.getValue());
        assertEquals("input2 is refreshed to empty string", "", input2.getValue());
        assertEquals("input3 is filled and refreshed", "1x", input3.getValue());
        assertEquals("input3 is filled", "setForm1input3:1", messages);

        // fill form2 input1
        input1 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form2:inputs:input1")));
        input1.setValue("1");
        input1.fireEvent("change");
        page.waitReqJs();

        input1 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form2:inputs:input1")));
        input2 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form2:inputs:input2")));
        input3 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form2:inputs:input3")));

        messages =  page.findElement(By.id("form2:messages")).getText();
        assertEquals("input1 is filled with 1", "1", input1.getValue());
        assertEquals("input2 is empty", "", input2.getValue());
        assertEquals("input3 is empty", "", input3.getValue());
        assertEquals("input1 is filled and input2 is empty", "setForm2input1:1\nsetForm2input2:", messages);

        // fill form2 input2
        input2 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form2:inputs:input2")));
        input2.setValue("1");
        input2.fireEvent("change");
        page.waitReqJs();

        input1 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form2:inputs:input1")));
        input2 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form2:inputs:input2")));
        input3 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form2:inputs:input3")));
        messages = page.findElement(By.id("form2:messages")).getText();
        assertEquals("input1 is filled with 1", "1", input1.getValue());
        assertEquals("input2 is filled with 1", "1", input2.getValue());
        assertEquals("input3 is empty", "", input3.getValue());
        assertEquals("input1 is filled and input2 is filled", "setForm2input1:1\nsetForm2input2:1", messages);

        // fill form2 input3
        input3 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form2:inputs:input3")));
        input3.setValue("1");
        input3.fireEvent("change");
        page.waitReqJs();

        input1 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form2:inputs:input1")));
        input2 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form2:inputs:input2")));
        input3 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form2:inputs:input3")));
        messages =  page.findElement(By.id("form2:messages")).getText();
        assertEquals("input1 is refreshed to empty string", "", input1.getValue());
        assertEquals("input2 is refreshed to empty string", "", input2.getValue());
        assertEquals("input3 is filled and refreshed", "1x", input3.getValue());
        assertEquals("input3 is filled", "setForm2input3:1", messages);

        // fill form3 input1
        input1 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form3:inputs:input1")));

        input1.setValue("1");
        input1.fireEvent("change");
        page.waitReqJs();

        input1 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form3:inputs:input1")));
        input2 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form3:inputs:input2")));
        input3 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form3:inputs:input3")));

        messages =  page.findElement(By.id("form3:messages")).getText();
        assertEquals("input1 is refreshed to 1x", "1x", input1.getValue());
        assertEquals("input2 is refreshed to x", "x", input2.getValue());
        assertEquals("input3 is empty", "", input3.getValue());
        assertEquals("input1 is filled and input2 is empty", "setForm3input1:1\nsetForm3input2:", messages);

        // fill form3 input2
        input2 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form3:inputs:input2")));
        input2.setValue("1");
        input2.fireEvent("change");
        page.waitReqJs();

        input1 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form3:inputs:input1")));
        input2 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form3:inputs:input2")));
        input3 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form3:inputs:input3")));
        messages = page.findElement(By.id("form3:messages")).getText();
        assertEquals("input1 is refreshed to 1xx", "1xx", input1.getValue());
        assertEquals("input2 is refreshed to 1x", "1x", input2.getValue());
        assertEquals("input3 is empty", "", input3.getValue());
        assertEquals("input1 is filled and input2 is filled", "setForm3input1:1x\nsetForm3input2:1", messages);

        // fill form3 input3
        input3 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form3:inputs:input3")));
        input3.setValue("1");
        input3.fireEvent("change");
        page.waitReqJs();

        input1 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form3:inputs:input1")));
        input2 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form3:inputs:input2")));
        input3 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form3:inputs:input3")));
        messages =  page.findElement(By.id("form3:messages")).getText();
        assertEquals("input1 is refreshed to empty string", "", input1.getValue());
        assertEquals("input2 is refreshed to empty string", "", input2.getValue());
        assertEquals("input3 is filled and refreshed", "1x", input3.getValue());
        assertEquals("input3 is filled", "setForm3input3:1", messages);
    }

}
