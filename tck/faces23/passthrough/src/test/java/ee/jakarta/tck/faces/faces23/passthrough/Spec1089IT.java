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

package ee.jakarta.tck.faces.faces23.passthrough;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * Verifies HTML5 pass-through attribute rendering: {@code f:passThroughAttribute},
 * {@code f:passThroughAttributes}, and the {@code p:} pass-through namespace, plus the
 * contrast with plain renderer-specific component attributes via {@code f:attributes}.
 */
class Spec1089IT extends BaseITNG {

    /**
     * A {@code data-*} attribute name and value, whether literal, EL-derived, nested or
     * deeply hyphenated, must render verbatim on the host element.
     *
     * @see jakarta.faces.component.UIComponent#getPassThroughAttributes()
     * @see https://github.com/jakartaee/faces/issues/1089
     */
    @Test
    void dataAttributesRenderVerbatim() throws Exception {
        WebPage page = getPage("spec1089-data.xhtml");
        String source = page.getSource();

        assertTrue(source.contains("data-development=\"/spec1089-data.xhtml\""), "EL-derived data-* name and value");
        assertTrue(source.contains("data-name=\"value\""), "simple data-* attribute");
        assertTrue(source.contains("data-outer-inner=\"innerValue\""), "nested data-* attribute");
        assertTrue(source.contains("data-a-b-c-d=\"e\""), "deeply hyphenated data-* attribute");
        assertTrue(source.contains("data-a-b-c-f=\"g\""), "deeply hyphenated data-* attribute");
        assertTrue(source.contains("data-a-b-c-h=\"i\""), "deeply hyphenated data-* attribute");
        assertTrue(source.contains("data-a-b-c-j-k-l-m=\"n\""), "deeply hyphenated data-* attribute");
    }

    /**
     * {@code f:passThroughAttributes} renders each map entry as an attribute, and the
     * {@code p:} namespace renders pass-through attributes without leaking the namespace.
     *
     * @see jakarta.faces.component.UIComponent#getPassThroughAttributes()
     * @see https://github.com/jakartaee/faces/issues/1089
     */
    @Test
    void passThroughAttributesAndNamespaceRender() throws Exception {
        WebPage page = getPage("spec1089-passthrough.xhtml");
        String source = page.getSource();

        assertTrue(source.contains("elname=\"/spec1089-passthrough.xhtml\""), "EL-derived map entry");
        assertTrue(source.contains("literalname=\"literalValue\""), "literal map entry");
        assertTrue(source.contains("foo=\"bar\""), "p: namespaced pass-through attribute");
        assertFalse(source.contains("jakarta.faces.passthrough"), "the p: namespace must not leak into the rendered markup");
    }
}
