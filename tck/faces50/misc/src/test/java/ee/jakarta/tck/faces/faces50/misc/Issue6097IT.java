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
package ee.jakarta.tck.faces.faces50.misc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;

class Issue6097IT extends BaseITNG {

    private static final Pattern TEXT_ELEMENT = Pattern.compile("<span id=\"text\"[^>]*>");

    /**
     * When a standard HTML component gets an attribute set and then another one bound after the view was built, then each attribute must be rendered once. The
     * raw response is asserted, since a browser keeps only one of two equal attributes.
     *
     * @see https://github.com/eclipse-ee4j/mojarra/issues/6097
     */
    @Test
    void testAttributesChangedAfterBuildAreRenderedOnce() {
        Matcher text = TEXT_ELEMENT.matcher(getResponseBody("issue6097.xhtml"));
        assertTrue(text.find(), "text element is rendered");

        String element = text.group();
        assertEquals(1, countAttribute(element, "class"), element);
        assertEquals(1, countAttribute(element, "title"), element);
        assertTrue(element.contains(" class=\"changed\""), element);
        assertTrue(element.contains(" title=\"Title\""), element);
    }

    private static long countAttribute(String element, String name) {
        return Pattern.compile("\\s" + name + "=\"").matcher(element).results().count();
    }

}
