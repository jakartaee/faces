/*
 * Copyright (c) Contributors to Eclipse Foundation.
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

package ee.jakarta.tck.faces.faces23.facelets;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.faces.component.html.HtmlDataTable;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue4128IT extends BaseITNG {

    /**
     * A command button bound into a dataTable via component binding must have its action invoked
     * exactly once per click. A Facelets-refresh regression recreated the bound subtree during the
     * postback build instead of reusing it, registering the action listener twice so it fired twice
     * per click. Each click must advance the counter by exactly one.
     *
     * @see HtmlDataTable
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4128
     */
    @Test
    void boundDataTableActionFiresOncePerClick() {
        WebPage page = getPage("issue4128.xhtml");
        assertEquals("0", page.findElement(By.id("form:count")).getText());

        page.guardHttp(page.findElement(By.id("form:table:add"))::click);
        assertEquals("1", page.findElement(By.id("form:count")).getText());

        page.guardHttp(page.findElement(By.id("form:table:add"))::click);
        assertEquals("2", page.findElement(By.id("form:count")).getText());

        page.guardHttp(page.findElement(By.id("form:table:add"))::click);
        assertEquals("3", page.findElement(By.id("form:count")).getText());
    }
}
