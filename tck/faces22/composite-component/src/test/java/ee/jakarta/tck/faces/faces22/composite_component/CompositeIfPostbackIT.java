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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * A {@code c:if} inside a composite component follows a test over that component's own attributes on every postback, as one over any other expression does. The
 * subtree it builds must appear on the very postback whose action made the test hold, and disappear on the one that made it stop holding.
 */
class CompositeIfPostbackIT extends BaseITNG {

    /**
     * The action fills the list the composite component attribute resolves to, so the subtree the {@code c:if} guards must be built into the response of that
     * same postback.
     *
     * @see jakarta.faces.component.UIComponent#getCurrentCompositeComponent(jakarta.faces.context.FacesContext)
     */
    @Test
    void conditionalOnCompositeAttributeIsBuiltOnTheSamePostbackThatMakesItHold() {
        WebPage page = emptied();

        page.guardHttp(page.findElement(By.id("form:load"))::click);

        assertEquals("items=3", page.findElement(By.id("form:panel:conditional")).getText(), "the subtree is built");
    }

    /**
     * Building the view from scratch with the list already filled must build that same subtree, which is what tells a failure of the postback tests apart from
     * a composite component that does not render at all.
     */
    @Test
    void conditionalOnCompositeAttributeIsBuiltOnAGetWithAFilledList() {
        WebPage page = emptied();
        page.guardHttp(page.findElement(By.id("form:load"))::click);

        WebPage rebuilt = getPage("composite-if-postback.xhtml");

        assertEquals("items=3", rebuilt.findElement(By.id("form:panel:conditional")).getText(), "the subtree is built");
    }

    /**
     * The action empties that list, so the subtree must be gone from the response of that same postback.
     */
    @Test
    void conditionalOnCompositeAttributeIsRemovedOnTheSamePostbackThatMakesItStopHolding() {
        WebPage page = emptied();

        page.guardHttp(page.findElement(By.id("form:load"))::click);
        assertEquals("items=3", page.findElement(By.id("form:panel:conditional")).getText(), "the subtree is built");

        page.guardHttp(page.findElement(By.id("form:clear"))::click);
        assertTrue(page.findElements(By.id("form:panel:conditional")).isEmpty(), "the subtree is gone");
    }

    /**
     * The page with the list emptied by an action rather than by its initial value, so the outcome does not depend on what an earlier test left in the session.
     */
    private WebPage emptied() {
        WebPage page = getPage("composite-if-postback.xhtml");
        page.guardHttp(page.findElement(By.id("form:clear"))::click);
        assertTrue(page.findElements(By.id("form:panel:conditional")).isEmpty(), "nothing is built for an empty list");
        return page;
    }

}
