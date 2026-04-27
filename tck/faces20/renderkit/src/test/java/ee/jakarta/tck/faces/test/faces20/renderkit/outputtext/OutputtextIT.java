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
package ee.jakarta.tck.faces.test.faces20.renderkit.outputtext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class OutputtextIT extends BaseITNG {

    @Test
    void outputTextRenderEncodeTest() {
        WebPage page = getPage("faces/outputtext/encodetest.xhtml");

        // case 1: id and styleClass only
        assertEquals("text", findByIdSuffix(page, "text1").getDomAttribute("class"), "text1 class");

        // case 2: default escape=true -> value renders as literal "<p>" text
        assertEquals("<p>", findByIdSuffix(page, "text2").getText(), "text2 text");

        // case 3: escape=true explicit -> value renders as literal "<p>" text
        assertEquals("<p>", findByIdSuffix(page, "text3").getText(), "text3 text");

        // case 4: escape=false -> "<p>" emitted as raw HTML, span has no visible text content
        assertNotEquals("<p>", findByIdSuffix(page, "text4").getText(), "text4 text (escape=false)");

        // case 5: binding sets styleClass via bean
        assertEquals("text", findByIdSuffix(page, "text5").getDomAttribute("class"), "text5 class");
    }

    @Test
    void outputTextRenderPassthroughTest() {
        Map<String, String> control = commonPassthroughAttributes();
        verifyPassthroughAttributes(getPage("faces/outputtext/passthroughtest.xhtml"), control);

        Map<String, String> faceletControl = new LinkedHashMap<>(control);
        addFaceletExtras(faceletControl);
        verifyPassthroughAttributes(getPage("faces/outputtext/passthroughtest_facelet.xhtml"), faceletControl);
    }

    private static Map<String, String> commonPassthroughAttributes() {
        Map<String, String> control = new LinkedHashMap<>();
        control.put("dir", "LTR");
        control.put("lang", "en");
        control.put("style", "Color: red;");
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
        WebElement output = findByIdSuffix(page, "output1");
        expected.forEach((name, value) ->
            assertEquals(value, output.getDomAttribute(name), "output1 attribute " + name));
    }

    private static WebElement findByIdSuffix(WebPage page, String id) {
        String suffix = ":" + id;
        return page.findElement(By.xpath(
            "//*[@id='" + id + "'"
            + " or substring(@id, string-length(@id) - " + (suffix.length() - 1) + ") = '" + suffix + "']"));
    }
}
