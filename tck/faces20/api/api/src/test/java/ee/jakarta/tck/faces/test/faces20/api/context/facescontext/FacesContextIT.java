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
package ee.jakarta.tck.faces.test.faces20.api.context.facescontext;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;

class FacesContextIT extends BaseITNG {

    private void runServletTest(String testName) {
        String body = getResponseBody("FacesContextTestServlet?testname=" + testName);
        assertTrue(body.contains("Test PASSED"), "Expected 'Test PASSED' in response but got:\n" + body);
    }

    @Test void facesCtxAddGetMessagesTest() { runServletTest("facesCtxAddGetMessagesTest"); }
    @Test void facesCtxAddMessageNPETest() { runServletTest("facesCtxAddMessageNPETest"); }
    @Test void facesCtxGetApplicationTest() { runServletTest("facesCtxGetApplicationTest"); }
    @Test void facesCtxGetAttributesEmptyTest() { runServletTest("facesCtxGetAttributesEmptyTest"); }
    @Test void facesCtxGetAttributesTest() { runServletTest("facesCtxGetAttributesTest"); }
    @Test void facesCtxGetClientIdsWithMessagesEmptyTest() { runServletTest("facesCtxGetClientIdsWithMessagesEmptyTest"); }
    @Test void facesCtxGetClientIdsWithMessagesTest() { runServletTest("facesCtxGetClientIdsWithMessagesTest"); }
    @Test void facesCtxGetELContextTest() { runServletTest("facesCtxGetELContextTest"); }
    @Test void facesCtxGetExternalContextTest() { runServletTest("facesCtxGetExternalContextTest"); }
    @Test void facesCtxGetMaximumSeverityTest() { runServletTest("facesCtxGetMaximumSeverityTest"); }
    @Test void facesCtxGetMessageListByIdTest() { runServletTest("facesCtxGetMessageListByIdTest"); }
    @Test void facesCtxGetMessageListTest() { runServletTest("facesCtxGetMessageListTest"); }
    @Test void facesCtxGetMessagesEmptyTest() { runServletTest("facesCtxGetMessagesEmptyTest"); }
    @Test void facesCtxGetPartialViewContextTest() { runServletTest("facesCtxGetPartialViewContextTest"); }
    @Test void facesCtxGetRenderKitTest() { runServletTest("facesCtxGetRenderKitTest"); }
    @Test void facesCtxISEAfterReleaseTest() { runServletTest("facesCtxISEAfterReleaseTest"); }
    @Test void facesCtxIsGetProcessingEventTest() { runServletTest("facesCtxIsGetProcessingEventTest"); }
    @Test void facesCtxIsProjectStageNPETest() { runServletTest("facesCtxIsProjectStageNPETest"); }
    @Test void facesCtxResponseCompleteTest() { runServletTest("facesCtxResponseCompleteTest"); }
    @Test void facesCtxSetGetCurrentInstanceTest() { runServletTest("facesCtxSetGetCurrentInstanceTest"); }
    @Test void facesCtxSetGetCurrentPhaseIdTest() { runServletTest("facesCtxSetGetCurrentPhaseIdTest"); }
    @Test void facesCtxSetGetExceptionHandlerTest() { runServletTest("facesCtxSetGetExceptionHandlerTest"); }
    @Test void facesCtxSetGetResponseStreamTest() { runServletTest("facesCtxSetGetResponseStreamTest"); }
    @Test void facesCtxSetGetResponseWriterTest() { runServletTest("facesCtxSetGetResponseWriterTest"); }
    @Test void facesCtxSetGetViewRootTest() { runServletTest("facesCtxSetGetViewRootTest"); }
    @Test void facesCtxSetResponseStreamNPETest() { runServletTest("facesCtxSetResponseStreamNPETest"); }
    @Test void facesCtxSetResponseWriterNPETest() { runServletTest("facesCtxSetResponseWriterNPETest"); }
    @Test void facesCtxSetViewRootNPETest() { runServletTest("facesCtxSetViewRootNPETest"); }
    @Test void facesCtxisPostbackTest() { runServletTest("facesCtxisPostbackTest"); }
    @Test void facesCtxisReleasedTest() { runServletTest("facesCtxisReleasedTest"); }
    @Test void facesCtxisValidationFailedTest() { runServletTest("facesCtxisValidationFailedTest"); }
    @Test void facexCtxRenderResponseTest() { runServletTest("facexCtxRenderResponseTest"); }
}
