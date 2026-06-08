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

package ee.jakarta.tck.faces.faces22.iteration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.component.UIData;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue2707IT extends BaseITNG {

    /**
     * Verifies that a NumberConverter installed dynamically per ui:repeat item (via a preRenderView listener on
     * the initial, non-postback request) is honored when each row's value is rendered, so the formatted values
     * (1.00, 2.00, ...) accumulate and persist correctly across non-ajax postbacks as items are added.
     *
     * @see jakarta.faces.component.UIData
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2707
     */
    @Test
    void testRepeatDynamicConverter() throws Exception {
        WebPage page = getPage("issue2707.xhtml");
        assertFalse(page.containsSource("value=\"1.00\""), "No items rendered before any are added");

        WebElement button = page.findElement(By.id("form:button"));
        page.guardHttp(button::click);
        assertTrue(page.containsSource("value=\"1.00\""), "First item rendered with dynamic converter");

        button = page.findElement(By.id("form:button"));
        page.guardHttp(button::click);
        assertTrue(page.containsSource("value=\"2.00\""), "Second item rendered with dynamic converter");

        button = page.findElement(By.id("form:button"));
        page.guardHttp(button::click);
        assertTrue(page.containsSource("value=\"3.00\""), "Third item rendered with dynamic converter");

        button = page.findElement(By.id("form:button"));
        page.guardHttp(button::click);
        assertTrue(page.containsSource("value=\"4.00\""), "Fourth item rendered with dynamic converter");
    }
}
