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
package ee.jakarta.tck.faces.test.faces20.renderkit.oneradio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class OneradioIT extends BaseITNG {

    @Test
    void oneradioRenderEncodeTest() {
        WebPage page = getPage("faces/oneradio/encodetest.xhtml");

        // radio1: two radios, none checked, correct values
        WebElement radio10 = findByIdSuffix(page, "radio1:0");
        WebElement radio11 = findByIdSuffix(page, "radio1:1");
        assertFalse(radio10.isSelected(), "radio1:0 initial");
        assertFalse(radio11.isSelected(), "radio1:1 initial");
        assertEquals("true", radio10.getDomAttribute("value"), "radio1:0 value");
        assertEquals("false", radio11.getDomAttribute("value"), "radio1:1 value");

        // radio2: disabledClass/enabledClass + itemDisabled
        WebElement radio20 = findByIdSuffix(page, "radio2:0");
        WebElement radio21 = findByIdSuffix(page, "radio2:1");
        assertNotNull(radio20.getDomAttribute("disabled"), "radio2:0 disabled");
        assertEquals("Color: red;", findLabelFor(page, radio20.getAttribute("id")).getDomAttribute("class"),
            "radio2:0 label class");
        assertNull(radio21.getDomAttribute("disabled"), "radio2:1 disabled");
        assertEquals("text", findLabelFor(page, radio21.getAttribute("id")).getDomAttribute("class"),
            "radio2:1 label class");

        // radio3: styleClass=text on enclosing table
        WebElement radio30 = findByIdSuffix(page, "radio3:0");
        WebElement table3 = findAncestorTable(radio30);
        assertEquals("text", table3.getDomAttribute("class"), "radio3 table class");

        // radio4: disabled=true
        assertNotNull(findByIdSuffix(page, "radio4:0").getDomAttribute("disabled"), "radio4:0 disabled");

        // radio5: disabled=false
        assertNull(findByIdSuffix(page, "radio5:0").getDomAttribute("disabled"), "radio5:0 disabled");

        // radio6: readonly=true
        assertNotNull(findByIdSuffix(page, "radio6:0").getDomAttribute("readonly"), "radio6:0 readonly");

        // radio7: readonly=false
        assertNull(findByIdSuffix(page, "radio7:0").getDomAttribute("readonly"), "radio7:0 readonly");

        // radio8: default layout -> horizontal (one row with 2 cells)
        WebElement radio80 = findByIdSuffix(page, "radio8:0");
        assertEquals(2, countCellsInEnclosingRow(radio80), "radio8 default layout cells");

        // radio9: lineDirection -> horizontal (one row with 2 cells)
        WebElement radio90 = findByIdSuffix(page, "radio9:0");
        assertEquals(2, countCellsInEnclosingRow(radio90), "radio9 lineDirection cells");

        // radio10: pageDirection -> vertical (one cell per row)
        WebElement radio100 = findByIdSuffix(page, "radio10:0");
        assertEquals(1, countCellsInEnclosingRow(radio100), "radio10 pageDirection cells");

        // radio11: border=11 + escape handling
        WebElement radio110 = findByIdSuffix(page, "radio11:0");
        WebElement radio111 = findByIdSuffix(page, "radio11:1");
        WebElement table11 = findAncestorTable(radio110);
        assertEquals("11", table11.getDomAttribute("border"), "radio11 border");
        WebElement label110 = findLabelFor(page, radio110.getAttribute("id"));
        WebElement label111 = findLabelFor(page, radio111.getAttribute("id"));
        assertEquals("&foo", label110.getText().trim(), "radio11:0 label text (unescaped)");
        assertEquals("&bar", label111.getText().trim(), "radio11:1 label text (escaped)");
        assertTrue(page.containsSource("&amp;bar</label>"), "radio11:1 escaped in source");

        // radio12: binding, two radios, none checked
        WebElement radio120 = findByIdSuffix(page, "radio12:0");
        WebElement radio121 = findByIdSuffix(page, "radio12:1");
        assertFalse(radio120.isSelected(), "radio12:0 initial");
        assertFalse(radio121.isSelected(), "radio12:1 initial");
        assertEquals("no", radio120.getDomAttribute("value"), "radio12:0 value");
        assertEquals("yes", radio121.getDomAttribute("value"), "radio12:1 value");
    }

    @Test
    void oneradioRenderDecodeTest() {
        WebPage page = getPage("faces/oneradio/decodetest.xhtml");

        WebElement radio10 = findByIdSuffix(page, "radio1:0");
        WebElement radio11 = findByIdSuffix(page, "radio1:1");
        assertFalse(radio10.isSelected(), "radio1:0 initial");
        assertFalse(radio11.isSelected(), "radio1:1 initial");

        // Check radio1:0 and submit
        findByIdSuffix(page, "radio1:0").click();
        page.guardHttp(findByIdSuffix(page, "button1")::click);

        assertTrue(findByIdSuffix(page, "radio1:0").isSelected(), "radio1:0 after first submit");
        assertFalse(findByIdSuffix(page, "radio1:1").isSelected(), "radio1:1 after first submit");

        // Check radio1:1 and submit
        findByIdSuffix(page, "radio1:1").click();
        page.guardHttp(findByIdSuffix(page, "button1")::click);

        assertFalse(findByIdSuffix(page, "radio1:0").isSelected(), "radio1:0 after second submit");
        assertTrue(findByIdSuffix(page, "radio1:1").isSelected(), "radio1:1 after second submit");
    }

    @Test
    void oneradioRenderPassthroughTest() {
        Map<String, String> control = commonPassthroughAttributes();
        verifyPassthroughAttributes(getPage("faces/oneradio/passthroughtest.xhtml"), control);

        Map<String, String> faceletControl = new LinkedHashMap<>(control);
        faceletControl.put("foo", "bar");
        faceletControl.put("singleatt", "singleAtt");
        faceletControl.put("manyattone", "manyOne");
        faceletControl.put("manyatttwo", "manyTwo");
        faceletControl.put("manyattthree", "manyThree");
        verifyPassthroughAttributes(getPage("faces/oneradio/passthroughtest_facelet.xhtml"), faceletControl);
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
        WebElement radio = findByIdSuffix(page, "radio1:0");
        expected.forEach((name, value) ->
            assertEquals(value, radio.getDomAttribute(name), "attribute " + name));
    }

    private static WebElement findLabelFor(WebPage page, String forId) {
        return page.findElement(By.xpath("//label[@for='" + forId + "']"));
    }

    private static WebElement findAncestorTable(WebElement element) {
        return element.findElement(By.xpath("ancestor::table[1]"));
    }

    private static int countCellsInEnclosingRow(WebElement element) {
        List<WebElement> cells = element.findElement(By.xpath("ancestor::tr[1]")).findElements(By.xpath("./td"));
        return cells.size();
    }

    private static WebElement findByIdSuffix(WebPage page, String id) {
        String suffix = ":" + id;
        return page.findElement(By.xpath(
            "//*[@id='" + id + "'"
            + " or substring(@id, string-length(@id) - " + (suffix.length() - 1) + ") = '" + suffix + "']"));
    }
}
