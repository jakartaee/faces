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

package ee.jakarta.tck.faces.faces22.client_window;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.lifecycle.ClientWindow;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Spec949IT extends BaseITNG {

    /**
     * Verifies that two independent windows of the same session get distinct ClientWindow tokens, as required by
     * {@link ClientWindow#getId()} ("uniquely identifies this ClientWindow within the scope of the current
     * session"), that the token stays stable when navigating to a second view via h:commandLink (ajax-able
     * command) and h:link (outcome target), and that it survives an ajax submit and a non-ajax submit on the
     * originating view. The token's internal format is left unspecified by the spec and is not asserted here.
     *
     * @see ClientWindow
     * @see https://github.com/jakartaee/faces/issues/949
     */
    @Test
    void testClientWindow() throws Exception {
        String clientWindow1 = doTestAndReturnClientWindow();
        String clientWindow2 = doTestAndReturnClientWindow();

        assertNotEquals(clientWindow1, clientWindow2, "Each window must get a distinct ClientWindow token within the session");
    }

    /**
     * Verifies that disableClientWindow (set via EL and via literal, on both h:link and h:button) causes the
     * target view to receive a different ClientWindow token, i.e. the source view's token is not propagated.
     *
     * @see ClientWindow
     * @see https://github.com/jakartaee/faces/issues/949
     */
    @Test
    void testDisableClientWindow() throws Exception {
        doTestClientWindowsDifferent("disableClientWindowEL");
        doTestClientWindowsDifferent("disableClientWindowLiteral");
        doTestClientWindowsDifferent("disableClientWindowButtonEL");
        doTestClientWindowsDifferent("disableClientWindowButtonLiteral");
    }

    private void doTestClientWindowsDifferent(String id) {
        WebPage page = getPage("spec949DisableClientWindow.xhtml");

        WebElement clientWindowHiddenField = page.findElement(By.name("jakarta.faces.ClientWindow"));
        String clientWindowBeforeClick = clientWindowHiddenField.getAttribute("value");

        WebElement link = page.findElement(By.id(id));
        page.guardHttp(link::click);

        clientWindowHiddenField = page.findElement(By.name("jakarta.faces.ClientWindow"));
        String clientWindowAfterClick = clientWindowHiddenField.getAttribute("value");

        assertNotEquals(clientWindowBeforeClick, clientWindowAfterClick,
                "ClientWindow should not be the same on second page when disableClientWindow is set via " + id);
    }

    private String doTestAndReturnClientWindow() {
        // Re-requesting the page within the same browser session, with no ClientWindow id in the request,
        // makes the runtime fabricate a fresh ClientWindow id — simulating a new tab/window in that session.
        WebPage page = getPage("spec949Main.xhtml");

        WebElement textField = page.findElement(By.id("firstName"));
        textField.clear();
        textField.sendKeys("ajaxFirstName");

        WebElement submitAjax = page.findElement(By.id("submitAjax"));
        page.guardAjax(submitAjax::click);
        assertTrue(page.containsText("|ajaxFirstName|"), "Ajax submit echoes the submitted value");

        String clientWindow = readClientWindowToken(page);

        textField = page.findElement(By.id("firstName"));
        textField.clear();
        textField.sendKeys("nonAjaxFirstName");

        WebElement submitNonAjax = page.findElement(By.id("submitNonAjax"));
        page.guardHttp(submitNonAjax::click);
        assertTrue(page.containsText("|nonAjaxFirstName|"), "Non-ajax submit echoes the submitted value");

        // Visit another page via a commandLink and verify the token is preserved.
        WebElement commandLink = page.findElement(By.id("commandLink"));
        page.guardHttp(commandLink::click);
        assertEquals(clientWindow, readClientWindowToken(page), "ClientWindow is preserved across h:commandLink navigation");

        // Go back to the first page.
        WebElement back = page.findElement(By.id("back"));
        page.guardHttp(back::click);

        // Visit another page via an h:link and verify the token is preserved.
        WebElement outcomeTargetLink = page.findElement(By.id("outcomeTargetLink"));
        page.guardHttp(outcomeTargetLink::click);
        assertEquals(clientWindow, readClientWindowToken(page), "ClientWindow is preserved across h:link navigation");

        return clientWindow;
    }

    /**
     * Clicks the getClientWindow button, whose inline handler writes the value returned by
     * {@code faces.getClientWindow(form)} into the echoClientWindow span, then reads it back.
     */
    private String readClientWindowToken(WebPage page) {
        WebElement getClientWindow = page.findElement(By.id("getClientWindow"));
        getClientWindow.click();
        page.waitForCondition($ -> !page.findElement(By.id("echoClientWindow")).getText().isEmpty());
        return page.findElement(By.id("echoClientWindow")).getText();
    }
}
