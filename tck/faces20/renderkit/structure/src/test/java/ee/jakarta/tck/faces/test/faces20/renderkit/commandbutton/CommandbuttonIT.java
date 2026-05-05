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
package ee.jakarta.tck.faces.test.faces20.renderkit.commandbutton;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class CommandbuttonIT extends BaseITNG {

    @Test
    void cbuttonRenderEncodeNonPassthroughTest() {
        WebPage page = getPage("faces/commandbutton/encodetest.xhtml");

        WebElement button1 = findByIdSuffix(page, "command1");
        assertEquals("Click Me", button1.getDomAttribute("value"), "command1 value");
        assertEquals("submit", button1.getDomAttribute("type"), "command1 type");

        WebElement button2 = findByIdSuffix(page, "command2");
        assertEquals("Click Me", button2.getDomAttribute("value"), "command2 value");
        assertEquals("submit", button2.getDomAttribute("type"), "command2 type");

        WebElement button3 = findByIdSuffix(page, "command3");
        assertEquals("Click Me", button3.getDomAttribute("value"), "command3 value");
        assertEquals("reset", button3.getDomAttribute("type"), "command3 type");

        // command4: invalid type "Reset" falls back to "submit"
        WebElement button4 = findByIdSuffix(page, "command4");
        assertEquals("Click Me", button4.getDomAttribute("value"), "command4 value");
        assertEquals("submit", button4.getDomAttribute("type"), "command4 type");

        WebElement button5 = findByIdSuffix(page, "command5");
        assertEquals("Click Me", button5.getDomAttribute("value"), "command5 value");
        assertEquals("submit", button5.getDomAttribute("type"), "command5 type");
        assertEquals("Color: red;", button5.getDomAttribute("class"), "command5 class");

        // command6: image button -> type=image, src contains pnglogo.png, value not set to "Click Me"
        WebElement button6 = findByIdSuffix(page, "command6");
        assertNotEquals("Click Me", button6.getDomAttribute("value"), "command6 value not rendered");
        assertEquals("image", button6.getDomAttribute("type"), "command6 type");
        assertTrue(button6.getDomAttribute("src").contains("pnglogo.png"), "command6 src contains pnglogo.png");

        assertNotNull(findByIdSuffix(page, "command7").getDomAttribute("disabled"), "command7 disabled");
        assertNull(findByIdSuffix(page, "command8").getDomAttribute("disabled"), "command8 disabled");
        assertNotNull(findByIdSuffix(page, "command9").getDomAttribute("readonly"), "command9 readonly");
        // old TCK checked disabled here even though markup has readonly="false"; we check readonly to match markup intent
        assertNull(findByIdSuffix(page, "command10").getDomAttribute("readonly"), "command10 readonly");

        WebElement button11 = findByIdSuffix(page, "command11");
        assertEquals("blue", button11.getDomAttribute("class"), "command11 class (from binding)");
        assertEquals("onoff", button11.getDomAttribute("value"), "command11 value (from binding)");
        assertEquals("onoff", button11.getDomAttribute("title"), "command11 title (from binding)");
    }

    @Test
    void cbuttonRenderDecodeTest() {
        WebPage page = getPage("faces/commandbutton/decodetest.xhtml");

        assertEquals("", findByIdSuffix(page, "result").getText(), "result before click");

        WebElement button1 = findByIdSuffix(page, "command1");
        page.guardHttp(button1::click);

        assertEquals("PASSED", findByIdSuffix(page, "result").getText(), "result after command1 click");
    }

    @Test
    void cbuttonRenderPassthroughTest() {
        Map<String, String> control = commonPassthroughAttributes();
        verifyPassthroughAttributes(getPage("faces/commandbutton/passthroughtest.xhtml"), control);

        Map<String, String> faceletControl = new LinkedHashMap<>(control);
        faceletControl.put("foo", "bar");
        faceletControl.put("singleatt", "singleAtt");
        faceletControl.put("manyattone", "manyOne");
        faceletControl.put("manyatttwo", "manyTwo");
        faceletControl.put("manyattthree", "manyThree");
        verifyPassthroughAttributes(getPage("faces/commandbutton/passthroughtest_facelet.xhtml"), faceletControl);
    }

    private static Map<String, String> commonPassthroughAttributes() {
        Map<String, String> control = new LinkedHashMap<>();
        control.put("accesskey", "U");
        control.put("dir", "LTR");
        control.put("lang", "en");
        control.put("onblur", "js1");
        control.put("ondblclick", "js3");
        control.put("onfocus", "js4");
        control.put("onkeydown", "js5");
        control.put("onkeypress", "js6");
        control.put("onkeyup", "js7");
        control.put("onmousedown", "js8");
        control.put("onmousemove", "js9");
        control.put("onmouseout", "js10");
        control.put("onmouseover", "js11");
        control.put("onmouseup", "js12");
        control.put("onselect", "js13");
        control.put("style", "Color: red;");
        control.put("tabindex", "0");
        control.put("title", "sample");
        return control;
    }

    private static void verifyPassthroughAttributes(WebPage page, Map<String, String> expected) {
        WebElement button = findByIdSuffix(page, "command1");
        expected.forEach((name, value) ->
            assertEquals(value, button.getDomAttribute(name), "attribute " + name));
    }

    private static WebElement findByIdSuffix(WebPage page, String id) {
        String suffix = ":" + id;
        return page.findElement(By.xpath(
            "//*[@id='" + id + "'"
            + " or substring(@id, string-length(@id) - " + (suffix.length() - 1) + ") = '" + suffix + "']"));
    }
}
