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

package ee.jakarta.tck.faces.faces22.facelets;

import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.component.html.HtmlInputText;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * Verifies that a style set programmatically on a bound h:inputText persists across an unrelated subsequent
 * postback.
 */
class Issue3879IT extends BaseITNG {

    /**
     * Clicks Step 1 to set background-color:blue on the bound h:inputText, asserts the style is rendered, then
     * clicks Step 2 (an unrelated no-op action) and asserts the style is still rendered.
     *
     * @see HtmlInputText#setStyle(String)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3879
     */
    @Test
    void testInputTextStyleDisappearing() throws Exception {
        WebPage page = getPage("issue3879.xhtml");

        WebElement changeColor = page.findElement(By.id("form:changeColor"));
        page.guardHttp(changeColor::click);
        assertTrue(page.containsSource("blue"), "Style must be rendered after Step 1");

        WebElement dummy = page.findElement(By.id("form:dummy"));
        page.guardHttp(dummy::click);
        assertTrue(page.containsSource("blue"), "Style must persist after the unrelated Step 2 postback");
    }
}
