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
package ee.jakarta.tck.faces.faces22.flows;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * The ampersand in an {@code f:param} (or programmatically built redirect URL) must be correctly
 * URL-encoded so that a single multi-word search term arrives intact as one query parameter on the
 * redirect target, rather than being split into several parameters by an unencoded ampersand.
 */
class Issue3027IT extends BaseITNG {

    private static final String FULL = "query=Laurel & Hardy";

    /**
     * Redirect built with explicit URL encoding: the full term survives as a single parameter.
     *
     * @see jakarta.faces.application.NavigationHandler
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3027
     */
    @Test
    void testOutcomeParameter1() {
        WebPage page = getPage("outcomeParameterForm.xhtml");
        page.guardHttp(() -> page.findElement(By.id("form:submit1")).click());
        assertTrue(page.containsText(FULL), "encoded redirect keeps the full term");
    }

    /**
     * Redirect built without URL encoding: the unencoded ampersand splits the value, so the full
     * term is lost but the leading word still appears as the query parameter.
     *
     * @see jakarta.faces.application.NavigationHandler
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3027
     */
    @Test
    void testOutcomeParameter2() {
        WebPage page = getPage("outcomeParameterForm.xhtml");
        page.guardHttp(() -> page.findElement(By.id("form:submit2")).click());
        assertFalse(page.containsText(FULL), "unencoded ampersand splits the value");
        assertTrue(page.containsText("query=Laurel"), "leading word still arrives");
    }

    /**
     * Redirect via ExternalContext with explicit URL encoding: the full term survives.
     *
     * @see jakarta.faces.context.ExternalContext#redirect(String)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3027
     */
    @Test
    void testOutcomeParameter3() {
        WebPage page = getPage("outcomeParameterForm.xhtml");
        page.guardHttp(() -> page.findElement(By.id("form:submit3")).click());
        assertTrue(page.containsText(FULL), "external-context redirect keeps the full term");
    }

    /**
     * Redirect using {@code h:button} with {@code f:param}: the component encodes the value.
     *
     * @see jakarta.faces.component.html.HtmlOutcomeTargetButton
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3027
     */
    @Test
    void testOutcomeParameter4() {
        WebPage page = getPage("outcomeParameterForm.xhtml");
        page.guardHttp(() -> page.findElement(By.id("form:submit4")).click());
        assertTrue(page.containsText(FULL), "h:button f:param encodes the value");
    }

    /**
     * Redirect using {@code h:link} with {@code f:param}: the component encodes the value.
     *
     * @see jakarta.faces.component.html.HtmlOutcomeTargetLink
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3027
     */
    @Test
    void testOutcomeParameter5() {
        WebPage page = getPage("outcomeParameterForm.xhtml");
        page.guardHttp(() -> page.findElement(By.id("form:submit5")).click());
        assertTrue(page.containsText(FULL), "h:link f:param encodes the value");
    }
}
