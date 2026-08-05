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

package ee.jakarta.tck.faces.faces22.full_state_saving;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;
import jakarta.faces.application.ResourceHandler;
import jakarta.faces.component.UIComponent;

/**
 * Behaviour which only breaks with partial state saving switched off. This module pins
 * {@code jakarta.faces.PARTIAL_STATE_SAVING} to {@code false}, a combination the reactor otherwise never exercises, so
 * the tests are grouped by that shared condition rather than one class per ticket.
 */
class FullStateSavingIT extends BaseITNG {

    /**
     * A composite component and its retargeted action source survive a postback: the inner command button keeps its
     * value and its action listener fires.
     *
     * @see UIComponent
     * @see https://github.com/eclipse-ee4j/mojarra/issues/1641
     */
    @Test
    void compositeComponentSurvivesPostback() {
        WebPage page = getPage("issue1641.xhtml");

        assertTrue(page.containsText("count=0"), "counter on initial render");
        assertTrue(page.containsSource("Click Me"), "composite on initial render");

        page.guardHttp(page.findElement(By.id("button:abutton"))::click);

        assertTrue(page.containsText("count=1"), "counter after postback");
        assertTrue(page.containsSource("Click Me"), "composite after postback");
    }

    /**
     * A single contracts directory with no contract-mapping in faces-config is discovered implicitly, and the template
     * it supplies keeps resolving on a postback rather than only on the initial render.
     *
     * @see ResourceHandler#WEBAPP_CONTRACTS_DIRECTORY_PARAM_NAME
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2918
     */
    @Test
    void implicitResourceLibraryContractResolvesOnPostback() {
        WebPage page = getPage("issue2918.xhtml");

        assertEquals(200, page.getResponseStatus(), "initial render");
        assertTrue(page.containsText("rendered from the contract"), "The contract template must have been used.");

        page.guardHttp(page.findElement(By.id("currentButton"))::click);

        assertEquals(200, page.getResponseStatus(), "postback");
        assertTrue(page.containsText("rendered from the contract"), "The contract must still resolve on a postback.");
    }
}
