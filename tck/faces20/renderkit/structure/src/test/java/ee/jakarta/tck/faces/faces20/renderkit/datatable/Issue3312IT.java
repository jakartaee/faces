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
package ee.jakarta.tck.faces.faces20.renderkit.datatable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.component.UIData;
import jakarta.faces.component.html.HtmlDataTable;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue3312IT extends BaseITNG {

    /**
     * The regions a table level facet renders into, in the order the HTML table model prescribes.
     */
    private static final String[] REGIONS_IN_ORDER = {
        "<caption",
        "background-color:red",
        "background-color:yellow",
        "<thead",
        "<tfoot",
        "<tbody"
    };

    /**
     * A single data table carrying the caption, colgroups, header and footer facets simultaneously
     * must render every one of them, in the prescribed structural order, and must reproduce that
     * exact structure after a postback rebuilds the view.
     *
     * @see UIData#getFacet(String)
     * @see HtmlDataTable
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3312
     */
    @Test
    void testAllFacetsOnOneTableSurvivePostback() {
        WebPage page = getPage("datatable/issue3312.xhtml");
        assertStructure(page, "initial render");

        page.guardHttp(page.findElement(By.id("form:button"))::click);
        assertStructure(page, "postback");
    }

    private static void assertStructure(WebPage page, String request) {
        WebElement table = page.findElement(By.id("table"));
        String markup = table.getAttribute("outerHTML");

        int previous = -1;
        for (String region : REGIONS_IN_ORDER) {
            int index = markup.indexOf(region);
            assertTrue(index > previous,
                request + ": expected '" + region + "' after the preceding region, but found it at " + index
                    + " in:\n" + markup);
            previous = index;
        }

        assertEquals("My Caption", table.findElement(By.tagName("caption")).getText().trim(), request + " caption text");
        assertEquals("My Header", table.findElement(By.tagName("thead")).getText().trim(), request + " header text");
        assertEquals("My Footer", table.findElement(By.tagName("tfoot")).getText().trim(), request + " footer text");
    }
}
