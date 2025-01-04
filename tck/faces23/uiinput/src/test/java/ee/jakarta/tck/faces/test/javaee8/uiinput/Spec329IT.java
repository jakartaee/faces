/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2023 Contributors to Eclipse Foundation.
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

package ee.jakarta.tck.faces.test.javaee8.uiinput;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.component.html.HtmlSelectOneRadio;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class Spec329IT extends BaseITNG {

    /**
     * @see HtmlSelectOneRadio#getGroup()
     * @see https://github.com/jakartaee/faces/issues/329
     */
    @Test
    void spec329() throws Exception {
        WebPage page = getPage("spec329.xhtml");
        assertTrue(page.findElement(By.id("messages")).getText().isEmpty());
        assertTrue(page.findElement(By.id("inDataTableWithEntityList:selectedItem")).getText().isEmpty());
        assertTrue(page.findElement(By.id("inRepeatWithSelectItemList:selectedItem")).getText().isEmpty());
        assertTrue(page.findElement(By.id("multipleRadioButtonsWithStaticItemsInFirstRadio:selectedItem")).getText().isEmpty());
        assertTrue(page.findElement(By.id("multipleRadioButtonsWithStaticItemsInEachRadio:selectedItem")).getText().isEmpty());
        assertTrue(page.findElement(By.id("multipleRadioButtonsWithSelectItemList:selectedItem")).getText().isEmpty());

        (page.findElement(By.id("inDataTableWithEntityList:button"))).click();
        assertEquals("required", page.findElement(By.id("messages")).getText()); // It should appear only once!

        WebElement inDataTableWithEntityListRadio = page.findElement(By.id("inDataTableWithEntityList:table:1:radio"));
        inDataTableWithEntityListRadio.click();
        (page.findElement(By.id("inDataTableWithEntityList:button"))).click();

        assertTrue(page.findElement(By.id("messages")).getText().isEmpty());
        assertEquals("two", page.findElement(By.id("inDataTableWithEntityList:selectedItem")).getText());

        (page.findElement(By.id("inRepeatWithSelectItemList:button"))).click();
        assertEquals("required", page.findElement(By.id("messages")).getText()); // It should appear only once!

        WebElement inRepeatWithSelectItemListRadio = page.findElement(By.id("inRepeatWithSelectItemList:repeat:1:radio"));
        inRepeatWithSelectItemListRadio.click();
        (page.findElement(By.id("inRepeatWithSelectItemList:button"))).click();
        assertTrue(page.findElement(By.id("messages")).getText().isEmpty());
        assertEquals("value2", page.findElement(By.id("inRepeatWithSelectItemList:selectedItem")).getText());

        (page.findElement(By.id("multipleRadioButtonsWithStaticItemsInFirstRadio:button"))).click();
        assertEquals("required1", page.findElement(By.id("messages")).getText()); // It should appear only once for first component!

        WebElement multipleRadioButtonsWithStaticItemsInFirstRadio = page.findElement(By.id("multipleRadioButtonsWithStaticItemsInFirstRadio:radio2"));
        multipleRadioButtonsWithStaticItemsInFirstRadio.click();
        (page.findElement(By.id("multipleRadioButtonsWithStaticItemsInFirstRadio:button"))).click();
        assertTrue(page.findElement(By.id("messages")).getText().isEmpty());
        assertEquals("two", page.findElement(By.id("multipleRadioButtonsWithStaticItemsInFirstRadio:selectedItem")).getText());

        (page.findElement(By.id("multipleRadioButtonsWithStaticItemsInEachRadio:button"))).click();
        assertEquals("required1", page.findElement(By.id("messages")).getText()); // It should appear only once for first component!

        WebElement multipleRadioButtonsWithStaticItemsInEachRadio = page.findElement(By.id("multipleRadioButtonsWithStaticItemsInEachRadio:radio2"));
        multipleRadioButtonsWithStaticItemsInEachRadio.click();
        (page.findElement(By.id("multipleRadioButtonsWithStaticItemsInEachRadio:button"))).click();
        assertTrue(page.findElement(By.id("messages")).getText().isEmpty());
        assertEquals("two", page.findElement(By.id("multipleRadioButtonsWithStaticItemsInEachRadio:selectedItem")).getText());

        (page.findElement(By.id("multipleRadioButtonsWithSelectItemList:button"))).click();
        assertEquals("required1", page.findElement(By.id("messages")).getText()); // It should appear only once for first component!

        WebElement multipleRadioButtonsWithSelectItemListRadio = page.findElement(By.id("multipleRadioButtonsWithSelectItemList:radio2"));
        multipleRadioButtonsWithSelectItemListRadio.click();
        (page.findElement(By.id("multipleRadioButtonsWithSelectItemList:button"))).click();
        assertTrue(page.findElement(By.id("messages")).getText().isEmpty());
        assertEquals("value2", page.findElement(By.id("multipleRadioButtonsWithSelectItemList:selectedItem")).getText());
    }

}
