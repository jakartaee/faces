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
package ee.jakarta.tck.faces.test.faces20.api.model.selectitem;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;

class SelectItemIT extends BaseITNG {

    private void runServletTest(String testName) {
        String body = getResponseBody("SelectItemTestServlet?testname=" + testName);
        assertTrue(body.contains("Test PASSED"), "Expected 'Test PASSED' in response but got:\n" + body);
    }

    @Test
    void selectItemCtor0Test() {
        runServletTest("selectItemCtor0Test");
    }

    @Test
    void selectItemCtor1Test() {
        runServletTest("selectItemCtor1Test");
    }

    @Test
    void selectItemCtor2Test() {
        runServletTest("selectItemCtor2Test");
    }

    @Test
    void selectItemCtor3Test() {
        runServletTest("selectItemCtor3Test");
    }

    @Test
    void selectItemCtor4Test() {
        runServletTest("selectItemCtor4Test");
    }

    @Test
    void selectItemCtor5Test() {
        runServletTest("selectItemCtor5Test");
    }

    @Test
    void selectItemGetSetValueTest() {
        runServletTest("selectItemGetSetValueTest");
    }

    @Test
    void selectItemGetSetLabelTest() {
        runServletTest("selectItemGetSetLabelTest");
    }

    @Test
    void selectItemGetSetDescriptionTest() {
        runServletTest("selectItemGetSetDescriptionTest");
    }

    @Test
    void selectItemIsSetDisabledTest() {
        runServletTest("selectItemIsSetDisabledTest");
    }

    @Test
    void selectItemIsSetEscapeTest() {
        runServletTest("selectItemIsSetEscapeTest");
    }
}
