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
package ee.jakarta.tck.faces.test.faces20.renderkit.outputlink;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class OutputlinkIT extends BaseITNG {

    @Test
    void outputLinkRenderEncodeTest() {
        WebPage page = getPage("faces/outputlink/encodetest.xhtml");

        // case 1: id and styleClass
        assertEquals("text", findByIdSuffix(page, "case_one").getDomAttribute("class"), "case_one class");

        // case 2: target attribute rendered
        assertEquals("_top", findByIdSuffix(page, "case_two").getDomAttribute("target"), "case_two target");

        // case 3: binding sets styleClass via bean
        assertEquals("text", findByIdSuffix(page, "case_three").getDomAttribute("class"), "case_three class");
    }

    @Test
    void outputLinkRenderPassthroughTest() {
        // Legacy TCK had no non-facelet passthroughtest.xhtml for outputlink; only the facelet variant exists.
        Map<String, String> control = anchorPassthroughAttributes();
        addFaceletExtras(control);
        verifyPassthroughAttributes(getPage("faces/outputlink/passthroughtest_facelet.xhtml"), control);
    }

    private static Map<String, String> anchorPassthroughAttributes() {
        Map<String, String> control = new LinkedHashMap<>();
        control.put("accesskey", "P");
        control.put("charset", "ISO_8859-1:1987");
        control.put("coords", "nothing");
        control.put("dir", "LTR");
        control.put("hreflang", "en");
        control.put("lang", "en");
        control.put("onblur", "js1");
        control.put("onclick", "js2");
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
        control.put("rel", "somewhere");
        control.put("rev", "beenthere");
        control.put("shape", "poly");
        control.put("style", "Color: red;");
        control.put("tabindex", "1");
        control.put("title", "title");
        return control;
    }

    private static void addFaceletExtras(Map<String, String> control) {
        control.put("foo", "bar");
        control.put("singleatt", "singleAtt");
        control.put("manyattone", "manyOne");
        control.put("manyatttwo", "manyTwo");
        control.put("manyattthree", "manyThree");
    }

    private static void verifyPassthroughAttributes(WebPage page, Map<String, String> expected) {
        WebElement link = findByIdSuffix(page, "output1");
        expected.forEach((name, value) ->
            assertEquals(value, link.getDomAttribute(name), "output1 attribute " + name));
    }

    private static WebElement findByIdSuffix(WebPage page, String id) {
        String suffix = ":" + id;
        return page.findElement(By.xpath(
            "//*[@id='" + id + "'"
            + " or substring(@id, string-length(@id) - " + (suffix.length() - 1) + ") = '" + suffix + "']"));
    }
}
