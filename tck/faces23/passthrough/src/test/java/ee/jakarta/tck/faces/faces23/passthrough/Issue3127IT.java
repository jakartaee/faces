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

import jakarta.faces.component.UIComponent;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue3127IT extends BaseITNG {

    /**
     * A pass through attribute whose expression resolves to something other than a String is rendered by calling toString() on the value, rather than failing
     * the request.
     *
     * @see UIComponent#getPassThroughAttributes()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3127
     */
    @Test
    void nonStringPassThroughAttributeIsRenderedViaToString() {
        WebPage page = getPage("issue3127.xhtml");

        assertEquals(200, page.getResponseStatus(), "The view must render.");
        assertEquals(
            Issue3127Bean.DATE.toString(),
            page.findElement(By.id("form:input")).getDomAttribute("placeholder"),
            "A non-String pass through attribute value must be rendered via toString()."
        );
    }

}
