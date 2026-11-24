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

/**
 * Each row which a c:if inside a c:forEach admits renders its own item, also after a postback changed which rows it admits, also in a nested c:forEach of which
 * the c:if admits only some enclosing rows, and also when each row is included.
 *
 * @see <a href="https://github.com/eclipse-ee4j/mojarra/issues/6194">issue 6194</a>
 */
class Issue6194IT extends BaseITNG {

    private WebPage page;

    private static final String ALL_ROWS = "[1][2][3][4][5][6]";

    private static final String EVEN_ROWS = "[2][4][6]";

    private static final String ROWS_OF_LAST_GROUPS = "[3][4][5][6]";

    /**
     * The rows follow the c:if after each ajax postback which toggles it.
     */
    @Test
    void testRowsFollowCondition() {
        page = getPage("issue6194-rows.xhtml");
        assertRows(ALL_ROWS);

        page.guardAjax(page.findElement(By.id("form:evenOnly"))::click);
        assertRows(EVEN_ROWS);

        page.guardAjax(page.findElement(By.id("form:evenOnly"))::click);
        assertRows(ALL_ROWS);

        page.guardAjax(page.findElement(By.id("form:evenOnly"))::click);
        assertRows(EVEN_ROWS);
    }

    /**
     * The rows of a nested c:forEach follow the c:if around it after each ajax postback which toggles it.
     */
    @Test
    void testNestedRowsFollowCondition() {
        page = getPage("issue6194-nested.xhtml");
        assertRows(ALL_ROWS);

        page.guardAjax(page.findElement(By.id("form:skipFirstGroup"))::click);
        assertRows(ROWS_OF_LAST_GROUPS);

        page.guardAjax(page.findElement(By.id("form:skipFirstGroup"))::click);
        assertRows(ALL_ROWS);

        page.guardAjax(page.findElement(By.id("form:skipFirstGroup"))::click);
        assertRows(ROWS_OF_LAST_GROUPS);
    }

    /**
     * The included rows follow the c:if after each ajax postback which toggles it, and the build preceding Render Response creates anew only the rows the c:if
     * admits again.
     */
    @Test
    void testIncludedRowsFollowCondition() {
        page = getPage("issue6194-included.xhtml");
        assertRows(ALL_ROWS);

        page.guardAjax(page.findElement(By.id("form:evenOnly"))::click);
        assertRows(EVEN_ROWS);
        assertCreated("[]");

        page.guardAjax(page.findElement(By.id("form:evenOnly"))::click);
        assertRows(ALL_ROWS);
        assertCreated("[1, 3, 5]");

        page.guardAjax(page.findElement(By.id("form:evenOnly"))::click);
        assertRows(EVEN_ROWS);
        assertCreated("[]");
    }

    private void assertCreated(String expected) {
        assertEquals(expected, page.findElement(By.id("form:created")).getText(), "rows created by the render build");
    }

    private void assertRows(String expected) {
        assertEquals(expected, page.findElement(By.id("form:rows")).getText());
    }

}
