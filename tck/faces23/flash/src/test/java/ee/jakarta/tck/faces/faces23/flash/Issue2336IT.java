/*
 * Copyright (c) 2026 Contributors to Eclipse Foundation.
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

package ee.jakarta.tck.faces.faces23.flash;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * A flash value put during a chunked response (large enough to be written in multiple chunks) survives the
 * ajax re-render or the action-method navigation and is rendered via {@code flash.keep} on the target.
 */
class Issue2336IT extends BaseITNG {

    /**
     * Adds a value to the flash in an ajax actionListener whose response is large enough to be chunked, then
     * verifies the value is rendered via {@code flash.keep} in the re-rendered region.
     *
     * @see jakarta.faces.context.Flash
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2336
     */
    @Test
    void testFlashChunkingLink1() {
        WebPage page = getPage("issue2336.xhtml");
        page.guardAjax(() -> page.findElement(By.id("form:link1")).click());
        assertTrue(page.containsText("== Flash value LINK1 =="), "Flash value LINK1 must survive the chunked ajax response");
    }

    /**
     * Adds a value to the flash in an action-method navigation whose target page is large enough to be chunked,
     * then verifies the value is rendered via {@code flash.keep} on the target.
     *
     * @see jakarta.faces.context.Flash
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2336
     */
    @Test
    void testFlashChunkingLink2() {
        WebPage page = getPage("issue2336.xhtml");
        page.guardHttp(() -> page.findElement(By.id("form:link2")).click());
        assertTrue(page.containsText("== Flash value LINK2 =="), "Flash value LINK2 must survive the chunked navigation response");
    }
}
