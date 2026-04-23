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
package ee.jakarta.tck.faces.test.faces20.api.render.renderkit;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;

class RenderKitIT extends BaseITNG {

    private void runServletTest(String testName) {
        String body = getResponseBody("RenderKitTestServlet?testname=" + testName);
        assertTrue(body.contains("Test PASSED"), "Expected 'Test PASSED' in response but got:\n" + body);
    }

    @Test void renderKitAddClientBehaviorRendererNPETest() { runServletTest("renderKitAddClientBehaviorRendererNPETest"); }
    @Test void renderKitAddGetClientBehaviorRendererTest() { runServletTest("renderKitAddGetClientBehaviorRendererTest"); }
    @Test void renderKitAddGetRendererTest() { runServletTest("renderKitAddGetRendererTest"); }
    @Test void renderKitAddRendererNPETest() { runServletTest("renderKitAddRendererNPETest"); }
    @Test void renderKitCreateResponseStreamTest() { runServletTest("renderKitCreateResponseStreamTest"); }
    @Test void renderKitCreateResponseWriterInvalidContentTypeTest() { runServletTest("renderKitCreateResponseWriterInvalidContentTypeTest"); }
    @Test void renderKitCreateResponseWriterInvalidEncodingTest() { runServletTest("renderKitCreateResponseWriterInvalidEncodingTest"); }
    @Test void renderKitCreateResponseWriterTest() { runServletTest("renderKitCreateResponseWriterTest"); }
    @Test void renderKitGetClientBehaviorRendererNPETest() { runServletTest("renderKitGetClientBehaviorRendererNPETest"); }
    @Test void renderKitGetClientBehaviorRendererTypesTest() { runServletTest("renderKitGetClientBehaviorRendererTypesTest"); }
    @Test void renderKitGetRendererNPETest() { runServletTest("renderKitGetRendererNPETest"); }
    @Test void renderKitGetRendererTypesTest() { runServletTest("renderKitGetRendererTypesTest"); }
    @Test void renderKitGetResponseStateManagerTest() { runServletTest("renderKitGetResponseStateManagerTest"); }
}
