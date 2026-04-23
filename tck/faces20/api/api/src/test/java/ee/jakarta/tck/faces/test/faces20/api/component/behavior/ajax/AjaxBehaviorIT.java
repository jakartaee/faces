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
package ee.jakarta.tck.faces.test.faces20.api.component.behavior.ajax;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;

class AjaxBehaviorIT extends BaseITNG {

    private void runServletTest(String testName) {
        String body = getResponseBody("AjaxBehaviorTestServlet?testname=" + testName);
        assertTrue(body.contains("Test PASSED"), "Expected 'Test PASSED' in response but got:\n" + body);
    }

    @Test
    void behaviorBroadcastNPETest() {
        runServletTest("behaviorBroadcastNPETest");
    }

    @Test
    void behaviorMICInitialStateTest() {
        runServletTest("behaviorMICInitialStateTest");
    }

    @Test
    void behaviorSITransientTest() {
        runServletTest("behaviorSITransientTest");
    }

    @Test
    void clientBehaviorDecodeNPETest() {
        runServletTest("clientBehaviorDecodeNPETest");
    }

    @Test
    void clientBehaviorGetScriptNPETest() {
        runServletTest("clientBehaviorGetScriptNPETest");
    }

    @Test
    void ajaxBehaviorBroadcastTest() {
        runServletTest("ajaxBehaviorBroadcastTest");
    }

    @Test
    void ajaxBehaviorAddListenerNPETest() {
        runServletTest("ajaxBehaviorAddListenerNPETest");
    }

    @Test
    void ajaxBehaviorGetSetDelayTest() {
        runServletTest("ajaxBehaviorGetSetDelayTest");
    }

    @Test
    void ajaxBehaviorGetSetExecuteTest() {
        runServletTest("ajaxBehaviorGetSetExecuteTest");
    }

    @Test
    void ajaxBehaviorSetIsDisabledTest() {
        runServletTest("ajaxBehaviorSetIsDisabledTest");
    }

    @Test
    void ajaxBehaviorSetIsImmediateTest() {
        runServletTest("ajaxBehaviorSetIsImmediateTest");
    }

    @Test
    void ajaxBehaviorGetSetValueExpressionNPETest() {
        runServletTest("ajaxBehaviorGetSetValueExpressionNPETest");
    }

    @Test
    void ajaxBehaviorAddRemoveBehaviorListenerNPETest() {
        runServletTest("ajaxBehaviorAddRemoveBehaviorListenerNPETest");
    }

    @Test
    void ajaxBehaviorGetSetOnerrorTest() {
        runServletTest("ajaxBehaviorGetSetOnerrorTest");
    }

    @Test
    void ajaxBehaviorGetSetOnventTest() {
        runServletTest("ajaxBehaviorGetSetOnventTest");
    }

    @Test
    void ajaxBehaviorGetSetRenderTest() {
        runServletTest("ajaxBehaviorGetSetRenderTest");
    }

    @Test
    void ajaxBehaviorGetSetValueExpressionTest() {
        runServletTest("ajaxBehaviorGetSetValueExpressionTest");
    }

    @Test
    void ajaxBehaviorIsSetResetValuesTest() {
        runServletTest("ajaxBehaviorIsSetResetValuesTest");
    }
}
