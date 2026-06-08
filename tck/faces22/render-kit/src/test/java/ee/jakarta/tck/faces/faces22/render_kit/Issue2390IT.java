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

package ee.jakarta.tck.faces.faces22.render_kit;

import static org.junit.jupiter.api.Assertions.assertFalse;

import jakarta.faces.component.html.HtmlOutcomeTargetLink;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;

/**
 * Verifies that a disabled h:link renders as a plain span without an href or a disabled attribute.
 */
class Issue2390IT extends BaseITNG {

    /**
     * A disabled {@link HtmlOutcomeTargetLink} must render as a span element carrying neither an href nor a
     * disabled attribute, so the rendered output contains no "disabled" token at all.
     *
     * @see HtmlOutcomeTargetLink
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2390
     */
    @Test
    void testDisabledLink() throws Exception {
        String body = getResponseBody("issue2390.xhtml");
        assertFalse(body.contains("disabled"), "Disabled h:link must not render a disabled attribute");
    }
}
