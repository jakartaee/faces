/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2020 Contributors to Eclipse Foundation.
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

package ee.jakarta.tck.faces.test.servlet40.exactmapping_selenium;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;
import jakarta.faces.application.Resource;
import jakarta.faces.application.ViewHandler;
import org.junit.Test;
import org.openqa.selenium.By;

import java.time.Duration;

import static org.junit.Assert.assertTrue;


public class Spec1260IT extends BaseITNG {


    /**
     * @see Resource#getRequestPath()
     * @see ViewHandler#deriveViewId(jakarta.faces.context.FacesContext, String)
     * @see ViewHandler#deriveLogicalViewId(jakarta.faces.context.FacesContext, String)
     * @see https://github.com/jakartaee/faces/issues/1260
     */
    @Test
    public void testExactMappedViewLoads() throws Exception {
        WebPage page = getPage("foo");
        String content = getWebDriver().getPageSource();

        // Basic test that if the FacesServlet is mapped to /foo, the right view "foo.xhtml" is loaded.
        assertTrue(content.contains("This is page foo"));
    }

    /**
     * @see Resource#getRequestPath()
     * @see ViewHandler#deriveViewId(jakarta.faces.context.FacesContext, String)
     * @see ViewHandler#deriveLogicalViewId(jakarta.faces.context.FacesContext, String)
     * @see https://github.com/jakartaee/faces/issues/1260
     */
    @Test
    public void testPostBackToExactMappedView() throws Exception {
        WebPage page = getPage("foo");

        getWebDriver().findElement(By.id("form:commandButton")).click();
        page.waitForCondition(webDriver -> getWebDriver().getPageTextReduced().contains("foo method invoked"));


        // If page /foo postbacks to itself, the new URL should be /foo again
        assertTrue(page.getCurrentUrl().split("\\?")[0].endsWith("/foo"));
    }

    /**
     * @see Resource#getRequestPath()
     * @see ViewHandler#deriveViewId(jakarta.faces.context.FacesContext, String)
     * @see ViewHandler#deriveLogicalViewId(jakarta.faces.context.FacesContext, String)
     * @see https://github.com/jakartaee/faces/issues/1260
     */
    @Test
    public void testLinkToNonExactMappedView() throws Exception {
        WebPage page = getPage("foo");

        page.waitForCondition(webDriver -> getWebDriver().getPageTextReduced().contains("This is page foo"));

        getWebDriver().findElement(By.id("form:button")).click();

        page.waitForCondition(webDriver -> getWebDriver().getPageTextReduced().contains("This is page bar"));

        // view "bar" is not exact mapped, so should be loaded via the suffix
        // or prefix the FacesServlet is mapped to when coming from /foo

        String path = page.getCurrentUrl().split("\\?")[0];

        assertTrue(path.endsWith("/bar.jsf") || path.endsWith("/faces/bar"));
    }

    /**
     * @see Resource#getRequestPath()
     * @see ViewHandler#deriveViewId(jakarta.faces.context.FacesContext, String)
     * @see ViewHandler#deriveLogicalViewId(jakarta.faces.context.FacesContext, String)
     * @see https://github.com/jakartaee/faces/issues/1260
     */
    @Test
    public void testPostBackOnLinkedNonExactMappedView() throws Exception {

        // Navigate from /foo to /bar.jsf
        WebPage page = getPage("foo");
        page.guardAjax(getWebDriver().findElement(By.id("form:button"))::click);

        // After navigating to a non-exact mapped view, a postback should stil work
        getWebDriver().findElement(By.id("form:commandButton")).click();
        page.waitForCondition(webDriver -> getWebDriver().getPageTextReduced().contains("foo method invoked"));

        // Check we're indeed on bar.jsf or faces/bar
        String path = page.getCurrentUrl().split("\\?")[0];
        assertTrue(path.endsWith("/bar.jsf") || path.endsWith("/faces/bar"));
    }

    /**
     * @see Resource#getRequestPath()
     * @see ViewHandler#deriveViewId(jakarta.faces.context.FacesContext, String)
     * @see ViewHandler#deriveLogicalViewId(jakarta.faces.context.FacesContext, String)
     * @see https://github.com/jakartaee/faces/issues/1260
     */
    @Test
    public void testResourceReferenceFromExactMappedView() throws Exception {

        WebPage page = getPage("foo");

        page.waitForCondition(webDriver -> {
            String content = getWebDriver().getPageSource();
            return content.contains("jakarta.faces.resource/faces.js.jsf") || content.contains("jakarta.faces.resource/faces/faces.js");
        });

        // Runtime must have found out the mappings of the FacesServlet and used one of the prefix or suffix
        // mappings to render the reference to "faces.js", which is not exactly mapped.
        // otherwise a timeout exception would have been thrown and the test would have failed
    }

    /**
     * @see Resource#getRequestPath()
     * @see ViewHandler#deriveViewId(jakarta.faces.context.FacesContext, String)
     * @see ViewHandler#deriveLogicalViewId(jakarta.faces.context.FacesContext, String)
     * @see https://github.com/jakartaee/faces/issues/1260
     */
    @Test
    public void testAjaxFromExactMappedView() throws Exception {
        WebPage page = getPage("foo");

        page.guardAjax(getWebDriver().findElement(By.id("form:commandButtonAjax"))::click);
        // AJAX from an exact-mapped view should work
        page.waitForCondition(webDriver -> getWebDriver().getPageTextReduced().contains("partial request = true"));

        // Part of page not updated via AJAX so should not show
        assertTrue(!getWebDriver().getPageTextReduced().contains("should not see this"));
    }


}
