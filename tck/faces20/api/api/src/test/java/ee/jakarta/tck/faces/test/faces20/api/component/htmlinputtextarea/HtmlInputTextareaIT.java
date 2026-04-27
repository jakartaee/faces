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
package ee.jakarta.tck.faces.test.faces20.api.component.htmlinputtextarea;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;

class HtmlInputTextareaIT extends BaseITNG {

    private void runServletTest(String testName) {
        String body = getResponseBody("HtmlInputTextareaTestServlet?testname=" + testName);
        assertTrue(body.contains("Test PASSED"), "Expected 'Test PASSED' in response but got:\n" + body);
    }

    @Test void componentAttributesTest() { runServletTest("componentAttributesTest"); }
    @Test void editableValueHolderAddGetRemoveValidatorTest() { runServletTest("editableValueHolderAddGetRemoveValidatorTest"); }
    @Test void editableValueHolderAddGetRemoveValueChangeListenerTest() { runServletTest("editableValueHolderAddGetRemoveValueChangeListenerTest"); }
    @Test void editableValueHolderAddValidatorNPETest() { runServletTest("editableValueHolderAddValidatorNPETest"); }
    @Test void editableValueHolderAddValueChangeListenerNPETest() { runServletTest("editableValueHolderAddValueChangeListenerNPETest"); }
    @Test void editableValueHolderGetSetSubmittedValueTest() { runServletTest("editableValueHolderGetSetSubmittedValueTest"); }
    @Test void editableValueHolderGetSetValueChangeListenerTest() { runServletTest("editableValueHolderGetSetValueChangeListenerTest"); }
    @Test void editableValueHolderIsSetImmediateTest() { runServletTest("editableValueHolderIsSetImmediateTest"); }
    @Test void editableValueHolderIsSetLocalValueSetTest() { runServletTest("editableValueHolderIsSetLocalValueSetTest"); }
    @Test void editableValueHolderIsSetRequiredTest() { runServletTest("editableValueHolderIsSetRequiredTest"); }
    @Test void editableValueHolderIsSetValidTest() { runServletTest("editableValueHolderIsSetValidTest"); }
    @Test void editableValueHolderRemoveValueChangeListenerNPETest() { runServletTest("editableValueHolderRemoveValueChangeListenerNPETest"); }
    @Test void partialStateHolderMICStateTest() { runServletTest("partialStateHolderMICStateTest"); }
    @Test void stateHolderIsSetTransientTest() { runServletTest("stateHolderIsSetTransientTest"); }
    @Test void stateHolderRestoreStateNPETest() { runServletTest("stateHolderRestoreStateNPETest"); }
    @Test void stateHolderSaveRestoreStateTest() { runServletTest("stateHolderSaveRestoreStateTest"); }
    @Test void stateHolderSaveStateNPETest() { runServletTest("stateHolderSaveStateNPETest"); }
    @Test void uiComponentEncodeAllNPETest() { runServletTest("uiComponentEncodeAllNPETest"); }
    @Test void uiComponentEncodeBeginELTest() { runServletTest("uiComponentEncodeBeginELTest"); }
    @Test void uiComponentEncodeBeginNotRenderedTest() { runServletTest("uiComponentEncodeBeginNotRenderedTest"); }
    @Test void uiComponentEncodeBeginNPETest() { runServletTest("uiComponentEncodeBeginNPETest"); }
    @Test void uiComponentEncodeBeginTest() { runServletTest("uiComponentEncodeBeginTest"); }
    @Test void uiComponentEncodeChildrenNotRenderedTest() { runServletTest("uiComponentEncodeChildrenNotRenderedTest"); }
    @Test void uiComponentEncodeChildrenNPETest() { runServletTest("uiComponentEncodeChildrenNPETest"); }
    @Test void uiComponentEncodeChildrenTest() { runServletTest("uiComponentEncodeChildrenTest"); }
    @Test void uiComponentEncodeEndELTest() { runServletTest("uiComponentEncodeEndELTest"); }
    @Test void uiComponentEncodeEndNotRenderedTest() { runServletTest("uiComponentEncodeEndNotRenderedTest"); }
    @Test void uiComponentEncodeEndNPETest() { runServletTest("uiComponentEncodeEndNPETest"); }
    @Test void uiComponentEncodeEndTest() { runServletTest("uiComponentEncodeEndTest"); }
    @Test void uiComponentFindComponentIAETest() { runServletTest("uiComponentFindComponentIAETest"); }
    @Test void uiComponentFindComponentNPETest() { runServletTest("uiComponentFindComponentNPETest"); }
    @Test void uiComponentFindComponentTest() { runServletTest("uiComponentFindComponentTest"); }
    @Test void uiComponentGetAttributesTest() { runServletTest("uiComponentGetAttributesTest"); }
    @Test void uiComponentGetChildCountTest() { runServletTest("uiComponentGetChildCountTest"); }
    @Test void uiComponentGetChildrenTest() { runServletTest("uiComponentGetChildrenTest"); }
    @Test void uiComponentGetClientIdContextTest() { runServletTest("uiComponentGetClientIdContextTest"); }
    @Test void uiComponentGetClientIdNPETest() { runServletTest("uiComponentGetClientIdNPETest"); }
    @Test void uiComponentGetClientIdTest() { runServletTest("uiComponentGetClientIdTest"); }
    @Test void uiComponentGetCurrentComponentNPETest() { runServletTest("uiComponentGetCurrentComponentNPETest"); }
    @Test void uiComponentGetCurrentComponentNullTest() { runServletTest("uiComponentGetCurrentComponentNullTest"); }
    @Test void uiComponentGetCurrentComponentTest() { runServletTest("uiComponentGetCurrentComponentTest"); }
    @Test void uiComponentGetCurrentCompositeComponentNPETest() { runServletTest("uiComponentGetCurrentCompositeComponentNPETest"); }
    @Test void uiComponentGetCurrentCompositeComponentNullTest() { runServletTest("uiComponentGetCurrentCompositeComponentNullTest"); }
    @Test void uiComponentGetFacetCountTest() { runServletTest("uiComponentGetFacetCountTest"); }
    @Test void uiComponentGetFacetsAndChildrenTest() { runServletTest("uiComponentGetFacetsAndChildrenTest"); }
    @Test void uiComponentGetFacetsTest() { runServletTest("uiComponentGetFacetsTest"); }
    @Test void uiComponentGetFacetTest() { runServletTest("uiComponentGetFacetTest"); }
    @Test void uiComponentGetRendersChildrenTest() { runServletTest("uiComponentGetRendersChildrenTest"); }
    @Test void uiComponentGetSetIdTest() { runServletTest("uiComponentGetSetIdTest"); }
    @Test void uiComponentGetSetParentTest() { runServletTest("uiComponentGetSetParentTest"); }
    @Test void uiComponentGetSetRendererTypeTest() { runServletTest("uiComponentGetSetRendererTypeTest"); }
    @Test void uiComponentGetSetValueExpressionNPETest() { runServletTest("uiComponentGetSetValueExpressionNPETest"); }
    @Test void uiComponentGetSetValueExpressionTest() { runServletTest("uiComponentGetSetValueExpressionTest"); }
    @Test void uiComponentIsCompositeComponentNegTest() { runServletTest("uiComponentIsCompositeComponentNegTest"); }
    @Test void uiComponentIsCompositeComponentNPETest() { runServletTest("uiComponentIsCompositeComponentNPETest"); }
    @Test void uiComponentIsInViewNegTest() { runServletTest("uiComponentIsInViewNegTest"); }
    @Test void uiComponentIsInViewTest() { runServletTest("uiComponentIsInViewTest"); }
    @Test void uiComponentIsSetRenderedTest() { runServletTest("uiComponentIsSetRenderedTest"); }
    @Test void uiComponentPopComponentFromELNPETest() { runServletTest("uiComponentPopComponentFromELNPETest"); }
    @Test void uiComponentPopComponentFromELTest() { runServletTest("uiComponentPopComponentFromELTest"); }
    @Test void uiComponentProcessDecodesNotRenderedTest() { runServletTest("uiComponentProcessDecodesNotRenderedTest"); }
    @Test void uiComponentProcessDecodesNPETest() { runServletTest("uiComponentProcessDecodesNPETest"); }
    @Test void uiComponentProcessDecodesRenderResponseTest() { runServletTest("uiComponentProcessDecodesRenderResponseTest"); }
    @Test void uiComponentProcessDecodesTest() { runServletTest("uiComponentProcessDecodesTest"); }
    @Test void uiComponentProcessRestoreStateNPETest() { runServletTest("uiComponentProcessRestoreStateNPETest"); }
    @Test void uiComponentProcessSaveRestoreStateTest() { runServletTest("uiComponentProcessSaveRestoreStateTest"); }
    @Test void uiComponentProcessSaveStateNPETest() { runServletTest("uiComponentProcessSaveStateNPETest"); }
    @Test void uiComponentProcessSaveStateTransientTest() { runServletTest("uiComponentProcessSaveStateTransientTest"); }
    @Test void uiComponentProcessUpdatesNotRenderedTest() { runServletTest("uiComponentProcessUpdatesNotRenderedTest"); }
    @Test void uiComponentProcessUpdatesNPETest() { runServletTest("uiComponentProcessUpdatesNPETest"); }
    @Test void uiComponentProcessUpdatesRenderResponseTest() { runServletTest("uiComponentProcessUpdatesRenderResponseTest"); }
    @Test void uiComponentProcessUpdatesTest() { runServletTest("uiComponentProcessUpdatesTest"); }
    @Test void uiComponentProcessValidatorsIsValidRenderResponseTest() { runServletTest("uiComponentProcessValidatorsIsValidRenderResponseTest"); }
    @Test void uiComponentProcessValidatorsNotRenderedTest() { runServletTest("uiComponentProcessValidatorsNotRenderedTest"); }
    @Test void uiComponentProcessValidatorsNPETest() { runServletTest("uiComponentProcessValidatorsNPETest"); }
    @Test void uiComponentProcessValidatorsRenderResponseTest() { runServletTest("uiComponentProcessValidatorsRenderResponseTest"); }
    @Test void uiComponentProcessValidatorsTest() { runServletTest("uiComponentProcessValidatorsTest"); }
    @Test void uiComponentPushComponentToELNPETest() { runServletTest("uiComponentPushComponentToELNPETest"); }
    @Test void uiComponentPushComponentToELTest() { runServletTest("uiComponentPushComponentToELTest"); }
    @Test void uiComponentQueueEventNPETest() { runServletTest("uiComponentQueueEventNPETest"); }
    @Test void uiComponentRestoreAttachedStateNPETest() { runServletTest("uiComponentRestoreAttachedStateNPETest"); }
    @Test void uiComponentSaveAttachedStateNPETest() { runServletTest("uiComponentSaveAttachedStateNPETest"); }
    @Test void uiComponentSetIdRestrictionsTest() { runServletTest("uiComponentSetIdRestrictionsTest"); }
    @Test void uiComponentSetValueExpressionIAETest() { runServletTest("uiComponentSetValueExpressionIAETest"); }
    @Test void uiComponentSetValueExpressionNPETest() { runServletTest("uiComponentSetValueExpressionNPETest"); }
    @Test void uiComponentSubscribeToEventNPETest() { runServletTest("uiComponentSubscribeToEventNPETest"); }
    @Test void uiComponentSubscribeToEventTest() { runServletTest("uiComponentSubscribeToEventTest"); }
    @Test void uiComponentVisitTreeNegTest() { runServletTest("uiComponentVisitTreeNegTest"); }
    @Test void uiComponentVisitTreeTest() { runServletTest("uiComponentVisitTreeTest"); }
    @Test void uiInputBroadcastTest() { runServletTest("uiInputBroadcastTest"); }
    @Test void uiInputBroadcastValueChangeListenerTest() { runServletTest("uiInputBroadcastValueChangeListenerTest"); }
    @Test void uiInputResetValueTest() { runServletTest("uiInputResetValueTest"); }
    @Test void uiInputUpdateModelFailsTest() { runServletTest("uiInputUpdateModelFailsTest"); }
    @Test void uiInputUpdateModelNoActionTest() { runServletTest("uiInputUpdateModelNoActionTest"); }
    @Test void uiInputUpdateModelSucceedsTest() { runServletTest("uiInputUpdateModelSucceedsTest"); }
    @Test void uiInputValidate1Test() { runServletTest("uiInputValidate1Test"); }
    @Test void uiInputValidate2Test() { runServletTest("uiInputValidate2Test"); }
    @Test void uiInputValidate3aTest() { runServletTest("uiInputValidate3aTest"); }
    @Test void uiInputValidate3bTest() { runServletTest("uiInputValidate3bTest"); }
    @Test void uiInputValidate3cTest() { runServletTest("uiInputValidate3cTest"); }
    @Test void uiInputValidate4Test() { runServletTest("uiInputValidate4Test"); }
    @Test void uiInputValidate5Test() { runServletTest("uiInputValidate5Test"); }
    @Test void valueHolderGetSetConverterTest() { runServletTest("valueHolderGetSetConverterTest"); }
    @Test void valueHolderGetSetValueTest() { runServletTest("valueHolderGetSetValueTest"); }
}
