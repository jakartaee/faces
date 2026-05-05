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
package ee.jakarta.tck.faces.test.faces20.api.event.exceptionqueuedeventcontext;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;

class ExceptionQueuedEventContextIT extends BaseITNG {

    private void runServletTest(String testName) {
        String body = getResponseBody("ExceptionQueuedEventContextTestServlet?testname=" + testName);
        assertTrue(body.contains("Test PASSED"), "Expected 'Test PASSED' in response but got:\n" + body);
    }

    @Test void exceptionQueuedEventContextCtorOneTest() { runServletTest("exceptionQueuedEventContextCtorOneTest"); }
    @Test void exceptionQueuedEventContextCtorTest() { runServletTest("exceptionQueuedEventContextCtorTest"); }
    @Test void exceptionQueuedEventContextCtorTwoTest() { runServletTest("exceptionQueuedEventContextCtorTwoTest"); }
    @Test void exceptionQueuedEventContextgetAttributesTest() { runServletTest("exceptionQueuedEventContextgetAttributesTest"); }
    @Test void exceptionQueuedEventContextgetComponentNullTest() { runServletTest("exceptionQueuedEventContextgetComponentNullTest"); }
    @Test void exceptionQueuedEventContextgetComponentTest() { runServletTest("exceptionQueuedEventContextgetComponentTest"); }
    @Test void exceptionQueuedEventContextgetContextTest() { runServletTest("exceptionQueuedEventContextgetContextTest"); }
    @Test void exceptionQueuedEventContextgetExceptionTest() { runServletTest("exceptionQueuedEventContextgetExceptionTest"); }
    @Test void exceptionQueuedEventContextgetPhaseIdNullTest() { runServletTest("exceptionQueuedEventContextgetPhaseIdNullTest"); }
    @Test void exceptionQueuedEventContextgetPhaseIdTest() { runServletTest("exceptionQueuedEventContextgetPhaseIdTest"); }
}
