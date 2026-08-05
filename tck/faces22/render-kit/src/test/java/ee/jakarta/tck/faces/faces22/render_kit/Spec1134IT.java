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

package ee.jakarta.tck.faces.faces22.render_kit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;
import jakarta.faces.component.html.HtmlPanelGrid;

class Spec1134IT extends BaseITNG {

    /**
     * The Grid renderer declares the ARIA role attribute, which it must write onto the generated table.
     *
     * @see HtmlPanelGrid#getRole()
     * @see https://github.com/jakartaee/faces/issues/1134
     */
    @Test
    void panelGridRendersItsRoleOntoTheTable() {
        WebPage page = getPage("spec1134.xhtml");

        assertEquals("table", page.findElement(By.id("grid")).getTagName(), "h:panelGrid renders a table");
        assertEquals("presentation", page.findElement(By.id("grid")).getDomAttribute("role"), "role attribute");
    }
}
