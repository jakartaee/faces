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

package ee.jakarta.tck.faces.faces23.build_time_component_handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue3982IT extends BaseITNG {

    /**
     * A composite component which a build time c:if adds to the view on a postback takes its place beside an
     * unconditional sibling composite without colliding with it: both keep their own ids and the tree stays
     * intact.
     *
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3982
     */
    @Test
    void conditionallyBuiltCompositeDoesNotCollideWithItsSibling() {
        WebPage page = getPage("issue3982.xhtml");

        assertEquals(200, page.getResponseStatus(), "initial render");
        assertEquals(1, page.findElements(By.id("form:panel2:grid")).size(), "the unconditional composite");
        assertEquals(0, page.findElements(By.id("form:panel1:grid")).size(), "the conditional composite is absent");

        page.guardHttp(page.findElement(By.id("form:click"))::click);

        assertEquals(200, page.getResponseStatus(), "postback");
        assertEquals(1, page.findElements(By.id("form:panel1:grid")).size(), "the conditional composite appeared");
        assertEquals(1, page.findElements(By.id("form:panel2:grid")).size(), "the unconditional composite survived");
    }
}
