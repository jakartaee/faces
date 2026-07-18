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
package ee.jakarta.tck.faces.faces20.renderkit.datatable;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class WcagDataTableIT extends BaseITNG {

    /**
     * A {@code colgroup} is emitted as a start/end tag pair, not self-closed.
     */
    private static final String COLGROUP_END = "(?:</colgroup>)?\\s*";

    /**
     * The HtmlDataTable renderer must emit the WCAG accessibility structure: the frame, rules
     * and summary table attributes, a caption facet, the colgroups facet, a thead with
     * scope=col header cells, a rowHeader column rendered as th scope=row, and multiple tbody
     * groups driven by the bodyrows attribute.
     *
     * @see jakarta.faces.component.html.HtmlDataTable
     * @see jakarta.faces.component.html.HtmlColumn
     */
    @Test
    void dataTableRendersWcagStructure() {
        WebPage page = getPage("datatable/wcagDataTable.xhtml");
        String source = page.getSource();

        assertTrue(source.matches("(?s).*<table.*frame=.hsides.*>.*"), "table frame=hsides");
        assertTrue(source.matches("(?s).*<table.*rules..groups.*>.*"), "table rules=groups");
        assertTrue(source.matches("(?s).*<table.*summary..Code page support in different versions of MS Windows.*>.*"),
                "table summary");
        assertTrue(source.matches("(?sm).*<table.*>\\s*<caption>.*CODE-PAGE SUPPORT IN MICROSOFT WINDOWS.*</caption>.*"),
                "caption immediately after table");
        assertTrue(source.matches("(?sm).*</caption>\\s*"
                + "<colgroup align=.center.>" + COLGROUP_END
                + "<colgroup align=.left.>" + COLGROUP_END
                + "<colgroup align=.center. span=.2.>" + COLGROUP_END
                + "<colgroup align=.center. span=.3.>" + COLGROUP_END + ".*"), "colgroups after caption");
        assertTrue(source.matches("(?sm).*<table.*>.*<thead>\\s*<tr>\\s*<th\\s*scope=.col.*"),
                "thead with th scope=col");
        assertTrue(source.matches("(?sm).*<table.*>.*<tbody>.*<th\\s*scope=.row.*"),
                "tbody with th scope=row");
        assertTrue(source.matches("(?sm).*<table.*>.*<tbody>.*</tbody>.*<tbody>.*</tbody>.*</table>.*"),
                "multiple tbody groups");
    }
}
