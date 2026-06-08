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

import static java.util.regex.Pattern.matches;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.component.UIComponent;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * Tests that {@code #{cc.clientId}} and {@code #{cc.attrs}} resolve to the correct
 * composite component context through nested composites.
 *
 * The outer composite (composite1) nests an inner composite (composite2). Within
 * composite1, {@code cc} must refer to composite1; within composite2 it must change to
 * composite2; and after composite2 it must change back to composite1. The using page
 * instantiates composite1 twice to verify the same behaviour at the top level.
 */
class Spec1383IT extends BaseITNG {

    /**
     * @see UIComponent
     * @see https://github.com/jakartaee/faces/issues/1383
     */
    @Test
    void testCompositeComponentNesting() throws Exception {
        WebPage page = getPage("spec1383.xhtml");

        assertTrue(
            matches("(?s)" +
                ".*clientID1:component1.*Parameter1:test1" +
                    ".*clientID2:component1:componentA.*Parameter2:testA" +
                ".*clientID1:component1.*Parameter1:test1.*" +

                ".*clientID1:component2.*Parameter1:test2" +
                    ".*clientID2:component2:componentA.*Parameter2:testA" +
                ".*clientID1:component2.*Parameter1:test2.*"
                ,
            page.getSource()));
    }

}
