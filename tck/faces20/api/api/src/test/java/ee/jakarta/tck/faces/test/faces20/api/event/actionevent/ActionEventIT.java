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
package ee.jakarta.tck.faces.test.faces20.api.event.actionevent;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;

class ActionEventIT extends BaseITNG {

    private void runServletTest(String testName) {
        String body = getResponseBody("ActionEventTestServlet?testname=" + testName);
        assertTrue(body.contains("Test PASSED"), "Expected 'Test PASSED' in response but got:\n" + body);
    }

    @Test void actionEventCtorIllegalArgumentExceptionTest() { runServletTest("actionEventCtorIllegalArgumentExceptionTest"); }
    @Test void actionEventCtorTest() { runServletTest("actionEventCtorTest"); }
    @Test void actionEventGetComponentTest() { runServletTest("actionEventGetComponentTest"); }
    @Test void actionEventIsApproiateListenerNegativeTest() { runServletTest("actionEventIsApproiateListenerNegativeTest"); }
    @Test void actionEventIsApproiateListenerPostiveTest() { runServletTest("actionEventIsApproiateListenerPostiveTest"); }
    @Test void actionEventProcessListenerTest() { runServletTest("actionEventProcessListenerTest"); }
}
