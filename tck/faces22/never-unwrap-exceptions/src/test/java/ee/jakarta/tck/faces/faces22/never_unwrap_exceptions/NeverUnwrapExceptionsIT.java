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
package ee.jakarta.tck.faces.faces22.never_unwrap_exceptions;

import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.webapp.FacesServlet;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;

class NeverUnwrapExceptionsIT extends BaseITNG {

    /**
     * When a bean getter throws during rendering, FacesServlet must rethrow it as a ServletException whose cause is the
     * original exception (not unwrapped) and whose message is that cause's message.
     *
     * @see FacesServlet#service(jakarta.servlet.ServletRequest, jakarta.servlet.ServletResponse)
     */
    @Test
    void testNeverUnwrapExceptions() {
        String body = getResponseBody("faces/neverUnwrapExceptions.xhtml");

        assertTrue(body.contains("Exception class: jakarta.servlet.ServletException"),
                "FacesServlet must rethrow as ServletException; got: " + body);
        assertTrue(body.contains("Root cause: java.lang.IllegalStateException"),
                "The original exception must be the ServletException cause; got: " + body);
        assertTrue(body.contains("Exception message: java.lang.IllegalArgumentException: java.lang.UnsupportedOperationException"),
                "The ServletException message must be the cause's message; got: " + body);
    }
}
