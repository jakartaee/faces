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
package ee.jakarta.tck.faces.test.faces20.api.convert.datetimeconverter;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;

class DateTimeConverterIT extends BaseITNG {

    private void runServletTest(String testName) {
        String body = getResponseBody("DateTimeConverterTestServlet?testname=" + testName);
        assertTrue(body.contains("Test PASSED"), "Expected 'Test PASSED' in response but got:\n" + body);
    }

    @Test
    void dateTimeConverterGetSetDateStyleTest() {
        runServletTest("dateTimeConverterGetSetDateStyleTest");
    }

    @Test
    void dateTimeConverterSetGetLocaleTest() {
        runServletTest("dateTimeConverterSetGetLocaleTest");
    }

    @Test
    void dateTimeConverterSetGetTimeZoneTest() {
        runServletTest("dateTimeConverterSetGetTimeZoneTest");
    }

    @Test
    void dateTimeConverterSetGetPatternTest() {
        runServletTest("dateTimeConverterSetGetPatternTest");
    }

    @Test
    void dateTimeConverterSetGetTimeStyleTest() {
        runServletTest("dateTimeConverterSetGetTimeStyleTest");
    }

    @Test
    void dateTimeConverterSetGetTypeTest() {
        runServletTest("dateTimeConverterSetGetTypeTest");
    }

    @Test
    void dateTimeConverterIsSetTransientTest() {
        runServletTest("dateTimeConverterIsSetTransientTest");
    }

    @Test
    void dateTimeConverterGetAsObjectDateStyleTest() {
        runServletTest("dateTimeConverterGetAsObjectDateStyleTest");
    }

    @Test
    void dateTimeConverterGetAsStringDateStyleTest() {
        runServletTest("dateTimeConverterGetAsStringDateStyleTest");
    }

    @Test
    void dateTimeConverterGetAsObjectTimeStyleTest() {
        runServletTest("dateTimeConverterGetAsObjectTimeStyleTest");
    }

    @Test
    void dateTimeConverterGetAsStringTimeStyleTest() {
        runServletTest("dateTimeConverterGetAsStringTimeStyleTest");
    }

    @Test
    void dateTimeConverterGetAsObjectBothStyleTest() {
        runServletTest("dateTimeConverterGetAsObjectBothStyleTest");
    }

    @Test
    void dateTimeConverterGetAsStringBothStyleTest() {
        runServletTest("dateTimeConverterGetAsStringBothStyleTest");
    }

    @Test
    void dateTimeConverterGetAsObjectPatternTest() {
        runServletTest("dateTimeConverterGetAsObjectPatternTest");
    }

    @Test
    void dateTimeConverterGetAsStringPatternTest() {
        runServletTest("dateTimeConverterGetAsStringPatternTest");
    }

    @Test
    void dateTimeConverterInvalidDateStyleTest() {
        runServletTest("dateTimeConverterInvalidDateStyleTest");
    }

    @Test
    void dateTimeConverterInvalidPatternTest() {
        runServletTest("dateTimeConverterInvalidPatternTest");
    }

    @Test
    void dateTimeConverterInvalidTimeStyleTest() {
        runServletTest("dateTimeConverterInvalidTimeStyleTest");
    }

    @Test
    void dateTimeConverterInvalidTypeTest() {
        runServletTest("dateTimeConverterInvalidTypeTest");
    }

    @Test
    void dateTimeConverterGetAsStringNPETest() {
        runServletTest("dateTimeConverterGetAsStringNPETest");
    }

    @Test
    void dateTimeConverterGetAsObjectNPETest() {
        runServletTest("dateTimeConverterGetAsObjectNPETest");
    }
}
