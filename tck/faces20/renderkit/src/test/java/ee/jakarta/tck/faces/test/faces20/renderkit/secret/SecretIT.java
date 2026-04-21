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
package ee.jakarta.tck.faces.test.faces20.renderkit.secret;

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

class SecretIT extends BaseITNG {

    @Test
    void secretRenderEncodeTest() {
        WebPage page = getPage("faces/secret/encodetest.xhtml");

        // secret1: redisplay not defined -> value not rendered
        assertValueNotRendered(findByIdSuffix(page, "secret1"), "secret1");

        // secret2: redisplay=true -> value rendered
        assertEquals("secret value", findByIdSuffix(page, "secret2").getDomAttribute("value"), "secret2 value");

        // secret3: redisplay=false + styleClass=secret -> no value, class=secret
        WebElement secret3 = findByIdSuffix(page, "secret3");
        assertValueNotRendered(secret3, "secret3");
        assertEquals("secret", secret3.getDomAttribute("class"), "secret3 class");

        // secret4: autocomplete=off -> rendered as 'off'
        WebElement secret4 = findByIdSuffix(page, "secret4");
        assertValueNotRendered(secret4, "secret4");
        assertEquals("off", secret4.getDomAttribute("autocomplete"), "secret4 autocomplete");

        // secret5: autocomplete=on -> not rendered
        WebElement secret5 = findByIdSuffix(page, "secret5");
        assertValueNotRendered(secret5, "secret5");
        assertNull(secret5.getDomAttribute("autocomplete"), "secret5 autocomplete not rendered");

        // secret6: disabled=true -> attribute present
        assertNotNull(findByIdSuffix(page, "secret6").getDomAttribute("disabled"), "secret6 disabled");
        // secret7: disabled=false -> attribute absent
        assertNull(findByIdSuffix(page, "secret7").getDomAttribute("disabled"), "secret7 disabled");
        // secret8: readonly=true -> attribute present
        assertNotNull(findByIdSuffix(page, "secret8").getDomAttribute("readonly"), "secret8 readonly");
        // secret9: readonly=false -> attribute absent
        assertNull(findByIdSuffix(page, "secret9").getDomAttribute("readonly"), "secret9 readonly");
    }

    @Test
    void secretRenderDecodeTest() {
        WebPage page = getPage("faces/secret/decodetest.xhtml");

        WebElement secret1 = findByIdSuffix(page, "secret1");
        secret1.clear();
        secret1.sendKeys("newSubmittedValue");

        WebElement button1 = findByIdSuffix(page, "button1");
        page.guardHttp(button1::click);

        assertEquals("newSubmittedValue", findByIdSuffix(page, "result").getDomAttribute("value"), "submitted value for secret1");
    }

    @Test
    void secretRenderPassthroughTest() {
        Map<String, String> control = commonPassthroughAttributes();
        verifyPassthroughAttributes(getPage("faces/secret/passthroughtest.xhtml"), control);

        Map<String, String> faceletControl = new LinkedHashMap<>(control);
        faceletControl.put("foo", "bar");
        faceletControl.put("singleatt", "singleAtt");
        faceletControl.put("manyattone", "manyOne");
        faceletControl.put("manyatttwo", "manyTwo");
        faceletControl.put("manyattthree", "manyThree");
        verifyPassthroughAttributes(getPage("faces/secret/passthroughtest_facelet.xhtml"), faceletControl);
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
        WebElement input = findByIdSuffix(page, "pass1");
        expected.forEach((name, value) ->
            assertEquals(value, input.getDomAttribute(name), "attribute " + name));
    }

    // Mojarra renders value="" rather than omitting the attribute; both satisfy "no value rendered" per spec.
    private static void assertValueNotRendered(WebElement element, String id) {
        String value = element.getDomAttribute("value");
        assertTrue(value == null || value.isEmpty(), id + " value not rendered (was '" + value + "')");
    }

    private static WebElement findByIdSuffix(WebPage page, String id) {
        String suffix = ":" + id;
        return page.findElement(By.xpath(
            "//*[@id='" + id + "'"
            + " or substring(@id, string-length(@id) - " + (suffix.length() - 1) + ") = '" + suffix + "']"));
    }
}
