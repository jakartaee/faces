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
package ee.jakarta.tck.faces.test.faces20.api.application.application;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;

// Several tests install inert TCK replacements (e.g. TCKResourceHandler) on the shared
// Application and do not restore the original; alphabetical ordering keeps read-only
// tests ahead of the mutating ones, matching how the old-tck JavaTest harness ran.
@TestMethodOrder(MethodOrderer.MethodName.class)
class ApplicationIT extends BaseITNG {

    private void runServletTest(String testName) {
        String body = getResponseBody("ApplicationTestServlet?testname=" + testName);
        assertTrue(body.contains("Test PASSED"), "Expected 'Test PASSED' in response but got:\n" + body);
    }

    @Test void applicationAddBehaviorNPETest() { runServletTest("applicationAddBehaviorNPETest"); }
    @Test void applicationAddBehaviorTest() { runServletTest("applicationAddBehaviorTest"); }
    @Test void applicationAddComponentNPETest() { runServletTest("applicationAddComponentNPETest"); }
    @Test void applicationAddComponentTest() { runServletTest("applicationAddComponentTest"); }
    @Test void applicationAddCreateConverterByClassNPETest() { runServletTest("applicationAddCreateConverterByClassNPETest"); }
    @Test void applicationAddCreateConverterByClassTest() { runServletTest("applicationAddCreateConverterByClassTest"); }
    @Test void applicationAddCreateConverterByStringNPETest() { runServletTest("applicationAddCreateConverterByStringNPETest"); }
    @Test void applicationAddCreateConverterByStringTest() { runServletTest("applicationAddCreateConverterByStringTest"); }
    @Test void applicationAddCreateValidatorTest() { runServletTest("applicationAddCreateValidatorTest"); }
    @Test void applicationAddDefaultValidatorIdTest() { runServletTest("applicationAddDefaultValidatorIdTest"); }
    @Test void applicationAddELResolverTest() { runServletTest("applicationAddELResolverTest"); }
    @Test void applicationAddGetRemoveELContextListenerTest() { runServletTest("applicationAddGetRemoveELContextListenerTest"); }
    @Test void applicationAddValidatorNPETest() { runServletTest("applicationAddValidatorNPETest"); }
    @Test void applicationCreateBehaviorFETest() { runServletTest("applicationCreateBehaviorFETest"); }
    @Test void applicationCreateBehaviorNPETest() { runServletTest("applicationCreateBehaviorNPETest"); }
    @Test void applicationCreateBehaviorTest() { runServletTest("applicationCreateBehaviorTest"); }
    @Test void applicationCreateComponentBindingTest() { runServletTest("applicationCreateComponentBindingTest"); }
    @Test void applicationCreateComponentExpressionFSSNPETest() { runServletTest("applicationCreateComponentExpressionFSSNPETest"); }
    @Test void applicationCreateComponentExpressionFSSNullTest() { runServletTest("applicationCreateComponentExpressionFSSNullTest"); }
    @Test void applicationCreateComponentExpressionFSSTest() { runServletTest("applicationCreateComponentExpressionFSSTest"); }
    @Test void applicationCreateComponentExpressionNPETest() { runServletTest("applicationCreateComponentExpressionNPETest"); }
    @Test void applicationCreateComponentExpressionTest() { runServletTest("applicationCreateComponentExpressionTest"); }
    @Test void applicationCreateComponentFETest() { runServletTest("applicationCreateComponentFETest"); }
    @Test void applicationCreateComponentFRNPETest() { runServletTest("applicationCreateComponentFRNPETest"); }
    @Test void applicationCreateComponentFSSNPETest() { runServletTest("applicationCreateComponentFSSNPETest"); }
    @Test void applicationCreateComponentFSSNullTest() { runServletTest("applicationCreateComponentFSSNullTest"); }
    @Test void applicationCreateComponentFSSTest() { runServletTest("applicationCreateComponentFSSTest"); }
    @Test void applicationCreateComponentNPETest() { runServletTest("applicationCreateComponentNPETest"); }
    @Test void applicationCreateComponentResNPETest() { runServletTest("applicationCreateComponentResNPETest"); }
    @Test void applicationCreateComponentTest() { runServletTest("applicationCreateComponentTest"); }
    @Test void applicationCreateConverterByClassNPETest() { runServletTest("applicationCreateConverterByClassNPETest"); }
    @Test void applicationCreateConverterByStringNPETest() { runServletTest("applicationCreateConverterByStringNPETest"); }
    @Test void applicationCreateConverterFETest() { runServletTest("applicationCreateConverterFETest"); }
    @Test void applicationCreateValidatorFETest() { runServletTest("applicationCreateValidatorFETest"); }
    @Test void applicationCreateValidatorNPETest() { runServletTest("applicationCreateValidatorNPETest"); }
    @Test void applicationCreateValueBindingTest() { runServletTest("applicationCreateValueBindingTest"); }
    @Test void applicationEvaluationExpressionGetTest() { runServletTest("applicationEvaluationExpressionGetTest"); }
    @Test void applicationGetActionListenerTest() { runServletTest("applicationGetActionListenerTest"); }
    @Test void applicationGetComponentTypesTest() { runServletTest("applicationGetComponentTypesTest"); }
    @Test void applicationGetConverterIdsTest() { runServletTest("applicationGetConverterIdsTest"); }
    @Test void applicationGetConverterTypesTest() { runServletTest("applicationGetConverterTypesTest"); }
    @Test void applicationGetDefaultValidatorInfoTest() { runServletTest("applicationGetDefaultValidatorInfoTest"); }
    @Test void applicationGetELContextListenersTest() { runServletTest("applicationGetELContextListenersTest"); }
    @Test void applicationGetELResolverTest() { runServletTest("applicationGetELResolverTest"); }
    @Test void applicationGetExpressionFactoryTest() { runServletTest("applicationGetExpressionFactoryTest"); }
    @Test void applicationGetProjectStageTest() { runServletTest("applicationGetProjectStageTest"); }
    @Test void applicationGetResourceBundleNPETest() { runServletTest("applicationGetResourceBundleNPETest"); }
    @Test void applicationGetResourceBundleTest() { runServletTest("applicationGetResourceBundleTest"); }
    @Test void applicationGetResourceHandlerTest() { runServletTest("applicationGetResourceHandlerTest"); }
    @Test void applicationGetSearchExpressionHandlerTest() { runServletTest("applicationGetSearchExpressionHandlerTest"); }
    @Test void applicationGetSetDefaultRenderKitIDTest() { runServletTest("applicationGetSetDefaultRenderKitIDTest"); }
    @Test void applicationGetSetMessageBundleTest() { runServletTest("applicationGetSetMessageBundleTest"); }
    @Test void applicationGetSetNavigationHandlerTest() { runServletTest("applicationGetSetNavigationHandlerTest"); }
    @Test void applicationGetSetStateManagerTest() { runServletTest("applicationGetSetStateManagerTest"); }
    @Test void applicationGetSetViewHandlerTest() { runServletTest("applicationGetSetViewHandlerTest"); }
    @Test void applicationGetValidatorIdsTest() { runServletTest("applicationGetValidatorIdsTest"); }
    @Test void applicationPublishEventNPETest1() { runServletTest("applicationPublishEventNPETest1"); }
    @Test void applicationPublishEventNPETest2() { runServletTest("applicationPublishEventNPETest2"); }
    @Test void applicationPublishEventTest1() { runServletTest("applicationPublishEventTest1"); }
    @Test void applicationPublishEventTest2() { runServletTest("applicationPublishEventTest2"); }
    @Test void applicationSetActionListenerNPETest() { runServletTest("applicationSetActionListenerNPETest"); }
    @Test void applicationSetActionListenerTest() { runServletTest("applicationSetActionListenerTest"); }
    @Test void applicationSetDefaultLocaleNPETest() { runServletTest("applicationSetDefaultLocaleNPETest"); }
    @Test void applicationSetGetDefaultLocaleTest() { runServletTest("applicationSetGetDefaultLocaleTest"); }
    @Test void applicationSetGetSupportedLocalesTest() { runServletTest("applicationSetGetSupportedLocalesTest"); }
    @Test void applicationSetMessageBundleNPETest() { runServletTest("applicationSetMessageBundleNPETest"); }
    @Test void applicationSetNavigationHandlerNPETest() { runServletTest("applicationSetNavigationHandlerNPETest"); }
    @Test void applicationSetResourceHandlerNPETest() { runServletTest("applicationSetResourceHandlerNPETest"); }
    @Test void applicationSetResourceHandlerTest() { runServletTest("applicationSetResourceHandlerTest"); }
    @Test void applicationSetSearchExpressionHandlerTest() { runServletTest("applicationSetSearchExpressionHandlerTest"); }
    @Test void applicationSetSupportedLocalesNPETest() { runServletTest("applicationSetSupportedLocalesNPETest"); }
    @Test void applicationSetViewHandlerNPETest() { runServletTest("applicationSetViewHandlerNPETest"); }
    @Test void applicationStateManagerNPETest() { runServletTest("applicationStateManagerNPETest"); }
    @Test void applicationSubscribeToEventNoSrcClassNPETest() { runServletTest("applicationSubscribeToEventNoSrcClassNPETest"); }
    @Test void applicationSubscribeToEventNoSrcClassTest() { runServletTest("applicationSubscribeToEventNoSrcClassTest"); }
    @Test void applicationSubscribeToEventNPETest() { runServletTest("applicationSubscribeToEventNPETest"); }
    @Test void applicationSubscribeToEventNullTest() { runServletTest("applicationSubscribeToEventNullTest"); }
    @Test void applicationSubscribeToEventTest() { runServletTest("applicationSubscribeToEventTest"); }
    @Test void applicationUnsubscribeFromEventNoSrcClassTest() { runServletTest("applicationUnsubscribeFromEventNoSrcClassTest"); }
    @Test void applicationUnsubscribeFromEventNPETest() { runServletTest("applicationUnsubscribeFromEventNPETest"); }
    @Test void applicationUnsubscribeFromEventSLTest() { runServletTest("applicationUnsubscribeFromEventSLTest"); }
    @Test void applicationUnsubscribeFromEventTest() { runServletTest("applicationUnsubscribeFromEventTest"); }
}
