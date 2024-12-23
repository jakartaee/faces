/*
 * Copyright (c) 2024 Contributors to the Eclipse Foundation.
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

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;

/**
 * https://github.com/eclipse-ee4j/mojarra/issues/5507
 */
public class Issue5507IT extends BaseITNG {

    @FindBy(id = "form1:table:0:radio")
    private WebElement form1Radio1;

    @FindBy(id = "form1:table:1:radio")
    private WebElement form1Radio2;

    @FindBy(id = "form1:table:2:radio")
    private WebElement form1Radio3;

    @FindBy(id = "form1:table:3:radio")
    private WebElement form1Radio4;

    @FindBy(id = "form2:radio1")
    private WebElement form2Radio1;

    @FindBy(id = "form2:radio2")
    private WebElement form2Radio2;

    @FindBy(id = "form2:radio3")
    private WebElement form2Radio3;

    @FindBy(id = "form2:radio4")
    private WebElement form2Radio4;

    @Test
    public void testSelectOneRadioStyleClassAttributeRendering() {
        getPage("issue5507.xhtml");
        assertEquals("someStyleClass", form1Radio1.getAttribute("class"));
        assertEquals("someStyleClass", form1Radio2.getAttribute("class"));
        assertEquals("someStyleClass", form1Radio3.getAttribute("class"));
        assertEquals("someStyleClass", form1Radio4.getAttribute("class"));
        assertEquals("someStyleClass", form2Radio1.getAttribute("class"));
        assertEquals("otherStyleClass", form2Radio2.getAttribute("class"));
        assertEquals("", form2Radio3.getAttribute("class"));
        assertEquals("", form2Radio4.getAttribute("class"));
    }

    @Test
    public void testSelectOneRadioStyleAttributeRendering() {
        getPage("issue5507.xhtml");
        assertEquals("accent-color: blue;", form1Radio1.getAttribute("style"));
        assertEquals("accent-color: blue;", form1Radio2.getAttribute("style"));
        assertEquals("accent-color: blue;", form1Radio3.getAttribute("style"));
        assertEquals("accent-color: blue;", form1Radio4.getAttribute("style"));
        assertEquals("accent-color: red;", form2Radio1.getAttribute("style"));
        assertEquals("", form2Radio2.getAttribute("style"));
        assertEquals("", form2Radio3.getAttribute("style"));
        assertEquals("accent-color: green;", form2Radio4.getAttribute("style"));
    }

}
