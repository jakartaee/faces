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

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;

class Spec329IT extends ITBase {

  /**
   * @see HtmlSelectOneRadio#getGroup()
     * @see https://github.com/jakartaee/faces/issues/329
   */
  @Test
  void spec329() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "spec329.xhtml");
        assertTrue(page.getHtmlElementById("messages").asNormalizedText().isEmpty());
        assertTrue(page.getHtmlElementById("inDataTableWithEntityList:selectedItem").asNormalizedText().isEmpty());
        assertTrue(page.getHtmlElementById("inRepeatWithSelectItemList:selectedItem").asNormalizedText().isEmpty());
        assertTrue(page.getHtmlElementById("multipleRadioButtonsWithStaticItemsInFirstRadio:selectedItem").asNormalizedText().isEmpty());
        assertTrue(page.getHtmlElementById("multipleRadioButtonsWithStaticItemsInEachRadio:selectedItem").asNormalizedText().isEmpty());
        assertTrue(page.getHtmlElementById("multipleRadioButtonsWithSelectItemList:selectedItem").asNormalizedText().isEmpty());

        page = ((HtmlSubmitInput) page.getHtmlElementById("inDataTableWithEntityList:button")).click();
        assertEquals("required", page.getHtmlElementById("messages").asNormalizedText()); // It should appear only once!
        
        HtmlRadioButtonInput inDataTableWithEntityListRadio = (HtmlRadioButtonInput) page.getHtmlElementById("inDataTableWithEntityList:table:1:radio");
        inDataTableWithEntityListRadio.setChecked(true);
        page = ((HtmlSubmitInput) page.getHtmlElementById("inDataTableWithEntityList:button")).click();
        
        System.out.println("\n\n***** " + page.getHtmlElementById("messages").asNormalizedText() + "\n\n\n");
        
        assertTrue(page.getHtmlElementById("messages").asNormalizedText().isEmpty());
        assertEquals("two", page.getHtmlElementById("inDataTableWithEntityList:selectedItem").asNormalizedText());

        page = ((HtmlSubmitInput) page.getHtmlElementById("inRepeatWithSelectItemList:button")).click();
        assertEquals("required", page.getHtmlElementById("messages").asNormalizedText()); // It should appear only once!

        HtmlRadioButtonInput inRepeatWithSelectItemListRadio = (HtmlRadioButtonInput) page.getHtmlElementById("inRepeatWithSelectItemList:repeat:1:radio");
        inRepeatWithSelectItemListRadio.setChecked(true);
        page = ((HtmlSubmitInput) page.getHtmlElementById("inRepeatWithSelectItemList:button")).click();
        assertTrue(page.getHtmlElementById("messages").asNormalizedText().isEmpty());
        assertEquals("value2", page.getHtmlElementById("inRepeatWithSelectItemList:selectedItem").asNormalizedText());

        page = ((HtmlSubmitInput) page.getHtmlElementById("multipleRadioButtonsWithStaticItemsInFirstRadio:button")).click();
        assertEquals("required1", page.getHtmlElementById("messages").asNormalizedText()); // It should appear only once for first component!

        HtmlRadioButtonInput multipleRadioButtonsWithStaticItemsInFirstRadio = (HtmlRadioButtonInput) page.getHtmlElementById("multipleRadioButtonsWithStaticItemsInFirstRadio:radio2");
        multipleRadioButtonsWithStaticItemsInFirstRadio.setChecked(true);
        page = ((HtmlSubmitInput) page.getHtmlElementById("multipleRadioButtonsWithStaticItemsInFirstRadio:button")).click();
        assertTrue(page.getHtmlElementById("messages").asNormalizedText().isEmpty());
        assertEquals("two", page.getHtmlElementById("multipleRadioButtonsWithStaticItemsInFirstRadio:selectedItem").asNormalizedText());

        page = ((HtmlSubmitInput) page.getHtmlElementById("multipleRadioButtonsWithStaticItemsInEachRadio:button")).click();
        assertEquals("required1", page.getHtmlElementById("messages").asNormalizedText()); // It should appear only once for first component!

        HtmlRadioButtonInput multipleRadioButtonsWithStaticItemsInEachRadio = (HtmlRadioButtonInput) page.getHtmlElementById("multipleRadioButtonsWithStaticItemsInEachRadio:radio2");
        multipleRadioButtonsWithStaticItemsInEachRadio.setChecked(true);
        page = ((HtmlSubmitInput) page.getHtmlElementById("multipleRadioButtonsWithStaticItemsInEachRadio:button")).click();
        assertTrue(page.getHtmlElementById("messages").asNormalizedText().isEmpty());
        assertEquals("two", page.getHtmlElementById("multipleRadioButtonsWithStaticItemsInEachRadio:selectedItem").asNormalizedText());

        page = ((HtmlSubmitInput) page.getHtmlElementById("multipleRadioButtonsWithSelectItemList:button")).click();
        assertEquals("required1", page.getHtmlElementById("messages").asNormalizedText()); // It should appear only once for first component!

        HtmlRadioButtonInput multipleRadioButtonsWithSelectItemListRadio = (HtmlRadioButtonInput) page.getHtmlElementById("multipleRadioButtonsWithSelectItemList:radio2");
        multipleRadioButtonsWithSelectItemListRadio.setChecked(true);
        page = ((HtmlSubmitInput) page.getHtmlElementById("multipleRadioButtonsWithSelectItemList:button")).click();
        assertTrue(page.getHtmlElementById("messages").asNormalizedText().isEmpty());
        assertEquals("value2", page.getHtmlElementById("multipleRadioButtonsWithSelectItemList:selectedItem").asNormalizedText());
    }

}
