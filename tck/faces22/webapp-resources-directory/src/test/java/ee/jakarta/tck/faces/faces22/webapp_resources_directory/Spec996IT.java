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
package ee.jakarta.tck.faces.faces22.webapp_resources_directory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.faces.application.ResourceHandler;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Spec996IT extends BaseITNG {

    /**
     * Verifies that a Facelets template placed under a custom web app resources directory (configured through
     * {@code jakarta.faces.WEBAPP_RESOURCES_DIRECTORY} at {@code /WEB-INF/myresources}) can be used to compose a view.
     *
     * @see ResourceHandler#WEBAPP_RESOURCES_DIRECTORY_PARAM_NAME
     * @see https://github.com/jakartaee/faces/issues/996
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2352
     */
    @Test
    void testWebInfCustomResourcesDirectory() {
        WebPage page = getPage("spec996.xhtml");

        assertEquals(200, page.getResponseStatus(), "View composing a template under the custom resources directory must render");
        assertEquals("Using a template inside /WEB-INF/myresources",
                page.findElement(By.id("top")).getText(), "top section");
        assertEquals("And it works!",
                page.findElement(By.id("content")).getText(), "content section");
    }
}
