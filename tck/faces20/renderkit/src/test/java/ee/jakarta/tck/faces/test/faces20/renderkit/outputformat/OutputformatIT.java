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
package ee.jakarta.tck.faces.test.faces20.renderkit.outputformat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class OutputformatIT extends BaseITNG {

    @Test
    void outputFormatRenderEncodeTest() {
        WebPage page = getPage("faces/outputformat/encodetest.xhtml");

        // case 1: id, value, styleClass
        assertEquals("text", findByIdSuffix(page, "formatter1").getDomAttribute("class"), "formatter1 class");

        // case 2: value uses f:param children
        assertEquals("Technology: JSF, Tag: h:outputFormat",
            findByIdSuffix(page, "formatter2").getText(), "formatter2 text");

        // case 3: default escape=true -> literal "<default>"
        assertEquals("<default>", findByIdSuffix(page, "formatter3").getText(), "formatter3 text");

        // case 4: escape=true -> literal "<true>"
        assertEquals("<true>", findByIdSuffix(page, "formatter4").getText(), "formatter4 text");

        // case 5: escape=false -> "<false>" rendered as raw HTML, span has no visible text
        assertNotEquals("<false>", findByIdSuffix(page, "formatter5").getText(), "formatter5 text (escape=false)");

        // case 6: f:param child binding values
        assertEquals("Technology: JSF, Tag: f:param",
            findByIdSuffix(page, "formatter6").getText(), "formatter6 text");

        // case 7: binding sets title, styleClass, value via bean
        WebElement formatter7 = page.findElement(By.xpath("//*[@title='formatter7']"));
        assertEquals("100-50", formatter7.getText(), "formatter7 text");
        assertEquals("text", formatter7.getDomAttribute("class"), "formatter7 class");
    }

    @Test
    void outputFormatRenderPassthroughTest() {
        Map<String, String> control = commonPassthroughAttributes();
        verifyPassthroughAttributes(getPage("faces/outputformat/passthroughtest.xhtml"), control);

        Map<String, String> faceletControl = new LinkedHashMap<>(control);
        addFaceletExtras(faceletControl);
        verifyPassthroughAttributes(getPage("faces/outputformat/passthroughtest_facelet.xhtml"), faceletControl);
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
        WebElement formatter = findByIdSuffix(page, "formatter1");
        expected.forEach((name, value) ->
            assertEquals(value, formatter.getDomAttribute(name), "formatter1 attribute " + name));
    }

    private static WebElement findByIdSuffix(WebPage page, String id) {
        String suffix = ":" + id;
        return page.findElement(By.xpath(
            "//*[@id='" + id + "'"
            + " or substring(@id, string-length(@id) - " + (suffix.length() - 1) + ") = '" + suffix + "']"));
    }
}
