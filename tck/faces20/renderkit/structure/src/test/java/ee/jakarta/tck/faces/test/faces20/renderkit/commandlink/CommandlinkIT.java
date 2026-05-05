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
package ee.jakarta.tck.faces.test.faces20.renderkit.commandlink;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class CommandlinkIT extends BaseITNG {

    @Test
    void commandLinkRenderEncodeTest() {
        WebPage page = getPage("faces/commandlink/encodetest.xhtml");

        WebElement link1 = findByIdSuffix(page, "link1");
        assertEquals("#", link1.getDomAttribute("href"), "link1 href");
        assertEquals("Click Me1", link1.getText(), "link1 text");
        assertNotNull(getBehaviorScript(page, link1), "link1 onclick script");

        WebElement link2 = findByIdSuffix(page, "link2");
        assertEquals("#", link2.getDomAttribute("href"), "link2 href");
        assertEquals("Click Me2", link2.getText(), "link2 text");
        assertEquals("sansserif", link2.getDomAttribute("class"), "link2 class");
        assertNotNull(getBehaviorScript(page, link2), "link2 onclick script");

        WebElement link3 = findByIdSuffix(page, "link3");
        assertEquals("#", link3.getDomAttribute("href"), "link3 href");
        assertEquals("Click Me3", link3.getText(), "link3 text");
        assertNotNull(getBehaviorScript(page, link3), "link3 onclick script");

        WebElement link5 = findByIdSuffix(page, "link5");
        assertEquals("sansserif", link5.getDomAttribute("class"), "link5 class");
        assertEquals("Disabled Link", link5.getText(), "link5 text");
        assertNull(getBehaviorScript(page, link5), "link5 no onclick (disabled)");

        WebElement link6 = findByIdSuffix(page, "link6");
        assertEquals("Disabled Link(Nested)", link6.getText(), "link6 text (nested, disabled)");

        WebElement link7 = findByIdSuffix(page, "link7");
        assertEquals("sansserif", link7.getDomAttribute("class"), "link7 class (from binding)");
        assertEquals("rectangle", link7.getDomAttribute("shape"), "link7 shape (from binding)");
        assertEquals("gone", link7.getDomAttribute("title"), "link7 title (from binding)");
    }

    @Test
    void commandLinkRenderDecodeTest() {
        WebPage page = getPage("faces/commandlink/decodetest.xhtml");

        assertEquals("", findByIdSuffix(page, "result").getText(), "result before click");

        WebElement link1 = findByIdSuffix(page, "link1");
        page.guardHttp(link1::click);

        assertEquals("PASSED", findByIdSuffix(page, "result").getText(), "result after link1 click");
    }

    @Test
    void commandLinkRenderPassthroughTest() {
        Map<String, String> linkControl = anchorPassthroughAttributes();
        Map<String, String> spanControl = spanPassthroughAttributes();

        WebPage legacy = getPage("faces/commandlink/passthroughtest.xhtml");
        verifyAttributes(legacy, "link1", linkControl);
        verifyAttributes(legacy, "link2", spanControl);

        addFaceletExtras(linkControl);
        addFaceletExtras(spanControl);
        WebPage facelet = getPage("faces/commandlink/passthroughtest_facelet.xhtml");
        verifyAttributes(facelet, "link1", linkControl);
        verifyAttributes(facelet, "link2", spanControl);
    }

    private static Map<String, String> anchorPassthroughAttributes() {
        Map<String, String> control = new LinkedHashMap<>();
        control.put("accesskey", "U");
        control.put("charset", "ISO-8859-1");
        control.put("coords", "31,45");
        control.put("dir", "LTR");
        control.put("hreflang", "en");
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
        control.put("rel", "somevalue");
        control.put("rev", "revsomevalue");
        control.put("shape", "rect");
        control.put("style", "Color: red;");
        control.put("tabindex", "0");
        control.put("title", "sample");
        control.put("type", "type");
        return control;
    }

    private static Map<String, String> spanPassthroughAttributes() {
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
        control.put("style", "Color: red;");
        control.put("tabindex", "0");
        control.put("title", "sample");
        return control;
    }

    private static void addFaceletExtras(Map<String, String> control) {
        control.put("foo", "bar");
        control.put("singleatt", "singleAtt");
        control.put("manyattone", "manyOne");
        control.put("manyatttwo", "manyTwo");
        control.put("manyattthree", "manyThree");
    }

    private static void verifyAttributes(WebPage page, String id, Map<String, String> expected) {
        WebElement element = findByIdSuffix(page, id);
        expected.forEach((name, value) ->
            assertEquals(value, element.getDomAttribute(name), id + " attribute " + name));
    }

    private static WebElement findByIdSuffix(WebPage page, String id) {
        String suffix = ":" + id;
        return page.findElement(By.xpath(
            "//*[@id='" + id + "'"
            + " or substring(@id, string-length(@id) - " + (suffix.length() - 1) + ") = '" + suffix + "']"));
    }
}
