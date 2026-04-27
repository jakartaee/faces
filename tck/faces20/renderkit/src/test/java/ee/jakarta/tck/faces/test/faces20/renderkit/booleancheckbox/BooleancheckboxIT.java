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
package ee.jakarta.tck.faces.test.faces20.renderkit.booleancheckbox;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

class BooleancheckboxIT extends BaseITNG {

    @Test
    void booleanCheckboxRenderEncodeTest() {
        WebPage page = getPage("faces/booleancheckbox/encodetest.xhtml");

        // case 1: only id defined, default value is false -> unchecked
        assertFalse(findByIdSuffix(page, "checkbox1").isSelected(), "checkbox1");

        // case 2: value=true -> checked
        assertTrue(findByIdSuffix(page, "checkbox2").isSelected(), "checkbox2");

        // case 3: value=false -> unchecked
        assertFalse(findByIdSuffix(page, "checkbox3").isSelected(), "checkbox3");

        // case 4: value=foo -> unchecked (non-boolean coerces to false)
        assertFalse(findByIdSuffix(page, "checkbox4").isSelected(), "checkbox4");

        // case 5: styleClass=text -> rendered as class="text"
        assertEquals("text", findByIdSuffix(page, "checkbox5").getDomAttribute("class"), "checkbox5 class");

        // case 6: disabled=true -> disabled attribute present (minimization)
        assertNotNull(findByIdSuffix(page, "checkbox6").getDomAttribute("disabled"), "checkbox6 disabled");

        // case 7: disabled=false -> disabled attribute absent
        assertNull(findByIdSuffix(page, "checkbox7").getDomAttribute("disabled"), "checkbox7 disabled");

        // case 8: readonly=true -> readonly attribute present (minimization)
        assertNotNull(findByIdSuffix(page, "checkbox8").getDomAttribute("readonly"), "checkbox8 readonly");

        // case 9: readonly=false -> readonly attribute absent
        assertNull(findByIdSuffix(page, "checkbox9").getDomAttribute("readonly"), "checkbox9 readonly");

        // case 10: binding sets disabled, styleClass, title via bean
        WebElement yesno = findByIdSuffix(page, "yesno");
        assertEquals("text", yesno.getDomAttribute("class"), "yesno class");
        assertNotNull(yesno.getDomAttribute("disabled"), "yesno disabled");
        assertEquals("yes&no", yesno.getDomAttribute("title"), "yesno title");
    }

    @Test
    void booleanCheckboxRenderDecodeTest() {
        WebPage page = getPage("faces/booleancheckbox/decodetest.xhtml");

        WebElement checkbox1 = findByIdSuffix(page, "checkbox1");
        assertFalse(checkbox1.isSelected(), "checkbox1 before click");

        checkbox1.click();
        WebElement button1 = findByIdSuffix(page, "button1");
        page.guardHttp(button1::click);

        assertTrue(findByIdSuffix(page, "checkbox1").isSelected(), "checkbox1 after submit");
    }

    @Test
    void booleanCheckboxRenderPassthroughTest() {
        Map<String, String> control = commonPassthroughAttributes();
        verifyPassthroughAttributes(getPage("faces/booleancheckbox/passthroughtest.xhtml"), control);

        Map<String, String> faceletControl = new LinkedHashMap<>(control);
        faceletControl.put("foo", "bar");
        faceletControl.put("singleatt", "singleAtt");
        faceletControl.put("manyattone", "manyOne");
        faceletControl.put("manyatttwo", "manyTwo");
        faceletControl.put("manyattthree", "manyThree");
        verifyPassthroughAttributes(getPage("faces/booleancheckbox/passthroughtest_facelet.xhtml"), faceletControl);
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
        control.put("style", "Color: red;");
        control.put("tabindex", "0");
        control.put("title", "title");
        return control;
    }

    private static void verifyPassthroughAttributes(WebPage page, Map<String, String> expected) {
        WebElement checkbox = findByIdSuffix(page, "checkbox1");
        expected.forEach((name, value) ->
            assertEquals(value, checkbox.getDomAttribute(name), "attribute " + name));
    }

    private static WebElement findByIdSuffix(WebPage page, String id) {
        String suffix = ":" + id;
        return page.findElement(By.xpath(
            "//*[@id='" + id + "'"
            + " or substring(@id, string-length(@id) - " + (suffix.length() - 1) + ") = '" + suffix + "']"));
    }
}
