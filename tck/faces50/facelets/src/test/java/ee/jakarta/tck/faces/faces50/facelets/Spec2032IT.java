/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
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

package ee.jakarta.tck.faces.faces50.facelets;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Spec2032IT extends BaseITNG {

    /**
     * @see org.glassfish.mojarra.facelets.tag.faces.PassThroughAttributeLibrary
     * @see https://github.com/jakartaee/faces/issues/2032
     */
    @Test
    void test() throws Exception {
        var page = getPage("spec2032.xhtml");

        var table = page.findElement(By.id("table"));
        var theads = table.findElements(By.tagName("thead"));
        var tfoots = table.findElements(By.tagName("tfoot"));

        assertEquals(1, theads.size());
        assertEquals(1, tfoots.size());

        var theadRows = theads.get(0).findElements(By.tagName("tr"));
        var tfootRows = tfoots.get(0).findElements(By.tagName("tr"));

        assertEquals(2, theadRows.size());
        assertEquals(1, tfootRows.size());

        var captionCells = theadRows.get(0).findElements(By.tagName("th"));
        var theadCells = theadRows.get(1).findElements(By.tagName("th"));
        var tfootCells = tfootRows.get(0).findElements(By.tagName("td"));

        assertEquals(1, captionCells.size());
        assertEquals(2, theadCells.size());
        assertEquals(2, tfootCells.size());

        var captionCellRole = captionCells.get(0).getAttribute("role");
        var theadFirstCellStyle = theadCells.get(0).getAttribute("style");
        var theadFirstCellClass = theadCells.get(0).getAttribute("class");
        var theadFirstCellTitle = theadCells.get(0).getAttribute("title");
        var theadSecondCellStyle = theadCells.get(1).getAttribute("style");
        var theadSecondCellClass = theadCells.get(1).getAttribute("class");
        var theadSecondCellTitle = theadCells.get(1).getAttribute("title");
        var tfootFirstCellStyle = tfootCells.get(0).getAttribute("style");
        var tfootFirstCellClass = tfootCells.get(0).getAttribute("class");
        var tfootFirstCellTitle = tfootCells.get(0).getAttribute("title");
        var tfootSecondCellStyle = tfootCells.get(1).getAttribute("style");
        var tfootSecondCellClass = tfootCells.get(1).getAttribute("class");
        var tfootSecondCellTitle = tfootCells.get(1).getAttribute("title");

        assertEquals("banner", captionCellRole);
        assertEquals("color: green;", theadFirstCellStyle);
        assertEquals("icon-header", theadFirstCellClass);
        assertEquals(0, theadCells.get(0).findElements(By.xpath("./*")).size(), "the automatically created UIPanel renders nothing");
        assertEquals("", theadFirstCellTitle);
        assertEquals("", theadSecondCellStyle);
        assertEquals("", theadSecondCellClass);
        assertEquals("tooltip", theadSecondCellTitle);
        assertEquals("font-size: 8px;", tfootFirstCellStyle);
        assertEquals("my-footer", tfootFirstCellClass);
        assertEquals("", tfootFirstCellTitle);
        assertEquals("", tfootSecondCellStyle);
        assertEquals("", tfootSecondCellClass);
        assertEquals("", tfootSecondCellTitle);
    }

    /**
     * The pass-through attributes of an f:facet go on the element which the renderer starts around the facet, and those of the facet component on its own
     * markup. Neither gets those of the other. This holds for a single child component, which is the facet component, and for several child components, which
     * the automatically created UIPanel wraps. It holds on a postback and on an ajax re-render as well, and in every row of a UIData which preserves its row
     * state.
     *
     * @see https://github.com/jakartaee/faces/issues/2032
     * @see https://github.com/eclipse-ee4j/mojarra/issues/6173
     */
    @Test
    void testFacetAndItsComponentKeepTheirOwnPassThroughAttributes() throws Exception {
        var page = getPage("spec2032.xhtml");
        assertFacetAndComponentPassThroughAttributes(page);

        page.guardHttp(page.findElement(By.id("form:submit"))::click);
        assertFacetAndComponentPassThroughAttributes(page);

        page.guardAjax(page.findElement(By.id("form:ajax"))::click);
        assertFacetAndComponentPassThroughAttributes(page);
    }

    /**
     * The colgroups facet of h:dataTable has no element of its own around it, so the pass-through attributes of its f:facet are not rendered, neither on the
     * table nor on the caption before it.
     *
     * @see https://github.com/jakartaee/faces/issues/2032
     */
    @Test
    void testColgroupsFacetDoesNotRenderPassThroughAttributes() throws Exception {
        var page = getPage("spec2032Colgroups.xhtml");

        for (var id : new String[] { "captioned", "uncaptioned" }) {
            var table = page.findElement(By.id(id));
            assertEquals("", table.getAttribute("title"), id);
            assertEquals(1, table.findElements(By.tagName("colgroup")).size(), id);
        }

        var caption = page.findElement(By.id("captioned")).findElement(By.tagName("caption"));
        assertEquals("caption", caption.getAttribute("title"));
    }

    /**
     * FacetHandler.getPassThroughAttributes() returns the pass-through attributes of the f:facet which created the facet component as an unmodifiable map, and
     * an empty map for a component which no f:facet created. This holds on a postback as well.
     *
     * @see https://github.com/jakartaee/faces/issues/2032
     */
    @Test
    void testFacetHandlerReturnsPassThroughAttributesOfFacet() throws Exception {
        var page = getPage("spec2032Lookup.xhtml");
        assertLookup(page);

        page.guardHttp(page.findElement(By.id("form:submit"))::click);
        assertLookup(page);
    }

    private static void assertLookup(WebPage page) {
        assertEquals("{role=banner, title=facet}", page.findElement(By.id("form:facetAttributes")).getText());
        assertEquals("true", page.findElement(By.id("form:unmodifiable")).getText());
        assertEquals("{}", page.findElement(By.id("form:tableAttributes")).getText());
    }

    private static void assertFacetAndComponentPassThroughAttributes(WebPage page) {
        var headerCell = page.findElement(By.id("form:mixed")).findElement(By.tagName("thead")).findElement(By.tagName("th"));
        var own = page.findElement(By.id("form:mixed:own"));

        assertEquals("facet", headerCell.getAttribute("title"));
        assertEquals("", headerCell.getAttribute("lang"));
        assertEquals("en", own.getAttribute("lang"));
        assertEquals("", own.getAttribute("title"));

        var multiHeaderCell = page.findElement(By.id("form:multi")).findElement(By.tagName("thead")).findElement(By.tagName("th"));
        var first = page.findElement(By.id("form:multi:first"));
        var second = page.findElement(By.id("form:multi:second"));

        assertEquals("facet", multiHeaderCell.getAttribute("title"));
        assertEquals("", multiHeaderCell.getAttribute("lang"));
        assertEquals("", multiHeaderCell.getAttribute("dir"));
        assertEquals("en", first.getAttribute("lang"));
        assertEquals("", first.getAttribute("title"));
        assertEquals("ltr", second.getAttribute("dir"));
        assertEquals("", second.getAttribute("title"));
        assertEquals(2, multiHeaderCell.findElements(By.xpath("./*")).size(), "the UIPanel around the children renders no element");

        for (int row = 0; row < 2; row++) {
            var gridHeaderCell = page.findElement(By.id("form:rows:" + row + ":grid")).findElement(By.tagName("th"));
            assertEquals("row", gridHeaderCell.getAttribute("title"));
        }
    }

}
