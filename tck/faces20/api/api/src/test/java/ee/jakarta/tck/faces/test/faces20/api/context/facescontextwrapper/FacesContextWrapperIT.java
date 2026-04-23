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
package ee.jakarta.tck.faces.test.faces20.api.context.facescontextwrapper;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;

class FacesContextWrapperIT extends BaseITNG {

    private void runServletTest(String testName) {
        String body = getResponseBody("FacesContextWrapperTestServlet?testname=" + testName);
        assertTrue(body.contains("Test PASSED"), "Expected 'Test PASSED' in response but got:\n" + body);
    }

    @Test void facesCtxWrapperAddGetMessagesTest() { runServletTest("facesCtxWrapperAddGetMessagesTest"); }
    @Test void facesCtxWrapperGetApplicationTest() { runServletTest("facesCtxWrapperGetApplicationTest"); }
    @Test void facesCtxWrapperGetAttributesEmptyTest() { runServletTest("facesCtxWrapperGetAttributesEmptyTest"); }
    @Test void facesCtxWrapperGetAttributesTest() { runServletTest("facesCtxWrapperGetAttributesTest"); }
    @Test void facesCtxWrapperGetClientIdsWithMessagesEmptyTest() { runServletTest("facesCtxWrapperGetClientIdsWithMessagesEmptyTest"); }
    @Test void facesCtxWrapperGetClientIdsWithMessagesTest() { runServletTest("facesCtxWrapperGetClientIdsWithMessagesTest"); }
    @Test void facesCtxWrapperGetELContextTest() { runServletTest("facesCtxWrapperGetELContextTest"); }
    @Test void facesCtxWrapperGetExceptionHandlerTest() { runServletTest("facesCtxWrapperGetExceptionHandlerTest"); }
    @Test void facesCtxWrapperGetExternalContextTest() { runServletTest("facesCtxWrapperGetExternalContextTest"); }
    @Test void facesCtxWrapperGetMaximumSeverityTest() { runServletTest("facesCtxWrapperGetMaximumSeverityTest"); }
    @Test void facesCtxWrapperGetMessageListByIdTest() { runServletTest("facesCtxWrapperGetMessageListByIdTest"); }
    @Test void facesCtxWrapperGetMessageListTest() { runServletTest("facesCtxWrapperGetMessageListTest"); }
    @Test void facesCtxWrapperGetMessagesEmptyTest() { runServletTest("facesCtxWrapperGetMessagesEmptyTest"); }
    @Test void facesCtxWrapperGetPartialViewContextTest() { runServletTest("facesCtxWrapperGetPartialViewContextTest"); }
    @Test void facesCtxWrapperGetRenderKitTest() { runServletTest("facesCtxWrapperGetRenderKitTest"); }
    @Test void facesCtxWrapperGetResponseCompleteTest() { runServletTest("facesCtxWrapperGetResponseCompleteTest"); }
    @Test void facesCtxWrapperGetViewRootTest() { runServletTest("facesCtxWrapperGetViewRootTest"); }
    @Test void facesCtxWrapperIsGetProcessingEventTest() { runServletTest("facesCtxWrapperIsGetProcessingEventTest"); }
    @Test void facesCtxWrapperIsPostbackTest() { runServletTest("facesCtxWrapperIsPostbackTest"); }
    @Test void facesCtxWrapperIsReleasedTest() { runServletTest("facesCtxWrapperIsReleasedTest"); }
    @Test void facesCtxWrapperIsValidationFailedTest() { runServletTest("facesCtxWrapperIsValidationFailedTest"); }
    @Test void facesCtxWrapperRenderResponseTest() { runServletTest("facesCtxWrapperRenderResponseTest"); }
    @Test void facesCtxWrapperSetExceptionHandlerTest() { runServletTest("facesCtxWrapperSetExceptionHandlerTest"); }
    @Test void facesCtxWrapperSetGetCurrentPhaseIdTest() { runServletTest("facesCtxWrapperSetGetCurrentPhaseIdTest"); }
    @Test void facesCtxWrapperSetGetResponseStreamTest() { runServletTest("facesCtxWrapperSetGetResponseStreamTest"); }
    @Test void facesCtxWrapperSetGetResponseWriterTest() { runServletTest("facesCtxWrapperSetGetResponseWriterTest"); }
}
