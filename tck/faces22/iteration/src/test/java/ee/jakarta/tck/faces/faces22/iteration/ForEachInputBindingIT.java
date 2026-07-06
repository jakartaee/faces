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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * An h:inputText bound to a per-item property inside a c:forEach must, on postback, push each submitted value to the
 * matching model element - so the loop var has to resolve to the correct element during Update Model Values, not only on
 * render. Covers every source kind whose var addresses a mutable element: a List (IndexedValueExpression), an
 * insertion-ordered Set of mutable rows (IteratedValueExpression) and a Map (MappedValueExpression). Complements
 * ForEachUnchangedPostbackIT, which only reads the var while rendering.
 */
class ForEachInputBindingIT extends BaseITNG {

    private static final String[] IDS = { "form:list_0", "form:list_1", "form:list_2", "form:set_0", "form:set_1",
            "form:set_2", "form:map_0", "form:map_1", "form:map_2" };

    /**
     * Type a distinct value into every per-item input and submit; each value must land on the matching model element for
     * every source kind, and each input must redisplay its element's updated value.
     */
    @Test
    void perItemInputUpdatesMatchingModelElementOnPostback() {
        WebPage page = getPage("foreach-input-binding.xhtml");

        // Initial GET: each input shows its element's initial value.
        assertInputs(page, "L0", "L1", "L2", "S0", "S1", "S2", "M0", "M1", "M2");

        // Give every input a distinct new value.
        for (String id : IDS) {
            type(page, id, page.findElement(By.id(id)).getAttribute("value") + "x");
        }

        page.guardHttp(() -> page.findElement(By.id("form:submit")).click());

        // Each submitted value reached the matching model element, in order, for every source kind.
        assertEquals("L:L0x,L1x,L2x", page.findElement(By.id("form:listSummary")).getText(), "list model");
        assertEquals("S:S0x,S1x,S2x", page.findElement(By.id("form:setSummary")).getText(), "set model");
        assertEquals("M:M0x,M1x,M2x", page.findElement(By.id("form:mapSummary")).getText(), "map model");

        // And each input redisplays its element's updated value.
        assertInputs(page, "L0x", "L1x", "L2x", "S0x", "S1x", "S2x", "M0x", "M1x", "M2x");
    }

    private void assertInputs(WebPage page, String... expected) {
        for (int i = 0; i < IDS.length; i++) {
            assertEquals(expected[i], page.findElement(By.id(IDS[i])).getAttribute("value"), IDS[i]);
        }
    }

    private void type(WebPage page, String id, String value) {
        WebElement input = page.findElement(By.id(id));
        input.clear();
        input.sendKeys(value);
    }
}
