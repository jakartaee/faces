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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue6128IT extends BaseITNG {

    private static final String TYPED = "typed";

    @FindBy(id = "form:submit")
    private WebElement submit;

    @FindBy(id = "form:addRow")
    private WebElement addRow;

    /**
     * The <code>UIData.setRowStatePreserved()</code> javadoc requires that re-entering a row reinitializes the children to the correct state for that row. A
     * row which has no saved state of its own yet starts from the initial state of the children, also when a child had nothing in its state helper initially.
     * It does not show the local value of the row before it.
     *
     * @see <a href="https://github.com/eclipse-ee4j/mojarra/issues/6128">issue 6128</a>
     */
    @Test
    void testNewRowDoesNotTakeLocalValueOfPreviousRow() {
        WebPage page = getPage("issue6128.xhtml");
        input("table", 1).sendKeys(TYPED);
        input("repeat", 1).sendKeys(TYPED);
        page.guardHttp(submit::click);

        page.guardAjax(addRow::click);

        assertAll(
            () -> assertEquals(TYPED, input("table", 1).getAttribute("value"), "form:table:1:in"),
            () -> assertEquals(TYPED, input("repeat", 1).getAttribute("value"), "form:repeat:1:in"),
            () -> assertEquals("", input("table", 2).getAttribute("value"), "form:table:2:in"),
            () -> assertEquals("", input("repeat", 2).getAttribute("value"), "form:repeat:2:in")
        );
    }

    private WebElement input(String iterator, int row) {
        return getWebDriver().findElement(By.id("form:" + iterator + ":" + row + ":in"));
    }

}
