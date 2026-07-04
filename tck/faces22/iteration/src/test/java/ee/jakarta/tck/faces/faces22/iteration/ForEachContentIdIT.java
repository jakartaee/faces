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
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * When a c:forEach list is replaced on postback with a same-size list of different values, the per-element build-time
 * attributes must be regenerated. Here each row's component id is derived from the item value, so an unchanged count
 * must not be mistaken for an unchanged iteration: the old content-derived ids must be gone and the new ones present.
 * This complements Issue2631 (which asserts changed <em>values</em>) by asserting changed <em>generated ids</em>.
 */
class ForEachContentIdIT extends BaseITNG {

    /**
     * @see jakarta.faces.component.UIComponent#getClientId()
     */
    @Test
    void sameCountContentChangeRegeneratesIds() {
        WebPage page = getPage("foreach-content-id.xhtml");

        // Initial GET: ids derived from a, b, c.
        assertEquals("a", page.findElement(By.id("form:item_a")).getText(), "item_a on initial render");
        assertEquals("b", page.findElement(By.id("form:item_b")).getText(), "item_b on initial render");
        assertEquals("c", page.findElement(By.id("form:item_c")).getText(), "item_c on initial render");

        // Replace with x, y, z - same count, different content.
        page.guardHttp(() -> page.findElement(By.id("form:replace")).click());

        // The new content-derived ids are present ...
        assertEquals("x", page.findElement(By.id("form:item_x")).getText(), "item_x after replace");
        assertEquals("y", page.findElement(By.id("form:item_y")).getText(), "item_y after replace");
        assertEquals("z", page.findElement(By.id("form:item_z")).getText(), "item_z after replace");

        // ... and the stale ones are gone (a same-count change must still be treated as a change).
        assertFalse(page.containsSource("form:item_a"), "stale id item_a gone after replace");
        assertFalse(page.containsSource("form:item_b"), "stale id item_b gone after replace");
        assertFalse(page.containsSource("form:item_c"), "stale id item_c gone after replace");
    }
}
