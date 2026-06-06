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

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * Verifies that a selectBooleanCheckbox nested inside two ui:repeat components retains correct per-row checked
 * state keyed by row index after a postback that sets only cell [0][0] true.
 */
class Issue1811IT extends BaseITNG {

    /**
     * Submits the form so the action sets only matrix[0][0] to true, then asserts each checkbox in the 2x2
     * nested ui:repeat reflects its own per-row state: only cell [0][0] is checked, all others remain unchecked.
     *
     * @see jakarta.faces.component.UIData
     * @see jakarta.faces.component.html.HtmlSelectBooleanCheckbox
     * @see https://github.com/eclipse-ee4j/mojarra/issues/1811
     */
    @Test
    void testRepeatNested() throws Exception {
        WebPage page = getPage("issue1811.xhtml");

        WebElement refresh = page.findElement(By.id("form:refresh"));
        page.guardHttp(refresh::click);

        WebElement cell0_0 = page.findElement(By.id("form:level1:0:level2:0:_"));
        assertTrue(cell0_0.isSelected(), "Cell [0][0] must be checked after refresh");

        WebElement cell0_1 = page.findElement(By.id("form:level1:0:level2:1:_"));
        assertFalse(cell0_1.isSelected(), "Cell [0][1] must remain unchecked after refresh");

        WebElement cell1_0 = page.findElement(By.id("form:level1:1:level2:0:_"));
        assertFalse(cell1_0.isSelected(), "Cell [1][0] must remain unchecked after refresh");

        WebElement cell1_1 = page.findElement(By.id("form:level1:1:level2:1:_"));
        assertFalse(cell1_1.isSelected(), "Cell [1][1] must remain unchecked after refresh");
    }
}
