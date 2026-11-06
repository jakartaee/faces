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
package ee.jakarta.tck.faces.faces50.uiinput;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue6131IT extends BaseITNG {

    private WebPage page;

    /**
     * Each radio button group in a nested ui:repeat updates the model with the value of its checked member.
     *
     * @see <a href="https://github.com/eclipse-ee4j/mojarra/issues/6131">issue 6131</a>
     */
    @Test
    void testEachGroupInNestedRepeatUpdatesModel() {
        page = getPage("issue6131.xhtml");
        checkAndSubmit("repeat", 0, 4, 8);
        assertEquals("{group_0=0, group_1=1, group_2=2}", getOutput("repeat"));
    }

    /**
     * Each radio button group in a nested h:dataTable updates the model with the value of its checked member.
     *
     * @see <a href="https://github.com/eclipse-ee4j/mojarra/issues/6131">issue 6131</a>
     */
    @Test
    void testEachGroupInNestedDataTableUpdatesModel() {
        page = getPage("issue6131.xhtml");
        checkAndSubmit("table", 0, 4, 8);
        assertEquals("{group_0=0, group_1=1, group_2=2}", getOutput("table"));
    }

    /**
     * A radio button group whose members are the rows of a ui:repeat updates the model with the value of its checked member.
     *
     * @see <a href="https://github.com/eclipse-ee4j/mojarra/issues/6131">issue 6131</a>
     */
    @Test
    void testGroupAcrossRepeatRowsUpdatesModel() {
        page = getPage("issue6131.xhtml");
        checkAndSubmit("single", 1);
        assertEquals("1", getOutput("single"));
    }

    /**
     * A required radio button group without a checked member gives one required message for the whole group.
     *
     * @see <a href="https://github.com/eclipse-ee4j/mojarra/issues/6131">issue 6131</a>
     */
    @Test
    void testRequiredGroupWithoutCheckedMemberGivesOneMessage() {
        page = getPage("issue6131.xhtml");
        checkAndSubmit("required");
        assertEquals(1, page.findElements(By.cssSelector("#required\\:messages li")).size());
    }

    /**
     * A required radio button group whose checked member comes after its unchecked members updates the model with the value of its checked member, without
     * required message, also when an empty value is always validated.
     *
     * @see <a href="https://github.com/eclipse-ee4j/mojarra/issues/6131">issue 6131</a>
     */
    @Test
    void testRequiredGroupWithLastMemberCheckedUpdatesModel() {
        page = getPage("issue6131.xhtml");
        checkAndSubmit("required", 2);
        assertEquals(0, page.findElements(By.cssSelector("#required\\:messages li")).size());
        assertEquals("2", getOutput("required"));
    }

    /**
     * A member which was invalid in an earlier request and is not rendered now does not count as validated, so a required radio button group without a checked
     * member still gives one required message.
     *
     * @see <a href="https://github.com/eclipse-ee4j/mojarra/issues/6131">issue 6131</a>
     */
    @Test
    void testUnrenderedInvalidMemberDoesNotSkipValidationOfItsGroup() {
        page = getPage("issue6131.xhtml");
        checkAndSubmit("hidden");
        assertEquals(1, page.findElements(By.cssSelector("#hidden\\:messages li")).size());
        page.guardHttp(page.findElement(By.id("hidden:submitHiding"))::click);
        assertEquals(0, page.findElements(By.id("hidden:radio0")).size());
        assertEquals(1, page.findElements(By.cssSelector("#hidden\\:messages li")).size());
    }

    private void checkAndSubmit(String form, int... radioIndexes) {
        List<WebElement> radios = page.findElements(By.cssSelector("#" + form + " input[type=radio]"));
        for (int radioIndex : radioIndexes) {
            radios.get(radioIndex).click();
        }
        page.guardHttp(page.findElement(By.id(form + ":submit"))::click);
    }

    private String getOutput(String form) {
        return page.findElement(By.id(form + ":output")).getText();
    }

}
