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

package ee.jakarta.tck.faces.faces23.resource_library_contracts_vdl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * A custom {@link jakarta.faces.view.ViewDeclarationLanguageFactory} whose
 * {@code calculateResourceLibraryContracts} computes the contracts of every single request must
 * drive the resolution of the view, its template, its includes, its composite components and its
 * resources. This module keys the contract off a request parameter, the way an application serving
 * several tenants from one war would key it off the tenant.
 */
class Spec971IT extends BaseITNG {

    private static final String DEFAULT_TEMPLATE = "/spec971-template.xhtml";
    private static final String HOST2_TEMPLATE = "host2/spec971-template.xhtml";

    /**
     * A resource which resolves from a contract carries the contract in the {@code con} parameter of
     * its URL; a resource which resolves from the webapp itself does not.
     *
     * @see jakarta.faces.view.ViewDeclarationLanguage#calculateResourceLibraryContracts(jakarta.faces.context.FacesContext, String)
     * @see https://github.com/jakartaee/faces/issues/971
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2515
     */
    @Test
    void contractScopedCssResourceUrlCarriesConParameter() throws Exception {
        assertTrue(getStylesheetHref(getPage("spec971.xhtml?contract=host1")).contains("con=host1"),
                "stylesheet of the host1 contract");
        assertFalse(getStylesheetHref(getPage("spec971.xhtml?contract=host2")).contains("con="),
                "host2 contract has no stylesheet of its own, so the webapp's one is used");
        assertFalse(getStylesheetHref(getPage("spec971.xhtml?contract=unknown")).contains("con="),
                "stylesheet without any contract");
    }

    /**
     * A template which the computed contract supplies wins over the webapp's own one.
     *
     * @see jakarta.faces.view.ViewDeclarationLanguage#calculateResourceLibraryContracts(jakarta.faces.context.FacesContext, String)
     * @see https://github.com/jakartaee/faces/issues/971
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2515
     */
    @Test
    void templateFromContractWins() throws Exception {
        WebPage page = getPage("spec971.xhtml?contract=host2");
        assertEquals(HOST2_TEMPLATE, page.findElement(By.id("template")).getText(), "template");
        assertEquals("main content", page.findElement(By.id("content")).getText(), "content");
        assertEquals("footer info", page.findElement(By.id("footer")).getText(), "footer");
    }

    /**
     * An include which the computed contract supplies wins over the webapp's own one.
     *
     * @see jakarta.faces.view.ViewDeclarationLanguage#calculateResourceLibraryContracts(jakarta.faces.context.FacesContext, String)
     * @see https://github.com/jakartaee/faces/issues/971
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2515
     */
    @Test
    void includeFromContractWins() throws Exception {
        WebPage page = getPage("spec971.xhtml?contract=host1");
        assertEquals(DEFAULT_TEMPLATE, page.findElement(By.id("template")).getText(),
                "host1 contract has no template of its own, so the webapp's one is used");
        assertEquals("host1/spec971-header.xhtml", page.findElement(By.id("header")).getText(), "header");
    }

    /**
     * A composite component which the computed contract supplies wins over the one of the webapp's
     * own resource library.
     *
     * @see jakarta.faces.view.ViewDeclarationLanguage#calculateResourceLibraryContracts(jakarta.faces.context.FacesContext, String)
     * @see https://github.com/jakartaee/faces/issues/971
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2515
     */
    @Test
    void compositeComponentResolvedFromContract() throws Exception {
        assertEquals("host1/lib/cc.xhtml", getPage("spec971.xhtml?contract=host1").findElement(By.id("ccContent")).getText(),
                "composite component of the host1 contract");
        assertEquals("lib/2_3/cc.xhtml", getPage("spec971.xhtml?contract=unknown").findElement(By.id("ccContent")).getText(),
                "composite component of the webapp's own resource library");
    }

    /**
     * When the computed contracts are empty, everything resolves from the webapp itself.
     *
     * @see jakarta.faces.view.ViewDeclarationLanguage#calculateResourceLibraryContracts(jakarta.faces.context.FacesContext, String)
     * @see https://github.com/jakartaee/faces/issues/971
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2515
     */
    @Test
    void unknownContractFallsBackToWebapp() throws Exception {
        WebPage page = getPage("spec971.xhtml?contract=unknown");
        assertEquals(DEFAULT_TEMPLATE, page.findElement(By.id("template")).getText(), "template");
        assertEquals("/spec971-header.xhtml", page.findElement(By.id("header")).getText(), "header");
        assertEquals("main content", page.findElement(By.id("content")).getText(), "content");
        assertTrue(page.findElements(By.id("footer")).isEmpty(), "webapp's own template has no footer");
    }

    /**
     * When the computed contracts hold more than one contract, an artifact which the first one does
     * not supply resolves from the next one. This is how a contract extends another one.
     *
     * @see jakarta.faces.view.ViewDeclarationLanguage#calculateResourceLibraryContracts(jakarta.faces.context.FacesContext, String)
     * @see https://github.com/jakartaee/faces/issues/971
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2515
     */
    @Test
    void extendingContractFallsThroughToExtendedContract() throws Exception {
        WebPage page = getPage("spec971.xhtml?contract=host4");
        assertEquals("host4 content", page.findElement(By.id("content")).getText(),
                "view of the host4 contract");
        assertEquals(HOST2_TEMPLATE, page.findElement(By.id("template")).getText(),
                "host4 contract has no template of its own, so the extended host2 contract's one is used");
        assertEquals("", page.findElement(By.id("footerContainer")).getText(),
                "view of the host4 contract defines no footer");
    }

    private String getStylesheetHref(WebPage page) {
        return page.findElement(By.tagName("link")).getAttribute("href");
    }
}
