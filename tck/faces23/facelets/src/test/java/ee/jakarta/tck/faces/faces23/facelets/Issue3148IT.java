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

package ee.jakarta.tck.faces.faces23.facelets;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * When a view renders several {@code h:form}s, each rendered {@code jakarta.faces.ViewState} hidden
 * field must carry a unique {@code id}. The id is suffixed with a per-view counter that increments
 * for every form, so three forms yield the suffixes {@code :0}, {@code :1} and {@code :2}.
 */
class Issue3148IT extends BaseITNG {

    /**
     * @see jakarta.faces.render.ResponseStateManager#VIEW_STATE_PARAM
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3148
     */
    @Test
    void testDuplicateId() {
        WebPage page = getPage("issue3148.xhtml");
        assertTrue(page.containsSource("jakarta.faces.ViewState:0"), "first form's ViewState id");
        assertTrue(page.containsSource("jakarta.faces.ViewState:1"), "second form's ViewState id");
        assertTrue(page.containsSource("jakarta.faces.ViewState:2"), "third form's ViewState id");
    }
}
