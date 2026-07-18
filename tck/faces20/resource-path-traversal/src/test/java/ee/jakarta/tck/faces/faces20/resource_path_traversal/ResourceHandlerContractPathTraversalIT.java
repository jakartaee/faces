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

package ee.jakarta.tck.faces.faces20.resource_path_traversal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;

/**
 * The resource handler must not let a crafted resource request traverse out of the resource base
 * (here via a {@code con} contract parameter of {@code ..}) to serve a protected file such as
 * {@code /WEB-INF/web.xml}.
 */
public class ResourceHandlerContractPathTraversalIT extends BaseITNG {

    private static final String TRAVERSAL = "jakarta.faces.resource/WEB-INF/web.xml.xhtml?con=..";

    /**
     * A path-traversal resource request must not disclose the deployment descriptor and must not
     * return 200.
     *
     * @see jakarta.faces.application.ResourceHandler#handleResourceRequest(jakarta.faces.context.FacesContext)
     * @see jakarta.faces.application.ResourceHandler#isResourceRequest(jakarta.faces.context.FacesContext)
     */
    @Test
    void testResourceTraversalIsRejected() {
        String body = getResponseBody(TRAVERSAL);
        assertFalse(body.contains("jakarta.faces.webapp.FacesServlet"), "web.xml content must not leak");
        assertFalse(body.contains("<servlet-class>"), "web.xml content must not leak");

        assertNotEquals(200, getStatusCode(TRAVERSAL), "traversal request must not return 200");
    }
}
