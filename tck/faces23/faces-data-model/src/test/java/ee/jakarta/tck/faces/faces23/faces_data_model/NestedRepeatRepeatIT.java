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

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * A {@code ui:repeat} nested in a {@code ui:repeat}, with the form wrapping both and one input per
 * cell. Each iteration keeps the per-row state of its stateful descendants, so an input must
 * round-trip the value submitted for its own (outer, inner) position, not one from another row.
 */
class NestedRepeatRepeatIT extends BaseITNG {

    private static final String[][] ROUND_1 = { { "0", "1", "2" }, { "3", "4", "5" } };
    private static final String[][] ROUND_2 = { { "a", "b", "c" }, { "d", "e", "f" } };

    /**
     * @see jakarta.faces.component.UIInput
     */
    @Test
    void inputsKeepPerRowStateAcrossPostbacks() {
        WebPage page = getPage("nestedRepeatRepeatFormOutside.xhtml");

        fillAndSubmit(page, ROUND_1);
        assertValues(page, ROUND_1);

        fillAndSubmit(page, ROUND_2);
        assertValues(page, ROUND_2);
    }

    private void fillAndSubmit(WebPage page, String[][] values) {
        for (int outer = 0; outer < values.length; outer++) {
            for (int inner = 0; inner < values[outer].length; inner++) {
                var input = page.findElement(By.id(inputId(outer, inner)));
                input.clear();
                input.sendKeys(values[outer][inner]);
            }
        }
        page.guardHttp(page.findElement(By.id("form:submit"))::click);
    }

    private void assertValues(WebPage page, String[][] values) {
        for (int outer = 0; outer < values.length; outer++) {
            for (int inner = 0; inner < values[outer].length; inner++) {
                assertEquals(values[outer][inner],
                        page.findElement(By.id(inputId(outer, inner))).getAttribute("value"),
                        "cell (" + outer + ", " + inner + ")");
            }
        }
    }

    private static String inputId(int outer, int inner) {
        return "form:outer:" + outer + ":inner:" + inner + ":input";
    }
}
