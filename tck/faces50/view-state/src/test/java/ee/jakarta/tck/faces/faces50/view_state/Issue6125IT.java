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
package ee.jakarta.tck.faces.faces50.view_state;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue6125IT extends BaseITNG {

    @FindBy(id = "form:table")
    private WebElement table;

    @FindBy(id = "form:map")
    private WebElement map;

    @FindBy(id = "form:set")
    private WebElement set;

    @FindBy(id = "form:submit")
    private WebElement submit;

    /**
     * A <code>List</code> or <code>Map</code> which Java code sets whole as the value of a component is restored as it was set: with its duplicates and in its
     * order.
     *
     * @see <a href="https://github.com/eclipse-ee4j/mojarra/issues/6125">issue 6125</a>
     */
    @Test
    void testValueSetWholeIsRestoredAsItWasSet() {
        WebPage page = getPage("issue6125.xhtml");

        page.guardHttp(set::click);
        assertValues("after set");

        page.guardHttp(submit::click);
        assertValues("after the next postback");
    }

    private void assertValues(String when) {
        assertEquals("bab", table.getText().replaceAll("\\s", ""), "rows of form:table " + when);
        assertEquals("{z=1,a=2,m=3}", map.getText().replaceAll("\\s", ""), "text of form:map " + when);
    }

}
