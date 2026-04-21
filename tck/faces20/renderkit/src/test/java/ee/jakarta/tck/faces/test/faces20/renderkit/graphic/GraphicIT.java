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
package ee.jakarta.tck.faces.test.faces20.renderkit.graphic;

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

class GraphicIT extends BaseITNG {

    private static final String EXPECTED_PNG = "pnglogo.png";
    private static final String EXPECTED_GIF = "duke-boxer.gif";

    @Test
    void graphicRenderEncodeTest() {
        WebPage page = getPage("faces/graphic/encodetest.xhtml");

        // img1: src resolves via ResourceHandler; no class attribute set
        WebElement img1 = findByIdSuffix(page, "img1");
        assertTrue(img1.getDomAttribute("src").contains(EXPECTED_PNG), "img1 src contains " + EXPECTED_PNG);
        assertNull(img1.getDomAttribute("class"), "img1 class (none expected)");

        // img2: styleClass renders as class
        WebElement img2 = findByIdSuffix(page, "img2");
        assertTrue(img2.getDomAttribute("src").contains(EXPECTED_PNG), "img2 src contains " + EXPECTED_PNG);
        assertEquals("newBorder", img2.getDomAttribute("class"), "img2 class");

        // img3: ismap=true -> attribute minimized (present)
        assertNotNull(findByIdSuffix(page, "img3").getDomAttribute("ismap"), "img3 ismap present");

        // img4: ismap=false -> attribute not rendered
        assertNull(findByIdSuffix(page, "img4").getDomAttribute("ismap"), "img4 ismap absent");

        // img5: binding sets title and styleClass via bean
        WebElement img5 = findByIdSuffix(page, "img5");
        assertEquals("myIMG", img5.getDomAttribute("title"), "img5 title (binding)");
        assertEquals("newBorder", img5.getDomAttribute("class"), "img5 class (binding)");

        // img6: EL resource expression for duke-boxer.gif resolves to a non-empty src
        WebElement img6 = findByIdSuffix(page, "img6");
        String img6Src = img6.getDomAttribute("src");
        assertNotNull(img6Src, "img6 src present");
        assertTrue(img6Src.contains(EXPECTED_GIF), "img6 src contains " + EXPECTED_GIF);
    }

    @Test
    void graphicRenderPassthroughTest() {
        Map<String, String> control = commonPassthroughAttributes();
        verifyPassthroughAttributes(getPage("faces/graphic/passthroughtest.xhtml"), control);

        Map<String, String> faceletControl = new LinkedHashMap<>(control);
        addFaceletExtras(faceletControl);
        verifyPassthroughAttributes(getPage("faces/graphic/passthroughtest_facelet.xhtml"), faceletControl);
    }

    private static Map<String, String> commonPassthroughAttributes() {
        Map<String, String> control = new LinkedHashMap<>();
        control.put("alt", "alt description");
        control.put("dir", "LTR");
        control.put("height", "10");
        control.put("lang", "en");
        control.put("longdesc", "description");
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
        control.put("style", "Color: red;");
        control.put("title", "title");
        control.put("usemap", "map");
        control.put("width", "10");
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
        WebElement img = findByIdSuffix(page, "img1");
        expected.forEach((name, value) ->
            assertEquals(value, img.getDomAttribute(name), "img1 attribute " + name));
    }

    private static WebElement findByIdSuffix(WebPage page, String id) {
        String suffix = ":" + id;
        return page.findElement(By.xpath(
            "//*[@id='" + id + "'"
            + " or substring(@id, string-length(@id) - " + (suffix.length() - 1) + ") = '" + suffix + "']"));
    }
}
