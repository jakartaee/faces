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
package ee.jakarta.tck.faces.test.faces20.navigation;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;

class NavigationIT extends BaseITNG {

    private void runServletTest(String testName) {
        String body = getResponseBody("TestServlet?testname=" + testName);
        assertTrue(body.contains("Test PASSED"), "Expected 'Test PASSED' in response but got:\n" + body);
    }

    @Test
    void navHandlerNullOutcomeTest() {
        runServletTest("navHandlerNullOutcomeTest");
    }

    @Test
    void navHandlerFromViewExactMatchTest() {
        runServletTest("navHandlerFromViewExactMatchTest");
    }

    @Test
    void navHandlerFromViewPatternMatchTest() {
        runServletTest("navHandlerFromViewPatternMatchTest");
    }

    @Test
    void navHandlerFromViewAsteriskOnlyTest() {
        runServletTest("navHandlerFromViewAsteriskOnlyTest");
    }

    @Test
    void navHandlerFromViewSearchOrderTest() {
        runServletTest("navHandlerFromViewSearchOrderTest");
    }

    @Test
    void navHandlerNavigationCaseTest() {
        runServletTest("navHandlerNavigationCaseTest");
    }

    @Test
    void navHandlerRedirectTest() {
        runServletTest("navHandlerRedirectTest");
    }

    @Test
    void navHandlerNoNavCaseGoToNextRuleTest() {
        runServletTest("navHandlerNoNavCaseGoToNextRuleTest");
    }
}
