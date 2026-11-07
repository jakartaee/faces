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

class Issue6100IT extends BaseITNG {

    @FindBy(id = "form:target")
    private WebElement target;

    @FindBy(id = "form:addA")
    private WebElement addA;

    @FindBy(id = "form:addB")
    private WebElement addB;

    @FindBy(id = "form:submit")
    private WebElement submit;

    /**
     * A component added at index 0 of a parent keeps that position on every later postback.
     *
     * @see https://github.com/eclipse-ee4j/mojarra/issues/6100
     */
    @Test
    void testDynamicChildKeepsItsIndexOnPostback() {
        WebPage page = getPage("issue6100.xhtml");
        assertChildren("S0S1");

        page.guardHttp(addA::click);
        assertChildren("AS0S1");

        page.guardHttp(submit::click);
        assertChildren("AS0S1");

        page.guardHttp(submit::click);
        assertChildren("AS0S1");
    }

    /**
     * A component added at index 0 of a parent, in a later request than another component added at index 0 of the same parent, ends up before that other
     * component, and keeps that position on every later postback.
     *
     * @see https://github.com/eclipse-ee4j/mojarra/issues/6100
     */
    @Test
    void testDynamicChildInsertedBeforeEarlierDynamicChild() {
        WebPage page = getPage("issue6100.xhtml");

        page.guardHttp(addA::click);
        assertChildren("AS0S1");

        page.guardHttp(addB::click);
        assertChildren("BAS0S1");

        page.guardHttp(submit::click);
        assertChildren("BAS0S1");
    }

    private void assertChildren(String expected) {
        assertEquals(expected, target.getText().replaceAll("\\s", ""), "rendered children of form:target");
    }

}
