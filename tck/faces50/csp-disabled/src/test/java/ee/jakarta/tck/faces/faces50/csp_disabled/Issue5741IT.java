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
package ee.jakarta.tck.faces.faces50.csp_disabled;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.application.ResourceHandler;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * Same CSP-disabled contract as {@link Issue5606IT}, but exercised through an exact servlet mapping
 * (the extensionless {@code /issue5741} url-pattern) rather than the standard {@code *.xhtml} suffix
 * mapping. Under exact mapping the wrong event handler used to be rendered for command components,
 * breaking the click; this verifies that with CSP disabled (the default) each command component keeps
 * its {@code onclick} handler wired and functioning and no CSP nonce is emitted.
 *
 * @see ResourceHandler#ENABLE_CSP_NONCE
 * @see https://github.com/eclipse-ee4j/mojarra/issues/5741
 */
class Issue5741IT extends BaseITNG {

    private static final String PAGE = "issue5741"; // Exact-mapped, hence extensionless.

    @FindBy(id = "form:commandLink")
    private WebElement commandLink;

    @FindBy(id = "form:commandLinkExecuted")
    private WebElement commandLinkExecuted;

    @FindBy(id = "form:commandButton")
    private WebElement commandButton;

    @FindBy(id = "form:commandButtonExecuted")
    private WebElement commandButtonExecuted;

    @FindBy(id = "form:ajaxButton")
    private WebElement ajaxButton;

    @FindBy(id = "form:ajaxButtonExecuted")
    private WebElement ajaxButtonExecuted;

    @FindBy(id = "form:ajaxLink")
    private WebElement ajaxLink;

    @FindBy(id = "form:ajaxLinkExecuted")
    private WebElement ajaxLinkExecuted;

    @FindBy(id = "form:outcomeButton")
    private WebElement outcomeButton;

    @Test
    public void testCommandLink() {
        var page = getPage(PAGE);
        assertNull(getNonce(page), "No CSP nonce must be emitted when CSP is disabled (the default)");
        assertTrue(page.isAttributeWired(commandLink, "onclick"), "commandLink must have its onclick handler rendered");
        assertEquals("false", commandLinkExecuted.getText());
        page.guardHttp(commandLink::click);
        assertEquals("true", commandLinkExecuted.getText());
    }

    // A plain h:commandButton renders a native submit input without an onclick handler, so unlike the
    // link and ajax variants there is nothing to assert on the handler; the full postback must just work.
    @Test
    public void testCommandButton() {
        var page = getPage(PAGE);
        assertNull(getNonce(page), "No CSP nonce must be emitted when CSP is disabled (the default)");
        assertEquals("false", commandButtonExecuted.getText());
        page.guardHttp(commandButton::click);
        assertEquals("true", commandButtonExecuted.getText());
    }

    @Test
    public void testCommandButtonAjax() {
        var page = getPage(PAGE);
        assertNull(getNonce(page), "No CSP nonce must be emitted when CSP is disabled (the default)");
        assertTrue(page.isAttributeWired(ajaxButton, "onclick"), "ajaxButton must have its onclick handler rendered");
        assertEquals("false", ajaxButtonExecuted.getText());
        page.guardAjax(ajaxButton::click);
        assertEquals("true", ajaxButtonExecuted.getText());
    }

    @Test
    public void testCommandLinkAjax() {
        var page = getPage(PAGE);
        assertNull(getNonce(page), "No CSP nonce must be emitted when CSP is disabled (the default)");
        assertTrue(page.isAttributeWired(ajaxLink, "onclick"), "ajaxLink must have its onclick handler rendered");
        assertEquals("false", ajaxLinkExecuted.getText());
        page.guardAjax(ajaxLink::click);
        assertEquals("true", ajaxLinkExecuted.getText());
    }

    @Test
    public void testOutcomeButton() {
        var page = getPage(PAGE);
        assertNull(getNonce(page), "No CSP nonce must be emitted when CSP is disabled (the default)");
        assertTrue(page.isAttributeWired(outcomeButton, "onclick"), "outcomeButton must have its onclick handler rendered");
    }

    private String getNonce(WebPage page) {
        var scripts = page.findElements(By.cssSelector("script[src*='jakarta.faces.resource/faces.js']"));
        return scripts.isEmpty() ? null : scripts.get(0).getDomAttribute("nonce");
    }
}
