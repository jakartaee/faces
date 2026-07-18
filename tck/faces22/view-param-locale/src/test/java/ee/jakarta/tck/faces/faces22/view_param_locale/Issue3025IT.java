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
package ee.jakarta.tck.faces.faces22.view_param_locale;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue3025IT extends BaseITNG {

    /**
     * A converter attached to an {@code f:viewParam} that fails conversion produces a message
     * resolved against the active view locale: with the view forced to Italian, the Italian variant
     * of the {@code messages} bundle is used, not the default.
     *
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3025
     * @see jakarta.faces.component.UIViewParameter
     */
    @Test
    void testViewParamLocale() {
        WebPage page = getPage("viewParamLocale.xhtml?id=test");
        assertTrue(page.containsText("This ought to be Italian"), "Italian bundle message");
        assertFalse(page.containsText("Definitely not Italian"), "default bundle message");
    }
}
