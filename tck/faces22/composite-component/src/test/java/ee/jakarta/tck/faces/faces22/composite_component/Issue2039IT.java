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

class Issue2039IT extends BaseITNG {

    /**
     * A composite component attribute value supplied by the using page must be rendered.
     *
     * @see jakarta.faces.view.facelets.FaceletContext
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2039
     */
    @Test
    void attributeValueIsRendered() throws Exception {
        WebPage page = getPage("issue2039.xhtml");
        assertTrue(page.containsSource("myDescription"), "supplied attribute value is rendered");
    }

    /**
     * When a required composite component attribute is omitted, its declared default value must be used.
     *
     * @see jakarta.faces.view.facelets.FaceletContext
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2039
     */
    @Test
    void defaultValueIsUsedWhenAttributeOmitted() throws Exception {
        WebPage page = getPage("issue2039b.xhtml");
        assertTrue(page.containsSource("No Description"), "declared default value is used when attribute is omitted");
    }
}
