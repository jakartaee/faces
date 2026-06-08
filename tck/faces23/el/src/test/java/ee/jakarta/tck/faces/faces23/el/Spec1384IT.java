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

package ee.jakarta.tck.faces.faces23.el;

import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.component.UIComponent;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * Tests both build time and render time availability of the correct
 * {@code #{component}} value.
 *
 * component1 wraps componentA, so this tests that inside component1 but before
 * componentA, {@code #{component}} refers to component1. Inside componentA,
 * {@code #{component}} needs to change to componentA, and directly outside
 * componentA but still inside component1 it needs to change back to component1.
 *
 * This therefore tests that {@code #{component}} is able to change to deeper
 * stacked components and able to move back when that stack unwinds. The test is
 * repeated to verify that the same changes are possible at the top level.
 */
class Spec1384IT extends BaseITNG {

    /**
     * @see UIComponent
     * @see https://github.com/jakartaee/faces/issues/1384
     */
    @Test
    void testComponentStackTracking() throws Exception {
        WebPage page = getPage("spec1384.xhtml");
        String source = page.getSource();

        int id11 = source.indexOf("id1-1:component1");
        int id21 = source.indexOf("id2-1:componentA");
        int id12 = source.indexOf("id1-2:component1");

        int id13 = source.indexOf("id1-3:component2");
        int id22 = source.indexOf("id2-2:componentA");
        int id14 = source.indexOf("id1-4:component2");

        assertTrue(
            id11 > -1 &&
            id21 > id11 &&
            id12 > id21 &&
            id13 > id12 &&
            id22 > id13 &&
            id14 > id22
        );
    }

}
