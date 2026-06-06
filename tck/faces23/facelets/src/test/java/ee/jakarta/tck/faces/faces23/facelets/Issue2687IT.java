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

package ee.jakarta.tck.faces.faces23.facelets;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.component.UIData;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue2687IT extends BaseITNG {

    /**
     * @see UIData#setRowStatePreserved(boolean)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2687
     */
    @Test
    void testDataTableRowStatePreserved() throws Exception {
        WebPage page = getPage("issue2687.xhtml");
        WebElement button = page.findElement(By.id("form:button"));
        page.guardHttp(button::click);
        assertTrue(page.findElements(By.id("form:table:inputText")).isEmpty(),
                "non-row-indexed per-row child clientId may not exist");
        assertFalse(page.findElements(By.id("form:table:0:inputText")).isEmpty(),
                "row-indexed per-row child clientId must exist");
    }

}
