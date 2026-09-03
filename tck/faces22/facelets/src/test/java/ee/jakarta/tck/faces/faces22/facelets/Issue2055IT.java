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

package ee.jakarta.tck.faces.faces22.facelets;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;

class Issue2055IT extends BaseITNG {

    private static final String DEBUG_MARKER = "faceletsDebug";

    /**
     * The VDL documentation of ui:debug states that the debug component is not rendered in the page when the rendered attribute evaluates to false, and
     * rendered otherwise.
     *
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2055
     */
    @Test
    void debugIsEmittedByDefault() {
        assertTrue(
            getPage("issue2055-rendered.xhtml").containsSource(DEBUG_MARKER),
            "ui:debug must emit its debug output by default."
        );
    }

    /**
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2055
     */
    @Test
    void debugIsSuppressedWhenNotRendered() {
        assertFalse(
            getPage("issue2055-not-rendered.xhtml").containsSource(DEBUG_MARKER),
            "ui:debug must emit nothing when rendered is false."
        );
    }

}
