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

package ee.jakarta.tck.faces.faces22.facelets;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.faces.component.html.HtmlCommandButton;
import jakarta.faces.component.html.HtmlCommandLink;
import jakarta.faces.component.html.HtmlForm;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * Verifies that both a statically declared onsubmit handler and a dynamically registered submit listener actually fire
 * when a form is submitted by a command link or a command button.
 */
class Issue4198IT extends BaseITNG {

    private static final String STATIC_MESSAGE = "static onSubmit triggered";
    private static final String DYNAMIC_MESSAGE = "dynamic onSubmit triggered";

    /**
     * @see HtmlForm#getOnsubmit()
     * @see HtmlCommandLink
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4198
     */
    @Test
    void testStaticOnSubmitByCommandLink() throws Exception {
        assertOnSubmitTriggered("staticOnSubmitForm:AAA", STATIC_MESSAGE);
    }

    /**
     * @see HtmlForm#getOnsubmit()
     * @see HtmlCommandButton
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4198
     */
    @Test
    void testStaticOnSubmitByCommandButton() throws Exception {
        assertOnSubmitTriggered("staticOnSubmitForm:BBB", STATIC_MESSAGE);
    }

    /**
     * @see HtmlForm
     * @see HtmlCommandLink
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4198
     */
    @Test
    void testDynamicOnSubmitByCommandLink() throws Exception {
        assertOnSubmitTriggered("dynamicOnSubmitForm:AAA", DYNAMIC_MESSAGE);
    }

    /**
     * @see HtmlForm
     * @see HtmlCommandButton
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4198
     */
    @Test
    void testDynamicOnSubmitByCommandButton() throws Exception {
        assertOnSubmitTriggered("dynamicOnSubmitForm:BBB", DYNAMIC_MESSAGE);
    }

    private void assertOnSubmitTriggered(String clientId, String expectedMessage) throws Exception {
        WebPage page = getPage("issue4198.xhtml");
        page.guardHttp(page.findElement(By.id(clientId))::click);
        assertEquals(expectedMessage, page.findElement(By.id("result")).getText(),
            "The onsubmit script must have run and its message must have round-tripped through the submit");
    }
}
