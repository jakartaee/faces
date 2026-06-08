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

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * An h:dataTable built programmatically and added to a panelGroup by a commandButton's actionListener must render
 * its dynamic rows after submit.
 */
class Issue2627IT extends BaseITNG {

    /**
     * Initially the page has no table rows; after clicking the button the dynamically created h:dataTable renders
     * its values (one, two, three, four).
     *
     * @see jakarta.faces.component.html.HtmlDataTable
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2627
     */
    @Test
    void testDataTableDynamic() {
        WebPage page = getPage("issue2627.xhtml");
        assertFalse(page.containsText("one"), "one");
        assertFalse(page.containsText("two"), "two");
        assertFalse(page.containsText("three"), "three");
        assertFalse(page.containsText("four"), "four");

        page.guardHttp(() -> page.findElement(By.id("form:button")).click());

        assertTrue(page.containsText("one"), "one");
        assertTrue(page.containsText("two"), "two");
        assertTrue(page.containsText("three"), "three");
        assertTrue(page.containsText("four"), "four");
    }
}
