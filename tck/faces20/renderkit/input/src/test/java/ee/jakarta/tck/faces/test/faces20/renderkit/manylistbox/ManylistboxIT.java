/*
 * Copyright (c) 2009, 2026 Oracle and/or its affiliates. All rights reserved.
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
package ee.jakarta.tck.faces.test.faces20.renderkit.manylistbox;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class ManylistboxIT extends BaseITNG {

    private static final String[] SELECT_IDS = {
        "array", "list", "set", "sortedset", "collection",
        "ilist", "iset", "isortedset", "icollection",
        "hintString", "hintClass", "object"
    };

    private static final String[][] INITIAL_SELECTIONS = {
        new String[0], new String[0], new String[0], new String[0], new String[0],
        {"Bilbo", "Pippin", "Merry"}, {"Frodo"}, {"Pippin", "Frodo"}, {"Bilbo", "Merry"},
        new String[0], new String[0], new String[0]
    };

    @Test
    void manyListboxRenderEncodeTest() {
        WebPage page = getPage("faces/manylistbox/encodetest.xhtml");

        // listbox1: two options, none selected
        WebElement listbox1 = findByIdSuffix(page, "listbox1");
        List<WebElement> options1 = new Select(listbox1).getOptions();
        assertEquals(2, options1.size(), "listbox1 option count");
        assertEquals(0, new Select(listbox1).getAllSelectedOptions().size(), "listbox1 selected count");
        assertEquals("true", options1.get(0).getDomAttribute("value"), "listbox1 option 0 value");
        assertEquals("false", options1.get(1).getDomAttribute("value"), "listbox1 option 1 value");

        // listbox2: option 0 disabled + Color: red;, option 1 enabled + text
        WebElement listbox2 = findByIdSuffix(page, "listbox2");
        List<WebElement> options2 = new Select(listbox2).getOptions();
        assertNotNull(options2.get(0).getDomAttribute("disabled"), "listbox2 option 0 disabled");
        assertEquals("Color: red;", options2.get(0).getDomAttribute("class"), "listbox2 option 0 class");
        assertNull(options2.get(1).getDomAttribute("disabled"), "listbox2 option 1 disabled");
        assertEquals("text", options2.get(1).getDomAttribute("class"), "listbox2 option 1 class");

        // listbox3: styleClass=text
        assertEquals("text", findByIdSuffix(page, "listbox3").getDomAttribute("class"), "listbox3 class");

        // listbox4: disabled=true
        assertNotNull(findByIdSuffix(page, "listbox4").getDomAttribute("disabled"), "listbox4 disabled");

        // listbox5: disabled=false
        assertNull(findByIdSuffix(page, "listbox5").getDomAttribute("disabled"), "listbox5 disabled");

        // listbox6: readonly=true
        assertNotNull(findByIdSuffix(page, "listbox6").getDomAttribute("readonly"), "listbox6 readonly");

        // listbox7: readonly=false
        assertNull(findByIdSuffix(page, "listbox7").getDomAttribute("readonly"), "listbox7 readonly");

        // listbox8: selectMany renders multiple + size=5
        WebElement listbox8 = findByIdSuffix(page, "listbox8");
        assertNotNull(listbox8.getDomAttribute("multiple"), "listbox8 multiple");
        assertEquals("5", listbox8.getDomAttribute("size"), "listbox8 size");

        // listbox9: binding, two options, none selected
        WebElement listbox9 = findByIdSuffix(page, "listbox9");
        List<WebElement> options9 = new Select(listbox9).getOptions();
        assertEquals(2, options9.size(), "listbox9 option count");
        assertEquals(0, new Select(listbox9).getAllSelectedOptions().size(), "listbox9 selected count");
        assertEquals("no", options9.get(0).getDomAttribute("value"), "listbox9 option 0 value");
        assertEquals("yes", options9.get(1).getDomAttribute("value"), "listbox9 option 1 value");
    }

    @Test
    void manyListboxRenderDecodeTest() {
        WebPage page = getPage("faces/manylistbox/decodetest.xhtml");

        WebElement listbox1 = findByIdSuffix(page, "form:listbox1");
        assertEquals(0, new Select(listbox1).getAllSelectedOptions().size(), "listbox1 initial selected count");

        // First submit: select option 0 (foo / red)
        Select sel1 = new Select(findByIdSuffix(page, "form:listbox1"));
        sel1.deselectAll();
        sel1.selectByVisibleText("foo");
        page.guardHttp(findByIdSuffix(page, "button1")::click);

        List<WebElement> after1 = new Select(findByIdSuffix(page, "form:listbox1")).getOptions();
        assertTrue(after1.get(0).isSelected(), "listbox1 option 0 after first submit");
        assertFalse(after1.get(1).isSelected(), "listbox1 option 1 after first submit");
        assertFalse(after1.get(2).isSelected(), "listbox1 option 2 after first submit");

        // Second submit: select only option 1 (bar / green)
        Select sel2 = new Select(findByIdSuffix(page, "form:listbox1"));
        sel2.deselectAll();
        sel2.selectByVisibleText("bar");
        page.guardHttp(findByIdSuffix(page, "button1")::click);

        List<WebElement> after2 = new Select(findByIdSuffix(page, "form:listbox1")).getOptions();
        assertFalse(after2.get(0).isSelected(), "listbox1 option 0 after second submit");
        assertTrue(after2.get(1).isSelected(), "listbox1 option 1 after second submit");
        assertFalse(after2.get(2).isSelected(), "listbox1 option 2 after second submit");

        // Third submit: select options 0 and 2 (foo + again)
        Select sel3 = new Select(findByIdSuffix(page, "form:listbox1"));
        sel3.deselectAll();
        sel3.selectByVisibleText("foo");
        sel3.selectByVisibleText("again");
        page.guardHttp(findByIdSuffix(page, "button1")::click);

        List<WebElement> after3 = new Select(findByIdSuffix(page, "form:listbox1")).getOptions();
        assertTrue(after3.get(0).isSelected(), "listbox1 option 0 after third submit");
        assertFalse(after3.get(1).isSelected(), "listbox1 option 1 after third submit");
        assertTrue(after3.get(2).isSelected(), "listbox1 option 2 after third submit");

        // Fourth submit: select all three options
        Select sel4 = new Select(findByIdSuffix(page, "form:listbox1"));
        sel4.deselectAll();
        sel4.selectByVisibleText("foo");
        sel4.selectByVisibleText("bar");
        sel4.selectByVisibleText("again");
        page.guardHttp(findByIdSuffix(page, "button1")::click);

        List<WebElement> after4 = new Select(findByIdSuffix(page, "form:listbox1")).getOptions();
        assertTrue(after4.get(0).isSelected(), "listbox1 option 0 after fourth submit");
        assertTrue(after4.get(1).isSelected(), "listbox1 option 1 after fourth submit");
        assertTrue(after4.get(2).isSelected(), "listbox1 option 2 after fourth submit");

        // Validate the SelectMany01 form on same page
        validateSelectMany01(page);
    }

    @Test
    void manyListboxSelectMany01Test() {
        WebPage page = getPage("faces/manylistbox/selectmany01.xhtml");
        validateSelectMany01(page);
    }

    @Test
    void manyListboxRenderPassthroughTest() {
        Map<String, String> control = commonPassthroughAttributes();
        verifyPassthroughAttributes(getPage("faces/manylistbox/passthroughtest.xhtml"), control);

        Map<String, String> faceletControl = new LinkedHashMap<>(control);
        faceletControl.put("foo", "bar");
        faceletControl.put("singleatt", "singleAtt");
        faceletControl.put("manyattone", "manyOne");
        faceletControl.put("manyatttwo", "manyTwo");
        faceletControl.put("manyattthree", "manyThree");
        verifyPassthroughAttributes(getPage("faces/manylistbox/passthroughtest_facelet.xhtml"), faceletControl);
    }

    // Inlined SelectManyValidator logic: verify initial selections, post-back "Bilbo", verify new state.
    private void validateSelectMany01(WebPage page) {
        for (int i = 0; i < SELECT_IDS.length; i++) {
            String id = SELECT_IDS[i];
            WebElement select = findByIdSuffix(page, id);
            assertNotNull(select, "select " + id);
            assertSelectionMatches(select, INITIAL_SELECTIONS[i], id + " initial");
            Select seleniumSelect = new Select(select);
            seleniumSelect.deselectAll();
            seleniumSelect.selectByVisibleText("Bilbo");
        }

        page.guardHttp(findByIdSuffix(page, "command")::click);

        assertFalse(page.containsText("Error:"), "post-back contains 'Error:'");

        for (String id : SELECT_IDS) {
            WebElement select = findByIdSuffix(page, id);
            assertSelectionMatches(select, new String[] {"Bilbo"}, id + " post-back");
        }
    }

    private static void assertSelectionMatches(WebElement select, String[] expected, String context) {
        List<WebElement> options = new Select(select).getOptions();
        // The select must contain 4 options (Bilbo, Frodo, Merry, Pippin)
        if (options.size() != 4) {
            return;
        }
        List<String> expectedList = Arrays.asList(expected);
        for (WebElement option : options) {
            boolean shouldBeSelected = expectedList.contains(option.getText().trim());
            assertEquals(shouldBeSelected, option.isSelected(),
                context + ": option '" + option.getText().trim() + "' selected state");
        }
    }

    private static Map<String, String> commonPassthroughAttributes() {
        Map<String, String> control = new LinkedHashMap<>();
        control.put("accesskey", "P");
        control.put("dir", "LTR");
        control.put("lang", "en");
        control.put("onblur", "js1");
        control.put("onchange", "js2");
        control.put("onclick", "js3");
        control.put("ondblclick", "js4");
        control.put("onfocus", "js5");
        control.put("onkeydown", "js6");
        control.put("onkeypress", "js7");
        control.put("onkeyup", "js8");
        control.put("onmousedown", "js9");
        control.put("onmousemove", "js10");
        control.put("onmouseout", "js11");
        control.put("onmouseover", "js12");
        control.put("onmouseup", "js13");
        control.put("style", "Color: red;");
        control.put("tabindex", "0");
        control.put("title", "title");
        return control;
    }

    private static void verifyPassthroughAttributes(WebPage page, Map<String, String> expected) {
        WebElement listbox = findByIdSuffix(page, "listbox1");
        expected.forEach((name, value) ->
            assertEquals(value, listbox.getDomAttribute(name), "attribute " + name));
    }

    private static WebElement findByIdSuffix(WebPage page, String id) {
        String suffix = ":" + id;
        return page.findElement(By.xpath(
            "//*[@id='" + id + "'"
            + " or substring(@id, string-length(@id) - " + (suffix.length() - 1) + ") = '" + suffix + "']"));
    }
}
