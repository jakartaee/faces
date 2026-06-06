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

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * Verifies that an empty HTML attribute on a plain (non-component) element is preserved in the rendered output
 * rather than being dropped by the Facelets compiler.
 */
class Issue2049IT extends BaseITNG {

    /**
     * Verifies that an empty {@code alt=""} attribute on a plain {@code img} element is rendered verbatim as
     * {@code alt=""} in the response, i.e. the Facelets view handler does not strip attributes whose value is the
     * empty string.
     *
     * @see jakarta.faces.view.facelets.Facelet
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2049
     */
    @Test
    void testHtmlAttributeEmpty() throws Exception {
        WebPage page = getPage("issue2049.xhtml");
        assertTrue(page.getSource().contains("alt=\"\""), "Empty alt attribute must be preserved in the output");
    }
}
