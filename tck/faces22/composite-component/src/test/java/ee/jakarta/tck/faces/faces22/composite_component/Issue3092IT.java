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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue3092IT extends BaseITNG {

    /**
     * When the facet passed to a composite holds more than one child, cc:renderFacet must render all
     * of them, not only the last one.
     *
     * @see jakarta.faces.component.UIComponent#getFacet(String)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3092
     */
    @Test
    void renderFacetRendersAllChildrenOfMultiChildFacet() {
        WebPage page = getPage("issue3092.xhtml");

        assertNotNull(page.findElement(By.id("renderFacet:notfound")), "first child of the facet is rendered");
        assertEquals("Faces Component", page.findElement(By.id("renderFacet:sibling")).getText(),
                "sibling child of the facet is rendered");
    }
}
