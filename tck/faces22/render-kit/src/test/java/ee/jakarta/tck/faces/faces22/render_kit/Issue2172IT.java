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

package ee.jakarta.tck.faces.faces22.render_kit;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;

class Issue2172IT extends BaseITNG {

    /**
     * Verifies that an h:outputScript with a library and a versioned name renders a resource URL whose
     * ampersand between the library (ln) and version (v) query parameters is HTML-escaped (ln=alibrary&amp;v=1).
     *
     * @see jakarta.faces.application.ResourceHandler
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2172
     */
    @Test
    void testIssue2172() throws Exception {
        String body = getResponseBody("issue2172.xhtml");
        assertTrue(body.contains("ln=alibrary") && body.contains("v=1") && body.contains("&amp;"),
                "Script resource URL must join the library and version params with an escaped ampersand");
    }
}
