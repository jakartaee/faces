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

class Issue6129IT extends BaseITNG {

    private static final String VIEW_STATE = "jakarta.faces.ViewState";
    private static final String FIRST = "first";
    private static final String SECOND = "second";
    private static final String TYPED = "typed";

    @FindBy(id = "form:submit")
    private WebElement submit;

    @FindBy(id = "form:refresh")
    private WebElement refresh;

    @FindBy(id = "form:change")
    private WebElement change;

    /**
     * Restoring a saved state gives the view as it was saved, also when a later request restored the same saved state and changed the per-row values of an
     * iterating component, as after the back button or in a second tab. The required input is immediate and stays empty, so an error message exists already
     * when the iterating components decode, and they keep and change their saved per-row values in every request.
     *
     * @see <a href="https://github.com/eclipse-ee4j/mojarra/issues/6129">issue 6129</a>
     */
    @Test
    void testSecondRestoreOfSavedStateDoesNotSeeChangesOfFirstRestore() {
        WebPage page = getPage("issue6129.xhtml");
        replace(input("table", 0), FIRST);
        replace(input("repeat", 0), FIRST);
        page.guardHttp(submit::click);
        String firstViewState = page.findElement(By.name(VIEW_STATE)).getAttribute("value");

        replace(input("table", 1), SECOND);
        replace(input("repeat", 1), SECOND);
        page.guardHttp(submit::click);

        page.executeScript("document.getElementsByName(arguments[0])[0].value = arguments[1];", VIEW_STATE, firstViewState);
        page.guardAjax(refresh::click);

        assertAll(
            () -> assertEquals(FIRST, input("table", 0).getAttribute("value"), "form:table:0:in"),
            () -> assertEquals(Issue6129Bean.ROW_1, input("table", 1).getAttribute("value"), "form:table:1:in"),
            () -> assertEquals(FIRST, input("repeat", 0).getAttribute("value"), "form:repeat:0:in"),
            () -> assertEquals(Issue6129Bean.ROW_1, input("repeat", 1).getAttribute("value"), "form:repeat:1:in")
        );
    }

    /**
     * The <code>UIInput.updateModel()</code> javadoc requires that a successful update clears the local value. With <code>rowStatePreserved</code>, the saved
     * state of a row therefore holds no local value after a valid submit, and a later change of the model shows in a request which does not execute the row.
     *
     * @see <a href="https://github.com/eclipse-ee4j/mojarra/issues/6129">issue 6129</a>
     */
    @Test
    void testRowStatePreservedDoesNotKeepLocalValueAfterUpdateModel() {
        WebPage page = getPage("issue6129RowStatePreserved.xhtml");
        replace(input("table", 0), TYPED);
        replace(input("repeat", 0), TYPED);
        page.guardHttp(submit::click);

        page.guardAjax(change::click);

        assertAll(
            () -> assertEquals(Issue6129Bean.CHANGED, input("table", 0).getAttribute("value"), "form:table:0:in"),
            () -> assertEquals(Issue6129Bean.CHANGED, input("repeat", 0).getAttribute("value"), "form:repeat:0:in")
        );
    }

    private WebElement input(String iterator, int row) {
        return getWebDriver().findElement(By.id("form:" + iterator + ":" + row + ":in"));
    }

    private static void replace(WebElement input, String value) {
        input.clear();
        input.sendKeys(value);
    }

}
