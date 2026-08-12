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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue2895IT extends BaseITNG {

    /**
     * Facelets delegates expression parsing to the Jakarta Expression Language implementation, so an
     * assignment expression with a set literal in inline template text is parsed and evaluated rather than
     * mistaken for template text delimiters.
     *
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2895
     */
    @Test
    void assignmentExpressionInInlineTextIsEvaluated() {
        WebPage page = getPage("issue2895.xhtml");

        assertEquals(200, page.getResponseStatus(), "The view must render.");
        assertEquals("[1, 2]", page.findElement(By.id("assignment")).getText(),
                "The assignment expression must evaluate to the assigned set.");
    }
}
