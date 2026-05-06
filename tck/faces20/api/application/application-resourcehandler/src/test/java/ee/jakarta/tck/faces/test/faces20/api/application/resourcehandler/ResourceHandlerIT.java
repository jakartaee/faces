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
package ee.jakarta.tck.faces.test.faces20.api.application.resourcehandler;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;

class ResourceHandlerIT extends BaseITNG {

    private void runServletTest(String testName) {
        String body = getResponseBody("ResourceHandlerTestServlet?testname=" + testName);
        assertTrue(body.contains("Test PASSED"), "Expected 'Test PASSED' in response but got:\n" + body);
    }

    @Test void resourceHandlerCreateResourceBothNullTest() { runServletTest("resourceHandlerCreateResourceBothNullTest"); }
    @Test void resourceHandlerCreateResourceContentNullTest() { runServletTest("resourceHandlerCreateResourceContentNullTest"); }
    @Test void resourceHandlerCreateResourceLibNullTest() { runServletTest("resourceHandlerCreateResourceLibNullTest"); }
    @Test void resourceHandlerCreateResourceNLCTest() { runServletTest("resourceHandlerCreateResourceNLCTest"); }
    @Test void resourceHandlerCreateResourceNLTest() { runServletTest("resourceHandlerCreateResourceNLTest"); }
    @Test void resourceHandlerCreateResourceNPETest() { runServletTest("resourceHandlerCreateResourceNPETest"); }
    @Test void resourceHandlerCreateResourceNTest() { runServletTest("resourceHandlerCreateResourceNTest"); }
    @Test void resourceHandlerHandleResourceRequestTest() { runServletTest("resourceHandlerHandleResourceRequestTest"); }
    @Test void resourceHandlerIsResourceRenderedTest() { runServletTest("resourceHandlerIsResourceRenderedTest"); }
    @Test void resourceHandlerMarkResourceRenderedTest() { runServletTest("resourceHandlerMarkResourceRenderedTest"); }
    @Test void resourceHandlergetRendererTypeForResourceNameTest() { runServletTest("resourceHandlergetRendererTypeForResourceNameTest"); }
    @Test void resourceHandlerlibraryExistsTest() { runServletTest("resourceHandlerlibraryExistsTest"); }
}
