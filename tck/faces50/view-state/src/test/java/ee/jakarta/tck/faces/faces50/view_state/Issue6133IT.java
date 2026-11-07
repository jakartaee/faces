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
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue6133IT extends BaseITNG {

    @FindBy(id = "form:submit")
    private WebElement submit;

    @FindBy(id = "form:addRow")
    private WebElement addRow;

    @FindBy(id = "form:tableNames")
    private WebElement tableNames;

    @FindBy(id = "form:repeatNames")
    private WebElement repeatNames;

    /**
     * The <code>UIData.setRowStatePreserved()</code> javadoc requires that re-entering a row reinitializes the children to the correct state for that row. A
     * validator which Java code added to an input in one row applies to that row only. A row which has no saved state of its own yet starts without it.
     *
     * @see <a href="https://github.com/eclipse-ee4j/mojarra/issues/6133">issue 6133</a>
     */
    @Test
    void testValidatorAddedInOneRowDoesNotApplyToNewRow() {
        WebPage page = getPage("issue6133.xhtml");
        page.guardHttp(element("table", 1, "restrict")::click);
        page.guardHttp(element("repeat", 1, "restrict")::click);
        page.guardAjax(addRow::click);

        replace(element("table", 2, "in"), "x");
        replace(element("repeat", 2, "in"), "x");
        page.guardHttp(submit::click);

        assertAll(
            () -> assertEquals("", message("table", 2), "message of form:table:2:in"),
            () -> assertEquals("", message("repeat", 2), "message of form:repeat:2:in"),
            () -> assertEquals("zero,one,x", tableNames.getText(), "model of form:table"),
            () -> assertEquals("zero,one,x", repeatNames.getText(), "model of form:repeat")
        );
    }

    /**
     * A validator which Java code added to an input in one row keeps validating that row.
     *
     * @see <a href="https://github.com/eclipse-ee4j/mojarra/issues/6133">issue 6133</a>
     */
    @Test
    void testValidatorAddedInOneRowKeepsValidatingThatRow() {
        WebPage page = getPage("issue6133.xhtml");
        page.guardHttp(element("table", 1, "restrict")::click);
        page.guardHttp(element("repeat", 1, "restrict")::click);

        replace(element("table", 1, "in"), "x");
        replace(element("repeat", 1, "in"), "x");
        page.guardHttp(submit::click);

        assertAll(
            () -> assertNotEquals("", message("table", 1), "message of form:table:1:in"),
            () -> assertNotEquals("", message("repeat", 1), "message of form:repeat:1:in"),
            () -> assertEquals("zero,one", tableNames.getText(), "model of form:table"),
            () -> assertEquals("zero,one", repeatNames.getText(), "model of form:repeat")
        );
    }

    private WebElement element(String iterator, int row, String id) {
        return getWebDriver().findElement(By.id("form:" + iterator + ":" + row + ":" + id));
    }

    /**
     * The text of the message of the input in the given row, or an empty string when no message element is rendered.
     */
    private String message(String iterator, int row) {
        List<WebElement> messages = getWebDriver().findElements(By.id("form:" + iterator + ":" + row + ":message"));
        return messages.isEmpty() ? "" : messages.get(0).getText();
    }

    private static void replace(WebElement input, String value) {
        input.clear();
        input.sendKeys(value);
    }

}
