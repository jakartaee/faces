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
package ee.jakarta.tck.faces.faces50.facelets;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;

class Spec1904IT extends BaseITNG {

    /**
     * An attribute declared as required is supplied where the tag is used, whether it is declared on a tag file in a tag library descriptor or on a composite
     * component, and both attributes reach it under the names they were declared with.
     *
     * @see https://github.com/jakartaee/faces/issues/1904
     */
    @Test
    void testDeclaredAttributesReachWhatDeclaresThem() {
        assertAll(
            () -> assertEquals(
                "[R|O]", getPage("spec1904Supplied.xhtml").findElement(By.id("supplied")).getText(),
                "a tag file declared in a tag library descriptor"
            ),
            () -> assertEquals(
                "[R|O]", getPage("spec1904CompositeSupplied.xhtml").findElement(By.id("compositeSupplied")).getText(),
                "a composite component"
            )
        );
    }

    /**
     * Omitting an attribute declared as required fails the page with a TagException identifying the tag and the attribute, rather than rendering it unset, in
     * every project stage. A composite component is identified by its own interface tag where the tag which used it is not recorded, which is everywhere but
     * the Development project stage.
     *
     * @see https://github.com/jakartaee/faces/issues/1904
     */
    @Test
    void testOmittingARequiredAttributeFails() {
        String tagFile = getResponseBody("spec1904Omitted.xhtml");
        String composite = getResponseBody("spec1904CompositeOmitted.xhtml");

        assertAll(
            () -> assertTrue(
                !tagFile.contains("[|O]") && tagFile.contains("t:tagFile") && tagFile.contains("requiredAttribute"),
                "a tag file declared in a tag library descriptor: " + tagFile
            ),
            () -> assertTrue(
                !composite.contains("[|O]") && composite.contains("spec1904RequiredAttribute") && composite.contains("requiredAttribute"),
                "a composite component: " + composite
            )
        );
    }

}
