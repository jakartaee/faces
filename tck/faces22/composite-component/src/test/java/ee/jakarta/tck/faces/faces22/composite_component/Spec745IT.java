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

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Spec745IT extends BaseITNG {

    private void assertTypeBeforeAndAfterPostback(String test, String expectedType) throws Exception {
        String expected = "type of @" + test + ": " + expectedType;
        WebPage page = getPage("spec745.xhtml?test=" + test);
        assertTrue(page.containsText(expected), "before postback: " + expected);
        page.guardHttp(() -> page.findElement(By.id("form:submit")).click());
        assertTrue(page.containsText(expected), "after postback: " + expected);
    }

    /**
     * @see jakarta.faces.view.facelets.CompositeFaceletHandler
     * @see https://github.com/jakartaee/faces/issues/745
     */
    @Test
    void untypedUnset() throws Exception {
        assertTypeBeforeAndAfterPostback("untypedXunset", "Object");
    }

    /**
     * @see jakarta.faces.view.facelets.CompositeFaceletHandler
     * @see https://github.com/jakartaee/faces/issues/745
     */
    @Test
    void untypedSetByApi() throws Exception {
        assertTypeBeforeAndAfterPostback("untypedXsetByApi", "Object");
    }

    /**
     * @see jakarta.faces.view.facelets.CompositeFaceletHandler
     * @see https://github.com/jakartaee/faces/issues/745
     */
    @Test
    void untypedLiteral() throws Exception {
        assertTypeBeforeAndAfterPostback("untypedXliteral", "Object");
    }

    /**
     * @see jakarta.faces.view.facelets.CompositeFaceletHandler
     * @see https://github.com/jakartaee/faces/issues/745
     */
    @Test
    void untypedWideEL() throws Exception {
        assertTypeBeforeAndAfterPostback("untypedXwideEL", "Animal");
    }

    /**
     * @see jakarta.faces.view.facelets.CompositeFaceletHandler
     * @see https://github.com/jakartaee/faces/issues/745
     */
    @Test
    void untypedMediumEL() throws Exception {
        assertTypeBeforeAndAfterPostback("untypedXmediumEL", "Dog");
    }

    /**
     * @see jakarta.faces.view.facelets.CompositeFaceletHandler
     * @see https://github.com/jakartaee/faces/issues/745
     */
    @Test
    void untypedNarrowEL() throws Exception {
        assertTypeBeforeAndAfterPostback("untypedXnarrowEL", "Wienerdoodle");
    }

    /**
     * @see jakarta.faces.view.facelets.CompositeFaceletHandler
     * @see https://github.com/jakartaee/faces/issues/745
     */
    @Test
    void untypedNullEL() throws Exception {
        assertTypeBeforeAndAfterPostback("untypedXnullEL", "Dog");
    }

    /**
     * @see jakarta.faces.view.facelets.CompositeFaceletHandler
     * @see https://github.com/jakartaee/faces/issues/745
     */
    @Test
    void typedUnset() throws Exception {
        assertTypeBeforeAndAfterPostback("typedXunset", "Dog");
    }

    /**
     * @see jakarta.faces.view.facelets.CompositeFaceletHandler
     * @see https://github.com/jakartaee/faces/issues/745
     */
    @Test
    void typedLiteral() throws Exception {
        assertTypeBeforeAndAfterPostback("typedXliteral", "Integer");
    }

    /**
     * @see jakarta.faces.view.facelets.CompositeFaceletHandler
     * @see https://github.com/jakartaee/faces/issues/745
     */
    @Test
    void typedWideEL() throws Exception {
        assertTypeBeforeAndAfterPostback("typedXwideEL", "Dog");
    }

    /**
     * @see jakarta.faces.view.facelets.CompositeFaceletHandler
     * @see https://github.com/jakartaee/faces/issues/745
     */
    @Test
    void typedMediumEL() throws Exception {
        assertTypeBeforeAndAfterPostback("typedXmediumEL", "Dog");
    }

    /**
     * @see jakarta.faces.view.facelets.CompositeFaceletHandler
     * @see https://github.com/jakartaee/faces/issues/745
     */
    @Test
    void typedNarrowEL() throws Exception {
        assertTypeBeforeAndAfterPostback("typedXnarrowEL", "Wienerdoodle");
    }

    /**
     * @see jakarta.faces.view.facelets.CompositeFaceletHandler
     * @see https://github.com/jakartaee/faces/issues/745
     */
    @Test
    void typedNullEL() throws Exception {
        assertTypeBeforeAndAfterPostback("typedXnullEL", "Dog");
    }

    /**
     * @see jakarta.faces.view.facelets.CompositeFaceletHandler
     * @see https://github.com/jakartaee/faces/issues/745
     */
    @Test
    void typedSetByApi() throws Exception {
        assertTypeBeforeAndAfterPostback("typedXsetByApi", "Dog");
    }
}
