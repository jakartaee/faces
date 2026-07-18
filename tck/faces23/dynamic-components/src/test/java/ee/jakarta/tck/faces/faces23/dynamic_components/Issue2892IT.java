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
 * A component that MOVES an existing child into a sibling panel group must render the child in its
 * new parent, and the relocation must survive a postback.
 */
public class Issue2892IT extends BaseITNG {

    private static final String MOVED_INTO_PANEL = "FooBar";

    /**
     * The moved output text renders inside the panel group on the initial render and stays there
     * after a postback.
     *
     * @see jakarta.faces.component.UIComponent#getChildren()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2892
     */
    @Test
    void testMoveComponent() {
        WebPage page = getPage("issue2892.xhtml");
        assertEquals(MOVED_INTO_PANEL, page.findElement(By.id("form:panel")).getText(), "initial render");

        page.guardHttp(page.findElement(By.id("form:button"))::click);
        assertEquals(MOVED_INTO_PANEL, page.findElement(By.id("form:panel")).getText(), "after postback");
    }
}
