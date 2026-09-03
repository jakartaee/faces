/*
 * Copyright (c) Contributors to Eclipse Foundation.
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
package ee.jakarta.tck.faces.faces50.facelets_view_mappings;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;

/**
 * The webapp declares {@code jakarta.faces.FACELETS_VIEW_MAPPINGS} as {@code /faces/*;*.tpl}, a prefix entry and an extension entry, neither of which names the
 * default Facelets suffix, and does not declare {@code jakarta.faces.FACELETS_SUFFIX} at all.
 */
class Issue5920IT extends BaseITNG {

    /**
     * {@code jakarta.faces.FACELETS_VIEW_MAPPINGS} declares additional views as Facelets, it does not withdraw the ones which are already recognized by their
     * suffix, so a view carrying the default suffix must still resolve. The whole application fails to deploy when it does not, because the runtime resolves a
     * view id carrying that suffix during startup to instantiate the Facelets view declaration language.
     *
     * @see https://github.com/eclipse-ee4j/mojarra/issues/5920
     */
    @Test
    void testDefaultSuffixView() {
        var page = getPage("defaultSuffix.jsf");
        assertTrue(page.containsText("default suffix view"));
    }

    /**
     * A view below a prefix entry of {@code jakarta.faces.FACELETS_VIEW_MAPPINGS} must resolve.
     */
    @Test
    void testPrefixMappedView() {
        var page = getPage("faces/prefixMapped.jsf");
        assertTrue(page.containsText("prefix mapped view"));
    }

    /**
     * An extension entry of {@code jakarta.faces.FACELETS_VIEW_MAPPINGS} declares that extension to be a Facelet, so an include of a fragment carrying it must
     * resolve.
     */
    @Test
    void testMappedSuffixInclude() {
        var page = getPage("mappedSuffixInclude.jsf");
        assertTrue(page.containsText("mapped suffix fragment"));
    }

    /**
     * A view carrying an extension entry of {@code jakarta.faces.FACELETS_VIEW_MAPPINGS} must be handled by Facelets, even though that extension is not a
     * suffix a view ID may be derived with. Requesting it through the prefix mapped {@code FacesServlet} is what makes it reachable, as a suffix mapped request
     * derives its view ID from {@code jakarta.faces.FACELETS_SUFFIX} instead.
     */
    @Test
    void testMappedSuffixView() {
        var page = getPage("views/mappedSuffix.tpl");
        assertTrue(page.containsText("mapped suffix view"));
    }

}
