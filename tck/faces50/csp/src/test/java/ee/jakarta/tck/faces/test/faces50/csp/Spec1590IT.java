/*
 * Copyright (c) Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the Apache License, Version 2.0
 * which is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GPL-2.0 with Classpath-exception-2.0 which
 * is available at https://openjdk.java.net/legal/gplv2+ce.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0 or Apache-2.0
 */
package ee.jakarta.tck.faces.test.faces50.csp;

import static java.net.URI.create;
import static java.net.http.HttpClient.newHttpClient;
import static java.net.http.HttpRequest.newBuilder;
import static java.net.http.HttpResponse.BodyHandlers.ofString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.application.ResourceHandler;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class Spec1590IT extends BaseITNG {

    @FindBy(id = "form1:commandLink")
    private WebElement commandLink;

    @FindBy(id = "form1:commandLinkExecuted")
    private WebElement commandLinkExecuted;

    @FindBy(id = "form2:ajaxInput")
    private WebElement ajaxInput;

    @FindBy(id = "form2:ajaxButton")
    private WebElement ajaxButton;

    @FindBy(id = "form2:ajaxOutput")
    private WebElement ajaxOutput;

    @FindBy(id = "form3:commandScript")
    private WebElement commandScript;

    @FindBy(id = "form3:commandScriptExecuted")
    private WebElement commandScriptExecuted;

    @FindBy(id = "form4:facesUtilChain")
    private WebElement facesUtilChain;

    @FindBy(id = "form4:facesUtilChainExecuted")
    private WebElement facesUtilChainExecuted;

    @FindBy(id = "form5:refreshButton")
    private WebElement refreshButton;

    /**
     * @see ResourceHandler#ENABLE_CSP_NONCE
     * @see https://github.com/jakartaee/faces/issues/1590
     */
    @Test
    public void testCommandLinkWithoutAjax() {
        var page = getPage("spec1590.xhtml");
        var nonce = getNonce(page);
        assertNotNull(nonce);
        assertEquals(nonce, getBehaviorScriptElement(page, commandLink).getAttribute("nonce"));
        assertEquals("false", commandLinkExecuted.getText());
        page.guardHttp(commandLink::click);
        assertEquals("true", commandLinkExecuted.getText());
        // Non-ajax postback must generate a new nonce.
        assertNotEquals(nonce, getNonce(page));
    }

    /**
     * @see ResourceHandler#ENABLE_CSP_NONCE
     * @see https://github.com/jakartaee/faces/issues/1590
     */
    @Test
    public void testAjaxInputAndButton() {
        var page = getPage("spec1590.xhtml");
        var nonce = getNonce(page);
        assertNotNull(nonce);
        assertEquals(nonce, getBehaviorScriptElement(page, ajaxInput).getAttribute("nonce"));
        assertEquals(nonce, getBehaviorScriptElement(page, ajaxButton).getAttribute("nonce"));
        assertEquals("", ajaxOutput.getText());
        ajaxInput.sendKeys("first");
        page.guardAjax(ajaxButton::click);
        assertEquals("first", ajaxOutput.getText());
        ajaxInput.clear();
        ajaxInput.sendKeys("second");
        page.guardAjax(ajaxButton::click);
        assertEquals("second", ajaxOutput.getText());
    }

    /**
     * @see ResourceHandler#ENABLE_CSP_NONCE
     * @see https://github.com/jakartaee/faces/issues/1590
     */
    @Test
    public void testCommandScript() {
        var page = getPage("spec1590.xhtml");
        var nonce = getNonce(page);
        assertNotNull(nonce);
        assertEquals(nonce, getBehaviorScriptElement(page, commandScript).getAttribute("nonce"));
        assertEquals("false", commandScriptExecuted.getText());
        page.guardAjax(() -> page.getJSExecutor().executeScript("commandScript()"));
        assertEquals("true", commandScriptExecuted.getText());
    }

    /**
     * @see ResourceHandler#ENABLE_CSP_NONCE
     * @see https://github.com/jakartaee/faces/issues/1590
     */
    @Test
    public void testFacesUtilChain() {
        var page = getPage("spec1590.xhtml");
        var nonce = getNonce(page);
        assertNotNull(nonce);
        assertEquals(nonce, getBehaviorScriptElement(page, facesUtilChain).getAttribute("nonce"));
        assertEquals("false", facesUtilChainExecuted.getText());
        page.guardAjax(facesUtilChain::click);
        assertEquals("true", facesUtilChainExecuted.getText());
    }

    /**
     * @see ResourceHandler#ENABLE_CSP_NONCE
     * @see https://github.com/jakartaee/faces/issues/1590
     */
    @Test
    public void testRefresh() {
        var page = getPage("spec1590.xhtml");
        var nonce = getNonce(page);
        assertNotNull(nonce);
        assertEquals(nonce, getBehaviorScriptElement(page, refreshButton).getAttribute("nonce"));
        page.guardHttp(refreshButton::click);
        assertNotEquals(nonce, getBehaviorScriptElement(page, refreshButton).getAttribute("nonce"));

        var nonceAfterRefresh = getNonce(page);
        assertEquals(nonceAfterRefresh, getBehaviorScriptElement(page, commandLink).getAttribute("nonce"));
        assertEquals(nonceAfterRefresh, getBehaviorScriptElement(page, ajaxInput).getAttribute("nonce"));
        assertEquals(nonceAfterRefresh, getBehaviorScriptElement(page, ajaxButton).getAttribute("nonce"));
        assertEquals(nonceAfterRefresh, getBehaviorScriptElement(page, commandScript).getAttribute("nonce"));
        assertEquals(nonceAfterRefresh, getBehaviorScriptElement(page, facesUtilChain).getAttribute("nonce"));
        assertEquals(nonceAfterRefresh, getBehaviorScriptElement(page, refreshButton).getAttribute("nonce"));
    }

    /**
     * @see ResourceHandler#CSP_POLICY_PARAM_NAME
     * @see https://github.com/jakartaee/faces/issues/1590
     */
    @Test
    public void testCspResponseHeader() throws Exception {
        var response = newHttpClient().send(newBuilder(create(webUrl + "spec1590.xhtml")).build(), ofString());
        var cspHeader = response.headers().firstValue("Content-Security-Policy");
        assertTrue(cspHeader.isPresent(), "Content-Security-Policy response header must be present");
        assertTrue(cspHeader.get().contains("script-src"), "Content-Security-Policy response header must contain script-src directive");
        assertTrue(cspHeader.get().contains("'nonce-"), "Content-Security-Policy response header must contain nonce");
    }

    /**
     * @see ResourceHandler#getCurrentNonce
     * @see https://github.com/jakartaee/faces/issues/1590
     */
    @Test
    public void testNonceConsistentDuringAjaxPostback() {
        var page = getPage("spec1590.xhtml");
        var nonce = getNonce(page);
        assertNotNull(nonce);
        ajaxInput.sendKeys("first");
        page.guardAjax(ajaxButton::click);
        // Ajax postback must retain the same nonce on faces.js.
        assertEquals(nonce, getNonce(page));
        // Verify behavior scripts were successfully eval'd during ajax by performing another round-trip.
        // If the event listeners weren't properly re-attached, this would fail.
        ajaxInput.clear();
        ajaxInput.sendKeys("second");
        page.guardAjax(ajaxButton::click);
        assertEquals("second", ajaxOutput.getText());
        assertEquals(nonce, getNonce(page));
    }

    private String getNonce(WebPage page) {
        return page.findElement(By.cssSelector("script[src*='jakarta.faces.resource/faces.js']")).getAttribute("nonce");
    }
}
