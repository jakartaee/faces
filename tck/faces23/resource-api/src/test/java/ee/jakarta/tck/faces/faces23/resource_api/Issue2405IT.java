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
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

public class Issue2405IT extends BaseITNG {

    /**
     * {@link jakarta.faces.application.ResourceHandler#createResource} must encode the library
     * name as a {@code ?ln=} query parameter when a library is given, and embed it in the
     * resource path segment when the resource id already includes the library directory.
     *
     * @see jakarta.faces.application.ResourceHandler
     * @see https://github.com/javaserverfaces/mojarra/issues/2405
     */
    @Test
    void testResourceWithAndWithoutLibrary() throws Exception {
        WebPage page = getPage("faces/start.xhtml");
        assertTrue(page.containsText("Resource created with library: ")
                && page.containsText("jakarta.faces.resource/images/background.png?ln=css"),
                "Resource created with a library carries a ?ln=css query parameter");
        assertTrue(page.containsText("Resource created without library: ")
                && page.containsText("jakarta.faces.resource/css/images/background.png"),
                "Resource created without a library embeds the directory in the path");
    }

    /**
     * {@link jakarta.faces.application.ResourceHandler#createResource} must select the correct
     * versioned resource regardless of trailing/leading underscores or an invalid version
     * directory name when scanning a versioned resource library.
     *
     * @see jakarta.faces.application.ResourceHandler
     * @see https://github.com/javaserverfaces/mojarra/issues/2405
     */
    @Test
    void testResourceVersions() throws Exception {
        WebPage page = getPage("faces/issue2565.xhtml");
        assertEquals("SUCCESS", page.findElement(By.id("trailingUnderscore")).getText());
        assertEquals("SUCCESS", page.findElement(By.id("noUnderscore")).getText());
        assertEquals("SUCCESS", page.findElement(By.id("leadingUnderscore")).getText());
        assertEquals("SUCCESS", page.findElement(By.id("validVersion")).getText());
    }
}
