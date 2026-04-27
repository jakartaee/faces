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
package ee.jakarta.tck.faces.test.faces20.renderkit.onemenu;

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

class OnemenuIT extends BaseITNG {

    @Test
    void oneMenuRenderEncodeTest() {
        WebPage page = getPage("faces/onemenu/encodetest.xhtml");

        // menu1: two options, one default-selected (first)
        WebElement menu1 = findByIdSuffix(page, "menu1");
        List<WebElement> options1 = new Select(menu1).getOptions();
        assertEquals(2, options1.size(), "menu1 option count");
        assertEquals(1, new Select(menu1).getAllSelectedOptions().size(), "menu1 selected count");
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

        // menu5: disabled=false -> no attribute
        assertNull(findByIdSuffix(page, "menu5").getDomAttribute("disabled"), "menu5 disabled");

        // menu6: readonly=true
        assertNotNull(findByIdSuffix(page, "menu6").getDomAttribute("readonly"), "menu6 readonly");

        // menu7: readonly=false -> no attribute
        assertNull(findByIdSuffix(page, "menu7").getDomAttribute("readonly"), "menu7 readonly");

        // menu8: binding, two items, one default-selected
        WebElement menu8 = findByIdSuffix(page, "menu8");
        List<WebElement> options8 = new Select(menu8).getOptions();
        assertEquals(2, options8.size(), "menu8 option count");
        assertEquals(1, new Select(menu8).getAllSelectedOptions().size(), "menu8 selected count");
        assertEquals("no", options8.get(0).getDomAttribute("value"), "menu8 option 0 value");
        assertEquals("yes", options8.get(1).getDomAttribute("value"), "menu8 option 1 value");
    }

    @Test
    void oneMenuRenderDecodeTest() {
        WebPage page = getPage("faces/onemenu/decodetest.xhtml");

        WebElement menu1 = findByIdSuffix(page, "menu1");
        List<WebElement> initialSelected = new Select(menu1).getAllSelectedOptions();
        assertEquals(1, initialSelected.size(), "menu1 initial selected count");
        assertEquals("red", initialSelected.get(0).getDomAttribute("value"), "menu1 default selection");

        // Select first option (foo/red) and submit
        new Select(findByIdSuffix(page, "menu1")).selectByVisibleText("foo");
        page.guardHttp(findByIdSuffix(page, "button1")::click);

        List<WebElement> options = new Select(findByIdSuffix(page, "menu1")).getOptions();
        assertTrue(options.get(0).isSelected(), "menu1 option 0 selected after first submit");

        // Select second option (bar/green) and submit
        new Select(findByIdSuffix(page, "menu1")).selectByVisibleText("bar");
        page.guardHttp(findByIdSuffix(page, "button1")::click);

        options = new Select(findByIdSuffix(page, "menu1")).getOptions();
        assertTrue(options.get(1).isSelected(), "menu1 option 1 selected after second submit");
    }

    @Test
    void oneMenuRenderPassthroughTest() {
        Map<String, String> control = commonPassthroughAttributes();
        verifyPassthroughAttributes(getPage("faces/onemenu/passthroughtest.xhtml"), control);

        Map<String, String> faceletControl = new LinkedHashMap<>(control);
        faceletControl.put("foo", "bar");
        faceletControl.put("singleatt", "singleAtt");
        faceletControl.put("manyattone", "manyOne");
        faceletControl.put("manyatttwo", "manyTwo");
        faceletControl.put("manyattthree", "manyThree");
        verifyPassthroughAttributes(getPage("faces/onemenu/passthroughtest_facelet.xhtml"), faceletControl);
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
