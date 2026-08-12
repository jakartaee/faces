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

package ee.jakarta.tck.faces.faces22.composite_component;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue2051IT extends BaseITNG {

    /**
     * A converterId supplied as a composite component attribute, evaluated by both the converterId and the
     * rendered attribute of a nested f:converter, is applied to the enclosing h:selectManyListbox: the view
     * renders and the selection converts back to the model on postback.
     *
     * @see jakarta.faces.convert.Converter
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2051
     */
    @Test
    void converterIdFromCompositeAttributeIsApplied() {
        WebPage page = getPage("issue2051.xhtml");

        assertEquals(200, page.getResponseStatus(), "initial render");

        new Select(page.findElement(By.id("form:composite:listBox"))).selectByVisibleText("Second");
        page.guardHttp(page.findElement(By.id("form:submit"))::click);

        assertEquals(200, page.getResponseStatus(), "postback");
        assertEquals("Second", page.findElement(By.id("form:selected")).getText(),
                "The selection must have been converted back into the model.");
    }
}
