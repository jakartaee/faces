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
package ee.jakarta.tck.faces.test.faces20.api.application.viewhandler;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;

class ViewHandlerIT extends BaseITNG {

    private void runServletTest(String testName) {
        String body = getResponseBody("ViewHandlerTestServlet?testname=" + testName);
        assertTrue(body.contains("Test PASSED"), "Expected 'Test PASSED' in response but got:\n" + body);
    }

    @Test void viewHandlerAddGetRemoveProtectedViewsTest() { runServletTest("viewHandlerAddGetRemoveProtectedViewsTest"); }
    @Test void viewHandlerCalculateCharEncodingEXTTest() { runServletTest("viewHandlerCalculateCharEncodingEXTTest"); }

    @Test
    void viewHandlerCalculateCharEncodingHDRTest() {
        // calculateCharacterEncoding pulls charset from Content-Type when no session encoding is set.
        String body = getResponseBody("ViewHandlerTestServlet?testname=viewHandlerCalculateCharEncodingHDRTest",
                Map.of("Content-Type", "text/html; charset=Thomas"));
        assertTrue(body.contains("Test PASSED"), "Expected 'Test PASSED' in response but got:\n" + body);
    }

    @Test void viewHandlerCalculateCharEncodingNULLTest() { runServletTest("viewHandlerCalculateCharEncodingNULLTest"); }
    @Test void viewHandlerCalculateLocaleNPETest() { runServletTest("viewHandlerCalculateLocaleNPETest"); }
    @Test void viewHandlerCalculateLocaleTest() { runServletTest("viewHandlerCalculateLocaleTest"); }
    @Test void viewHandlerCalculateRenderKitIdNPETest() { runServletTest("viewHandlerCalculateRenderKitIdNPETest"); }
    @Test void viewHandlerCalculateRenderKitIdTest() { runServletTest("viewHandlerCalculateRenderKitIdTest"); }
    @Test void viewHandlerCreateViewNPETest() { runServletTest("viewHandlerCreateViewNPETest"); }
    @Test void viewHandlerCreateViewTest() { runServletTest("viewHandlerCreateViewTest"); }
    @Test void viewHandlerDeriveLogicalViewIDTest() { runServletTest("viewHandlerDeriveLogicalViewIDTest"); }
    @Test void viewHandlerGetActionURLNPETest() { runServletTest("viewHandlerGetActionURLNPETest"); }
    @Test void viewHandlerGetResourceURLNPETest() { runServletTest("viewHandlerGetResourceURLNPETest"); }
    @Test void viewHandlerRenderViewNPETest() { runServletTest("viewHandlerRenderViewNPETest"); }
    @Test void viewHandlerRestoreViewNPETest() { runServletTest("viewHandlerRestoreViewNPETest"); }
    @Test void viewHandlerWriteStateNPETest() { runServletTest("viewHandlerWriteStateNPETest"); }
}
