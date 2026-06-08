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

package ee.jakarta.tck.faces.faces22.bean_lifecycle_events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.component.UIInput;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Spec566IT extends BaseITNG {

    /**
     * @see UIInput
     * @see https://github.com/jakartaee/faces/issues/566
     */
    @Test
    void testConfigurationEffective() throws Exception {
        WebPage page = getPage("spec566.xhtml");

        // Submit valid number values in both fields.
        WebElement input1 = page.findElement(By.id("input1"));
        input1.sendKeys("3");
        WebElement input2 = page.findElement(By.id("input2"));
        input2.sendKeys("4");
        page.findElement(By.id("reload")).click();

        // Clear the first field and enter an invalid value in the second, then submit again.
        input1 = page.findElement(By.id("input1"));
        input1.clear();
        input2 = page.findElement(By.id("input2"));
        input2.clear();
        input2.sendKeys("abcd");
        page.findElement(By.id("reload")).click();

        // The cleared valid field produces no validation error.
        WebElement message1 = page.findElement(By.id("input1Message"));
        assertFalse(message1.getText().contains("input1:"));

        // The invalid field produces a validation error.
        WebElement message2 = page.findElement(By.id("input2Message"));
        assertTrue(message2.getText().contains("input2:"));

        // The cleared field redisplays empty.
        input1 = page.findElement(By.id("input1"));
        assertEquals(0, input1.getAttribute("value").length());

        // The invalid field retains exactly the submitted raw value.
        input2 = page.findElement(By.id("input2"));
        assertEquals("abcd", input2.getAttribute("value"));
    }
}
