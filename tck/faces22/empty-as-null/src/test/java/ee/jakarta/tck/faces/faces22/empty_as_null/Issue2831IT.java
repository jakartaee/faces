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

package ee.jakarta.tck.faces.faces22.empty_as_null;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * When {@code jakarta.faces.INTERPRET_EMPTY_STRING_SUBMITTED_VALUES_AS_NULL} is
 * {@code true}, an empty submitted value sets the model to {@code null} and a
 * value change event fires only on a real change.
 */
class Issue2831IT extends BaseITNG {

    /**
     * @see jakarta.faces.event.ValueChangeEvent
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2831
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2827
     */
    @Test
    void testVerifyEmptyAsNull() throws Exception {
        WebPage page = getPage("issue2831.xhtml");

        WebElement stringInput = page.findElement(By.id("form:string"));
        WebElement integerInput = page.findElement(By.id("form:integer"));
        assertNotNull(stringInput);
        assertNotNull(integerInput);
        assertEquals("", stringInput.getAttribute("value"));
        assertEquals("", integerInput.getAttribute("value"));

        assertTrue(page.containsText("VC1 Fired: false"));
        assertTrue(page.containsText("VC2 Fired: false"));
        assertTrue(page.containsText("String model set with null: false"));
        assertTrue(page.containsText("Integer model set with null: false"));

        // Submission 2: empty -> 11, both value change events must fire.
        stringInput.clear();
        stringInput.sendKeys("11");
        integerInput.clear();
        integerInput.sendKeys("11");
        page.guardHttp(page.findElement(By.id("form:command"))::click);

        stringInput = page.findElement(By.id("form:string"));
        integerInput = page.findElement(By.id("form:integer"));
        assertEquals("11", stringInput.getAttribute("value"));
        assertEquals("11", integerInput.getAttribute("value"));

        assertTrue(page.containsText("VC1 Fired: true"));
        assertTrue(page.containsText("VC2 Fired: true"));
        assertTrue(page.containsText("String model set with null: false"));
        assertTrue(page.containsText("Integer model set with null: false"));

        // Submission 3: 11 -> empty, value change events fire and model is set null.
        stringInput.clear();
        integerInput.clear();
        page.guardHttp(page.findElement(By.id("form:command"))::click);

        stringInput = page.findElement(By.id("form:string"));
        integerInput = page.findElement(By.id("form:integer"));
        assertEquals("", stringInput.getAttribute("value"));
        assertEquals("", integerInput.getAttribute("value"));

        assertTrue(page.containsText("VC1 Fired: true"));
        assertTrue(page.containsText("VC2 Fired: true"));
        assertTrue(page.containsText("String model set with null: true"));
        assertTrue(page.containsText("Integer model set with null: true"));

        // Submission 4: empty -> empty, no value change event and model stays null.
        stringInput.clear();
        integerInput.clear();
        page.guardHttp(page.findElement(By.id("form:command"))::click);

        stringInput = page.findElement(By.id("form:string"));
        integerInput = page.findElement(By.id("form:integer"));
        assertEquals("", stringInput.getAttribute("value"));
        assertEquals("", integerInput.getAttribute("value"));

        assertTrue(page.containsText("VC1 Fired: false"));
        assertTrue(page.containsText("VC2 Fired: false"));
        assertTrue(page.containsText("String model set with null: true"));
        assertTrue(page.containsText("Integer model set with null: true"));
    }
}
