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
package ee.jakarta.tck.faces.faces23.faces_data_model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedHashSet;
import java.util.Set;

import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * Drives the nested-iteration per-row-state scenarios over {@code NestedIterationBean}'s 2x3 matrix:
 * every cell is filled with a distinct value and must round-trip its own value across postbacks.
 *
 * <p>The form placement determines how many cells one postback carries, so cells are submitted in
 * groups keyed by {@link #submitId(int, int)}. After each submit the entire matrix is asserted
 * against the values submitted so far, which catches both cross-row leakage and state that survives
 * only a single postback. A second round with different values repeats the exercise.
 */
abstract class NestedIterationITBase extends BaseITNG {

    private static final String[][] ROUND_1 = { { "0", "1", "2" }, { "3", "4", "5" } };
    private static final String[][] ROUND_2 = { { "a", "b", "c" }, { "d", "e", "f" } };
    private static final String[][][] ROUNDS = { ROUND_1, ROUND_2 };

    private static final int OUTER_SIZE = 2;
    private static final int INNER_SIZE = 3;

    /**
     * @return the view to test, relative to the context root.
     */
    protected abstract String pageName();

    /**
     * @return the client ID of the input of the given cell.
     */
    protected abstract String inputId(int outer, int inner);

    /**
     * @return the client ID of the submit button of the form containing the given cell.
     */
    protected abstract String submitId(int outer, int inner);

    protected void assertCellsKeepOwnValueAcrossPostbacks() {
        WebPage page = getPage(pageName());
        String[][] expected = new String[OUTER_SIZE][INNER_SIZE];

        for (String[][] round : ROUNDS) {
            for (String submitId : submitIds()) {
                fillCellsOf(page, submitId, round, expected);
                page.guardHttp(page.findElement(By.id(submitId))::click);
                assertValues(page, expected);
            }
        }
    }

    private Set<String> submitIds() {
        Set<String> submitIds = new LinkedHashSet<>();

        for (int outer = 0; outer < OUTER_SIZE; outer++) {
            for (int inner = 0; inner < INNER_SIZE; inner++) {
                submitIds.add(submitId(outer, inner));
            }
        }

        return submitIds;
    }

    private void fillCellsOf(WebPage page, String submitId, String[][] round, String[][] expected) {
        for (int outer = 0; outer < OUTER_SIZE; outer++) {
            for (int inner = 0; inner < INNER_SIZE; inner++) {
                if (!submitId.equals(submitId(outer, inner))) {
                    continue;
                }

                var input = page.findElement(By.id(inputId(outer, inner)));
                input.clear();
                input.sendKeys(round[outer][inner]);
                expected[outer][inner] = round[outer][inner];
            }
        }
    }

    private void assertValues(WebPage page, String[][] expected) {
        for (int outer = 0; outer < OUTER_SIZE; outer++) {
            for (int inner = 0; inner < INNER_SIZE; inner++) {
                String value = expected[outer][inner] == null ? "" : expected[outer][inner];
                assertEquals(value,
                        page.findElement(By.id(inputId(outer, inner))).getAttribute("value"),
                        "cell (" + outer + ", " + inner + ")");
            }
        }
    }
}
