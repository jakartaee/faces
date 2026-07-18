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

package ee.jakarta.tck.faces.faces23.resource_api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;

/**
 * A resource request whose library name attempts to traverse out of the resources directory (for
 * example {@code ln=.\..} to reach {@code /WEB-INF/faces-config.xml}) must not be served: the
 * resource handler rejects the path and the container returns 404 rather than leaking a file from
 * outside the resource tree.
 */
public class Issue3208IT extends BaseITNG {

    private static final String TRAVERSAL_RESOURCE =
            "jakarta.faces.resource/WEB-INF/faces-config.xml.xhtml?ln=.\\..";

    /**
     * A path-traversal resource request is rejected with 404, not served.
     *
     * @see jakarta.faces.application.ResourceHandler#handleResourceRequest(jakarta.faces.context.FacesContext)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3208
     */
    @Test
    void testResourceTraversalIsRejected() {
        assertEquals(404, getStatusCode(TRAVERSAL_RESOURCE),
                "A path-traversal resource request must return 404");
    }
}
