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

package ee.jakarta.tck.faces.faces22.facelets;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * Resource-library-contract resources are private and must not be reachable by a direct path
 * reference. A component referencing {@code /contracts/...} directly renders the {@code RES_NOT_FOUND}
 * marker instead of a served resource URL.
 */
public class Issue3172IT extends BaseITNG {

    private static final String RES_NOT_FOUND = "RES_NOT_FOUND";

    /**
     * {@code h:graphicImage} with a direct contract path in {@code value} yields RES_NOT_FOUND.
     *
     * @see jakarta.faces.application.ResourceHandler#createResource(String, String)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3172
     */
    @Test
    void testGraphicImageDirectContract() {
        WebPage page = getPage("issue3172GraphicImageValue.xhtml");
        assertTrue(page.containsSource(RES_NOT_FOUND));
    }

    /**
     * {@code h:graphicImage} with a direct contract path in {@code name} yields RES_NOT_FOUND.
     *
     * @see jakarta.faces.application.ResourceHandler#createResource(String, String)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3172
     */
    @Test
    void testGraphicImageDirectContract2() {
        WebPage page = getPage("issue3172GraphicImageName.xhtml");
        assertTrue(page.containsSource(RES_NOT_FOUND));
    }

    /**
     * {@code h:outputStylesheet} with a direct contract path yields RES_NOT_FOUND.
     *
     * @see jakarta.faces.application.ResourceHandler#createResource(String, String)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3172
     */
    @Test
    void testOutputStylesheetDirectContract() {
        WebPage page = getPage("issue3172OutputStylesheet.xhtml");
        assertTrue(page.containsSource(RES_NOT_FOUND));
    }

    /**
     * {@code h:outputScript} with a direct contract path yields RES_NOT_FOUND.
     *
     * @see jakarta.faces.application.ResourceHandler#createResource(String, String)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3172
     */
    @Test
    void testOutputScriptDirectContract() {
        WebPage page = getPage("issue3172OutputScript.xhtml");
        assertTrue(page.containsSource(RES_NOT_FOUND));
    }
}
