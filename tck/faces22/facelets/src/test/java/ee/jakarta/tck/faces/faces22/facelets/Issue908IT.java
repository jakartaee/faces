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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.component.html.HtmlSelectBooleanCheckbox;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * Verifies that checking an immediate h:selectBooleanCheckbox whose valueChangeListener calls renderResponse and
 * which auto-submits onclick preserves the submitted value of a sibling non-immediate h:selectBooleanCheckbox.
 */
class Issue908IT extends BaseITNG {

    /**
     * Checks box1 (non-immediate), then box2 (immediate, valueChangeListener calls renderResponse, auto-submits
     * onclick) and asserts both checkboxes remain checked after the roundtrip.
     *
     * @see HtmlSelectBooleanCheckbox
     * @see https://github.com/eclipse-ee4j/mojarra/issues/908
     */
    @Test
    void testSelectBooleanCheckboxSubmittedValue() throws Exception {
        WebPage page = getPage("issue908.xhtml");

        WebElement box1 = page.findElement(By.id("box1"));
        assertNotNull(box1);
        box1.click();

        WebElement box2 = page.findElement(By.id("box2"));
        assertNotNull(box2);
        page.guardHttp(box2::click);

        assertTrue(page.findElement(By.id("box1")).isSelected(), "box1 must remain checked after the roundtrip");
        assertTrue(page.findElement(By.id("box2")).isSelected(), "box2 must remain checked after the roundtrip");
    }
}
