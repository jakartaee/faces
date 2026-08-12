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

package ee.jakarta.tck.faces.faces23.cdi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Spec1328IT extends BaseITNG {

    /**
     * The session implicit object resolves through the CDI resolver chain to the HttpSession of the current
     * request, so it can be dereferenced like any other bean.
     *
     * @see https://github.com/jakartaee/faces/issues/1328
     */
    @Test
    void sessionImplicitObjectResolvesThroughCdi() {
        WebPage page = getPage("spec1328.xhtml");

        assertEquals(200, page.getResponseStatus(), "The view must render.");
        assertFalse(page.findElement(By.id("session")).getText().isEmpty(),
                "The session implicit object must resolve.");
        assertFalse(page.findElement(By.id("sessionId")).getText().isEmpty(),
                "The resolved session must be an HttpSession, so its id is readable.");
    }
}
