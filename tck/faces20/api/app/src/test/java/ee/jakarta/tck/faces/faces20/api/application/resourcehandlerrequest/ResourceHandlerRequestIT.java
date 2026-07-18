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

package ee.jakarta.tck.faces.faces20.api.application.resourcehandlerrequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * The public {@link jakarta.faces.application.ResourceHandler} contract: {@code createResource}
 * derives library, resource name and content type (honoring an explicit content type and falling
 * back for a null one), returns {@code null} for non-existent resources or libraries, and
 * {@code libraryExists} reports a missing library. The built-in Faces script resource is always
 * resolvable.
 */
class ResourceHandlerRequestIT extends BaseITNG {

    /**
     * @see jakarta.faces.application.ResourceHandler#createResource(String, String, String)
     * @see jakarta.faces.application.Resource
     */
    @Test
    void testResourceHandlerContract() {
        WebPage page = getPage("faces/resourceHandlerRequest.xhtml");
        assertEquals("true", page.findElement(By.id("result")).getText(),
                "every ResourceHandler/Resource public-API assertion must hold");
    }
}
