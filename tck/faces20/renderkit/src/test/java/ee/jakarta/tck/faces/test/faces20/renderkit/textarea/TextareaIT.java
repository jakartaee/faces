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
package ee.jakarta.tck.faces.test.faces20.renderkit.textarea;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

class TextareaIT extends BaseITNG {

    @Test
    void textareaRenderEncodeTest() {
        WebPage page = getPage("faces/textarea/encodetest.xhtml");

        assertEquals("textarea value", findByIdSuffix(page, "textarea1").getText(), "textarea1 value");

        WebElement textarea2 = findByIdSuffix(page, "textarea2");
        assertTrue(textarea2.getText().length() > 0, "textarea2 has value");
        assertEquals("textarea", textarea2.getDomAttribute("class"), "textarea2 class");

        assertNotNull(findByIdSuffix(page, "textarea3").getDomAttribute("disabled"), "textarea3 disabled");
        assertNull(findByIdSuffix(page, "textarea4").getDomAttribute("disabled"), "textarea4 disabled");
        assertNotNull(findByIdSuffix(page, "textarea5").getDomAttribute("readonly"), "textarea5 readonly");
        assertNull(findByIdSuffix(page, "textarea6").getDomAttribute("readonly"), "textarea6 readonly");
    }

    @Test
    void textareaRenderDecodeTest() {
        WebPage page = getPage("faces/textarea/decodetest.xhtml");

        WebElement textarea1 = findByIdSuffix(page, "textarea1");
        textarea1.clear();
        textarea1.sendKeys("newSubmittedValue");

        WebElement button1 = findByIdSuffix(page, "button1");
        page.guardHttp(button1::click);

        assertEquals("newSubmittedValue", findByIdSuffix(page, "result").getDomAttribute("value"), "submitted value for textarea1");
    }

    @Test
    void textareaRenderPassthroughTest() {
        Map<String, String> control = commonPassthroughAttributes();
        verifyPassthroughAttributes(getPage("faces/textarea/passthroughtest.xhtml"), control);

        Map<String, String> faceletControl = new LinkedHashMap<>(control);
        faceletControl.put("foo", "bar");
        faceletControl.put("singleatt", "singleAtt");
        faceletControl.put("manyattone", "manyOne");
        faceletControl.put("manyatttwo", "manyTwo");
        faceletControl.put("manyattthree", "manyThree");
        verifyPassthroughAttributes(getPage("faces/textarea/passthroughtest_facelet.xhtml"), faceletControl);
    }

    private static Map<String, String> commonPassthroughAttributes() {
        Map<String, String> control = new LinkedHashMap<>();
        control.put("accesskey", "P");
        control.put("dir", "LTR");
        control.put("lang", "en");
        control.put("onblur", "js1");
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
        WebElement textarea = findByIdSuffix(page, "textarea1");
        expected.forEach((name, value) ->
            assertEquals(value, textarea.getDomAttribute(name), "attribute " + name));
    }

    private static WebElement findByIdSuffix(WebPage page, String id) {
        String suffix = ":" + id;
        return page.findElement(By.xpath(
            "//*[@id='" + id + "'"
            + " or substring(@id, string-length(@id) - " + (suffix.length() - 1) + ") = '" + suffix + "']"));
    }
}
