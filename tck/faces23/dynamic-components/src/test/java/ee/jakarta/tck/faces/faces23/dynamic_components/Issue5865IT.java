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

package ee.jakarta.tck.faces.faces23.dynamic_components;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * A component added programmatically to a container inside a data table row must render in the proper place, and
 * must stay there across postbacks.
 */
public class Issue5865IT extends BaseITNG {

    private static final int ROWS = 3;

    private static int addedCount(WebPage page) {
        String source = page.getSource();
        int count = 0;
        for (int i = source.indexOf(Issue5865Bean.ADDED); i >= 0;
                i = source.indexOf(Issue5865Bean.ADDED, i + Issue5865Bean.ADDED.length())) {
            count++;
        }
        return count;
    }

    /**
     * The add is performed by a command button inside a row, so the row index is set while it runs. The panel group
     * it targets is a single component instance reused for every row, so one add renders once per row.
     *
     * @see jakarta.faces.component.UIComponent#getChildren()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/5865
     */
    @Test
    void testAddInsideDataTableRow() throws Exception {
        WebPage page = getPage("issue5865.xhtml");
        assertEquals(0, addedCount(page), "nothing added yet");

        WebElement add = page.findElement(By.id("form:table:1:add"));
        page.guardHttp(add::click);
        assertEquals(ROWS, addedCount(page), "the added child must render, in every row of the shared group");

        WebElement postback = page.findElement(By.id("form:postback"));
        page.guardHttp(postback::click);
        assertEquals(ROWS, addedCount(page), "the added child must survive a postback");
    }
}
