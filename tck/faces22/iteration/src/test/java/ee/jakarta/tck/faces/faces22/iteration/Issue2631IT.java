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
 * c:forEach over a session-scoped collection that is mutated on postback reflects the current collection contents
 * in both plain EL output and inside an h:outputText component.
 */
class Issue2631IT extends BaseITNG {

    /**
     * Toggling the bean swaps the backing set; after the postback both the plain EL column and the h:outputText
     * column must show the newly selected set and none of the previous set's entries, and toggling back restores
     * the original set in both columns.
     *
     * @see jakarta.faces.component.UIComponent
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2631
     */
    @Test
    void testModifiedForEach() {
        WebPage page = getPage("issue2631.xhtml");

        page.guardHttp(() -> page.findElement(By.id("form:submit")).click());
        assertTrue(page.containsSource("2-SET2"), "First column reflects SET2 after toggle");
        assertTrue(page.containsSource("1-SET2"), "First column reflects SET2 after toggle");
        assertTrue(page.containsSource("0-SET2"), "First column reflects SET2 after toggle");
        assertFalse(page.containsSource("2-SET1"), "SET1 no longer shown after toggle");
        assertFalse(page.containsSource("1-SET1"), "SET1 no longer shown after toggle");
        assertFalse(page.containsSource("0-SET1"), "SET1 no longer shown after toggle");

        page.guardHttp(() -> page.findElement(By.id("form:submit")).click());
        assertFalse(page.containsSource("2-SET2"), "SET2 no longer shown after toggling back");
        assertFalse(page.containsSource("1-SET2"), "SET2 no longer shown after toggling back");
        assertFalse(page.containsSource("0-SET2"), "SET2 no longer shown after toggling back");
        assertTrue(page.containsSource("2-SET1"), "First column reflects SET1 after toggling back");
        assertTrue(page.containsSource("1-SET1"), "First column reflects SET1 after toggling back");
        assertTrue(page.containsSource("0-SET1"), "First column reflects SET1 after toggling back");
    }
}
