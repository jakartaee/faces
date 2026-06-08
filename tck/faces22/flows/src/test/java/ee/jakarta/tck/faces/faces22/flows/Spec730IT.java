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
package ee.jakarta.tck.faces.faces22.flows;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * A bounded task flow can be entered from its valid start node, its flow-scoped bean is
 * visible on every page, a value put into the flow scope persists across the flow's pages,
 * and that value is gone after the flow returns.
 */
class Spec730IT extends BaseITNG {

    /**
     * @see jakarta.faces.flow.Flow
     * @see https://github.com/jakartaee/faces/issues/730
     */
    @Test
    void testFlowEntryExit() {
        WebPage page = getPage("index.xhtml");
        assertTrue(page.containsText("Outside of flow"), "outside of flow");

        page.guardHttp(() -> page.findElement(By.id("start")).click());
        assertTrue(page.containsText("First page in the flow"), "first page in the flow");
        assertTrue(page.containsText("basicFlow"), "flow bean name");
    }

    /**
     * @see jakarta.faces.flow.FlowHandler#getCurrentFlowScope()
     * @see https://github.com/jakartaee/faces/issues/730
     */
    @Test
    void testFacesFlowScope() {
        WebPage page = getPage("index.xhtml");
        page.guardHttp(() -> page.findElement(By.id("start")).click());
        assertTrue(page.containsText("First page in the flow"), "first page in the flow");
        assertTrue(page.containsText("basicFlow"), "flow bean name");

        page.guardHttp(() -> page.findElement(By.id("next_a")).click());

        String flowScopeValue = "Value in faces flow scope";
        page.findElement(By.id("input")).sendKeys(flowScopeValue);

        page.guardHttp(() -> page.findElement(By.id("next")).click());
        assertTrue(page.containsText(flowScopeValue), "flow scope value persisted");

        page.guardHttp(() -> page.findElement(By.id("return")).click());
        assertTrue(page.containsText("return page"), "returned out of flow");
        assertFalse(page.containsText(flowScopeValue), "flow scope value revoked after return");
    }
}
