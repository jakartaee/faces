/*
 * Copyright (c) 2009, 2026 Oracle and/or its affiliates. All rights reserved.
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
package ee.jakarta.tck.faces.test.faces20.renderkit.grid;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class GridIT extends BaseITNG {

    private static final String[] EXPECTED_VALUES = { "3", "7", "31", "127", "8191", "131071" };

    @Test
    void gridRenderEncodeBasicTest() {
        WebPage page = getPage("faces/grid/encodetestBasic.xhtml");

        WebElement grid1 = findByIdSuffix(page, "grid1");
        assertEquals("color: yellow;", grid1.getDomAttribute("class"), "grid1 styleClass");
        assertNull(findOptionalChild(grid1, "caption"), "grid1 caption");
        assertNull(findOptionalChild(grid1, "thead"), "grid1 thead");
        validateBody(grid1, "grid1", 6, 1, null, null);

        WebElement grid2 = findByIdSuffix(page, "grid2");
        assertNull(findOptionalChild(grid2, "caption"), "grid2 caption");
        assertNull(findOptionalChild(grid2, "thead"), "grid2 thead");
        validateBody(grid2, "grid2", 3, 2, null, null);

        WebElement grid3 = findByIdSuffix(page, "grid3");
        assertNull(findOptionalChild(grid3, "caption"), "grid3 caption");
        assertNull(findOptionalChild(grid3, "thead"), "grid3 thead");
        validateBody(grid3, "grid3", 2, 3, null, null);

        WebElement grid4 = findByIdSuffix(page, "grid4");
        assertNull(findOptionalChild(grid4, "caption"), "grid4 caption");
        assertNull(findOptionalChild(grid4, "thead"), "grid4 thead");
        validateBody(grid4, "grid4", 1, 6, null, null);

        WebElement grid5 = findByIdSuffix(page, "grid5");
        assertNull(findOptionalChild(grid5, "caption"), "grid5 caption");
        assertNull(findOptionalChild(grid5, "thead"), "grid5 thead");
        validateBody(grid5, "grid5", 2, 3,
            new String[] { "odd", "even" },
            new String[] { "even", "odd", "even" });

        // grid6 is bound to GridUIBean which injects 2 columns containing 3 and 7.
        WebElement grid6 = findByIdSuffix(page, "grid6");
        validateBody(grid6, "grid6", 1, 2, null, new String[] { "odd", "even" });
    }

    @Test
    void gridRenderEncodeCaptionTest() {
        WebPage page = getPage("faces/grid/encodetestCaption.xhtml");

        WebElement grid1 = findByIdSuffix(page, "grid1");
        WebElement caption1 = grid1.findElement(By.tagName("caption"));
        assertEquals("Caption Text For Grid1", caption1.getText(), "grid1 caption text");
        assertNull(caption1.getDomAttribute("class"), "grid1 caption class");
        assertNull(caption1.getDomAttribute("style"), "grid1 caption style");
        validateBody(grid1, "grid1", 2, 3, null, null);

        WebElement grid2 = findByIdSuffix(page, "grid2");
        WebElement caption2 = grid2.findElement(By.tagName("caption"));
        assertEquals("Caption Text For Grid2", caption2.getText(), "grid2 caption text");
        assertEquals("Color: red;", caption2.getDomAttribute("style"), "grid2 caption style");
        assertNull(caption2.getDomAttribute("class"), "grid2 caption class");
        validateBody(grid2, "grid2", 3, 2, null, null);

        WebElement grid3 = findByIdSuffix(page, "grid3");
        WebElement caption3 = grid3.findElement(By.tagName("caption"));
        assertEquals("Caption Text For Grid3", caption3.getText(), "grid3 caption text");
        assertEquals("sansserif", caption3.getDomAttribute("class"), "grid3 caption class");
        assertNull(caption3.getDomAttribute("style"), "grid3 caption style");
        validateBody(grid3, "grid3", 2, 3, null, null);

        WebElement grid4 = findByIdSuffix(page, "grid4");
        WebElement caption4 = grid4.findElement(By.tagName("caption"));
        assertEquals("Caption Text For Grid4", caption4.getText(), "grid4 caption text");
        assertEquals("sansserif", caption4.getDomAttribute("class"), "grid4 caption class");
        assertEquals("Color: red;", caption4.getDomAttribute("style"), "grid4 caption style");
        validateBody(grid4, "grid4", 3, 2, null, null);
    }

    @Test
    void gridRenderEncodeTableHeaderFooterTest() {
        WebPage page = getPage("faces/grid/encodetestTableHeaderFooter.xhtml");

        WebElement grid1 = findByIdSuffix(page, "grid1");
        validateTableHeader(grid1, "grid1", "Header Text For Grid1", null);
        validateTableFooter(grid1, "grid1", "Footer Text For Grid1", null);
        validateBody(grid1, "grid1", 1, 3, null, null);

        WebElement grid2 = findByIdSuffix(page, "grid2");
        validateTableHeader(grid2, "grid2", "Header Text For Grid2", "sansserif");
        validateTableFooter(grid2, "grid2", "Footer Text For Grid2", "sansserif");
        validateBody(grid2, "grid2", 1, 3, null, null);
    }

    private static void validateTableHeader(WebElement grid, String id, String expectedText, String expectedClass) {
        WebElement thead = grid.findElement(By.tagName("thead"));
        List<WebElement> headerRows = thead.findElements(By.tagName("tr"));
        assertEquals(1, headerRows.size(), id + " thead row count");
        WebElement cell = headerRows.get(0).findElement(By.tagName("th"));
        assertEquals("3", cell.getDomAttribute("colspan"), id + " th colspan");
        assertEquals(expectedText, cell.getText(), id + " th text");
        assertEquals("colgroup", cell.getDomAttribute("scope"), id + " th scope");
        assertEquals(expectedClass, cell.getDomAttribute("class"), id + " th class");
    }

    private static void validateTableFooter(WebElement grid, String id, String expectedText, String expectedClass) {
        WebElement tfoot = grid.findElement(By.tagName("tfoot"));
        List<WebElement> footerRows = tfoot.findElements(By.tagName("tr"));
        assertEquals(1, footerRows.size(), id + " tfoot row count");
        WebElement cell = footerRows.get(0).findElement(By.tagName("td"));
        assertEquals("3", cell.getDomAttribute("colspan"), id + " footer td colspan");
        assertEquals(expectedText, cell.getText(), id + " footer td text");
        assertNull(cell.getDomAttribute("scope"), id + " footer td scope");
        assertEquals(expectedClass, cell.getDomAttribute("class"), id + " footer td class");
    }

    private static void validateBody(WebElement grid, String id, int expectedRows, int columns,
                                     String[] rowClasses, String[] columnClasses) {
        WebElement tbody = grid.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        assertEquals(expectedRows, rows.size(), id + " tbody row count");

        // Old TCK logic: rowLength = (EXPECTED_VALUES.length > totalCells) ? totalCells : EXPECTED_VALUES.length / rows.size()
        int totalCells = rows.size() * columns;
        int rowLength = (EXPECTED_VALUES.length > totalCells) ? totalCells : EXPECTED_VALUES.length / rows.size();

        for (int i = 0; i < rows.size(); i++) {
            WebElement row = rows.get(i);
            assertEquals(rowClasses == null ? null : rowClasses[i],
                row.getDomAttribute("class"), id + " row " + (i + 1) + " class");

            List<WebElement> cells = row.findElements(By.tagName("td"));
            for (int j = 0; j < rowLength; j++) {
                int cellNo = i * rowLength + j;
                WebElement cell = cells.get(j);
                assertEquals(EXPECTED_VALUES[cellNo], cell.getText(),
                    id + " cell(" + (i + 1) + "," + (j + 1) + ") text");
                assertEquals(columnClasses == null ? null : columnClasses[j],
                    cell.getDomAttribute("class"), id + " cell(" + (i + 1) + "," + (j + 1) + ") class");
            }
        }
    }

    private static WebElement findOptionalChild(WebElement parent, String tagName) {
        List<WebElement> children = parent.findElements(By.xpath("./" + tagName));
        return children.isEmpty() ? null : children.get(0);
    }

    private static WebElement findByIdSuffix(WebPage page, String id) {
        String suffix = ":" + id;
        return page.findElement(By.xpath(
            "//*[@id='" + id + "'"
            + " or substring(@id, string-length(@id) - " + (suffix.length() - 1) + ") = '" + suffix + "']"));
    }
}
