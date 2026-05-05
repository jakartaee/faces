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
package ee.jakarta.tck.faces.test.faces20.renderkit.inputtext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class InputtextIT extends BaseITNG {

    @Test
    void inputTextRenderEncodeTest() {
        WebPage page = getPage("faces/inputtext/encodetest.xhtml");

        assertEquals("text value", findByIdSuffix(page, "text1").getDomAttribute("value"), "text1 value");

        WebElement text2 = findByIdSuffix(page, "text2");
        assertEquals("text value", text2.getDomAttribute("value"), "text2 value");
        assertEquals("text", text2.getDomAttribute("class"), "text2 class");

        WebElement text3 = findByIdSuffix(page, "text3");
        assertEquals("text value", text3.getDomAttribute("value"), "text3 value");
        assertEquals("off", text3.getDomAttribute("autocomplete"), "text3 autocomplete");

        WebElement text4 = findByIdSuffix(page, "text4");
        assertEquals("text value", text4.getDomAttribute("value"), "text4 value");
        assertNull(text4.getDomAttribute("autocomplete"), "text4 autocomplete not rendered");

        assertNotNull(findByIdSuffix(page, "text5").getDomAttribute("disabled"), "text5 disabled");
        assertNull(findByIdSuffix(page, "text6").getDomAttribute("disabled"), "text6 disabled");
        assertNotNull(findByIdSuffix(page, "text7").getDomAttribute("readonly"), "text7 readonly");
        assertNull(findByIdSuffix(page, "text8").getDomAttribute("readonly"), "text8 readonly");

        WebElement text9 = findByIdSuffix(page, "text9");
        assertEquals("hello", text9.getDomAttribute("value"), "text9 value (from binding)");
        assertEquals("10", text9.getDomAttribute("size"), "text9 size (from binding)");
        assertEquals("text", text9.getDomAttribute("class"), "text9 class (from binding)");
    }

    @Test
    void inputTextRenderDecodeTest() {
        WebPage page = getPage("faces/inputtext/decodetest.xhtml");

        WebElement text1 = findByIdSuffix(page, "text1");
        text1.clear();
        text1.sendKeys("newSubmittedValue");

        WebElement button1 = findByIdSuffix(page, "button1");
        page.guardHttp(button1::click);

        assertEquals("newSubmittedValue", findByIdSuffix(page, "result").getDomAttribute("value"), "submitted value for text1");
    }

    @Test
    void inputTextRenderPassthroughTest() {
        Map<String, String> control = commonPassthroughAttributes();
        verifyPassthroughAttributes(getPage("faces/inputtext/passthroughtest.xhtml"), control);

        Map<String, String> faceletControl = new LinkedHashMap<>(control);
        faceletControl.put("foo", "bar");
        faceletControl.put("singleatt", "singleAtt");
        faceletControl.put("manyattone", "manyOne");
        faceletControl.put("manyatttwo", "manyTwo");
        faceletControl.put("manyattthree", "manyThree");
        verifyPassthroughAttributes(getPage("faces/inputtext/passthroughtest_facelet.xhtml"), faceletControl);
    }

    private static Map<String, String> commonPassthroughAttributes() {
        Map<String, String> control = new LinkedHashMap<>();
        control.put("accesskey", "P");
        control.put("alt", "alt description");
        control.put("dir", "LTR");
        control.put("lang", "en");
        control.put("maxlength", "15");
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
        control.put("size", "15");
        control.put("style", "Color: red;");
        control.put("tabindex", "0");
        control.put("title", "title");
        return control;
    }

    private static void verifyPassthroughAttributes(WebPage page, Map<String, String> expected) {
        WebElement input = findByIdSuffix(page, "input1");
        expected.forEach((name, value) ->
            assertEquals(value, input.getDomAttribute(name), "attribute " + name));
    }

    private static WebElement findByIdSuffix(WebPage page, String id) {
        String suffix = ":" + id;
        return page.findElement(By.xpath(
            "//*[@id='" + id + "'"
            + " or substring(@id, string-length(@id) - " + (suffix.length() - 1) + ") = '" + suffix + "']"));
    }
}
