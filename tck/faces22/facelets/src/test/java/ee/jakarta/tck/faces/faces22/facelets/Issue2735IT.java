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

package ee.jakarta.tck.faces.faces22.facelets;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * A view marked {@code transient="true"} is stateless: its state is not saved, so the
 * {@code jakarta.faces.ViewState} hidden field carries the marker value {@code "stateless"} on the
 * initial render and on every postback.
 */
public class Issue2735IT extends BaseITNG {

    private static final String STATELESS = "\"stateless\"";

    /**
     * A transient view renders the stateless marker and keeps it after a postback.
     *
     * @see jakarta.faces.component.UIViewRoot#isTransient()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2735
     */
    @Test
    void testDefaultStateless() {
        WebPage page = getPage("issue2735.xhtml");
        assertTrue(page.containsSource(STATELESS), "initial render is stateless");

        page.guardHttp(page.findElement(By.id("form:button"))::click);
        assertTrue(page.containsSource(STATELESS), "postback stays stateless");
    }

    /**
     * A transient view stays stateless across both an ajax postback and a subsequent non-ajax submit.
     *
     * @see jakarta.faces.component.UIViewRoot#isTransient()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2735
     */
    @Test
    void testStatelessAjax() {
        WebPage page = getPage("issue2735Ajax.xhtml");
        assertTrue(page.containsSource(STATELESS), "initial render is stateless");
        assertTrue(page.containsText("[]"), "no parameter1 yet");

        page.guardAjax(page.findElement(By.id("form:ajaxButton"))::click);
        assertTrue(page.containsSource(STATELESS), "ajax postback stays stateless");
        assertTrue(page.containsText("[ajax]"), "ajax parameter1 rendered");

        page.guardHttp(page.findElement(By.id("form:submitButton"))::click);
        assertTrue(page.containsSource(STATELESS), "non-ajax postback stays stateless");
        assertTrue(page.containsText("[non-ajax]"), "non-ajax parameter1 rendered");
    }

    /**
     * A view marked {@code transient="false"} is stateful and carries no stateless postback marker.
     *
     * @see jakarta.faces.component.UIViewRoot#isTransient()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2735
     */
    @Test
    void testDefaultStateful() {
        WebPage page = getPage("issue2735Stateful.xhtml");
        assertFalse(page.containsSource(STATELESS), "initial render is stateful");

        page.guardHttp(page.findElement(By.id("form:button"))::click);
        assertFalse(page.containsSource(STATELESS), "postback stays stateful");
    }
}
