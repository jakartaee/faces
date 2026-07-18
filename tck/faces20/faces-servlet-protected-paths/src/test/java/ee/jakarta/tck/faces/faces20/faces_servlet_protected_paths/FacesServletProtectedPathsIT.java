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
package ee.jakarta.tck.faces.faces20.faces_servlet_protected_paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.faces.webapp.FacesServlet;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;

class FacesServletProtectedPathsIT extends BaseITNG {

    /**
     * A view under the FacesServlet prefix mapping renders normally.
     *
     * @see FacesServlet
     */
    @Test
    void testNormalPage() {
        assertEquals(200, getStatusCode("faces/index.xhtml"), "Normal view under /faces/* must render");
    }

    /**
     * FacesServlet must not serve /WEB-INF/web.xml through its prefix mapping.
     *
     * @see FacesServlet
     */
    @Test
    void testWebInfWebXml() {
        assertEquals(404, getStatusCode("faces/WEB-INF/web.xml"), "/WEB-INF/web.xml must not be served");
    }

    /**
     * FacesServlet must not serve a facelet placed under /WEB-INF through its prefix mapping.
     *
     * @see FacesServlet
     */
    @Test
    void testWebInfProtectedView() {
        assertEquals(404, getStatusCode("faces/WEB-INF/youcantgetme.xhtml"), "/WEB-INF view must not be served");
    }

    /**
     * FacesServlet must not serve /WEB-INF/faces-config.xml through its prefix mapping.
     *
     * @see FacesServlet
     */
    @Test
    void testWebInfFacesConfig() {
        assertEquals(404, getStatusCode("faces/WEB-INF/faces-config.xml"), "/WEB-INF/faces-config.xml must not be served");
    }

    /**
     * FacesServlet must not serve a facelet placed under /META-INF through its prefix mapping.
     *
     * @see FacesServlet
     */
    @Test
    void testMetaInfProtectedView() {
        assertEquals(404, getStatusCode("faces/META-INF/youcantgetme.xhtml"), "/META-INF view must not be served");
    }
}
