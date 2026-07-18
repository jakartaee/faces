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

import jakarta.faces.component.UIData;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue3039IT extends BaseITNG {

    /**
     * Toggles two conditionally-rendered {@code ui:repeat} blocks via an ajax command link and verifies that each ajax
     * re-render swaps which block is shown, i.e. the repeat row state does not leak across the toggle.
     *
     * @see jakarta.faces.component.UIRepeat
     * @see UIData
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3039
     */
    @Test
    void testRepeatToggleClick() {
        WebPage page = getPage("issue3039.xhtml");

        page.guardAjax(page.findElement(By.id("form:submit"))::click);
        assertTrue(page.containsText("- even"), "After first toggle the 'even' repeat must be shown");
        assertFalse(page.containsText("- odd"), "After first toggle the 'odd' repeat must be hidden");

        page.guardAjax(page.findElement(By.id("form:submit"))::click);
        assertFalse(page.containsText("- even"), "After second toggle the 'even' repeat must be hidden");
        assertTrue(page.containsText("- odd"), "After second toggle the 'odd' repeat must be shown");
    }
}
