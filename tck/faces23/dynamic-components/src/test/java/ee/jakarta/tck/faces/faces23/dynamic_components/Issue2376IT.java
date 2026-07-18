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

/**
 * A child inserted at an explicit index must land in between the pre-existing static children
 * instead of being appended, both on the initial render and after a postback.
 */
public class Issue2376IT extends BaseITNG {

    private static final String EXPECTED_ORDER = "Static Text Dynamic Text Static Text";

    /**
     * The dynamic child added at index 1 renders in between both static children, and the index is
     * honored again after a postback.
     *
     * @see jakarta.faces.component.UIComponent#getChildren()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2376
     */
    @Test
    void testDynamicChildAtCorrectIndex() {
        WebPage page = getPage("issue2376.xhtml");
        assertEquals(EXPECTED_ORDER, page.findElement(By.id("form:children")).getText(), "initial render");

        page.guardHttp(page.findElement(By.id("form:button"))::click);
        assertEquals(EXPECTED_ORDER, page.findElement(By.id("form:children")).getText(), "after postback");
    }
}
