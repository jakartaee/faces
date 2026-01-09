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

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;

/**
 * https://github.com/jakartaee/faces/issues/1507
 */
class Spec1791IT extends BaseITNG {

    @FindBy(id = "form:selectItemArray")
    private WebElement selectItemArray;

    @FindBy(id = "form:selectItemArrayWithVar")
    private WebElement selectItemArrayWithVar;

    @FindBy(id = "form:selectItemList")
    private WebElement selectItemList;

    @FindBy(id = "form:selectItemListWithVar")
    private WebElement selectItemListWithVar;

    @FindBy(id = "form:selectItemMap")
    private WebElement selectItemMap;

    @FindBy(id = "form:selectItemMapWithVar")
    private WebElement selectItemMapWithVar;

    @Test
    void testSelectItemArray() {
        getPage("spec1791.xhtml");
        assertEquals("label1", new Select(selectItemArray).getFirstSelectedOption().getText());
        assertEquals("label: label1", new Select(selectItemArrayWithVar).getFirstSelectedOption().getText());
    }

    @Test
    void testSelectItemList() {
        getPage("spec1791.xhtml");
        assertEquals("label1", new Select(selectItemList).getFirstSelectedOption().getText());
        assertEquals("label: label1", new Select(selectItemListWithVar).getFirstSelectedOption().getText());
    }

    @Test
    void testSelectItemMap() {
        getPage("spec1791.xhtml");
        assertEquals("label1", new Select(selectItemMap).getFirstSelectedOption().getText());
        assertEquals("label: label1", new Select(selectItemMapWithVar).getFirstSelectedOption().getText());
    }

}
