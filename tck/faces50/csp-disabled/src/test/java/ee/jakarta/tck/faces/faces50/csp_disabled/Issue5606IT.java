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
 * With CSP nonce injection disabled (the default, i.e. {@link ResourceHandler#ENABLE_CSP_NONCE} not set),
 * command components must render their {@code onclick} handler and keep working, and no CSP nonce may be
 * emitted onto the {@code faces.js} script element. Whether the handler is rendered as an inline
 * {@code onclick} attribute or wired via a {@code <script>} block is implementation-specific per
 * jakartaee/faces#2167, so this asserts the observable contract (handler present, click still works,
 * no nonce) rather than the rendering strategy.
 *
 * @see ResourceHandler#ENABLE_CSP_NONCE
 * @see https://github.com/eclipse-ee4j/mojarra/issues/5606
 */
class Issue5606IT extends BaseITNG {

    @FindBy(id = "form:commandLink")
    private WebElement commandLink;

    @FindBy(id = "form:commandLinkExecuted")
    private WebElement commandLinkExecuted;

    @FindBy(id = "form:ajaxButton")
    private WebElement ajaxButton;

    @FindBy(id = "form:ajaxExecuted")
    private WebElement ajaxExecuted;

    @Test
    public void testCommandLinkWithoutCsp() {
        var page = getPage("issue5606.xhtml");
        assertNull(getNonce(page), "No CSP nonce must be emitted when CSP is disabled (the default)");
        assertTrue(page.isAttributeWired(commandLink, "onclick"), "commandLink must have its onclick handler rendered");
        assertEquals("false", commandLinkExecuted.getText());
        page.guardHttp(commandLink::click);
        assertEquals("true", commandLinkExecuted.getText());
    }

    @Test
    public void testCommandButtonAjaxWithoutCsp() {
        var page = getPage("issue5606.xhtml");
        assertNull(getNonce(page), "No CSP nonce must be emitted when CSP is disabled (the default)");
        assertTrue(page.isAttributeWired(ajaxButton, "onclick"), "ajaxButton must have its onclick handler rendered");
        assertEquals("false", ajaxExecuted.getText());
        page.guardAjax(ajaxButton::click);
        assertEquals("true", ajaxExecuted.getText());
    }

    private String getNonce(WebPage page) {
        var scripts = page.findElements(By.cssSelector("script[src*='jakarta.faces.resource/faces.js']"));
        return scripts.isEmpty() ? null : scripts.get(0).getDomAttribute("nonce");
    }
}
