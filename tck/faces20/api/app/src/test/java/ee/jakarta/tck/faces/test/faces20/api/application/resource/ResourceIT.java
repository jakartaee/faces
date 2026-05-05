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
package ee.jakarta.tck.faces.test.faces20.api.application.resource;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;

class ResourceIT extends BaseITNG {

    private void runServletTest(String testName) {
        String body = getResponseBody("ResourceTestServlet?testname=" + testName);
        assertTrue(body.contains("Test PASSED"), "Expected 'Test PASSED' in response but got:\n" + body);
    }

    @Test void resourceGetContentTypeTest() { runServletTest("resourceGetContentTypeTest"); }
    @Test void resourceGetInputStreamTest() { runServletTest("resourceGetInputStreamTest"); }
    @Test void resourceGetLibraryNameNullTest() { runServletTest("resourceGetLibraryNameNullTest"); }
    @Test void resourceGetLibraryNameTest() { runServletTest("resourceGetLibraryNameTest"); }
    @Test void resourceGetRequestPathLibTest() { runServletTest("resourceGetRequestPathLibTest"); }
    @Test void resourceGetRequestPathTest() { runServletTest("resourceGetRequestPathTest"); }
    @Test void resourceGetResourceNameTest() { runServletTest("resourceGetResourceNameTest"); }
    @Test void resourceGetResponseHeadersTest() { runServletTest("resourceGetResponseHeadersTest"); }
    @Test void resourceGetURLTest() { runServletTest("resourceGetURLTest"); }
    @Test void resourceSetContentTypeTest() { runServletTest("resourceSetContentTypeTest"); }
    @Test void resourceSetLibraryNameTest() { runServletTest("resourceSetLibraryNameTest"); }
    @Test void resourceSetResourceNameNPETest() { runServletTest("resourceSetResourceNameNPETest"); }
    @Test void resourceSetResourceNameTest() { runServletTest("resourceSetResourceNameTest"); }
    @Test void resourceToStringTest() { runServletTest("resourceToStringTest"); }
    @Test void resourceUserAgentNeedsUpdateTrueTest() { runServletTest("resourceUserAgentNeedsUpdateTrueTest"); }
}
