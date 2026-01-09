/*
 * Copyright (c) Contributors to Eclipse Foundation.
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

package ee.jakarta.tck.faces.test.faces50.facelets;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;

class Spec2032IT extends BaseITNG {

    /**
     * @see com.sun.faces.facelets.tag.faces.PassThroughAttributeLibrary
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
}
