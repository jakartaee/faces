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

class Issue3139IT extends BaseITNG {

    /**
     * Curly braces inside a string literal of an inline expression, written either literally or as character entities, belong to the literal and must not be
     * taken for expression delimiters.
     *
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3139
     */
    @Test
    void bracesInsideStringLiteralsAreParsed() {
        WebPage page = getPage("issue3139.xhtml");

        assertEquals(200, page.getResponseStatus(), "The view must render.");
        assertEquals("{0}", page.findElement(By.id("plain")).getText(), "literal braces");
        assertEquals("{", page.findElement(By.id("openingEntity")).getText(), "opening brace entity");
        assertEquals("}", page.findElement(By.id("closingEntity")).getText(), "closing brace entity");
        assertEquals("{0}", page.findElement(By.id("bothEntities")).getText(), "both brace entities");
        assertEquals(
            "Answer: Expression Language", page.findElement(By.id("formatted")).getText(),
            "A pattern containing braces must survive being passed as a method argument."
        );
    }

}
