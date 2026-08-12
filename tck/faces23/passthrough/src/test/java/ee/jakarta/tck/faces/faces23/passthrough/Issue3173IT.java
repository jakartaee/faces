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

package ee.jakarta.tck.faces.faces23.passthrough;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;
import jakarta.faces.view.facelets.TagDecorator;

class Issue3173IT extends BaseITNG {

    /**
     * An input element decorated with jsf:id is a void element, so promoting it to a component must not
     * emit a closing tag for it, which would nest the following markup inside it.
     *
     * @see TagDecorator
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3173
     */
    @Test
    void promotedVoidElementGetsNoClosingTag() {
        WebPage page = getPage("issue3173.xhtml");

        assertEquals(200, page.getResponseStatus(), "The view must render.");
        assertFalse(page.getSource().contains("</input"), "A void element must not be given a closing tag.");
        assertEquals(Issue3173Bean.VALUE, page.findElement(By.id("date")).getDomAttribute("value"),
                "The promoted input must keep its value.");
        assertEquals("Before", page.findElement(By.id("before")).getText(), "button before the input");
        assertEquals("After", page.findElement(By.id("after")).getText(), "button after the input");
    }
}
