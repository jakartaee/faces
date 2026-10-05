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
package ee.jakarta.tck.faces.faces50.csp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;

/**
 * The webapp enables <code>jakarta.faces.ENABLE_CSP_NONCE</code>, so <code>h:button</code> wires its click handler via an inline script instead of an
 * <code>onclick</code> attribute. The view loads <code>faces.js</code> via a component resource in the <code>body</code> target, which is rendered at the end
 * of the body, after that inline script.
 */
class Issue6038IT extends BaseITNG {

    @FindBy(id = "button")
    private WebElement button;

    @FindBy(id = "clicked")
    private WebElement clicked;

    /**
     * The inline script requires <code>faces.js</code> to be loaded at that point. A <code>faces.js</code> component resource which is only rendered later in
     * the body must not count as loaded, and <code>faces.js</code> must still be rendered only once.
     *
     * @see <a href="https://github.com/eclipse-ee4j/mojarra/issues/6038">https://github.com/eclipse-ee4j/mojarra/issues/6038</a>
     */
    @Test
    public void testButtonNavigatesWhenFacesJsIsInBodyTarget() {
        var page = getPage("issue6038.xhtml");
        assertEquals(1, page.findElements(By.cssSelector("script[src*='jakarta.faces.resource/faces.js']")).size());
        assertEquals("false", clicked.getText());
        page.guardHttp(button::click);
        assertEquals("true", clicked.getText());
    }

}
