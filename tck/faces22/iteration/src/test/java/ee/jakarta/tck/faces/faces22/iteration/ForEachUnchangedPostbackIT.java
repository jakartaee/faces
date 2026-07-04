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
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * A c:forEach whose backing source is <em>not</em> mutated on postback must re-render every row unchanged, for each
 * source kind - List, array, begin/end integer range and Map. This complements Issue2631 (collection swapped on
 * postback) and Issue2896 (item added on postback), which both mutate; here the no-op postback must preserve each
 * iteration's generated id and value.
 */
class ForEachUnchangedPostbackIT extends BaseITNG {

    /**
     * After a postback that only bumps a counter and leaves every source untouched, each iteration of every source kind
     * must still be present with its original generated id and value.
     *
     * @see jakarta.faces.component.UIComponent#getClientId()
     */
    @Test
    void unchangedForEachRetainsRowsOnPostback() {
        WebPage page = getPage("foreach-unchanged-postback.xhtml");

        // Initial GET: every source rendered its three rows.
        assertRows(page);
        assertTrue(page.findElement(By.id("form:count")).getText().contains("postbacks=0"), "no postback yet");

        // Postback that does not touch any source.
        page.guardHttp(() -> page.findElement(By.id("form:submit")).click());

        // The postback executed and every row survived unchanged.
        assertTrue(page.findElement(By.id("form:count")).getText().contains("postbacks=1"), "postback executed");
        assertRows(page);
    }

    private void assertRows(WebPage page) {
        for (int i = 0; i < 3; i++) {
            assertEquals("list-" + (char) ('A' + i), page.findElement(By.id("form:list_" + i)).getText(), "list row " + i);
            assertEquals("array-" + (char) ('A' + i), page.findElement(By.id("form:array_" + i)).getText(), "array row " + i);
            assertEquals("range-" + i, page.findElement(By.id("form:range_" + i)).getText(), "range row " + i);
            assertEquals("map-" + (char) ('A' + i), page.findElement(By.id("form:map_" + i)).getText(), "map row " + i);
        }
    }
}
