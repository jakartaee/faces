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

package ee.jakarta.tck.faces.faces23.facelets;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

public class Issue3476IT extends BaseITNG {

    /**
     * An {@code h:selectManyCheckbox} fed by {@code SelectItemGroup}s must assign
     * indexed client-ids only to the actual options, skipping the group-label
     * index. With two groups of three items, the options occupy indices
     * 0,1,2 and 4,5,6 (index 3 is the second group label).
     *
     * @see jakarta.faces.component.html.HtmlSelectManyCheckbox
     * @see jakarta.faces.model.SelectItemGroup
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3476
     */
    @Test
    void selectManyCheckboxIndexedClientIds() throws Exception {
        WebPage page = getPage("issue3476-selectmanycheckboxmultigroups.xhtml");

        assertNotNull(page.findElement(By.id("form:selectManyCheckbox:0")));
        assertNotNull(page.findElement(By.id("form:selectManyCheckbox:1")));
        assertNotNull(page.findElement(By.id("form:selectManyCheckbox:2")));
        assertNotNull(page.findElement(By.id("form:selectManyCheckbox:4")));
        assertNotNull(page.findElement(By.id("form:selectManyCheckbox:5")));
        assertNotNull(page.findElement(By.id("form:selectManyCheckbox:6")));
    }
}
