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
package ee.jakarta.tck.faces.faces22.request_char_encoding;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * A multibyte character typed into an input must survive the request round-trip: the submitted value
 * is decoded with the correct request character encoding and rendered back unchanged.
 */
class Issue2221IT extends BaseITNG {

    /**
     * A Japanese character round-trips through submit and re-render.
     *
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2221
     * @see jakarta.faces.component.html.HtmlInputText
     */
    @Test
    void testJapanese() {
        assertRoundTrip("日");
    }

    /**
     * A Hebrew character round-trips through submit and re-render.
     *
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2221
     * @see jakarta.faces.component.html.HtmlInputText
     */
    @Test
    void testHebrew() {
        assertRoundTrip("א");
    }

    private void assertRoundTrip(String character) {
        WebPage page = getPage("issue2221.xhtml");
        assertTrue(page.containsText("Hello, my name is Duke. What's yours?"), "greeting rendered");

        page.findElement(By.id("form:username")).sendKeys(character);
        page.guardHttp(page.findElement(By.id("form:submit"))::click);

        assertTrue(page.findElement(By.id("form:echo")).getText().contains(character),
                "submitted character " + character + " must round-trip");
    }
}
