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
package ee.jakarta.tck.faces.test.faces20.renderkit.datatable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class DatatableIT extends BaseITNG {

    private static final String[][] EXPECTED_BODY = {
        {"Anna", "f", "28"},
        {"Cort", "m", "7"},
        {"Cade", "m", "4"}
    };

    @Test
    void dtableRenderEncodeBasicTest() {
        WebPage page = getPage("faces/datatable/encodetestBasic.xhtml");

        // data1: no caption, no header, no row/column class attributes.
        WebElement data1 = findByIdSuffix(page, "data1");
        assertNull(findOptionalChild(data1, "caption"), "data1 caption");
        assertNull(findOptionalChild(data1, "thead"), "data1 thead");
        assertBodyRows(data1, "data1", null, null);

        // data2: rowClasses="odd,even" columnClasses="even,odd" -> repeats per row/col.
        WebElement data2 = findByIdSuffix(page, "data2");
        assertNull(findOptionalChild(data2, "caption"), "data2 caption");
        assertNull(findOptionalChild(data2, "thead"), "data2 thead");
        assertBodyRows(data2, "data2",
            new String[] {"odd", "even", "odd"},
            new String[] {"even", "odd", null});

        // data3: bound HtmlDataTable with title/bgcolor/border attributes set by bean.
        WebElement data3 = findByIdSuffix(page, "data3");
        assertEquals("Books", data3.getDomAttribute("title"), "data3 title");
        assertEquals("FFFF99", data3.getDomAttribute("bgcolor"), "data3 bgcolor");
        assertEquals("2", data3.getDomAttribute("border"), "data3 border");
    }

    @Test
    void dtableRenderEncodeCaptionTest() {
        WebPage page = getPage("faces/datatable/encodetestCaption.xhtml");

        // data1: caption facet, no captionClass/Style -> caption rendered with no style/class.
        WebElement data1 = findByIdSuffix(page, "data1");
        WebElement caption1 = data1.findElement(By.tagName("caption"));
        assertEquals("Caption Text For Data1", caption1.getText().trim(), "data1 caption text");
        assertNull(caption1.getDomAttribute("class"), "data1 caption class");
        assertNull(caption1.getDomAttribute("style"), "data1 caption style");
        assertBodyRows(data1, "data1", null, null);

        // data2: captionStyle only -> style rendered, class absent.
        WebElement data2 = findByIdSuffix(page, "data2");
        WebElement caption2 = data2.findElement(By.tagName("caption"));
        assertEquals("Caption Text For Data2", caption2.getText().trim(), "data2 caption text");
        assertEquals("Color: red;", caption2.getDomAttribute("style"), "data2 caption style");
        assertNull(caption2.getDomAttribute("class"), "data2 caption class");
        assertBodyRows(data2, "data2", null, null);

        // data3: captionClass only -> class rendered, style absent.
        WebElement data3 = findByIdSuffix(page, "data3");
        WebElement caption3 = data3.findElement(By.tagName("caption"));
        assertEquals("Caption Text For Data3", caption3.getText().trim(), "data3 caption text");
        assertEquals("sansserif", caption3.getDomAttribute("class"), "data3 caption class");
        assertNull(caption3.getDomAttribute("style"), "data3 caption style");
        assertBodyRows(data3, "data3", null, null);

        // data4: both captionClass and captionStyle -> both rendered.
        WebElement data4 = findByIdSuffix(page, "data4");
        WebElement caption4 = data4.findElement(By.tagName("caption"));
        assertEquals("Caption Text For Data4", caption4.getText().trim(), "data4 caption text");
        assertEquals("sansserif", caption4.getDomAttribute("class"), "data4 caption class");
        assertEquals("Color: red;", caption4.getDomAttribute("style"), "data4 caption style");
        assertBodyRows(data4, "data4", null, null);
    }

    @Test
    void dtableRenderEncodeColGrpTest() {
        WebPage page = getPage("faces/datatable/encodetestCaption.xhtml");

        // data5 defines a colgroups facet with three <colgroup> children, each with distinct align attribute.
        findByIdSuffix(page, "data5");
        assertEquals("left", findByIdSuffix(page, "lefty").getDomAttribute("align"), "lefty align");
        assertEquals("center", findByIdSuffix(page, "middle").getDomAttribute("align"), "middle align");
        assertEquals("right", findByIdSuffix(page, "righty").getDomAttribute("align"), "righty align");
    }

    @Test
    void dtableRenderEncodeTableHeaderFooterTest() {
        WebPage page = getPage("faces/datatable/encodetestTableHeaderFooter.xhtml");

        // data1: table-level header+footer -> single <tr> holding one th (scope=colgroup, colspan=3, no class)
        // and single <tr> holding one td (no scope, colspan=3, no class).
        WebElement data1 = findByIdSuffix(page, "data1");
        validateTableHeader(data1, "data1", "Header Text For Data1", null);
        validateTableFooter(data1, "data1", "Footer Text For Data1", null);
        assertBodyRows(data1, "data1", null, null);

        // data2: headerClass="sansserif" footerClass="sansserif" -> same as data1, plus class attribute.
        WebElement data2 = findByIdSuffix(page, "data2");
        validateTableHeader(data2, "data2", "Header Text For Data2", "sansserif");
        validateTableFooter(data2, "data2", "Footer Text For Data2", "sansserif");
        assertBodyRows(data2, "data2", null, null);
    }

    private static void validateTableHeader(WebElement table, String id, String expectedText, String expectedClass) {
        WebElement thead = table.findElement(By.tagName("thead"));
        List<WebElement> rows = thead.findElements(By.tagName("tr"));
        assertEquals(1, rows.size(), id + " thead row count");
        WebElement cell = rows.get(0).findElement(By.tagName("th"));
        assertEquals("3", cell.getDomAttribute("colspan"), id + " th colspan");
        assertEquals(expectedText, cell.getText().trim(), id + " th text");
        assertEquals("colgroup", cell.getDomAttribute("scope"), id + " th scope");
        assertEquals(expectedClass, cell.getDomAttribute("class"), id + " th class");
    }

    private static void validateTableFooter(WebElement table, String id, String expectedText, String expectedClass) {
        WebElement tfoot = table.findElement(By.tagName("tfoot"));
        List<WebElement> rows = tfoot.findElements(By.tagName("tr"));
        assertEquals(1, rows.size(), id + " tfoot row count");
        WebElement cell = rows.get(0).findElement(By.tagName("td"));
        assertEquals("3", cell.getDomAttribute("colspan"), id + " tfoot td colspan");
        assertEquals(expectedText, cell.getText().trim(), id + " tfoot td text");
        assertNull(cell.getDomAttribute("scope"), id + " tfoot td scope");
        assertEquals(expectedClass, cell.getDomAttribute("class"), id + " tfoot td class");
    }

    @Test
    void dtableRenderEncodeColumnHeaderFooterTest() {
        WebPage page = getPage("faces/datatable/encodetestColumnHeaderFooter.xhtml");

        // data1: per-column header/footer facets -> each th has scope=col + no class; each td has no scope + no class.
        assertColumnHeadersAndFooters(findByIdSuffix(page, "data1"), "data1",
            new String[] {"Name Header", "", "Age Header"},
            new String[] {"", "Gender Footer", "Age Footer"},
            null, null);
        assertBodyRows(findByIdSuffix(page, "data1"), "data1", null, null);

        // data2: headerClass+footerClass on table -> every th+td gets "sansserif" class.
        assertColumnHeadersAndFooters(findByIdSuffix(page, "data2"), "data2",
            new String[] {"Name Header", "", "Age Header"},
            new String[] {"", "Gender Footer", "Age Footer"},
            new String[] {"sansserif", "sansserif", "sansserif"},
            new String[] {"sansserif", "sansserif", "sansserif"});
        assertBodyRows(findByIdSuffix(page, "data2"), "data2", null, null);

        // data3: headerClass+footerClass on table AND on columns -> column-level overrides.
        assertColumnHeadersAndFooters(findByIdSuffix(page, "data3"), "data3",
            new String[] {"Name Header", "", "Age Header"},
            new String[] {"", "Gender Footer", "Age Footer"},
            new String[] {"columnClass", "sansserif", "sansserif"},
            new String[] {"sansserif", "sansserif", "columnClass"});
        assertBodyRows(findByIdSuffix(page, "data3"), "data3", null, null);
    }

    @Test
    void dtableRenderPassthroughTest() {
        Map<String, String> control = commonPassthroughAttributes();
        verifyPassthroughAttributes(getPage("faces/datatable/passthroughtest.xhtml"), control);

        Map<String, String> faceletControl = new LinkedHashMap<>(control);
        faceletControl.put("foo", "bar");
        faceletControl.put("singleatt", "singleAtt");
        faceletControl.put("manyattone", "manyOne");
        faceletControl.put("manyatttwo", "manyTwo");
        faceletControl.put("manyattthree", "manyThree");
        verifyPassthroughAttributes(getPage("faces/datatable/passthroughtest_facelet.xhtml"), faceletControl);
    }

    private static void assertColumnHeadersAndFooters(WebElement table, String id,
            String[] headerTexts, String[] footerTexts,
            String[] headerClasses, String[] footerClasses) {
        WebElement thead = table.findElement(By.tagName("thead"));
        List<WebElement> headerRows = thead.findElements(By.tagName("tr"));
        assertEquals(1, headerRows.size(), id + " header row count");
        List<WebElement> headerCells = headerRows.get(0).findElements(By.tagName("th"));
        for (int i = 0; i < headerTexts.length; i++) {
            WebElement cell = headerCells.get(i);
            assertEquals(headerTexts[i], cell.getText().trim(), id + " header cell[" + i + "] text");
            assertEquals("col", cell.getDomAttribute("scope"), id + " header cell[" + i + "] scope");
            assertEquals(headerClasses == null ? null : headerClasses[i],
                cell.getDomAttribute("class"), id + " header cell[" + i + "] class");
        }

        WebElement tfoot = table.findElement(By.tagName("tfoot"));
        List<WebElement> footerRows = tfoot.findElements(By.tagName("tr"));
        assertEquals(1, footerRows.size(), id + " footer row count");
        List<WebElement> footerCells = footerRows.get(0).findElements(By.tagName("td"));
        for (int i = 0; i < footerTexts.length; i++) {
            WebElement cell = footerCells.get(i);
            assertEquals(footerTexts[i], cell.getText().trim(), id + " footer cell[" + i + "] text");
            assertNull(cell.getDomAttribute("scope"), id + " footer cell[" + i + "] scope");
            assertEquals(footerClasses == null ? null : footerClasses[i],
                cell.getDomAttribute("class"), id + " footer cell[" + i + "] class");
        }
    }

    private static void assertBodyRows(WebElement table, String id, String[] rowClasses, String[] columnClasses) {
        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        assertEquals(EXPECTED_BODY.length, rows.size(), id + " tbody row count");

        for (int r = 0; r < EXPECTED_BODY.length; r++) {
            WebElement row = rows.get(r);
            List<WebElement> cells = row.findElements(By.tagName("td"));
            for (int c = 0; c < EXPECTED_BODY[r].length; c++) {
                WebElement cell = cells.get(c);
                assertEquals(EXPECTED_BODY[r][c], cell.getText().trim(),
                    id + " body[" + r + "][" + c + "] text");
                assertEquals(columnClasses == null ? null : columnClasses[c],
                    cell.getDomAttribute("class"), id + " body[" + r + "][" + c + "] class");
            }
            assertEquals(rowClasses == null ? null : rowClasses[r],
                row.getDomAttribute("class"), id + " row[" + r + "] class");
        }
    }

    private static WebElement findOptionalChild(WebElement parent, String tagName) {
        List<WebElement> children = parent.findElements(By.xpath("./" + tagName));
        return children.isEmpty() ? null : children.get(0);
    }

    private static Map<String, String> commonPassthroughAttributes() {
        Map<String, String> control = new LinkedHashMap<>();
        control.put("bgcolor", "blue");
        control.put("border", "1");
        control.put("cellpadding", "1");
        control.put("cellspacing", "1");
        control.put("dir", "LTR");
        control.put("frame", "box");
        control.put("lang", "en");
        control.put("onclick", "js1");
        control.put("ondblclick", "js2");
        control.put("onkeydown", "js3");
        control.put("onkeypress", "js4");
        control.put("onkeyup", "js5");
        control.put("onmousedown", "js6");
        control.put("onmousemove", "js7");
        control.put("onmouseout", "js8");
        control.put("onmouseover", "js9");
        control.put("onmouseup", "js10");
        control.put("rules", "all");
        control.put("style", "Color: red;");
        control.put("summary", "DataTableSummary");
        control.put("title", "DataTableTitle");
        control.put("width", "100%");
        return control;
    }

    private static void verifyPassthroughAttributes(WebPage page, Map<String, String> expected) {
        WebElement data1 = findByIdSuffix(page, "data1");
        expected.forEach((name, value) ->
            assertEquals(value, data1.getDomAttribute(name), "attribute " + name));
    }

    private static WebElement findByIdSuffix(WebPage page, String id) {
        String suffix = ":" + id;
        return page.findElement(By.xpath(
            "//*[@id='" + id + "'"
            + " or substring(@id, string-length(@id) - " + (suffix.length() - 1) + ") = '" + suffix + "']"));
    }
}
