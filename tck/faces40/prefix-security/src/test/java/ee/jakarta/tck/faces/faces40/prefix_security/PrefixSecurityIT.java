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
package ee.jakarta.tck.faces.faces40.prefix_security;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.faces.webapp.FacesServlet;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;

class PrefixSecurityIT extends BaseITNG {

    /**
     * A prefix-mapped ({@code /faces/*}) FacesServlet must never expose {@code WEB-INF} content,
     * regardless of case: every such request returns 404.
     *
     * @see FacesServlet
     */
    @Test
    void webInfIsNotServed() {
        assertNotFound("faces/WEB-INF/web.xml");
        assertNotFound("faces/WEB-INF");
        assertNotFound("faces/WEB-INF/");
        assertNotFound("faces/web-Inf/web.xml");
    }

    /**
     * A prefix-mapped ({@code /faces/*}) FacesServlet must never expose {@code META-INF} content,
     * regardless of case: every such request returns 404.
     *
     * @see FacesServlet
     */
    @Test
    void metaInfIsNotServed() {
        assertNotFound("faces/META-INF/MANIFEST.MF");
        assertNotFound("faces/META-INF");
        assertNotFound("faces/META-INF/");
        assertNotFound("faces/mEtA-InF/MANIFEST.MF");
    }

    private void assertNotFound(String path) {
        assertEquals(404, getStatusCode(path), "Expected 404 for " + path);
    }
}
