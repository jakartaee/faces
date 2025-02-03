/*
 * Copyright (c) Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the Apache License, Version 2.0
 * which is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GPL-2.0 with Classpath-exception-2.0 which
 * is available at https://openjdk.java.net/legal/gplv2+ce.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0 or Apache-2.0
 */
package ee.jakarta.tck.faces.test.faces50.uiinput;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;

/**
 * https://github.com/jakartaee/faces/issues/1507
 */
class Spec1507IT extends BaseITNG {

    @FindBy(id = "form1:input1")
    private WebElement input1;

    @FindBy(id = "form1:output1")
    private WebElement output1;

    @FindBy(id = "form2:input2")
    private WebElement input2;

    @FindBy(id = "form2:output2")
    private WebElement output2;

    @FindBy(id = "form3:input3")
    private WebElement input3;

    @FindBy(id = "form3:output3a")
    private WebElement output3a;

    @FindBy(id = "form3:output3b")
    private WebElement output3b;

    @FindBy(id = "form4:input4")
    private WebElement input4;

    @FindBy(id = "form4:output4a")
    private WebElement output4a;

    @FindBy(id = "form4:output4b")
    private WebElement output4b;

    @FindBy(id = "form4:output4c")
    private WebElement output4c;

    @FindBy(id = "form4:output4d")
    private WebElement output4d;

    @FindBy(id = "form5:input5")
    private WebElement input5;

    @FindBy(id = "form5:output5a")
    private WebElement output5a;

    @FindBy(id = "form5:output5b")
    private WebElement output5b;

    @FindBy(id = "form5:output5c")
    private WebElement output5c;

    @FindBy(id = "form6:input6")
    private WebElement input6;

    @Test
    void testComponentAttribute() {
        getPage("spec1507.xhtml");

        assertNotNull(input1.getDomAttribute("oninput"));

        assertEquals("", output1.getText());

        input1.sendKeys("abc");

        assertEquals("abc", output1.getText());
    }

    @Test
    void testAjaxEvent() {
        var page = getPage("spec1507.xhtml");

        assertNotNull(input2.getDomAttribute("oninput"));

        assertEquals("", output2.getText());

        page.guardAjax(() -> input2.sendKeys("abc" + Keys.TAB));

        assertEquals("abc", output2.getText());
    }

    @Test
    void testComponentAttributeAndSingleAjaxEvent() {
        var page = getPage("spec1507.xhtml");

        assertNotNull(input3.getDomAttribute("oninput"));

        assertEquals("", output3a.getText());
        assertEquals("", output3b.getText());

        page.guardAjax(() -> input3.sendKeys("abc" + Keys.TAB));

        assertEquals("abc", output3a.getText());
        assertEquals("abc", output3b.getText());
    }

    @Test
    void testComponentAttributeAndMultipleAjaxEvents() {
        var page = getPage("spec1507.xhtml");

        assertNotNull(input4.getDomAttribute("oninput"));

        assertEquals("", output4a.getText());
        assertEquals("", output4b.getText());
        assertEquals("", output4c.getText());

        page.guardAjax(() -> input4.sendKeys("abc" + Keys.TAB));

        assertEquals("abc", output4a.getText());
        assertEquals("abc", output4b.getText());
        assertEquals("abc", output4c.getText());
        assertEquals("abc", output4d.getText());
    }

    @Test
    void testMixedAjaxEvents() {
        var page = getPage("spec1507.xhtml");

        assertNotNull(input5.getDomAttribute("onkeydown"));
        assertNotNull(input5.getDomAttribute("oninput"));
        assertNotNull(input5.getDomAttribute("onchange"));

        assertEquals("", output5a.getText());
        assertEquals("", output5b.getText());
        assertEquals("", output5c.getText());

        page.guardAjax(() -> input5.sendKeys("abc" + Keys.TAB));

        assertEquals("abc", output5a.getText());
        assertEquals("abc", output5b.getText());
        assertEquals("abc", output5c.getText());
    }

    @Test
    void testCustomAjaxEvents() {
        getPage("spec1507.xhtml");
        
        assertNotNull(input6.getDomAttribute("onspec1507customevent1"));
        assertNotNull(input6.getDomAttribute("onspec1507customevent2"));
    }

    @Test
    void testUnsupportedAjaxEvent() {
        var page = getPage("spec1507.xhtml?renderInputWithUnsupportedAjaxEvent=true");

        assertTrue(page.getPageSource().contains("500"));
    }

}
