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

package ee.jakarta.tck.faces.faces23.resource_library_contracts;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * Dynamic, EL-based resource library contract selection via the {@code f:view
 * contracts} attribute. The selected contract supplies a template (layout2) or
 * an included header (layout1); an unknown value (layout3) falls back to the
 * webapp's own template and header.
 */
class Issue2658IT extends BaseITNG {

    /**
     * @see jakarta.faces.component.UIViewRoot
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2658
     */
    @Test
    void testLayout1() throws Exception {
        WebPage page = getPage("Issue2658.xhtml?style=layout1");
        assertEquals("layout1", page.getTitle());
    }

    /**
     * @see jakarta.faces.component.UIViewRoot
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2658
     */
    @Test
    void testLayout2() throws Exception {
        WebPage page = getPage("Issue2658.xhtml?style=layout2");
        assertEquals("layout2", page.getTitle());
    }

    /**
     * @see jakarta.faces.component.UIViewRoot
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2658
     */
    @Test
    void testLayout3() throws Exception {
        WebPage page = getPage("Issue2658.xhtml?style=layout3");
        assertEquals("layout3", page.getTitle());
    }
}
