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

package ee.jakarta.tck.faces.faces22.iteration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * A ui:repeat must not double-iterate its children when its child value-holders are visited via visitTree, so each
 * per-row value is appended exactly once.
 */
class Issue2960IT extends BaseITNG {

    /**
     * Clicking the visitChildren button walks the component tree with UIComponent#visitTree, appending each visited
     * per-row "out" value. The accumulated result must be the single sequence 12345678910 and never the
     * double-iterated 1234567891010.
     *
     * @see jakarta.faces.component.UIComponent#visitTree
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2960
     */
    @Test
    void testTooManyIterations() {
        WebPage page = getPage("issue2960.xhtml");
        page.guardHttp(() -> page.findElement(By.id("form:submit")).click());

        assertFalse(page.containsText("Iterations: 1234567891010"), "ui:repeat must not double-iterate its children");
        assertTrue(page.containsText("Iterations: 12345678910"), "Each per-row value is visited exactly once");
    }
}
