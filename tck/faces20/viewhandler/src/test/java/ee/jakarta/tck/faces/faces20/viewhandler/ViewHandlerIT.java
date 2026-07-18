/*
 * Copyright (c) 2009, 2020 Oracle and/or its affiliates. All rights reserved.
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
package ee.jakarta.tck.faces.faces20.viewhandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class ViewHandlerIT extends BaseITNG {

    private static final String EXPECTED_TEXT = "Hi. My name is Duke.";

    private static final String CHAR_ENC_VIEW = "viewHandlerCharEnc.jsf";

    private static final String ASCII_CHAR_ENC_VIEW = "viewHandlerCharEncAscii.jsf";

    private static final String UTF_8 = "UTF-8";

    private static final String US_ASCII = "US-ASCII";

    /**
     * @testName: viewHandlerCreateViewTest
     *
     * @assertion_ids: JSF:SPEC:97; JSF:SPEC:97.1; JSF:SPEC:97.2; JSF:SPEC:97.3
     *
     * @test_Strategy: Verify on an initial request that the ViewId has not been
     *                 manipulated. By Setting the suffix as ".jsf" and then
     *                 looking for the correct suffix in the
     *                 ViewHandler.createView() method. The default suffix
     *                 ".xhtml" must be preserved.
     *
     * @since 1.2
     */
    @Test
    void viewHandlerCreateViewTest() throws Exception {
        WebPage page = getPage("greetings.jsf");

        assertEquals(200, page.getResponseStatus(),
                "Initial GET of greetings.jsf must not fail; ViewHandler.createView received an invalid viewId.");
        assertTrue(page.containsText(EXPECTED_TEXT),
                "Expected greetings page to render; instead got: " + page.getSource());
    }

    /**
     * @testName: viewHandlerRestoreViewTest
     *
     * @assertion_ids: JSF:SPEC:1; JSF:SPEC:102; JSF:SPEC:1.2.2
     *
     * @test_Strategy: Verify on postback that the ViewId has not been
     *                 manipulated. By Setting the suffix as ".jsf" and then
     *                 looking for the correct suffix in the
     *                 ViewHandler.restoreView() method. The default suffix
     *                 ".xhtml" must be preserved.
     *
     * @since 1.2
     */
    @Test
    void viewHandlerRestoreViewTest() throws Exception {
        WebPage page = getPage("greetings.jsf");

        WebElement submit = page.findElement(By.id("helloForm:submit"));
        page.guardHttp(submit::click);

        assertEquals(200, page.getResponseStatus(),
                "Postback to greetings.jsf must not fail; ViewHandler.restoreView received an invalid viewId.");
        assertTrue(page.containsText(EXPECTED_TEXT),
                "Expected greetings page after postback; instead got: " + page.getSource());
    }

    /**
     * A view rendered without an available session still gets the default response character encoding, and rendering it
     * must not force a session into existence.
     *
     * @see jakarta.faces.application.ViewHandler#initView(jakarta.faces.context.FacesContext)
     * @see jakarta.faces.context.ExternalContext#getResponseCharacterEncoding()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/1846
     */
    @Test
    void viewHandlerCharacterEncodingWithoutSessionTest() throws Exception {
        getPage(CHAR_ENC_VIEW + "?invalidateSession=true");

        WebPage page = getPage(CHAR_ENC_VIEW);

        assertEquals("false", getCharEnc(page, "hasSession"),
                "Rendering a view without a form must not create a session.");
        assertEquals(UTF_8, getCharEnc(page, "responseCharEnc"),
                "Response character encoding must fall back to the default when no session encoding is known.");
    }

    /**
     * Faces stores the character encoding it rendered the view with under {@code ViewHandler.CHARACTER_ENCODING_KEY} in
     * the session map, and reads it back on the next request through {@code calculateCharacterEncoding}, which
     * {@code initView} feeds into {@code ExternalContext.setRequestCharacterEncoding}. The stored encoding follows the
     * view: a view declaring {@code US-ASCII} replaces the previously stored {@code UTF-8}.
     *
     * @see jakarta.faces.application.ViewHandler#CHARACTER_ENCODING_KEY
     * @see jakarta.faces.context.ExternalContext#setRequestCharacterEncoding(String)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/1846
     */
    @Test
    void viewHandlerCharacterEncodingStoredInSessionTest() throws Exception {
        getPage(CHAR_ENC_VIEW + "?invalidateSession=true");
        getPage(CHAR_ENC_VIEW + "?makeSession=true");

        WebPage page = getPage(CHAR_ENC_VIEW);
        assertEquals("true", getCharEnc(page, "hasSession"), "Session created on the previous request must be present.");
        assertEquals(UTF_8, getCharEnc(page, "sessionCharEnc"), "Faces must store the rendered encoding in the session.");
        assertEquals(UTF_8, getCharEnc(page, "responseCharEnc"), "Unexpected response character encoding.");
        assertEquals(UTF_8, getCharEnc(page, "requestCharEnc"),
                "initView must set the encoding calculated from the session as the request character encoding.");

        // The stored encoding is written after the view is rendered, so the US-ASCII view must be requested twice
        // before the bean can observe the new value in the session.
        getPage(ASCII_CHAR_ENC_VIEW);
        page = getPage(ASCII_CHAR_ENC_VIEW);
        assertEquals(US_ASCII, getCharEnc(page, "sessionCharEnc"),
                "The encoding stored in the session must follow the encoding the view declares.");
        assertEquals(US_ASCII, getCharEnc(page, "responseCharEnc"), "Unexpected response character encoding.");
        assertEquals(US_ASCII, getCharEnc(page, "requestCharEnc"),
                "initView must set the encoding calculated from the session as the request character encoding.");

        getPage(CHAR_ENC_VIEW + "?invalidateSession=true");
    }

    private static String getCharEnc(WebPage page, String id) {
        return page.findElement(By.id(id)).getText();
    }

}
