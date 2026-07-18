/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
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
package ee.jakarta.tck.faces.faces23.two_contracts_in_jar;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * A JAR that carries exactly two resource library contracts under {@code META-INF/contracts}
 * ({@code siteLayout} supplying the template and its stylesheets, {@code resourcesInContractInJar}
 * supplying image resources) is honoured by the runtime: the template resolves and every resource
 * request is keyed on the contract that owns it ({@code con=...}).
 *
 * @see <a href="https://github.com/eclipse-ee4j/mojarra/issues/3718">mojarra#3718</a>
 */
class Issue3718IT extends BaseITNG {

    /**
     * The template packaged in the {@code siteLayout} contract inside the JAR is applied to the view.
     *
     * @see jakarta.faces.context.FacesContext#getResourceLibraryContracts()
     * @see <a href="https://github.com/eclipse-ee4j/mojarra/issues/3718">mojarra#3718</a>
     */
    @Test
    void testTemplatesAreUsed() {
        WebPage page = getPage("index.xhtml");
        assertTrue(page.containsText("Top Navigation Menu"), "Top Navigation Menu from siteLayout template");

        page.guardHttp(page.findElement(By.id("form:button"))::click);
        assertTrue(page.containsText("This is page2"), "navigated to page2, still under the template");
    }

    /**
     * Stylesheets resolve from the {@code siteLayout} contract and images from the
     * {@code resourcesInContractInJar} contract, each request carrying its owning contract.
     *
     * @see jakarta.faces.application.Resource#getRequestPath()
     * @see <a href="https://github.com/eclipse-ee4j/mojarra/issues/3718">mojarra#3718</a>
     */
    @Test
    void testResourcesAreRendered() {
        WebPage page = getPage("index.xhtml");

        examineCss(page.findElements(By.cssSelector("link[href*='.css']")));

        assertImage(page, "img01");
        assertImage(page, "img02");
    }

    private void examineCss(List<WebElement> cssLinks) {
        for (WebElement link : cssLinks) {
            String href = getHrefURI(link);
            assertTrue(href.contains("con=siteLayout"),
                    "stylesheet must request contract siteLayout but requested " + href);
            String content = getResponseBody(href);
            if (href.contains("default.css")) {
                assertTrue(content.contains("#AFAFAF"), "default.css served from siteLayout");
            } else if (href.contains("cssLayout.css")) {
                assertTrue(content.contains("#036fab"), "cssLayout.css served from siteLayout");
            } else {
                fail("unexpected stylesheet: " + href);
            }
        }
    }

    private void assertImage(WebPage page, String name) {
        String src = page.findElement(By.id("form:" + name)).getAttribute("src");
        assertTrue(src.contains("/jakarta.faces.resource/" + name + ".gif"),
                name + " must be a Faces resource but was " + src);
        assertTrue(src.contains("con=resourcesInContractInJar"),
                name + " must request contract resourcesInContractInJar but was " + src);
    }
}
