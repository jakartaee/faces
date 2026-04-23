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
package ee.jakarta.tck.faces.test.faces20.api.validator.lengthvalidator;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;

class LengthValidatorIT extends BaseITNG {

    private void runServletTest(String testName) {
        String body = getResponseBody("LengthValidatorTestServlet?testname=" + testName);
        assertTrue(body.contains("Test PASSED"), "Expected 'Test PASSED' in response but got:\n" + body);
    }

    @Test
    void lengthValidatorCtor1Test() {
        runServletTest("lengthValidatorCtor1Test");
    }

    @Test
    void lengthValidatorCtor2Test() {
        runServletTest("lengthValidatorCtor2Test");
    }

    @Test
    void lengthValidatorCtor3Test() {
        runServletTest("lengthValidatorCtor3Test");
    }

    @Test
    void lengthValidatorGetSetMaximumTest() {
        runServletTest("lengthValidatorGetSetMaximumTest");
    }

    @Test
    void lengthValidatorGetSetMinimumTest() {
        runServletTest("lengthValidatorGetSetMinimumTest");
    }

    @Test
    void lengthValidatorValidateTest() {
        runServletTest("lengthValidatorValidateTest");
    }

    @Test
    void lengthValidatorValidateMaxViolationTest() {
        runServletTest("lengthValidatorValidateMaxViolationTest");
    }

    @Test
    void lengthValidatorValidateMinViolationTest() {
        runServletTest("lengthValidatorValidateMinViolationTest");
    }

    @Test
    void lengthValidatorValidateNPETest() {
        runServletTest("lengthValidatorValidateNPETest");
    }

    @Test
    void stateHolderSaveRestoreStateTest() {
        runServletTest("stateHolderSaveRestoreStateTest");
    }

    @Test
    void stateHolderIsSetTransientTest() {
        runServletTest("stateHolderIsSetTransientTest");
    }

    @Test
    void stateHolderRestoreStateNPETest() {
        runServletTest("stateHolderRestoreStateNPETest");
    }

    @Test
    void stateHolderSaveStateNPETest() {
        runServletTest("stateHolderSaveStateNPETest");
    }

    @Test
    void validatorPartialStateTest() {
        runServletTest("validatorPartialStateTest");
    }
}
