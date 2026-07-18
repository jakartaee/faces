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

import static java.util.Collections.nCopies;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue1962IT extends BaseITNG {

    private static final int USAGES = 8;

    /**
     * The same cc:renderFacet-bearing composite used repeatedly within one form must render every
     * usage's facets, in order. Each usage is a distinct NamingContainer, so the facet children may
     * reuse component ids without colliding.
     *
     * @see jakarta.faces.component.UINamingContainer
     * @see https://github.com/eclipse-ee4j/mojarra/issues/1962
     */
    @Test
    void repeatedRenderFacetUsagesRenderWithoutDuplicateId() {
        WebPage page = getPage("issue1962.xhtml");

        List<String> expected = nCopies(USAGES, List.of("col1Link", "col2Link")).stream().flatMap(List::stream).toList();
        List<String> actual = page.getAnchors().stream().map(WebElement::getText).toList();

        assertEquals(expected, actual, "every usage renders both facets in order");
    }
}
