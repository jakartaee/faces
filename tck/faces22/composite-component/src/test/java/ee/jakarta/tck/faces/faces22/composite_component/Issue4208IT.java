/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
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

package ee.jakarta.tck.faces.faces22.composite_component;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.component.UINamingContainer;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * Composite components conditionally included by {@code c:if} must be added to and removed from the
 * component tree on every postback that flips the condition, both when the {@code c:if} sits
 * directly in the form and when it is wrapped in a {@code ui:fragment}.
 */
public class Issue4208IT extends BaseITNG {

    /**
     * Each of three independently toggled composite components appears when shown and disappears
     * when hidden again.
     *
     * @see UINamingContainer
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4208
     */
    @Test
    void testToggleEachComposite() {
        assertToggling("issue4208.xhtml");
    }

    /**
     * The same toggling holds when each {@code c:if} is wrapped in a {@code ui:fragment}.
     *
     * @see UINamingContainer
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4208
     */
    @Test
    void testToggleEachCompositeWrappedInFragment() {
        assertToggling("issue4208-fragments.xhtml");
    }

    /**
     * Shows composites 3, 2 and 1 in turn, asserting each one appears without disturbing the others,
     * then hides them again in the same order.
     */
    private void assertToggling(String view) {
        WebPage page = getPage(view);

        for (int idx : new int[] { 3, 2, 1 }) {
            assertAbsent(page, idx);
            click(page, idx, "show");
            assertPresent(page, idx);
        }

        for (int idx : new int[] { 3, 2, 1 }) {
            assertPresent(page, idx);
            click(page, idx, "hide");
            assertAbsent(page, idx);
        }
    }

    private void click(WebPage page, int idx, String button) {
        page.guardHttp(page.findElement(By.id("form:button_" + idx + ":" + button))::click);
    }

    private void assertPresent(WebPage page, int idx) {
        assertEquals("OutputText " + idx, page.findElement(By.id("form:included_" + idx + ":outputText")).getText(),
                "composite " + idx + " is rendered while shown");
    }

    private void assertAbsent(WebPage page, int idx) {
        assertTrue(page.findElements(By.id("form:included_" + idx)).isEmpty(),
                "composite " + idx + " is absent from the tree while hidden");
    }
}
