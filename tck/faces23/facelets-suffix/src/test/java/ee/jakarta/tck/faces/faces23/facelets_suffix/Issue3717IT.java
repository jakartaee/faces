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

package ee.jakarta.tck.faces.faces23.facelets_suffix;

import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.webapp.FacesServlet;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue3717IT extends BaseITNG {

    /**
     * The Faces Servlet is mapped to {@code *.foo} and {@code jakarta.faces.FACELETS_SUFFIX}
     * is set to {@code .bar}, so requesting {@code Issue3717.foo} must resolve and render the
     * {@code Issue3717.bar} facelet.
     *
     * @see FacesServlet
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3717
     */
    @Test
    void testCustomFaceletsSuffix() throws Exception {
        WebPage page = getPage("Issue3717.foo");
        assertTrue(page.containsText("Hello from Facelets"));
    }
}
