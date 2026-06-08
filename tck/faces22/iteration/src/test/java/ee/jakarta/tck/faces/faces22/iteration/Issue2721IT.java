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

/**
 * Verifies that clearing a ui:repeat input field to empty submits an empty (null-or-empty) value into the
 * backing list, so the bean reports null-or-empty = true after the non-ajax submit.
 */
class Issue2721IT extends BaseITNG {

    /**
     * Loads a view with a single-element ui:repeat bound to an h:inputText pre-populated with "myString",
     * confirms the bean initially reports null-or-empty = false, clears the input, submits, and confirms the
     * empty value reached the backing list (null-or-empty = true).
     *
     * @see UIData
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2721
     */
    @Test
    void testIssue2721() throws Exception {
        WebPage page = getPage("issue2721.xhtml");
        assertTrue(page.findElement(By.id("repeat:0:input1")).getAttribute("value").contains("myString"),
                "Input is pre-populated with myString");
        assertTrue(page.containsText("isnull Or Empty ? false"), "Backing value is initially neither null nor empty");

        WebElement input = page.findElement(By.id("repeat:0:input1"));
        input.clear();
        assertFalse(page.findElement(By.id("repeat:0:input1")).getAttribute("value").contains("myString"),
                "Input value is cleared");

        WebElement submit = page.findElement(By.id("submit"));
        page.guardHttp(submit::click);
        assertTrue(page.containsText("isnull Or Empty ? true"), "Cleared value submits null or empty into the backing list");
    }
}
