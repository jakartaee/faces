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

import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.el.ValueExpression;
import jakarta.el.ValueReference;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue4163IT extends BaseITNG {

    /**
     * @see ValueExpression
     * @see ValueReference
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4163
     */
    @Test
    void testValueExpressionReference() throws Exception {
        WebPage page = getPage("issue4163.xhtml");
        assertTrue(page.containsText("value expression base: ee.jakarta.tck.faces.faces23.el.Issue4163Bean"));
        assertTrue(page.containsText("value expression property: hello"));
    }
}
