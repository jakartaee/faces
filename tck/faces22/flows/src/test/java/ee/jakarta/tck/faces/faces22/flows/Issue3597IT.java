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
 * Navigation originating from a method-call node: from a method-call node the flow must be able to navigate to
 * another method-call node (chaining), to a flow-call node, and to a switch node. Each path must reach its
 * destination view.
 */
class Issue3597IT extends BaseITNG {

    /**
     * Method-call node chains into two further method-call nodes, the last of which resolves to the view at the
     * end of the method calls.
     *
     * @see jakarta.faces.flow.Flow
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3597
     */
    @Test
    void testMethodCallToMethodCall() {
        enterFlowAndAssert("method-call-button-02", "View node at end of method calls");
    }

    /**
     * Method-call node navigates to a flow-call node that enters the switch-start-node child flow.
     *
     * @see jakarta.faces.flow.Flow
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3597
     */
    @Test
    void testMethodCallToFlowCall() {
        enterFlowAndAssert("method-call-button-03", "First page in the switch-start-node flow");
    }

    /**
     * Method-call node navigates to a switch node that resolves to the view at the end of the method calls.
     *
     * @see jakarta.faces.flow.Flow
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3597
     */
    @Test
    void testMethodCallToSwitch() {
        enterFlowAndAssert("method-call-button-04", "View node at end of method calls");
    }

    private void enterFlowAndAssert(String inFlowButtonId, String expected) {
        WebPage page = getPage("index.xhtml");
        page.guardHttp(() -> page.findElement(By.id("go_to_issue3597_method_call_start_node")).click());
        page.guardHttp(() -> page.findElement(By.id(inFlowButtonId)).click());
        assertTrue(page.containsText(expected), expected);
    }
}
