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
 * A composite component conditionally included by {@code c:if} must be added to and removed from the
 * component tree by an ajax postback that flips the condition and re-renders the enclosing form. The
 * unconditional sibling composite must be unaffected throughout.
 */
public class Issue4244IT extends BaseITNG {

    /**
     * The conditional composite is absent initially, present after the ajax show, and absent again
     * after the ajax hide.
     *
     * @see UINamingContainer
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4244
     */
    @Test
    void testShowAndHideConditionalComposite() {
        WebPage page = getPage("issue4244.xhtml");
        assertConditionalAbsent(page, "initially the item is not visible");
        assertStaticPresent(page);

        page.guardAjax(page.findElement(By.id("form:show"))::click);
        assertEquals("text", page.findElement(By.id("form:test1:text")).getText(),
                "the conditional composite is added to the tree by the ajax show");
        assertStaticPresent(page);

        page.guardAjax(page.findElement(By.id("form:hide"))::click);
        assertConditionalAbsent(page, "the conditional composite is removed from the tree by the ajax hide");
        assertStaticPresent(page);
    }

    private void assertConditionalAbsent(WebPage page, String message) {
        assertTrue(page.findElements(By.id("form:test1:text")).isEmpty(), message);
    }

    private void assertStaticPresent(WebPage page) {
        assertEquals("text", page.findElement(By.id("form:test2:text")).getText(),
                "the unconditional sibling composite stays rendered");
    }
}
