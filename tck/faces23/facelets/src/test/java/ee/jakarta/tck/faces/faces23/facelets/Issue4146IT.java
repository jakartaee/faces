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

package ee.jakarta.tck.faces.faces23.facelets;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import jakarta.faces.component.html.HtmlDataTable;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue4146IT extends BaseITNG {

    private static final String[][] EXPECTED_ROWS = {
        {"a1b2c3d4", "1998", "BMW", "Black"},
        {"b2c3d4e5", "2003", "Mercedes", "White"},
        {"c3d4e5f6", "2007", "Volvo", "Green"},
        {"d4e5f6a7", "2011", "Audi", "Red"},
        {"e5f6a7b8", "2015", "Renault", "Blue"}
    };

    /**
     * A data table bound via {@code binding} to a session scoped property must render identically
     * when the very same view is requested again within that session. On the second GET the binding
     * property still holds the component instance of the first view, and building the fresh view
     * must overwrite it rather than reuse the stale, already detached instance.
     *
     * @see HtmlDataTable
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4146
     */
    @Test
    void boundDataTableRendersOnRepeatedGetWithinSession() {
        assertRows(getPage("issue4146.xhtml"), "first GET");

        // Same Selenium session, hence the same HTTP session, hence the same Issue4146Bean instance.
        assertRows(getPage("issue4146.xhtml"), "second GET");
    }

    private static void assertRows(WebPage page, String request) {
        WebElement table = page.findElement(By.id("form:table"));
        List<WebElement> rows = table.findElements(By.cssSelector("tbody tr"));
        assertEquals(EXPECTED_ROWS.length, rows.size(), request + " row count");

        for (int r = 0; r < EXPECTED_ROWS.length; r++) {
            List<WebElement> cells = rows.get(r).findElements(By.tagName("td"));
            assertEquals(EXPECTED_ROWS[r].length, cells.size(), request + " row[" + r + "] cell count");

            for (int c = 0; c < EXPECTED_ROWS[r].length; c++) {
                assertEquals(EXPECTED_ROWS[r][c], cells.get(c).getText().trim(),
                    request + " cell[" + r + "][" + c + "] text");
            }
        }
    }
}
