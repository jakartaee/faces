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

package ee.jakarta.tck.faces.faces23.misc_additive_config;

import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.component.html.HtmlOutputFormat;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue2115IT extends BaseITNG {

    /**
     * The {@code converter} attribute of {@code h:outputFormat} must be applied to the formatted
     * value (after {@code f:param} substitution), not bypassed. The converted output of cases with a
     * converter must carry the converter's prefix, while cases without a converter render the plain
     * formatted value.
     *
     * @see HtmlOutputFormat
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2115
     */
    @Test
    void testOutputFormatMessages() {
        WebPage page = getPage("Issue2115.xhtml");
        assertTrue(page.containsText("MyConverter.getAsString Called: MESSAGE 1 MyConverter.getAsString Called: MESSAGE 2 MYPARAM MESSAGE 3 MESSAGE 4 MYPARAM"),
                "all four outputFormat cases render in order with converter applied to cases 1 and 2");
    }

    /**
     * Case 1: a literal message with a converter renders the converter-prefixed value.
     *
     * @see HtmlOutputFormat
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2115
     */
    @Test
    void testOutputFormatMessage1() {
        WebPage page = getPage("Issue2115.xhtml");
        assertTrue(page.containsText("MESSAGE 1 MyConverter.getAsString"), "case 1 is converted");
    }

    /**
     * Case 2: a message with an {@code f:param} substitution and a converter renders the substituted
     * value (the converter prefix from case 1 precedes it).
     *
     * @see HtmlOutputFormat
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2115
     */
    @Test
    void testOutputFormatMessage2() {
        WebPage page = getPage("Issue2115.xhtml");
        assertTrue(page.containsText("MESSAGE 2 MYPARAM"), "case 2 substitutes the param");
    }

    /**
     * Case 3: a literal message with no converter renders verbatim.
     *
     * @see HtmlOutputFormat
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2115
     */
    @Test
    void testOutputFormatMessage3() {
        WebPage page = getPage("Issue2115.xhtml");
        assertTrue(page.containsText("MESSAGE 3"), "case 3 renders verbatim");
    }

    /**
     * Case 4: a message with an {@code f:param} substitution and no converter substitutes the param.
     *
     * @see HtmlOutputFormat
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2115
     */
    @Test
    void testOutputFormatMessage4() {
        WebPage page = getPage("Issue2115.xhtml");
        assertTrue(page.containsText("MESSAGE 4 MYPARAM"), "case 4 substitutes the param without a converter");
    }
}
