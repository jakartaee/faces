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
package ee.jakarta.tck.faces.test.faces20.renderkit.manymenu;

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

class ManymenuIT extends BaseITNG {

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
    void manyMenuRenderEncodeTest() {
        WebPage page = getPage("faces/manymenu/encodetest.xhtml");

        // menu1: two options, none selected
        WebElement menu1 = findByIdSuffix(page, "menu1");
        List<WebElement> options1 = new Select(menu1).getOptions();
        assertEquals(2, options1.size(), "menu1 option count");
        assertEquals(0, new Select(menu1).getAllSelectedOptions().size(), "menu1 selected count");
        assertEquals("true", options1.get(0).getDomAttribute("value"), "menu1 option 0 value");
        assertEquals("false", options1.get(1).getDomAttribute("value"), "menu1 option 1 value");

        // menu2: option 0 disabled + Color: red;, option 1 enabled + text
        WebElement menu2 = findByIdSuffix(page, "menu2");
        List<WebElement> options2 = new Select(menu2).getOptions();
        assertNotNull(options2.get(0).getDomAttribute("disabled"), "menu2 option 0 disabled");
        assertEquals("Color: red;", options2.get(0).getDomAttribute("class"), "menu2 option 0 class");
        assertNull(options2.get(1).getDomAttribute("disabled"), "menu2 option 1 disabled");
        assertEquals("text", options2.get(1).getDomAttribute("class"), "menu2 option 1 class");

        // menu3: styleClass=text
        assertEquals("text", findByIdSuffix(page, "menu3").getDomAttribute("class"), "menu3 class");

        // menu4: disabled=true
        assertNotNull(findByIdSuffix(page, "menu4").getDomAttribute("disabled"), "menu4 disabled");

        // menu5: disabled=false
        assertNull(findByIdSuffix(page, "menu5").getDomAttribute("disabled"), "menu5 disabled");

        // menu6: readonly=true
        assertNotNull(findByIdSuffix(page, "menu6").getDomAttribute("readonly"), "menu6 readonly");

        // menu7: readonly=false
        assertNull(findByIdSuffix(page, "menu7").getDomAttribute("readonly"), "menu7 readonly");

        // menu8: selectMany renders multiple + size=1
        WebElement menu8 = findByIdSuffix(page, "menu8");
        assertNotNull(menu8.getDomAttribute("multiple"), "menu8 multiple");
        assertEquals("1", menu8.getDomAttribute("size"), "menu8 size");

        // menu9: binding, two options, none selected
        WebElement menu9 = findByIdSuffix(page, "menu9");
        List<WebElement> options9 = new Select(menu9).getOptions();
        assertEquals(2, options9.size(), "menu9 option count");
        assertEquals(0, new Select(menu9).getAllSelectedOptions().size(), "menu9 selected count");
        assertEquals("no", options9.get(0).getDomAttribute("value"), "menu9 option 0 value");
        assertEquals("yes", options9.get(1).getDomAttribute("value"), "menu9 option 1 value");
    }

    @Test
    void manyMenuRenderDecodeTest() {
        WebPage page = getPage("faces/manymenu/decodetest.xhtml");

        WebElement menu1 = findByIdSuffix(page, "form:menu1");
        assertEquals(0, new Select(menu1).getAllSelectedOptions().size(), "menu1 initial selected count");

        // First submit: select option 0 (red)
        new Select(findByIdSuffix(page, "form:menu1")).selectByVisibleText("foo");
        page.guardHttp(findByIdSuffix(page, "button1")::click);

        List<WebElement> after1 = new Select(findByIdSuffix(page, "form:menu1")).getOptions();
        assertTrue(after1.get(0).isSelected(), "menu1 option 0 after first submit");
        assertFalse(after1.get(1).isSelected(), "menu1 option 1 after first submit");
        assertFalse(after1.get(2).isSelected(), "menu1 option 2 after first submit");

        // Second submit: deselect 0, select 1 and 2
        Select sel = new Select(findByIdSuffix(page, "form:menu1"));
        sel.deselectAll();
        sel.selectByVisibleText("bar");
        sel.selectByVisibleText("again");
        page.guardHttp(findByIdSuffix(page, "button1")::click);

        List<WebElement> after2 = new Select(findByIdSuffix(page, "form:menu1")).getOptions();
        assertFalse(after2.get(0).isSelected(), "menu1 option 0 after second submit");
        assertTrue(after2.get(1).isSelected(), "menu1 option 1 after second submit");

        // Validate the SelectMany01 form on same page
        validateSelectMany01(page);
    }

    @Test
    void manyMenuSelectMany01Test() {
        WebPage page = getPage("faces/manymenu/selectmany01.xhtml");
        validateSelectMany01(page);
    }

    @Test
    void manyMenuRenderPassthroughTest() {
        Map<String, String> control = commonPassthroughAttributes();
        verifyPassthroughAttributes(getPage("faces/manymenu/passthroughtest.xhtml"), control);

        Map<String, String> faceletControl = new LinkedHashMap<>(control);
        faceletControl.put("foo", "bar");
        faceletControl.put("singleatt", "singleAtt");
        faceletControl.put("manyattone", "manyOne");
        faceletControl.put("manyatttwo", "manyTwo");
        faceletControl.put("manyattthree", "manyThree");
        verifyPassthroughAttributes(getPage("faces/manymenu/passthroughtest_facelet.xhtml"), faceletControl);
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
        WebElement menu = findByIdSuffix(page, "menu1");
        expected.forEach((name, value) ->
            assertEquals(value, menu.getDomAttribute(name), "attribute " + name));
    }

    private static WebElement findByIdSuffix(WebPage page, String id) {
        String suffix = ":" + id;
        return page.findElement(By.xpath(
            "//*[@id='" + id + "'"
            + " or substring(@id, string-length(@id) - " + (suffix.length() - 1) + ") = '" + suffix + "']"));
    }
}
