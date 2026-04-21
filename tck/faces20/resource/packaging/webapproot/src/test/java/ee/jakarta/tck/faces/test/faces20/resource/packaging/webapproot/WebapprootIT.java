/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
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
package ee.jakarta.tck.faces.test.faces20.resource.packaging.webapproot;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;

class WebapprootIT extends BaseITNG {

    private void runServletTest(String testName) {
        String body = getResponseBody("TestServlet?testname=" + testName);
        assertTrue(body.contains("Test PASSED"), "Expected 'Test PASSED' in response but got:\n" + body);
    }

    private void runServletTest(String testName, String acceptLanguage) {
        try {
            HttpRequest request = HttpRequest.newBuilder(URI.create(webUrl + "TestServlet?testname=" + testName))
                .header("Accept-Language", acceptLanguage)
                .build();
            String body = HttpClient.newHttpClient().send(request, BodyHandlers.ofString()).body();
            assertTrue(body.contains("Test PASSED"), "Expected 'Test PASSED' in response but got:\n" + body);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(e);
        }
        catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    void resourceResPkgTest() {
        runServletTest("resourceResPkgTest");
    }

    @Test
    void resourceLibResPkgTest() {
        runServletTest("resourceLibResPkgTest");
    }

    @Test
    void resourceLibVerResPkgTest() {
        runServletTest("resourceLibVerResPkgTest");
    }

    @Test
    void resourceResVerPkgTest() {
        runServletTest("resourceResVerPkgTest");
    }

    @Test
    void resourceLibVerResVerPkgTest() {
        runServletTest("resourceLibVerResVerPkgTest");
    }

    @Test
    void reourceNoFileExtPkgTest() {
        runServletTest("reourceNoFileExtPkgTest");
    }

    @Test
    void resourceLocaleDEPkgTest() {
        runServletTest("resourceLocaleDEPkgTest", "de");
    }

    @Test
    void resourceLocaleENPkgTest() {
        runServletTest("resourceLocaleENPkgTest", "en");
    }

    @Test
    void resourceLocaleFRPkgTest() {
        runServletTest("resourceLocaleFRPkgTest", "fr");
    }

    @Test
    void resourceLibVerResVerNegativePkgTest() {
        runServletTest("resourceLibVerResVerNegativePkgTest");
    }

    @Test
    void reourceLibVerResNegativePkgTest() {
        runServletTest("reourceLibVerResNegativePkgTest");
    }

    @Test
    void reourceTrailingUSNegativePkgTest() {
        runServletTest("reourceTrailingUSNegativePkgTest");
    }

    @Test
    void reourceLeadingUSNegativePkgTest() {
        runServletTest("reourceLeadingUSNegativePkgTest");
    }

    @Test
    void reourceNoUSNegetivePkgTest() {
        runServletTest("reourceNoUSNegetivePkgTest");
    }

    @Test
    void reourceNoFileExtVerNegetivePkgTest() {
        runServletTest("reourceNoFileExtVerNegetivePkgTest");
    }
}
