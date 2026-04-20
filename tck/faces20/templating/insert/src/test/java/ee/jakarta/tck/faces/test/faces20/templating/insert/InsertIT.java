/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
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
package ee.jakarta.tck.faces.test.faces20.templating.insert;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class InsertIT extends BaseITNG {

    private static final Map<String, String> EXPECTED_DEFAULTS = new TreeMap<>();
    private static final Map<String, String> EXPECTED_OVERRIDES = new TreeMap<>();

    static {
        EXPECTED_DEFAULTS.put("title", "Default Title");
        EXPECTED_DEFAULTS.put("heading", "Default Heading");
        EXPECTED_DEFAULTS.put("content", "Default Content");

        EXPECTED_OVERRIDES.put("title", "OverRide Title");
        EXPECTED_OVERRIDES.put("heading", "OverRide Heading");
        EXPECTED_OVERRIDES.put("content", "OverRide Content");
    }

    @Test
    void templateInsertUICompositeTest() {
        WebPage pageOne = getPage("faces/compositionPgOne.xhtml");
        testPage(pageOne, EXPECTED_DEFAULTS);

        WebPage pageTwo = getPage("faces/compositionPgTwo.xhtml");
        testPage(pageTwo, EXPECTED_OVERRIDES);
    }

    @Test
    void templateInsertUICompositeNegTest() {
        WebPage pageThree = getPage("faces/compositionPgThree.xhtml");
        testPage(pageThree, EXPECTED_OVERRIDES);

        assertEquals(0, pageThree.findElements(By.id("IGNORED_TOP")).size(),
            "JSF should ignore everything outside of the composition tag (IGNORED_TOP)");
        assertEquals(0, pageThree.findElements(By.id("IGNORED_BOTTOM")).size(),
            "JSF should ignore everything outside of the composition tag (IGNORED_BOTTOM)");
    }

    @Test
    void templateInsertUIDecorateTest() {
        WebPage page = getPage("faces/decoratePgOne.xhtml");

        assertEquals(1, page.findElements(By.id("title_header_content")).size(),
            "Expected <div id='title_header_content'>");
        assertEquals("Default TitleDefault HeadingDefault Content".replaceAll("\\s+", ""),
            page.findElements(By.id("title_header_content")).get(0).getText().replaceAll("\\s+", ""),
            "Expected default composed content");
    }

    @Test
    void templateInsertUIDecorateOutSideTest() {
        WebPage page = getPage("faces/decoratePgTwo.xhtml");

        assertEquals(1, page.findElements(By.id("PROCESSED_TOP")).size(),
            "Expected <div id='PROCESSED_TOP'>");
        assertEquals(1, page.findElements(By.id("PROCESSED_BOTTOM")).size(),
            "Expected <div id='PROCESSED_BOTTOM'>");
    }

    private void testPage(WebPage page, Map<String, String> expected) {
        assertEquals(expected.get("title"), page.getTitle(),
            "Unexpected page title");

        assertTrue(page.findElements(By.id("heading")).size() == 1,
            "Expected <div id='heading'>");
        assertEquals(expected.get("heading"),
            page.findElements(By.id("heading")).get(0).getText(),
            "Unexpected heading value");

        assertTrue(page.findElements(By.id("content")).size() == 1,
            "Expected <div id='content'>");
        assertEquals(expected.get("content"),
            page.findElements(By.id("content")).get(0).getText(),
            "Unexpected content value");
    }
}
