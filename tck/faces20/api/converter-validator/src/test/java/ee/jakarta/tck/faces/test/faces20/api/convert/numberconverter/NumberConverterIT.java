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
package ee.jakarta.tck.faces.test.faces20.api.convert.numberconverter;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;

class NumberConverterIT extends BaseITNG {

    private void runServletTest(String testName) {
        String body = getResponseBody("NumberConverterTestServlet?testname=" + testName);
        assertTrue(body.contains("Test PASSED"), "Expected 'Test PASSED' in response but got:\n" + body);
    }

    @Test void numConverterGetSetCurrencyCodeTest() { runServletTest("numConverterGetSetCurrencyCodeTest"); }
    @Test void numConverterGetSetCurrencySymbolTest() { runServletTest("numConverterGetSetCurrencySymbolTest"); }
    @Test void numConverterGetSetLocaleTest() { runServletTest("numConverterGetSetLocaleTest"); }
    @Test void numConverterGetSetMaxFractionDigitsTest() { runServletTest("numConverterGetSetMaxFractionDigitsTest"); }
    @Test void numConverterGetSetMaxIntegerDigitsTest() { runServletTest("numConverterGetSetMaxIntegerDigitsTest"); }
    @Test void numConverterGetSetMinFractionDigitsTest() { runServletTest("numConverterGetSetMinFractionDigitsTest"); }
    @Test void numConverterGetSetMinIntegerDigitsTest() { runServletTest("numConverterGetSetMinIntegerDigitsTest"); }
    @Test void numConverterGetSetPatternTest() { runServletTest("numConverterGetSetPatternTest"); }
    @Test void numConverterGetSetTypeTest() { runServletTest("numConverterGetSetTypeTest"); }
    @Test void numConverterIsSetGroupingUsedTest() { runServletTest("numConverterIsSetGroupingUsedTest"); }
    @Test void numConverterIsSetIntegerOnlyTest() { runServletTest("numConverterIsSetIntegerOnlyTest"); }
    @Test void numConverterIsSetTransientTest() { runServletTest("numConverterIsSetTransientTest"); }
    @Test void numConverterGetAsObjectNullZeroLengthTest() { runServletTest("numConverterGetAsObjectNullZeroLengthTest"); }
    @Test void numConverterGetAsObjectLocaleTest() { runServletTest("numConverterGetAsObjectLocaleTest"); }
    @Test void numConverterGetAsObjectPatternTest() { runServletTest("numConverterGetAsObjectPatternTest"); }
    @Test void numConverterGetAsObjectTypeTest() { runServletTest("numConverterGetAsObjectTypeTest"); }
    @Test void numConverterGetAsObjectParseIntOnlyTest() { runServletTest("numConverterGetAsObjectParseIntOnlyTest"); }
    @Test void numConverterGetAsStringNullZeroLengthTest() { runServletTest("numConverterGetAsStringNullZeroLengthTest"); }
    @Test void numConverterGetAsStringLocaleTest() { runServletTest("numConverterGetAsStringLocaleTest"); }
    @Test void numConverterGetAsStringPatternTest() { runServletTest("numConverterGetAsStringPatternTest"); }
    @Test void numConverterGetAsStringTypeTest() { runServletTest("numConverterGetAsStringTypeTest"); }
    @Test void numConverterGetAsStringGroupingTest() { runServletTest("numConverterGetAsStringGroupingTest"); }
    @Test void numConverterGetAsStringMinMaxIntegerTest() { runServletTest("numConverterGetAsStringMinMaxIntegerTest"); }
    @Test void numConverterGetAsStringMinMaxFractionTest() { runServletTest("numConverterGetAsStringMinMaxFractionTest"); }
    @Test void numConverterGetAsStringCurrencySymbolTest() { runServletTest("numConverterGetAsStringCurrencySymbolTest"); }
    @Test void numConverterGetAsStringCurrencyCodeTest() { runServletTest("numConverterGetAsStringCurrencyCodeTest"); }
    @Test void numConverterGetAsStringCurrencyCodeSymbolTest() { runServletTest("numConverterGetAsStringCurrencyCodeSymbolTest"); }
    @Test void numConverterGetAsObjectNPETest() { runServletTest("numConverterGetAsObjectNPETest"); }
    @Test void numConverterGetAsStringNPETest() { runServletTest("numConverterGetAsStringNPETest"); }
}
