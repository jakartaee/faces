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
package ee.jakarta.tck.faces.test.faces20.api.application.applicationwrapper;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;

// Alphabetical ordering matches the old-tck JavaTest harness run order and
// keeps read-only tests ahead of mutating ones (see ApplicationIT).
@TestMethodOrder(MethodOrderer.MethodName.class)
class ApplicationWrapperIT extends BaseITNG {

    private void runServletTest(String testName) {
        String body = getResponseBody("ApplicationWrapperTestServlet?testname=" + testName);
        assertTrue(body.contains("Test PASSED"), "Expected 'Test PASSED' in response but got:\n" + body);
    }

    @Test void applicationWrapperAddBehaviorTest() { runServletTest("applicationWrapperAddBehaviorTest"); }
    @Test void applicationWrapperAddComponentTest() { runServletTest("applicationWrapperAddComponentTest"); }
    @Test void applicationWrapperAddCreateConverterByClassTest() { runServletTest("applicationWrapperAddCreateConverterByClassTest"); }
    @Test void applicationWrapperAddCreateConverterByStringTest() { runServletTest("applicationWrapperAddCreateConverterByStringTest"); }
    @Test void applicationWrapperAddCreateValidatorTest() { runServletTest("applicationWrapperAddCreateValidatorTest"); }
    @Test void applicationWrapperAddDefaultValidatorIdTest() { runServletTest("applicationWrapperAddDefaultValidatorIdTest"); }
    @Test void applicationWrapperAddELResolverTest() { runServletTest("applicationWrapperAddELResolverTest"); }
    @Test void applicationWrapperAddGetRemoveELContextListenerTest() { runServletTest("applicationWrapperAddGetRemoveELContextListenerTest"); }
    @Test void applicationWrapperCreateBehaviorFETest() { runServletTest("applicationWrapperCreateBehaviorFETest"); }
    @Test void applicationWrapperCreateBehaviorTest() { runServletTest("applicationWrapperCreateBehaviorTest"); }
    @Test void applicationWrapperCreateComponentBindingTest() { runServletTest("applicationWrapperCreateComponentBindingTest"); }
    @Test void applicationWrapperCreateComponentExpressionFETest() { runServletTest("applicationWrapperCreateComponentExpressionFETest"); }
    @Test void applicationWrapperCreateComponentExpressionFSSNullTest() { runServletTest("applicationWrapperCreateComponentExpressionFSSNullTest"); }
    @Test void applicationWrapperCreateComponentExpressionFSSTest() { runServletTest("applicationWrapperCreateComponentExpressionFSSTest"); }
    @Test void applicationWrapperCreateComponentExpressionTest() { runServletTest("applicationWrapperCreateComponentExpressionTest"); }
    @Test void applicationWrapperCreateComponentFETest() { runServletTest("applicationWrapperCreateComponentFETest"); }
    @Test void applicationWrapperCreateComponentFSSNullTest() { runServletTest("applicationWrapperCreateComponentFSSNullTest"); }
    @Test void applicationWrapperCreateComponentFSSTest() { runServletTest("applicationWrapperCreateComponentFSSTest"); }
    @Test void applicationWrapperCreateComponentTest() { runServletTest("applicationWrapperCreateComponentTest"); }
    @Test void applicationWrapperCreateValidatorFETest() { runServletTest("applicationWrapperCreateValidatorFETest"); }
    @Test void applicationWrapperEvaluationExpressionGetTest() { runServletTest("applicationWrapperEvaluationExpressionGetTest"); }
    @Test void applicationWrapperGetActionListenerTest() { runServletTest("applicationWrapperGetActionListenerTest"); }
    @Test void applicationWrapperGetBehaviorIdsTest() { runServletTest("applicationWrapperGetBehaviorIdsTest"); }
    @Test void applicationWrapperGetComponentTypesTest() { runServletTest("applicationWrapperGetComponentTypesTest"); }
    @Test void applicationWrapperGetConverterIdsTest() { runServletTest("applicationWrapperGetConverterIdsTest"); }
    @Test void applicationWrapperGetConverterTypesTest() { runServletTest("applicationWrapperGetConverterTypesTest"); }
    @Test void applicationWrapperGetDefaultValidatorInfoTest() { runServletTest("applicationWrapperGetDefaultValidatorInfoTest"); }
    @Test void applicationWrapperGetELResolverTest() { runServletTest("applicationWrapperGetELResolverTest"); }
    @Test void applicationWrapperGetExpressionFactoryTest() { runServletTest("applicationWrapperGetExpressionFactoryTest"); }
    @Test void applicationWrapperGetProjectStageTest() { runServletTest("applicationWrapperGetProjectStageTest"); }
    @Test void applicationWrapperGetResourceBundleTest() { runServletTest("applicationWrapperGetResourceBundleTest"); }
    @Test void applicationWrapperGetResourceHandlerTest() { runServletTest("applicationWrapperGetResourceHandlerTest"); }
    @Test void applicationWrapperGetSetDefaultRenderKitIDTest() { runServletTest("applicationWrapperGetSetDefaultRenderKitIDTest"); }
    @Test void applicationWrapperGetSetMessageBundleTest() { runServletTest("applicationWrapperGetSetMessageBundleTest"); }
    @Test void applicationWrapperGetSetNavigationHandlerTest() { runServletTest("applicationWrapperGetSetNavigationHandlerTest"); }
    @Test void applicationWrapperGetSetStateManagerTest() { runServletTest("applicationWrapperGetSetStateManagerTest"); }
    @Test void applicationWrapperGetSetViewHandlerTest() { runServletTest("applicationWrapperGetSetViewHandlerTest"); }
    @Test void applicationWrapperGetValidatorIdsTest() { runServletTest("applicationWrapperGetValidatorIdsTest"); }
    @Test void applicationWrapperSetActionListenerTest() { runServletTest("applicationWrapperSetActionListenerTest"); }
    @Test void applicationWrapperSetGetDefaultLocaleTest() { runServletTest("applicationWrapperSetGetDefaultLocaleTest"); }
    @Test void applicationWrapperSetGetSupportedLocalesTest() { runServletTest("applicationWrapperSetGetSupportedLocalesTest"); }
    @Test void applicationWrapperSetResourceHandlerNPETest() { runServletTest("applicationWrapperSetResourceHandlerNPETest"); }
    @Test void applicationWrapperSetResourceHandlerTest() { runServletTest("applicationWrapperSetResourceHandlerTest"); }
    @Test void applicationWrapperSetStateManagerNPETest() { runServletTest("applicationWrapperSetStateManagerNPETest"); }
    @Test void applicationWrapperSetViewHandlerNPETest() { runServletTest("applicationWrapperSetViewHandlerNPETest"); }
    @Test void applicationWrapperSubscribeToEventNoSrcClassTest() { runServletTest("applicationWrapperSubscribeToEventNoSrcClassTest"); }
    @Test void applicationWrapperSubscribeToEventNullTest() { runServletTest("applicationWrapperSubscribeToEventNullTest"); }
    @Test void applicationWrapperSubscribeToEventTest() { runServletTest("applicationWrapperSubscribeToEventTest"); }
    @Test void applicationWrapperUnsubscribeFromEventNoSrcClassTest() { runServletTest("applicationWrapperUnsubscribeFromEventNoSrcClassTest"); }
    @Test void applicationWrapperUnsubscribeFromEventTest() { runServletTest("applicationWrapperUnsubscribeFromEventTest"); }
}
