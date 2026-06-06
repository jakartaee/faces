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

package ee.jakarta.tck.faces.faces22.composite_component;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue2441IT extends BaseITNG {

    /**
     * A facet passed into a composite component must be rendered where the implementation
     * calls cc:renderFacet for that facet name.
     *
     * @see jakarta.faces.view.facelets.FaceletContext
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2441
     */
    @Test
    void renderFacetRendersPassedFacet() throws Exception {
        WebPage page = getPage("issue2441a.xhtml");
        assertTrue(page.containsText("This came from a rendered facet"), "facet content is rendered by cc:renderFacet");
    }

    /**
     * A facet passed into a composite component must remain renderable when forwarded into a
     * nested composite component via cc:insertFacet and rendered there with cc:renderFacet.
     *
     * @see jakarta.faces.view.facelets.FaceletContext
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2441
     */
    @Test
    void renderFacetRendersForwardedFacet() throws Exception {
        WebPage page = getPage("issue2441b.xhtml");
        assertTrue(page.containsText("myFacet Text"), "facet content is rendered through nested cc:insertFacet and cc:renderFacet");
    }
}
