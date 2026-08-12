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
package ee.jakarta.tck.faces.faces50.facelets_suffix;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;

/**
 * The webapp declares {@code jakarta.faces.FACELETS_SUFFIX} as {@code .page  .html}, with a redundant space between
 * the two entries, and {@code jakarta.faces.FACELETS_VIEW_MAPPINGS} as {@code *.page}.
 */
class Issue5918IT extends BaseITNG {

    /**
     * A view whose physical resource carries the first configured suffix must resolve.
     */
    @Test
    void testFirstConfiguredSuffix() {
        var page = getPage("firstSuffix.jsf");
        assertTrue(page.containsText("first suffix view"));
    }

    /**
     * {@code jakarta.faces.FACELETS_SUFFIX} is a list of which the first suffix whose physical resource exists wins,
     * so a view which exists only under the second configured suffix must resolve as well. This must also hold when
     * {@code jakarta.faces.FACELETS_VIEW_MAPPINGS} is declared and does not name that suffix.
     *
     * @see https://github.com/eclipse-ee4j/mojarra/issues/5918
     */
    @Test
    void testSecondConfiguredSuffix() {
        var page = getPage("secondSuffix.jsf");
        assertTrue(page.containsText("second suffix view"));
    }

    /**
     * The default Facelets suffix identifies a Facelet regardless of which view suffixes the webapp configures, so an
     * include of an {@code .xhtml} fragment must resolve even when the configured list does not name it.
     */
    @Test
    void testIncludeDefaultSuffixFragment() {
        var page = getPage("defaultSuffixInclude.jsf");
        assertTrue(page.containsText("default suffix fragment"));
    }

    /**
     * Redundant whitespace in {@code jakarta.faces.FACELETS_SUFFIX} must not yield an empty suffix, which would match
     * every resource and so let an include disclose a deployment descriptor.
     */
    @Test
    void testIncludeDescriptorBlocked() {
        String source = getResponseBody("descriptorInclude.jsf");
        assertFalse(source.contains("<web-app"), "web.xml content must not leak");
        assertFalse(source.contains("FACELETS_SUFFIX"), "web.xml content must not leak");
    }
}
