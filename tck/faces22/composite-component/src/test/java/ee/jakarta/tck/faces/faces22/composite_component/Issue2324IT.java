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

package ee.jakarta.tck.faces.faces22.composite_component;

import static ee.jakarta.tck.faces.faces22.composite_component.Issue2324IdUniquenessListener.DUPLICATE;
import static ee.jakarta.tck.faces.faces22.composite_component.Issue2324IdUniquenessListener.UNIQUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;
import jakarta.faces.component.UIComponent;

class Issue2324IT extends BaseITNG {

    /**
     * Facet children of sibling components and of a composite component get distinct generated ids, so the
     * view contains no two components sharing a client id.
     *
     * @see UIComponent#getClientId()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2324
     */
    @Test
    void facetsAroundAndInsideACompositeYieldUniqueIds() {
        WebPage page = getPage("issue2324.xhtml");

        assertEquals(200, page.getResponseStatus(), "The view must render.");
        assertFalse(page.containsText(DUPLICATE), "No two components may share a client id.");
        assertEquals(UNIQUE, page.findElement(By.id("messages")).getText(), "id uniqueness verdict");
    }
}
