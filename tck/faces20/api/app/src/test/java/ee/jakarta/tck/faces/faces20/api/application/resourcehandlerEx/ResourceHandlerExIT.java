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
package ee.jakarta.tck.faces.faces20.api.application.resourcehandlerEx;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;

class ResourceHandlerExIT extends BaseITNG {

    private static final String EXCLUDED_RESOURCE = "faces/jakarta.faces.resource/secret.xhtml?ln=showcase";

    private void runServletTest(String testName) {
        String body = getResponseBody("ResourceHandlerExTestServlet?testname=" + testName);
        assertTrue(body.contains("Test PASSED"), "Expected 'Test PASSED' in response but got:\n" + body);
    }

    @Test void resourceHandlerExcludeClassTest() { runServletTest("resourceHandlerExcludeClassTest"); }
    @Test void resourceHandlerExcludeJSPTest() { runServletTest("resourceHandlerExcludeJSPTest"); }
    @Test void resourceHandlerExcludeJSPXTest() { runServletTest("resourceHandlerExcludeJSPXTest"); }
    @Test void resourceHandlerExcludePropertiesTest() { runServletTest("resourceHandlerExcludePropertiesTest"); }
    @Test void resourceHandlerExcludeXHTMLTest() { runServletTest("resourceHandlerExcludeXHTMLTest"); }

    /**
     * The sibling tests above only assert that '.xhtml' is listed in the excludes constant. This one
     * asserts the runtime enforcement: a resource request for a Facelet inside a resource library
     * must be rejected and must never deliver the Facelet source.
     *
     * @see jakarta.faces.application.ResourceHandler#RESOURCE_EXCLUDES_PARAM_NAME
     * @see https://jakarta.ee/specifications/faces/5.0/apidocs/jakarta.faces/jakarta/faces/application/resourcehandler#RESOURCE_EXCLUDES_PARAM_NAME
     */
    @Test
    void resourceHandlerExcludeXHTMLRuntimeTest() {
        assertNotEquals(200, getStatusCode(EXCLUDED_RESOURCE), "An excluded resource must not be served");

        String body = getResponseBody(EXCLUDED_RESOURCE);
        assertFalse(body.contains("<h:form"), "Facelet source leaked:\n" + body);
        assertFalse(body.contains("<h:body"), "Facelet source leaked:\n" + body);
    }
}
