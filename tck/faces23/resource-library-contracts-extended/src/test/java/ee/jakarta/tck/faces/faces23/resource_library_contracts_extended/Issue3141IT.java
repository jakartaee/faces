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

package ee.jakarta.tck.faces.faces23.resource_library_contracts_extended;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.application.Resource;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * Resource library contracts resolve per artifact against the whole contract list, in order. A
 * contract may live in a JAR ({@code META-INF/contracts}) just as well as in the WAR
 * ({@code /contracts}), and a contract that only carries part of a layout falls through to the next
 * contract in the list for everything else - templates as well as resources.
 * <p>
 * The {@code red} contract carries only {@code template.xhtml} and {@code css/contract.css}; both
 * {@code warbase} (WAR) and {@code jarbase} (JAR) carry the full set, so {@code red} can be layered
 * on either.
 */
class Issue3141IT extends BaseITNG {

    /**
     * A contract packaged in a JAR supplies template, subtemplate and resources.
     *
     * @see jakarta.faces.context.FacesContext#getResourceLibraryContracts()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3141
     */
    @Test
    void testJarContract() {
        assertContracts("jarbase", "jarbase", "jarbase", "jarbase", "jarbase");
    }

    /**
     * A contract packaged in the WAR supplies template, subtemplate and resources.
     *
     * @see jakarta.faces.context.FacesContext#getResourceLibraryContracts()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3141
     */
    @Test
    void testWarContract() {
        assertContracts("warbase", "warbase", "warbase", "warbase", "warbase");
    }

    /**
     * Layered on a WAR contract, {@code red} supplies the template and its own stylesheet while
     * everything it lacks falls back to {@code warbase}.
     *
     * @see jakarta.faces.context.FacesContext#getResourceLibraryContracts()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3141
     */
    @Test
    void testMultiContractRedOverWarbase() {
        assertContracts("red,warbase", "red", "warbase", "red", "warbase");
    }

    /**
     * Layered on a JAR contract, {@code red} supplies the template and its own stylesheet while
     * everything it lacks falls back to {@code jarbase}.
     *
     * @see jakarta.faces.context.FacesContext#getResourceLibraryContracts()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3141
     */
    @Test
    void testMultiContractRedOverJarbase() {
        assertContracts("red,jarbase", "red", "jarbase", "red", "jarbase");
    }

    /**
     * Requests the page under the given contract list and asserts, per artifact, which contract
     * resolved it: the template (through its header), the subtemplate, and each of the two
     * stylesheets. Also asserts the contract list the runtime reports back on the view.
     */
    private void assertContracts(String contracts, String expectedTemplate, String expectedSubtemplate,
            String expectedContractCss, String expectedLayoutCss) {
        WebPage page = getPage("issue3141.xhtml?contracts=" + contracts);

        assertEquals("\"" + expectedTemplate + "\" template header",
                page.findElement(By.id("header")).getText(), "Template contract");
        assertEquals("from \"" + expectedSubtemplate + "\" subtemplate.xhtml",
                page.findElement(By.id("subtemplateOrigin")).getText(), "Subtemplate contract");
        assertEquals("[" + contracts.replace(",", ", ") + "]",
                page.findElement(By.id("resolvedContracts")).getText(), "Resolved contracts");

        assertStylesheetContract(page, "contract.css", expectedContractCss);
        assertStylesheetContract(page, "cssLayout.css", expectedLayoutCss);
    }

    /**
     * Asserts which contract a stylesheet resolved to, on both counts the spec makes observable: the
     * request path must carry the contract name, as {@link Resource#getRequestPath()} requires it to
     * include "con=" + the contract for a resource contained in one, and fetching that path must
     * serve that contract's own content rather than another's.
     *
     * @see Resource#getRequestPath()
     */
    private void assertStylesheetContract(WebPage page, String resourceName, String expectedContract) {
        WebElement link = page.findElement(By.cssSelector("link[href*='" + resourceName + "']"));
        String uri = getHrefURI(link);
        assertTrue(uri.contains("con=" + expectedContract),
                resourceName + " must request contract " + expectedContract + " but requested " + uri);
        assertTrue(getResponseBody(uri).contains("/* contract: " + expectedContract + ", resource: " + resourceName + " */"),
                resourceName + " must be served from contract " + expectedContract);
    }
}
