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

package ee.jakarta.tck.faces.faces22.empty_as_null;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.component.UIViewParameter;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue3062IT extends BaseITNG {

    /**
     * @see UIViewParameter
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3062
     */
    @Test
    void testViewParamRequiredPositive() throws Exception {
        WebPage page = getPage("issue3062.xhtml?input1=test");
        assertFalse(page.containsText("INPUT1 HAS NO VALUE!"));
    }

    /**
     * @see UIViewParameter
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3062
     */
    @Test
    void testViewParamRequiredNegative() throws Exception {
        WebPage page = getPage("issue3062.xhtml");
        assertTrue(page.containsText("INPUT1 HAS NO VALUE!"));
    }
}
