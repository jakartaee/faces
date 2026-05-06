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
package ee.jakarta.tck.faces.test.faces20.api.component.uicommand;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;

class UICommandIT extends BaseITNG {

    private void runServletTest(String testName) {
        String body = getResponseBody("UICommandTestServlet?testname=" + testName);
        assertTrue(body.contains("Test PASSED"), "Expected 'Test PASSED' in response but got:\n" + body);
    }

    @Test void uiComponentEncodeBeginNotRenderedTest() { runServletTest("uiComponentEncodeBeginNotRenderedTest"); }
    @Test void uiComponentEncodeBeginNPETest() { runServletTest("uiComponentEncodeBeginNPETest"); }
    @Test void uiComponentEncodeBeginELTest() { runServletTest("uiComponentEncodeBeginELTest"); }
    @Test void uiComponentEncodeBeginTest() { runServletTest("uiComponentEncodeBeginTest"); }
    @Test void uiComponentEncodeChildrenNotRenderedTest() { runServletTest("uiComponentEncodeChildrenNotRenderedTest"); }
    @Test void uiComponentEncodeChildrenNPETest() { runServletTest("uiComponentEncodeChildrenNPETest"); }
    @Test void uiComponentEncodeChildrenTest() { runServletTest("uiComponentEncodeChildrenTest"); }
    @Test void uiComponentEncodeEndNotRenderedTest() { runServletTest("uiComponentEncodeEndNotRenderedTest"); }
    @Test void uiComponentEncodeEndNPETest() { runServletTest("uiComponentEncodeEndNPETest"); }
    @Test void uiComponentEncodeEndTest() { runServletTest("uiComponentEncodeEndTest"); }
    @Test void uiComponentEncodeAllNPETest() { runServletTest("uiComponentEncodeAllNPETest"); }
    @Test void uiCommandBroadcastNPETest() { runServletTest("uiCommandBroadcastNPETest"); }
    @Test void uiComponentEncodeEndELTest() { runServletTest("uiComponentEncodeEndELTest"); }
    @Test void uiComponentFindComponentIAETest() { runServletTest("uiComponentFindComponentIAETest"); }
    @Test void uiComponentFindComponentNPETest() { runServletTest("uiComponentFindComponentNPETest"); }
    @Test void uiComponentFindComponentTest() { runServletTest("uiComponentFindComponentTest"); }
    @Test void uiComponentGetAttributesTest() { runServletTest("uiComponentGetAttributesTest"); }
    @Test void uiComponentGetChildCountTest() { runServletTest("uiComponentGetChildCountTest"); }
    @Test void uiComponentGetChildrenTest() { runServletTest("uiComponentGetChildrenTest"); }
    @Test void uiComponentGetClientIdNPETest() { runServletTest("uiComponentGetClientIdNPETest"); }
    @Test void uiComponentGetClientIdTest() { runServletTest("uiComponentGetClientIdTest"); }
    @Test void uiComponentGetClientIdContextTest() { runServletTest("uiComponentGetClientIdContextTest"); }
    @Test void uiComponentGetCurrentComponentTest() { runServletTest("uiComponentGetCurrentComponentTest"); }
    @Test void uiComponentGetCurrentComponentNPETest() { runServletTest("uiComponentGetCurrentComponentNPETest"); }
    @Test void uiComponentGetCurrentComponentNullTest() { runServletTest("uiComponentGetCurrentComponentNullTest"); }
    @Test void uiComponentGetCurrentCompositeComponentNPETest() { runServletTest("uiComponentGetCurrentCompositeComponentNPETest"); }
    @Test void uiComponentGetCurrentCompositeComponentNullTest() { runServletTest("uiComponentGetCurrentCompositeComponentNullTest"); }
    @Test void uiComponentIsCompositeComponentNPETest() { runServletTest("uiComponentIsCompositeComponentNPETest"); }
    @Test void uiComponentIsCompositeComponentNegTest() { runServletTest("uiComponentIsCompositeComponentNegTest"); }
    @Test void uiComponentGetFacetsAndChildrenTest() { runServletTest("uiComponentGetFacetsAndChildrenTest"); }
    @Test void uiComponentGetFacetsTest() { runServletTest("uiComponentGetFacetsTest"); }
    @Test void uiComponentGetFacetTest() { runServletTest("uiComponentGetFacetTest"); }
    @Test void uiComponentGetFacetCountTest() { runServletTest("uiComponentGetFacetCountTest"); }
    @Test void uiComponentGetFamilyTest() { runServletTest("uiComponentGetFamilyTest"); }
    @Test void uiComponentGetRendersChildrenTest() { runServletTest("uiComponentGetRendersChildrenTest"); }
    @Test void uiComponentGetSetIdTest() { runServletTest("uiComponentGetSetIdTest"); }
    @Test void uiComponentGetSetParentTest() { runServletTest("uiComponentGetSetParentTest"); }
    @Test void uiComponentQueueEventNPETest() { runServletTest("uiComponentQueueEventNPETest"); }
    @Test void uiComponentRestoreAttachedStateNPETest() { runServletTest("uiComponentRestoreAttachedStateNPETest"); }
    @Test void uiComponentSaveAttachedStateNPETest() { runServletTest("uiComponentSaveAttachedStateNPETest"); }
    @Test void uiComponentGetSetRendererTypeTest() { runServletTest("uiComponentGetSetRendererTypeTest"); }
    @Test void uiComponentInvokeOnComponentTest() { runServletTest("uiComponentInvokeOnComponentTest"); }
    @Test void uiComponentInvokeOnComponentNegativeTest() { runServletTest("uiComponentInvokeOnComponentNegativeTest"); }
    @Test void uiComponentIsSetRenderedTest() { runServletTest("uiComponentIsSetRenderedTest"); }
    @Test void uiComponentIsInViewTest() { runServletTest("uiComponentIsInViewTest"); }
    @Test void uiComponentIsInViewNegTest() { runServletTest("uiComponentIsInViewNegTest"); }
    @Test void uiComponentProcessDecodesNotRenderedTest() { runServletTest("uiComponentProcessDecodesNotRenderedTest"); }
    @Test void uiComponentProcessDecodesRenderResponseTest() { runServletTest("uiComponentProcessDecodesRenderResponseTest"); }
    @Test void uiComponentProcessDecodesTest() { runServletTest("uiComponentProcessDecodesTest"); }
    @Test void uiComponentProcessDecodesNPETest() { runServletTest("uiComponentProcessDecodesNPETest"); }
    @Test void uiComponentProcessSaveRestoreStateTest() { runServletTest("uiComponentProcessSaveRestoreStateTest"); }
    @Test void uiComponentProcessSaveStateNPETest() { runServletTest("uiComponentProcessSaveStateNPETest"); }
    @Test void uiComponentProcessRestoreStateNPETest() { runServletTest("uiComponentProcessRestoreStateNPETest"); }
    @Test void uiComponentProcessSaveStateTransientTest() { runServletTest("uiComponentProcessSaveStateTransientTest"); }
    @Test void uiComponentProcessUpdatesNotRenderedTest() { runServletTest("uiComponentProcessUpdatesNotRenderedTest"); }
    @Test void uiComponentProcessUpdatesRenderResponseTest() { runServletTest("uiComponentProcessUpdatesRenderResponseTest"); }
    @Test void uiComponentProcessUpdatesTest() { runServletTest("uiComponentProcessUpdatesTest"); }
    @Test void uiComponentProcessUpdatesNPETest() { runServletTest("uiComponentProcessUpdatesNPETest"); }
    @Test void uiComponentProcessValidatorsIsValidRenderResponseTest() { runServletTest("uiComponentProcessValidatorsIsValidRenderResponseTest"); }
    @Test void uiComponentProcessValidatorsNotRenderedTest() { runServletTest("uiComponentProcessValidatorsNotRenderedTest"); }
    @Test void uiComponentProcessValidatorsRenderResponseTest() { runServletTest("uiComponentProcessValidatorsRenderResponseTest"); }
    @Test void uiComponentProcessValidatorsTest() { runServletTest("uiComponentProcessValidatorsTest"); }
    @Test void uiComponentProcessValidatorsNPETest() { runServletTest("uiComponentProcessValidatorsNPETest"); }
    @Test void uiComponentPushComponentToELTest() { runServletTest("uiComponentPushComponentToELTest"); }
    @Test void uiComponentPushComponentToELNPETest() { runServletTest("uiComponentPushComponentToELNPETest"); }
    @Test void uiComponentPopComponentFromELTest() { runServletTest("uiComponentPopComponentFromELTest"); }
    @Test void uiComponentPopComponentFromELNPETest() { runServletTest("uiComponentPopComponentFromELNPETest"); }
    @Test void uiComponentSetIdRestrictionsTest() { runServletTest("uiComponentSetIdRestrictionsTest"); }
    @Test void uiComponentGetSetValueExpressionTest() { runServletTest("uiComponentGetSetValueExpressionTest"); }
    @Test void uiComponentGetSetValueExpressionNPETest() { runServletTest("uiComponentGetSetValueExpressionNPETest"); }
    @Test void uiComponentSetValueExpressionNPETest() { runServletTest("uiComponentSetValueExpressionNPETest"); }
    @Test void uiComponentSetValueExpressionIAETest() { runServletTest("uiComponentSetValueExpressionIAETest"); }
    @Test void uiComponentSubscribeToEventTest() { runServletTest("uiComponentSubscribeToEventTest"); }
    @Test void uiComponentSubscribeToEventNPETest() { runServletTest("uiComponentSubscribeToEventNPETest"); }
    @Test void uiComponentVisitTreeTest() { runServletTest("uiComponentVisitTreeTest"); }
    @Test void uiComponentVisitTreeNegTest() { runServletTest("uiComponentVisitTreeNegTest"); }
    @Test void stateHolderIsSetTransientTest() { runServletTest("stateHolderIsSetTransientTest"); }
    @Test void stateHolderSaveRestoreStateTest() { runServletTest("stateHolderSaveRestoreStateTest"); }
    @Test void stateHolderRestoreStateNPETest() { runServletTest("stateHolderRestoreStateNPETest"); }
    @Test void stateHolderSaveStateNPETest() { runServletTest("stateHolderSaveStateNPETest"); }
    @Test void partialStateHolderMICStateTest() { runServletTest("partialStateHolderMICStateTest"); }
    @Test void actionSourceSetIsImmediateTest() { runServletTest("actionSourceSetIsImmediateTest"); }
    @Test void actionSourceAddActListNPETest() { runServletTest("actionSourceAddActListNPETest"); }
    @Test void actionSourceRemoveActListNPETest() { runServletTest("actionSourceRemoveActListNPETest"); }
    @Test void actionSourceAddGetRemoveActListTest() { runServletTest("actionSourceAddGetRemoveActListTest"); }
    @Test void actionSourceGetSetActionTest() { runServletTest("actionSourceGetSetActionTest"); }
    @Test void actionSourceGetSetActListTest() { runServletTest("actionSourceGetSetActListTest"); }
    @Test void actionSourceGetSetActionExpressionTest() { runServletTest("actionSourceGetSetActionExpressionTest"); }
    @Test void uiCommandSetGetValueTest() { runServletTest("uiCommandSetGetValueTest"); }
    @Test void uiCommandBroadcastTest() { runServletTest("uiCommandBroadcastTest"); }
    @Test void uiCommandBroadcastImmediateTest() { runServletTest("uiCommandBroadcastImmediateTest"); }
    @Test void uiCommandBroadcastInvokeActionListenerTest() { runServletTest("uiCommandBroadcastInvokeActionListenerTest"); }
}
