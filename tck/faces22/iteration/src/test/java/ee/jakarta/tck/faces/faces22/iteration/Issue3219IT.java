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

package ee.jakarta.tck.faces.faces22.iteration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue3219IT extends BaseITNG {

    /**
     * When the inputs of a ui:repeat row sit in mutually exclusive rendered panels, only the panel belonging to that row's kind contributes an input, and
     * saving the state of the repeat must not carry a value from one row over into another.
     *
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3219
     */
    @Test
    void perRowStateDoesNotLeakBetweenRows() {
        WebPage page = getPage("issue3219.xhtml");

        assertEquals(200, page.getResponseStatus(), "initial render");
        assertRowValues(page);

        page.guardHttp(page.findElement(By.id("form:submit"))::click);

        assertEquals(200, page.getResponseStatus(), "postback");
        assertRowValues(page);
    }

    private static void assertRowValues(WebPage page) {
        assertEquals("a0", page.findElement(By.id("form:repeat:0:a")).getDomProperty("value"), "row 0");
        assertEquals("b1", page.findElement(By.id("form:repeat:1:b")).getDomProperty("value"), "row 1");
        assertEquals("a2", page.findElement(By.id("form:repeat:2:a")).getDomProperty("value"), "row 2");
        assertEquals("b3", page.findElement(By.id("form:repeat:3:b")).getDomProperty("value"), "row 3");
        assertEquals(
            4, page.findElements(By.cssSelector("#form input[type=text]")).size(),
            "Only the panel matching each row's kind may contribute an input."
        );
    }

}
