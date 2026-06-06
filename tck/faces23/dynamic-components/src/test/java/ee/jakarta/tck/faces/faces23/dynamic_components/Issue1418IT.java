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
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * A child component moved programmatically between panel groups during an action listener must
 * persist across postbacks, toggling back and forth on each submit.
 */
public class Issue1418IT extends BaseITNG {

    private static int childCount(WebPage page, String id) {
        return page.findElement(By.id(id)).findElements(By.xpath("./*")).size();
    }

    /**
     * @see jakarta.faces.component.UIComponent#getChildren()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/1418
     */
    @Test
    void testTemporaryMove() throws Exception {
        WebPage page = getPage("issue1418.xhtml");

        assertTrue(childCount(page, "form:movefrom") > 0);
        assertEquals(0, childCount(page, "form:moveto"));

        WebElement button = page.findElement(By.id("form:submit"));
        page.guardHttp(button::click);
        assertEquals(0, childCount(page, "form:movefrom"));
        assertTrue(childCount(page, "form:moveto") > 0);

        button = page.findElement(By.id("form:submit"));
        page.guardHttp(button::click);
        assertTrue(childCount(page, "form:movefrom") > 0);
        assertEquals(0, childCount(page, "form:moveto"));

        button = page.findElement(By.id("form:submit"));
        page.guardHttp(button::click);
        assertEquals(0, childCount(page, "form:movefrom"));
        assertTrue(childCount(page, "form:moveto") > 0);

        button = page.findElement(By.id("form:submit"));
        page.guardHttp(button::click);
        assertTrue(childCount(page, "form:movefrom") > 0);
        assertEquals(0, childCount(page, "form:moveto"));
    }
}
