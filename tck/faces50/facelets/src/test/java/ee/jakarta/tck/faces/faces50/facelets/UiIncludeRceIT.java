/*
 * Copyright (c) Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the Apache License, Version 2.0
 * which is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GPL-2.0 with Classpath-exception-2.0 which
 * is available at https://openjdk.java.net/legal/gplv2+ce.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0 or Apache-2.0
 */
package ee.jakarta.tck.faces.faces50.facelets;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * A {@code src} of {@code ui:include} which is under the control of the request must resolve to a Facelet
 * inside the application. It may not name an absolute URL whose scheme discards the application base, escape
 * the deployment through path traversal, or point at a non-Facelet resource such as a deployment descriptor.
 */
class UiIncludeRceIT extends BaseITNG {

    private static final String PAGE = "uiIncludeRce.xhtml";
    private static final String WEB_XML_CONTENT = "<web-app";
    private static final String REMOTE_CONTENT = "support \"begin\" and \"end\"";

    // Positive cases: legitimate includes must still work.

    @Test
    void testSameDirectoryInclude() {
        var page = getPage(PAGE + "?p=uiIncludeRceLocal.xhtml");
        assertTrue(page.containsText("local content"));
    }

    @Test
    void testSubdirectoryInclude() {
        var page = getPage(PAGE + "?p=uiIncludeRceSub/nested.xhtml");
        assertTrue(page.containsText("nested content"));
    }

    /**
     * WEB-INF is protected by the container against direct requests, but remains a legitimate location for a
     * Facelet which is pulled in by an include.
     */
    @Test
    void testAbsolutePathToFaceletInWebInf() {
        var page = getPage(PAGE + "?p=/WEB-INF/uiIncludeRceSecret.xhtml");
        assertTrue(page.containsText("secret WEB-INF content"));
    }

    // Remote URL schemes.

    @Test
    void testHardcodedRemoteInclude() {
        var page = getPage("uiIncludeRceRemote.xhtml");
        assertBlocked(page, REMOTE_CONTENT);
    }

    @Test
    void testHttpsScheme() {
        var page = getPage(PAGE + "?p=https://raw.githubusercontent.com/eclipse-ee4j/mojarra/refs/heads/4.0/test/issue5750/src/main/webapp/issue5750.xhtml");
        assertBlocked(page, REMOTE_CONTENT);
    }

    @Test
    void testHttpScheme() {
        var page = getPage(PAGE + "?p=http://example.com/evil.xhtml");
        assertBlocked(page);
    }

    @Test
    void testFileScheme() {
        var page = getPage(PAGE + "?p=file:///etc/passwd");
        assertBlocked(page, "root:");
    }

    @Test
    void testJarScheme() {
        var page = getPage(PAGE + "?p=jar:file:///tmp/evil.jar!/META-INF/evil.xhtml");
        assertBlocked(page);
    }

    @Test
    void testFtpScheme() {
        var page = getPage(PAGE + "?p=ftp://attacker.example.com/evil.xhtml");
        assertBlocked(page);
    }

    @Test
    void testDataScheme() {
        var page = getPage(PAGE + "?p=data:text/xml,%3Chtml/%3E");
        assertBlocked(page);
    }

    // Path traversal variants.

    @Test
    void testPlainTraversal() {
        var page = getPage(PAGE + "?p=../../WEB-INF/web.xml");
        assertBlocked(page, WEB_XML_CONTENT);
    }

    @Test
    void testUrlEncodedTraversal() {
        var page = getPage(PAGE + "?p=..%2F..%2FWEB-INF/web.xml");
        assertBlocked(page, WEB_XML_CONTENT);
    }

    @Test
    void testDotEncodedTraversal() {
        var page = getPage(PAGE + "?p=%2e%2e/%2e%2e/WEB-INF/web.xml");
        assertBlocked(page, WEB_XML_CONTENT);
    }

    @Test
    void testDoubleEncodedTraversal() {
        var page = getPage(PAGE + "?p=..%252F..%252FWEB-INF/web.xml");
        assertBlocked(page, WEB_XML_CONTENT);
    }

    @Test
    void testTraversalWithFaceletSuffix() {
        var page = getPage(PAGE + "?p=../../some-other-app/evil.xhtml");
        assertBlocked(page);
    }

    // Absolute path disclosure.

    @Test
    void testAbsolutePathToWebXml() {
        var page = getPage(PAGE + "?p=/WEB-INF/web.xml");
        assertBlocked(page, WEB_XML_CONTENT);
    }

    @Test
    void testNonFaceletSuffix() {
        var page = getPage(PAGE + "?p=uiIncludeRce.properties");
        assertBlocked(page, "non-Facelet content");
    }

    // Malformed and degenerate inputs.

    @Test
    void testMalformedUri() {
        var page = getPage(PAGE + "?p=://bad");
        assertBlocked(page);
    }

    @Test
    void testEmptyParam() {
        assertNoStackTrace(getPage(PAGE + "?p="));
    }

    @Test
    void testMissingParam() {
        assertNoStackTrace(getPage(PAGE));
    }

    private void assertBlocked(WebPage page, String... forbiddenContent) {
        assertNotEquals(200, page.getResponseStatus(), "Request should have been rejected");

        for (String content : forbiddenContent) {
            assertFalse(page.getSource().contains(content), () -> "Must not disclose: " + content);
        }
    }

    private void assertNoStackTrace(WebPage page) {
        assertFalse(page.getSource().contains("NullPointerException"), "Must not fail with an unhandled NPE");
    }
}
