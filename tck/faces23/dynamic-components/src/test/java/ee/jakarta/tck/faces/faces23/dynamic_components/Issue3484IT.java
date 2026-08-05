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

package ee.jakarta.tck.faces.faces23.dynamic_components;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue3484IT extends BaseITNG {

    /**
     * Removing a Facelets created child and adding it straight back at the same index is a no-op: the child
     * keeps its identity and its position, and the view still restores on the next postback.
     *
     * @see jakarta.faces.component.UIComponent#getChildren()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3484
     */
    @Test
    void removeAndReAddAtSameIndexLeavesTheViewUnchanged() {
        WebPage page = getPage("issue3484.xhtml");

        assertEquals("0", page.findElement(By.id("index")).getText(), "index on initial render");
        assertEquals("facelets created child", page.findElement(By.id("form:outputText")).getText(),
                "child on initial render");

        page.guardHttp(page.findElement(By.id("form:submit"))::click);

        assertEquals("0", page.findElement(By.id("index")).getText(), "index after remove and re-add");
        assertEquals("facelets created child", page.findElement(By.id("form:outputText")).getText(),
                "child after remove and re-add");

        page.guardHttp(page.findElement(By.id("form:submit"))::click);

        assertEquals("0", page.findElement(By.id("index")).getText(), "index after a second postback");
        assertEquals("facelets created child", page.findElement(By.id("form:outputText")).getText(),
                "child after a second postback");
    }
}
