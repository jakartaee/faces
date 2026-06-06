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

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * Flow node-to-node navigation matrix: a flow-call node navigates into child flows
 * that start respectively from a view node, a method-call node, a switch node, a
 * return node, and another flow-call node. Each path must end on the destination view.
 */
class FlowCallNaviToOtherNodesIT extends BaseITNG {

    private static final String DESTINATION = "Great! You are now in the correct destination view.";

    /**
     * @see jakarta.faces.flow.Flow
     */
    @Test
    void testFlowCallNaviToView() {
        navigateAndAssert("navigate_to_view_node");
    }

    /**
     * @see jakarta.faces.flow.Flow
     */
    @Test
    void testFlowCallNaviToMethodCall() {
        navigateAndAssert("navigate_to_method_call_node");
    }

    /**
     * @see jakarta.faces.flow.Flow
     */
    @Test
    void testFlowCallNaviToSwitch() {
        navigateAndAssert("navigate_to_switch");
    }

    /**
     * @see jakarta.faces.flow.Flow
     */
    @Test
    void testFlowCallNaviToReturn() {
        navigateAndAssert("navigate_to_return");
    }

    /**
     * @see jakarta.faces.flow.Flow
     */
    @Test
    void testFlowCallNaviToFlowCall() {
        navigateAndAssert("navigate_to_flow_call");
    }

    private void navigateAndAssert(String childNavigationButtonId) {
        WebPage page = getPage("index.xhtml");
        page.guardHttp(() -> page.findElement(By.id("go_to_start_from_flow_call_node")).click());
        page.guardHttp(() -> page.findElement(By.id(childNavigationButtonId)).click());
        assertTrue(page.containsText(DESTINATION), DESTINATION);
    }
}
