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
package ee.jakarta.tck.faces.faces50.misc;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue6070IT extends BaseITNG {

    @FindBy(id = "form:redirect")
    private WebElement redirect;

    /**
     * Opened without {@code enc}, the view is rendered in the default UTF-8, so the redirect from its action must encode "ä" as UTF-8.
     *
     * @see https://github.com/eclipse-ee4j/mojarra/issues/6070
     */
    @Test
    void testRedirectFromViewWithEncodingExpressionWithoutValue() {
        assertRedirectQueryString("issue6070.xhtml", "q=%C3%A4");
    }

    /**
     * Opened with an empty {@code enc}, the view is rendered in the default UTF-8, so the redirect from its action must encode "ä" as UTF-8.
     *
     * @see https://github.com/eclipse-ee4j/mojarra/issues/6070
     */
    @Test
    void testRedirectFromViewWithEncodingExpressionWithEmptyValue() {
        assertRedirectQueryString("issue6070.xhtml?enc=", "q=%C3%A4");
    }

    /**
     * Opened with {@code ?enc=ISO-8859-1}, the view is rendered in ISO-8859-1. Its form posts back without that request parameter, so the expression has no
     * value on the postback. The redirect from its action must still encode "ä" as ISO-8859-1, the encoding of the submitted page.
     *
     * @see https://github.com/eclipse-ee4j/mojarra/issues/6070
     */
    @Test
    void testRedirectFromViewWithEncodingExpressionWithoutValueOnPostback() {
        assertRedirectQueryString("issue6070.xhtml?enc=ISO-8859-1", "q=%E4");
    }

    private void assertRedirectQueryString(String page, String expected) {
        WebPage webPage = getPage(page);
        webPage.guardHttp(redirect::click);
        String url = webPage.getCurrentUrl();
        assertTrue(url.endsWith("?" + expected), "redirect URL should end with ?" + expected + ", was: " + url);
    }

}
