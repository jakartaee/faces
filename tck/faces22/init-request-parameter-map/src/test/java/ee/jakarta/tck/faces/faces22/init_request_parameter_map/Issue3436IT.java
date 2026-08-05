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

package ee.jakarta.tck.faces.faces22.init_request_parameter_map;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.ExternalContextFactory;

/**
 * The module decorates both the faces context factory and the external context factory, and the latter reads the
 * request parameter map while it is still creating the external context. Factories apply to every view of their
 * webapp, hence the dedicated module.
 */
class Issue3436IT extends BaseITNG {

    /**
     * An ExternalContextFactory may consult the request parameter map of the ExternalContext it is creating, before
     * the FacesContext that will own it exists. The application starts and serves views regardless.
     *
     * @see ExternalContextFactory#getExternalContext(Object, Object, Object)
     * @see ExternalContext#getRequestParameterMap()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3436
     */
    @Test
    void requestParameterMapIsReadableWhileTheExternalContextIsBeingCreated() {
        WebPage page = getPage("issue3436.xhtml?issue3436=value");

        assertEquals(200, page.getResponseStatus(), "initial render");
        assertEquals("served", page.findElement(By.id("form:result")).getText(), "The view must be served.");
        assertEquals("[value]", page.findElement(By.id("form:parameter")).getText(),
                "The request parameter must still be readable from the view.");

        page.guardHttp(page.findElement(By.id("form:submit"))::click);

        assertEquals(200, page.getResponseStatus(), "postback");
        assertEquals("served", page.findElement(By.id("form:result")).getText(), "The view must survive a postback.");
    }
}
