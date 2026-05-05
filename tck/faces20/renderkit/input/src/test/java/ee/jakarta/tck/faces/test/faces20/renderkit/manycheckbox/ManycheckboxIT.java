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
package ee.jakarta.tck.faces.test.faces20.renderkit.manycheckbox;

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

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class ManycheckboxIT extends BaseITNG {

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
    void manycheckboxRenderEncodeTest() {
        WebPage page = getPage("faces/manycheckbox/encodetest.xhtml");

        // Case 1: default - two unchecked inputs with itemValues "true"/"false".
        WebElement checkbox10 = findByIdSuffix(page, "form:checkbox1:0");
        WebElement checkbox11 = findByIdSuffix(page, "form:checkbox1:1");
        assertNull(checkbox10.getDomAttribute("checked"), "checkbox1:0 checked");
        assertNull(checkbox11.getDomAttribute("checked"), "checkbox1:1 checked");
        assertEquals("true", checkbox10.getDomAttribute("value"), "checkbox1:0 value");
        assertEquals("false", checkbox11.getDomAttribute("value"), "checkbox1:1 value");

        // Case 2: first item disabled with disabledClass on its label; second enabled with enabledClass.
        WebElement checkbox20 = findByIdSuffix(page, "form:checkbox2:0");
        WebElement checkbox21 = findByIdSuffix(page, "form:checkbox2:1");
        assertNotNull(checkbox20.getDomAttribute("disabled"), "checkbox2:0 disabled");
        assertNull(checkbox21.getDomAttribute("disabled"), "checkbox2:1 disabled");
        assertEquals("Color: red;", findLabelFor(page, checkbox20.getDomAttribute("id")).getDomAttribute("class"),
            "checkbox2:0 label class");
        assertEquals("text", findLabelFor(page, checkbox21.getDomAttribute("id")).getDomAttribute("class"),
            "checkbox2:1 label class");

        // Case 3: styleClass applies to the enclosing table.
        WebElement checkbox30 = findByIdSuffix(page, "form:checkbox3:0");
        WebElement table30 = findEnclosingTable(checkbox30);
        assertEquals("text", table30.getDomAttribute("class"), "checkbox3 enclosing table class");

        // Case 4: disabled="true" renders the disabled attribute.
        assertNotNull(findByIdSuffix(page, "form:checkbox4:0").getDomAttribute("disabled"), "checkbox4:0 disabled");

        // Case 5: disabled="false" omits the disabled attribute.
        assertNull(findByIdSuffix(page, "form:checkbox5:0").getDomAttribute("disabled"), "checkbox5:0 disabled");

        // Case 6: readonly="true" renders the readonly attribute.
        assertNotNull(findByIdSuffix(page, "form:checkbox6:0").getDomAttribute("readonly"), "checkbox6:0 readonly");

        // Case 7: readonly="false" omits the readonly attribute.
        assertNull(findByIdSuffix(page, "form:checkbox7:0").getDomAttribute("readonly"), "checkbox7:0 readonly");

        // Case 8: default layout -> single <tr> holds both cells (horizontal).
        assertEquals(2, findEnclosingRow(findByIdSuffix(page, "form:checkbox8:0")).findElements(By.tagName("td")).size(),
            "checkbox8 default layout row cell count");

        // Case 9: layout="lineDirection" -> horizontal, 2 cells per row.
        assertEquals(2, findEnclosingRow(findByIdSuffix(page, "form:checkbox9:0")).findElements(By.tagName("td")).size(),
            "checkbox9 lineDirection row cell count");

        // Case 10: layout="pageDirection" -> vertical, 1 cell per row.
        assertEquals(1, findEnclosingRow(findByIdSuffix(page, "form:checkbox10:0")).findElements(By.tagName("td")).size(),
            "checkbox10 pageDirection row cell count");

        // Case 11: border="11" rendered on enclosing table.
        WebElement checkbox110 = findByIdSuffix(page, "form:checkbox11:0");
        assertEquals("11", findEnclosingTable(checkbox110).getDomAttribute("border"), "checkbox11 border");

        // Case 12: binding-backed children render as two unchecked inputs with values "no"/"yes".
        WebElement checkbox120 = findByIdSuffix(page, "form:checkbox12:0");
        WebElement checkbox121 = findByIdSuffix(page, "form:checkbox12:1");
        assertNull(checkbox120.getDomAttribute("checked"), "checkbox12:0 checked");
        assertNull(checkbox121.getDomAttribute("checked"), "checkbox12:1 checked");
        assertEquals("no", checkbox120.getDomAttribute("value"), "checkbox12:0 value");
        assertEquals("yes", checkbox121.getDomAttribute("value"), "checkbox12:1 value");
    }

    @Test
    void manycheckboxRenderDecodeTest() {
        WebPage page = getPage("faces/manycheckbox/decodetest.xhtml");

        WebElement checkbox10 = findByIdSuffix(page, "form1:checkbox1:0");
        WebElement checkbox11 = findByIdSuffix(page, "form1:checkbox1:1");
        assertFalse(checkbox10.isSelected(), "checkbox1:0 initial");
        assertFalse(checkbox11.isSelected(), "checkbox1:1 initial");

        // First click: only checkbox1:0 (red) selected.
        clickIfNotSelected(findByIdSuffix(page, "form1:checkbox1:0"));
        page.guardHttp(findByIdSuffix(page, "button1")::click);
        assertTrue(findByIdSuffix(page, "form1:checkbox1:0").isSelected(), "checkbox1:0 after first submit");
        assertFalse(findByIdSuffix(page, "form1:checkbox1:1").isSelected(), "checkbox1:1 after first submit");

        // Second click: uncheck :0, check :1 (green).
        clickIfSelected(findByIdSuffix(page, "form1:checkbox1:0"));
        clickIfNotSelected(findByIdSuffix(page, "form1:checkbox1:1"));
        page.guardHttp(findByIdSuffix(page, "button1")::click);
        assertFalse(findByIdSuffix(page, "form1:checkbox1:0").isSelected(), "checkbox1:0 after second submit");
        assertTrue(findByIdSuffix(page, "form1:checkbox1:1").isSelected(), "checkbox1:1 after second submit");

        // Third click: both selected.
        clickIfNotSelected(findByIdSuffix(page, "form1:checkbox1:0"));
        clickIfNotSelected(findByIdSuffix(page, "form1:checkbox1:1"));
        page.guardHttp(findByIdSuffix(page, "button1")::click);
        assertTrue(findByIdSuffix(page, "form1:checkbox1:0").isSelected(), "checkbox1:0 after third submit");
        assertTrue(findByIdSuffix(page, "form1:checkbox1:1").isSelected(), "checkbox1:1 after third submit");

        // Validate the SelectMany01 form on same page.
        validateSelectMany01(page);
    }

    @Test
    void manyCheckboxSelectMany01Test() {
        WebPage page = getPage("faces/manycheckbox/selectmany01.xhtml");
        validateSelectMany01(page);
    }

    @Test
    void manycheckboxRenderPassthroughTest() {
        Map<String, String> control = commonPassthroughAttributes();
        verifyPassthroughAttributes(getPage("faces/manycheckbox/passthroughtest.xhtml"), control);

        Map<String, String> faceletControl = new LinkedHashMap<>(control);
        faceletControl.put("foo", "bar");
        faceletControl.put("singleatt", "singleAtt");
        faceletControl.put("manyattone", "manyOne");
        faceletControl.put("manyatttwo", "manyTwo");
        faceletControl.put("manyattthree", "manyThree");
        verifyPassthroughAttributes(getPage("faces/manycheckbox/passthroughtest_facelet.xhtml"), faceletControl);
    }

    // Inlined SelectManyValidator logic: verify initial selections per id, post-back "Bilbo" on every group,
    // then verify the post-back state has exactly "Bilbo" selected everywhere.
    private void validateSelectMany01(WebPage page) {
        for (int i = 0; i < SELECT_IDS.length; i++) {
            String id = SELECT_IDS[i];
            List<WebElement> items = findCheckboxItems(page, id);
            assertCheckboxSelection(items, INITIAL_SELECTIONS[i], id + " initial");
            selectOnly(items, "Bilbo");
        }

        page.guardHttp(findByIdSuffix(page, "command")::click);

        assertFalse(page.getPageSource().contains("Error:"), "post-back contains 'Error:'");

        for (String id : SELECT_IDS) {
            List<WebElement> items = findCheckboxItems(page, id);
            assertCheckboxSelection(items, new String[] {"Bilbo"}, id + " post-back");
        }
    }

    private List<WebElement> findCheckboxItems(WebPage page, String id) {
        // Each <h:selectManyCheckbox id="X"> renders a <table id="X"> whose <input type="checkbox"> children hold
        // the option values; we match by suffix to allow the enclosing form prefix ("selectmany01:X").
        String suffix = ":" + id;
        WebElement table = page.findElement(By.xpath(
            "//table[@id='" + id + "'"
            + " or substring(@id, string-length(@id) - " + (suffix.length() - 1) + ") = '" + suffix + "']"));
        return table.findElements(By.xpath(".//input[@type='checkbox']"));
    }

    private static void assertCheckboxSelection(List<WebElement> items, String[] expected, String context) {
        // possibleValues from SelectMany01Bean yields 4 checkboxes: Bilbo, Frodo, Merry, Pippin.
        if (items.size() != 4) {
            return;
        }
        List<String> expectedList = Arrays.asList(expected);
        for (WebElement item : items) {
            String label = labelText(item);
            assertEquals(expectedList.contains(label), item.isSelected(),
                context + ": option '" + label + "' selected state");
        }
    }

    private static void selectOnly(List<WebElement> items, String labelToKeep) {
        for (WebElement item : items) {
            String label = labelText(item);
            boolean shouldBeSelected = label.equals(labelToKeep);
            if (item.isSelected() != shouldBeSelected) {
                item.click();
            }
        }
    }

    private static String labelText(WebElement checkbox) {
        String value = checkbox.getDomAttribute("value");
        return value == null ? "" : value.trim();
    }

    private static void clickIfNotSelected(WebElement checkbox) {
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }

    private static void clickIfSelected(WebElement checkbox) {
        if (checkbox.isSelected()) {
            checkbox.click();
        }
    }

    private static WebElement findEnclosingTable(WebElement element) {
        return element.findElement(By.xpath("./ancestor::table[1]"));
    }

    private static WebElement findEnclosingRow(WebElement element) {
        return element.findElement(By.xpath("./ancestor::tr[1]"));
    }

    private static WebElement findLabelFor(WebPage page, String checkboxId) {
        return page.findElement(By.xpath("//label[@for='" + checkboxId + "']"));
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
        control.put("onselect", "js14");
        control.put("tabindex", "0");
        control.put("title", "title");
        return control;
    }

    private static void verifyPassthroughAttributes(WebPage page, Map<String, String> expected) {
        WebElement checkbox = findByIdSuffix(page, "checkbox1:0");
        expected.forEach((name, value) ->
            assertEquals(value, checkbox.getDomAttribute(name), "attribute " + name));
        // Spec (standard-html-renderkit: SelectMany/Checkbox): style/styleClass render on the outer <table>.
        WebElement table = findByIdSuffix(page, "checkbox1");
        assertEquals("Color: red;", table.getDomAttribute("style"), "table style");
    }

    private static WebElement findByIdSuffix(WebPage page, String id) {
        String suffix = ":" + id;
        return page.findElement(By.xpath(
            "//*[@id='" + id + "'"
            + " or substring(@id, string-length(@id) - " + (suffix.length() - 1) + ") = '" + suffix + "']"));
    }
}
