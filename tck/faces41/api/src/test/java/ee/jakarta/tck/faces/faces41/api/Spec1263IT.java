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

package ee.jakarta.tck.faces.faces41.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.faces.component.UIData;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * Drives the {@code rowStatePreserved} scenarios over a 2x3 matrix rendered by an iterating component nested in another one of the same kind, with the form
 * wrapping both and one input per cell.
 *
 * <p>
 * Every case runs against both kinds, as the attribute is specified to behave identically on {@code ui:repeat} and on {@code h:dataTable}, and both render
 * their cells under the same client IDs.
 */
class Spec1263IT extends BaseITNG {

    private static final String UI_REPEAT = "spec1263.xhtml";
    private static final String DATA_TABLE = "spec1263-datatable.xhtml";

    private static final int OUTER_SIZE = 2;
    private static final int INNER_SIZE = 3;

    private static final String[][] ROUND_1 = { { "0", "1", "2" }, { "3", "4", "5" } };
    private static final String[][] ROUND_2 = { { "a", "b", "c" }, { "d", "e", "f" } };
    private static final String[][][] ROUNDS = { ROUND_1, ROUND_2 };

    private static final String MARKED = "marked";
    private static final int MARKED_OUTER = 0;
    private static final int MARKED_INNER = 1;

    /**
     * Every cell must render under its own client ID. An iterating component which does not reset the client IDs of its descendants when the row index changes
     * renders each row with row 0's IDs, so the page carries one ID many times over and a lookup by ID can only ever reach the first row.
     *
     * @see UIData#setRowStatePreserved(boolean)
     * @see https://github.com/jakartaee/faces/issues/1263
     */
    @ParameterizedTest
    @ValueSource(strings = { UI_REPEAT, DATA_TABLE })
    void cellsRenderUnderTheirOwnClientId(String pageName) {
        WebPage page = getPage(pageName);

        for (int outer = 0; outer < OUTER_SIZE; outer++) {
            for (int inner = 0; inner < INNER_SIZE; inner++) {
                String inputId = inputId(outer, inner);
                assertEquals(1, page.findElements(By.id(inputId)).size(), "number of elements with ID " + inputId);
            }
        }
    }

    /**
     * Preserving the full per-row component state instead of only the submitted values must not change which cell a value belongs to, so every cell must
     * round-trip the value submitted for its own position across repeated postbacks.
     *
     * @see UIData#setRowStatePreserved(boolean)
     * @see https://github.com/jakartaee/faces/issues/1263
     */
    @ParameterizedTest
    @ValueSource(strings = { UI_REPEAT, DATA_TABLE })
    void inputsKeepPerRowStateAcrossPostbacks(String pageName) {
        WebPage page = getPage(pageName);

        for (String[][] round : ROUNDS) {
            for (int outer = 0; outer < OUTER_SIZE; outer++) {
                for (int inner = 0; inner < INNER_SIZE; inner++) {
                    var input = page.findElement(By.id(inputId(outer, inner)));
                    input.clear();
                    input.sendKeys(round[outer][inner]);
                }
            }

            page.guardHttp(page.findElement(By.id("form:submit"))::click);
            assertValues(page, round);
        }
    }

    /**
     * Marking one cell sets a style class on that cell's input, which is a property of the single child instance the cells share. Only the full per-row
     * component state covers it, so without this attribute the modification would appear on every cell instead of the marked one.
     *
     * @see UIData#setRowStatePreserved(boolean)
     * @see https://github.com/jakartaee/faces/issues/1263
     */
    @ParameterizedTest
    @ValueSource(strings = { UI_REPEAT, DATA_TABLE })
    void markedCellKeepsItsOwnComponentState(String pageName) {
        WebPage page = getPage(pageName);
        page.guardHttp(page.findElement(By.id(markId(MARKED_OUTER, MARKED_INNER)))::click);

        for (int outer = 0; outer < OUTER_SIZE; outer++) {
            for (int inner = 0; inner < INNER_SIZE; inner++) {
                String expected = outer == MARKED_OUTER && inner == MARKED_INNER ? MARKED : "";
                String actual = page.findElement(By.id(inputId(outer, inner))).getAttribute("class");
                assertEquals(expected, actual == null ? "" : actual, "cell (" + outer + ", " + inner + ")");
            }
        }
    }

    private void assertValues(WebPage page, String[][] expected) {
        for (int outer = 0; outer < OUTER_SIZE; outer++) {
            for (int inner = 0; inner < INNER_SIZE; inner++) {
                assertEquals(
                    expected[outer][inner],
                    page.findElement(By.id(inputId(outer, inner))).getAttribute("value"),
                    "cell (" + outer + ", " + inner + ")"
                );
            }
        }
    }

    private static String inputId(int outer, int inner) {
        return "form:outer:" + outer + ":inner:" + inner + ":input";
    }

    private static String markId(int outer, int inner) {
        return "form:outer:" + outer + ":inner:" + inner + ":mark";
    }

}
