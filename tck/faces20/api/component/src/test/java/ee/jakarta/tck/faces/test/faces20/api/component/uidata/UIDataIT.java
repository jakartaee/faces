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
package ee.jakarta.tck.faces.test.faces20.api.component.uidata;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;

class UIDataIT extends BaseITNG {

    private void runServletTest(String testName) {
        String body = getResponseBody("UIDataTestServlet?testname=" + testName);
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
    @Test void uiComponentEncodeEndELTest() { runServletTest("uiComponentEncodeEndELTest"); }
    @Test void uiComponentFindComponentIAETest() { runServletTest("uiComponentFindComponentIAETest"); }
    @Test void uiComponentFindComponentNPETest() { runServletTest("uiComponentFindComponentNPETest"); }
    @Test void uiComponentFindComponentTest() { runServletTest("uiComponentFindComponentTest"); }
    @Test void uiComponentGetAttributesTest() { runServletTest("uiComponentGetAttributesTest"); }
    @Test void uiComponentGetContainerClientIdNPETest() { runServletTest("uiComponentGetContainerClientIdNPETest"); }
    @Test void uiComponentGetContainerClientIdTest() { runServletTest("uiComponentGetContainerClientIdTest"); }
    @Test void uiComponentGetChildCountTest() { runServletTest("uiComponentGetChildCountTest"); }
    @Test void uiComponentGetChildrenTest() { runServletTest("uiComponentGetChildrenTest"); }
    @Test void uiComponentGetClientIdNPETest() { runServletTest("uiComponentGetClientIdNPETest"); }
    @Test void uiComponentGetClientIdTest() { runServletTest("uiComponentGetClientIdTest"); }
    @Test void uiComponentGetClientIdContextTest() { runServletTest("uiComponentGetClientIdContextTest"); }
    @Test void uiComponentGetCurrentComponentTest() { runServletTest("uiComponentGetCurrentComponentTest"); }
    @Test void uiComponentGetCurrentComponentNPETest() { runServletTest("uiComponentGetCurrentComponentNPETest"); }
    @Test void uiComponentGetCurrentComponentNullTest() { runServletTest("uiComponentGetCurrentComponentNullTest"); }
    @Test void uiComponentIsCompositeComponentNPETest() { runServletTest("uiComponentIsCompositeComponentNPETest"); }
    @Test void uiComponentIsCompositeComponentNegTest() { runServletTest("uiComponentIsCompositeComponentNegTest"); }
    @Test void uiComponentGetCurrentCompositeComponentNPETest() { runServletTest("uiComponentGetCurrentCompositeComponentNPETest"); }
    @Test void uiComponentGetCurrentCompositeComponentNullTest() { runServletTest("uiComponentGetCurrentCompositeComponentNullTest"); }
    @Test void uiComponentGetFacetsAndChildrenTest() { runServletTest("uiComponentGetFacetsAndChildrenTest"); }
    @Test void uiComponentGetFacetsTest() { runServletTest("uiComponentGetFacetsTest"); }
    @Test void uiComponentGetFacetTest() { runServletTest("uiComponentGetFacetTest"); }
    @Test void uiComponentGetFacetCountTest() { runServletTest("uiComponentGetFacetCountTest"); }
    @Test void uiComponentGetFamilyTest() { runServletTest("uiComponentGetFamilyTest"); }
    @Test void uiComponentGetRendersChildrenTest() { runServletTest("uiComponentGetRendersChildrenTest"); }
    @Test void uiComponentGetSetIdTest() { runServletTest("uiComponentGetSetIdTest"); }
    @Test void uiComponentGetSetParentTest() { runServletTest("uiComponentGetSetParentTest"); }
    @Test void uiComponentGetSetRendererTypeTest() { runServletTest("uiComponentGetSetRendererTypeTest"); }
    @Test void uiComponentInvokeOnComponentTest() { runServletTest("uiDataPositiveInvokeOnComponentTest"); }
    @Test void uiComponentInvokeOnComponentNPETest() { runServletTest("uiDataNPEInvokeOnComponentTest"); }
    @Test void uiComponentInvokeOnComponentFETest() { runServletTest("uiDataFEInvokeOnComponentTest"); }
    @Test void uiComponentIsSetRenderedTest() { runServletTest("uiComponentIsSetRenderedTest"); }
    @Test void uiComponentIsInViewTest() { runServletTest("uiComponentIsInViewTest"); }
    @Test void uiComponentIsInViewNegTest() { runServletTest("uiComponentIsInViewNegTest"); }
    @Test void uiComponentProcessSaveRestoreStateTest() { runServletTest("uiComponentProcessSaveRestoreStateTest"); }
    @Test void uiComponentProcessSaveStateNPETest() { runServletTest("uiComponentProcessSaveStateNPETest"); }
    @Test void uiComponentProcessRestoreStateNPETest() { runServletTest("uiComponentProcessRestoreStateNPETest"); }
    @Test void uiComponentProcessSaveStateTransientTest() { runServletTest("uiComponentProcessSaveStateTransientTest"); }
    @Test void uiComponentProcessUpdatesNPETest() { runServletTest("uiComponentProcessUpdatesNPETest"); }
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
    @Test void uiDataGetSetFirstTest() { runServletTest("uiDataGetSetFirstTest"); }
    @Test void uiDataGetSetFooterTest() { runServletTest("uiDataGetSetFooterTest"); }
    @Test void uiDataGetSetHeaderTest() { runServletTest("uiDataGetSetHeaderTest"); }
    @Test void uiDataSetFooterNPETest() { runServletTest("uiDataSetFooterNPETest"); }
    @Test void uiDataSetHeaderNPETest() { runServletTest("uiDataSetHeaderNPETest"); }
    @Test void uiDataSetFirstIAETest() { runServletTest("uiDataSetFirstIAETest"); }
    @Test void uiDataIsRowAvailableTest() { runServletTest("uiDataIsRowAvailableTest"); }
    @Test void uiDataBroadcastNPETest() { runServletTest("uiDataBroadcastNPETest"); }
    @Test void uiDataGetRowCountTest() { runServletTest("uiDataGetRowCountTest"); }
    @Test void uiDataGetRowDataTest() { runServletTest("uiDataGetRowDataTest"); }
    @Test void uiDataGetRowDataIAETest() { runServletTest("uiDataGetRowDataIAETest"); }
    @Test void uiDataGetSetRowIndexTest() { runServletTest("uiDataGetSetRowIndexTest"); }
    @Test void uiDataSetRowIndexIAETest() { runServletTest("uiDataSetRowIndexIAETest"); }
    @Test void uiDataGetSetRowsTest() { runServletTest("uiDataGetSetRowsTest"); }
    @Test void uiDataSetRowsIAETest() { runServletTest("uiDataSetRowsIAETest"); }
    @Test void uiDataGetSetVarTest() { runServletTest("uiDataGetSetVarTest"); }
    @Test void uiDataQueueEventNPETest() { runServletTest("uiDataQueueEventNPETest"); }
    @Test void uiDataQueueEventISETest() { runServletTest("uiDataQueueEventISETest"); }
    @Test void uiDataGetSetValueTest() { runServletTest("uiDataGetSetValueTest"); }
    @Test void uiDataProcessDecodesNPETest() { runServletTest("uiDataProcessDecodesNPETest"); }
    @Test void uiDataSetValueExpressionNPETest() { runServletTest("uiDataSetValueExpressionNPETest"); }
    @Test void uiDatatVisitTreeNPETest() { runServletTest("uiDatatVisitTreeNPETest"); }
    @Test void uiDataCreateUniqueIdTest() { runServletTest("uiDataCreateUniqueIdTest"); }
    @Test void uiDataIsSetRowStatePreservedTest() { runServletTest("uiDataIsSetRowStatePreservedTest"); }
}
