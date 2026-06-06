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

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;

/**
 * A composed Facelet whose template declares a DOCTYPE still renders that DOCTYPE on a second reload of the
 * same view, i.e. the DOCTYPE persists across reloads.
 */
class Issue2579IT extends BaseITNG {

    private static final String PAGE = "issue2579.xhtml";
    private static final String DOCTYPE = "<!DOCTYPE html>";

    /**
     * Requests a view that composes a template declaring a DOCTYPE, then re-requests the same view, and verifies
     * the DOCTYPE is present in the rendered response on both requests.
     *
     * @see jakarta.faces.view.facelets.Facelet
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2579
     */
    @Test
    void testNotMissingDoctypeReload() throws Exception {
        assertTrue(getResponseBody(PAGE).contains(DOCTYPE), "DOCTYPE is rendered on first request");
        assertTrue(getResponseBody(PAGE).contains(DOCTYPE), "DOCTYPE is still rendered on reload");
    }
}
