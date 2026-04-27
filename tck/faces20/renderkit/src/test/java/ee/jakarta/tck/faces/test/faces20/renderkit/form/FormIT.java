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
package ee.jakarta.tck.faces.test.faces20.renderkit.form;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class FormIT extends BaseITNG {

    private static final String METHOD_VALUE = "post";
    private static final String ENCTYPE_VALUE = "application/x-www-form-urlencoded";

    @Test
    void formRenderEncodeTest() {
        WebPage page = getPage("faces/form/encodetest.xhtml");

        WebElement form1 = findByIdSuffix(page, "form1");
        assertEquals(METHOD_VALUE, form1.getDomAttribute("method"), "form1 method");
        assertEquals(ENCTYPE_VALUE, form1.getDomAttribute("enctype"), "form1 enctype");
        assertNull(form1.getDomAttribute("class"), "form1 class not rendered");
        assertEquals(form1.getDomAttribute("id"), form1.getDomAttribute("name"), "form1 id equals name");

        WebElement form2 = findByIdSuffix(page, "form2");
        assertEquals(METHOD_VALUE, form2.getDomAttribute("method"), "form2 method");
        assertEquals(ENCTYPE_VALUE, form2.getDomAttribute("enctype"), "form2 enctype");
        assertEquals(form2.getDomAttribute("id"), form2.getDomAttribute("name"), "form2 id equals name");
        assertEquals("fancy", form2.getDomAttribute("class"), "form2 class");

        WebElement form3 = findByIdSuffix(page, "form3");
        assertEquals("fancy", form3.getDomAttribute("class"), "form3 class (from binding)");
    }

    @Test
    void formRenderDecodeTest() {
        WebPage page = getPage("faces/form/decodetest.xhtml");

        // Submit form1
        WebElement formOneInput = findByIdSuffix(page, "id1");
        formOneInput.clear();
        formOneInput.sendKeys("CLICKED");
        WebElement button1 = findByIdSuffix(page, "button1");
        page.guardHttp(button1::click);

        assertEquals("CLICKED", findByIdSuffix(page, "result1").getDomAttribute("value"), "form1 submitted, result1");
        assertEquals("", valueOrEmpty(findByIdSuffix(page, "result2")), "form1 submitted, result2");

        // Submit form2
        WebElement formTwoInput = findByIdSuffix(page, "id2");
        formTwoInput.clear();
        formTwoInput.sendKeys("CLICKED");
        WebElement button2 = findByIdSuffix(page, "button2");
        page.guardHttp(button2::click);

        assertEquals("", valueOrEmpty(findByIdSuffix(page, "result1")), "form2 submitted, result1");
        assertEquals("CLICKED", findByIdSuffix(page, "result2").getDomAttribute("value"), "form2 submitted, result2");
    }

    @Test
    void formRenderPassthroughTest() {
        Map<String, String> control = commonPassthroughAttributes();
        verifyPassthroughAttributes(getPage("faces/form/passthroughtest.xhtml"), control);

        Map<String, String> faceletControl = new LinkedHashMap<>(control);
        faceletControl.put("foo", "bar");
        faceletControl.put("singleatt", "singleAtt");
        faceletControl.put("manyattone", "manyOne");
        faceletControl.put("manyatttwo", "manyTwo");
        faceletControl.put("manyattthree", "manyThree");
        verifyPassthroughAttributes(getPage("faces/form/passthroughtest_facelet.xhtml"), faceletControl);
    }

    private static Map<String, String> commonPassthroughAttributes() {
        Map<String, String> control = new LinkedHashMap<>();
        control.put("accept", "text/html");
        control.put("accept-charset", "ISO-8859-1");
        control.put("dir", "LTR");
        control.put("enctype", "noneDefault");
        control.put("lang", "en");
        control.put("onclick", "js1");
        control.put("ondblclick", "js2");
        control.put("onkeydown", "js3");
        control.put("onkeypress", "js4");
        control.put("onkeyup", "js5");
        control.put("onmousedown", "js6");
        control.put("onmousemove", "js7");
        control.put("onmouseout", "js8");
        control.put("onmouseover", "js9");
        control.put("onmouseup", "js10");
        control.put("onreset", "js11");
        control.put("onsubmit", "js12");
        control.put("style", "Color: red;");
        control.put("target", "frame1");
        control.put("title", "FormTitle");
        return control;
    }

    private static void verifyPassthroughAttributes(WebPage page, Map<String, String> expected) {
        WebElement form = findByIdSuffix(page, "form1");
        expected.forEach((name, value) ->
            assertEquals(value, form.getDomAttribute(name), "attribute " + name));
    }

    private static String valueOrEmpty(WebElement element) {
        String value = element.getDomAttribute("value");
        return value == null ? "" : value;
    }

    private static WebElement findByIdSuffix(WebPage page, String id) {
        String suffix = ":" + id;
        return page.findElement(By.xpath(
            "//*[@id='" + id + "'"
            + " or substring(@id, string-length(@id) - " + (suffix.length() - 1) + ") = '" + suffix + "']"));
    }
}
