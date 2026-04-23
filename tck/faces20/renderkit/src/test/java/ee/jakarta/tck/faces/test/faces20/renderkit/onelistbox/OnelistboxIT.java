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
package ee.jakarta.tck.faces.test.faces20.renderkit.onelistbox;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class OnelistboxIT extends BaseITNG {

    @Test
    void oneListboxRenderEncodeTest() {
        WebPage page = getPage("faces/onelistbox/encodetest.xhtml");

        // listbox1: two options, none selected, size=2
        WebElement listbox1 = findByIdSuffix(page, "listbox1");
        List<WebElement> options1 = new Select(listbox1).getOptions();
        assertEquals(2, options1.size(), "listbox1 option count");
        assertEquals(0, new Select(listbox1).getAllSelectedOptions().size(), "listbox1 selected count");
        assertEquals("2", listbox1.getDomAttribute("size"), "listbox1 size");
        assertEquals("true", options1.get(0).getDomAttribute("value"), "listbox1 option 0 value");
        assertEquals("false", options1.get(1).getDomAttribute("value"), "listbox1 option 1 value");

        // listbox2: option 0 disabled with class Color: red;, option 1 enabled with class text
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

        // listbox5: disabled=false -> no attribute
        assertNull(findByIdSuffix(page, "listbox5").getDomAttribute("disabled"), "listbox5 disabled");

        // listbox6: readonly=true
        assertNotNull(findByIdSuffix(page, "listbox6").getDomAttribute("readonly"), "listbox6 readonly");

        // listbox7: readonly=false -> no attribute
        assertNull(findByIdSuffix(page, "listbox7").getDomAttribute("readonly"), "listbox7 readonly");

        // listbox8: size=5 preserved even with fewer options
        assertEquals("5", findByIdSuffix(page, "listbox8").getDomAttribute("size"), "listbox8 size");

        // listbox9: binding sets two items (no/yes), none selected
        WebElement listbox9 = findByIdSuffix(page, "listbox9");
        List<WebElement> options9 = new Select(listbox9).getOptions();
        assertEquals(2, options9.size(), "listbox9 option count");
        assertEquals(0, new Select(listbox9).getAllSelectedOptions().size(), "listbox9 selected count");
        assertEquals("no", options9.get(0).getDomAttribute("value"), "listbox9 option 0 value");
        assertEquals("yes", options9.get(1).getDomAttribute("value"), "listbox9 option 1 value");
    }

    @Test
    void oneListboxRenderDecodeTest() {
        WebPage page = getPage("faces/onelistbox/decodetest.xhtml");

        WebElement listbox1 = findByIdSuffix(page, "listbox1");
        assertEquals(0, new Select(listbox1).getAllSelectedOptions().size(), "listbox1 initial selected count");

        // Select first option (foo -> red) and submit
        new Select(findByIdSuffix(page, "listbox1")).selectByVisibleText("foo");
        page.guardHttp(findByIdSuffix(page, "button1")::click);

        List<WebElement> options = new Select(findByIdSuffix(page, "listbox1")).getOptions();
        assertTrue(options.get(0).isSelected(), "listbox1 option 0 selected after first submit");

        // Select second option (bar -> green) and submit
        new Select(findByIdSuffix(page, "listbox1")).selectByVisibleText("bar");
        page.guardHttp(findByIdSuffix(page, "button1")::click);

        options = new Select(findByIdSuffix(page, "listbox1")).getOptions();
        assertTrue(options.get(1).isSelected(), "listbox1 option 1 selected after second submit");
    }

    @Test
    void oneListboxRenderPassthroughTest() {
        Map<String, String> control = commonPassthroughAttributes();
        verifyPassthroughAttributes(getPage("faces/onelistbox/passthroughtest.xhtml"), control);

        Map<String, String> faceletControl = new LinkedHashMap<>(control);
        faceletControl.put("foo", "bar");
        faceletControl.put("singleatt", "singleAtt");
        faceletControl.put("manyattone", "manyOne");
        faceletControl.put("manyatttwo", "manyTwo");
        faceletControl.put("manyattthree", "manyThree");
        verifyPassthroughAttributes(getPage("faces/onelistbox/passthroughtest_facelet.xhtml"), faceletControl);
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
