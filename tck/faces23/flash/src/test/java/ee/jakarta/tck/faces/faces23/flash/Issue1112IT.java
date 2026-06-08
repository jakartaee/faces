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

package ee.jakarta.tck.faces.faces23.flash;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * A global {@link jakarta.faces.application.FacesMessage} added before a redirect survives that redirect when
 * {@code flash.keepMessages} is set to {@code true}, but only for a single hop: it is gone after the next
 * request to a view that does not keep messages. The survival also holds across validation errors before the
 * final navigation.
 */
class Issue1112IT extends BaseITNG {

    private static final String GLOBAL_MESSAGE = "This is a global message";

    /**
     * Submits a form whose action adds a global message and redirects. Asserts the message survives the redirect
     * (keepMessages), then submits again on the target view (which does not keep messages) and asserts the message
     * is no longer displayed, confirming the single-hop lifetime.
     *
     * @see jakarta.faces.context.Flash#setKeepMessages(boolean)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/1112
     */
    @Test
    void testMessagesAreKeptAfterRedirect() {
        WebPage page = getPage("issue1112.xhtml");
        page.guardHttp(() -> page.findElement(By.id("form:submit")).click());
        assertTrue(page.containsText(GLOBAL_MESSAGE), "FacesMessage must survive the redirect when keepMessages is true");

        page.guardHttp(() -> page.findElement(By.id("form:button")).click());
        assertFalse(page.containsText(GLOBAL_MESSAGE), "FacesMessage must not be re-displayed on a view that does not keep messages");
    }

    /**
     * Submits the required-input form twice while the input is empty (two consecutive validation errors), then
     * fills it and submits a final time to navigate. Asserts the global message added during the final action
     * survives the redirect, confirming keepMessages also holds across preceding validation errors.
     *
     * @see jakarta.faces.context.Flash#setKeepMessages(boolean)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/1112
     */
    @Test
    void testMessagesAreKeptAfterRedirectAfterDoubleValidationError() {
        WebPage page = getPage("issue1112.xhtml");

        page.guardHttp(() -> page.findElement(By.id("requiredForm:submitRequired")).click());
        assertTrue(page.getTitle().contains("first page"), "Validation error must keep the user on the first page");

        page.guardHttp(() -> page.findElement(By.id("requiredForm:submitRequired")).click());
        assertTrue(page.getTitle().contains("first page"), "Second validation error must keep the user on the first page");

        page.findElement(By.id("requiredForm:requiredInput")).sendKeys("a value");
        page.guardHttp(() -> page.findElement(By.id("requiredForm:submitRequired")).click());
        assertTrue(page.getTitle().contains("second page"), "Valid submit must navigate to the second page");

        assertTrue(page.containsText(GLOBAL_MESSAGE), "FacesMessage must survive the redirect after preceding validation errors");
    }
}
