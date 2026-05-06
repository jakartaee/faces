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
package ee.jakarta.tck.faces.test.faces20.viewhandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class ViewHandlerIT extends BaseITNG {

    private static final String EXPECTED_TEXT = "Hi. My name is Duke.";

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

}
