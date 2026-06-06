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
 * Flow node-to-node navigation matrix. Each source flow is entered from index.xhtml starting respectively
 * from a view node, a method-call node, a switch node and a return node, then navigates to each
 * destination node type (view, method-call, switch, return, flow-call). A successful traversal lands on
 * the destination view; a flow-return lands outside any flow with an empty flow scope.
 */
class FlowNodeNavigationIT extends BaseITNG {

    private static final String DESTINATION = "Great! You are now in the correct destination view.";
    private static final String RETURNED_NO_FLOW = "Has a flow: false.";
    private static final String RETURNED_EMPTY_FLOW_SCOPE = "flowScope value, should be empty: .";

    // --- view node as source ---

    /**
     * @see jakarta.faces.flow.Flow
     */
    @Test
    void testViewNaviToView() {
        WebPage page = enterViewFlow();
        page.guardHttp(() -> page.findElement(By.id("go_to_destination_view")).click());
        assertDestination(page);
    }

    /**
     * @see jakarta.faces.flow.Flow
     */
    @Test
    void testViewNaviToMethodCall() {
        WebPage page = enterViewFlow();
        page.guardHttp(() -> page.findElement(By.id("go_to_destination_method_call")).click());
        assertDestination(page);
    }

    /**
     * @see jakarta.faces.flow.Flow
     */
    @Test
    void testViewNaviToSwitch() {
        WebPage page = enterViewFlow();
        page.guardHttp(() -> page.findElement(By.id("go_to_destination_switch")).click());
        assertDestination(page);
    }

    /**
     * @see jakarta.faces.flow.Flow
     */
    @Test
    void testViewNaviToReturn() {
        WebPage page = enterViewFlow();
        page.guardHttp(() -> page.findElement(By.id("go_to_destination_return")).click());
        assertReturnedOutsideFlow(page);
    }

    /**
     * @see jakarta.faces.flow.Flow
     */
    @Test
    void testViewNaviToFlowCall() {
        WebPage page = enterViewFlow();
        page.guardHttp(() -> page.findElement(By.id("go_to_destination_flow_call")).click());
        assertDestination(page);
    }

    // --- method-call node as source ---

    /**
     * @see jakarta.faces.flow.Flow
     */
    @Test
    void testMethodCallNaviToMethodCall() {
        WebPage page = enterMethodCallFlow();
        page.guardHttp(() -> page.findElement(By.id("navigate_to_method_call_node")).click());
        assertDestination(page);
    }

    /**
     * @see jakarta.faces.flow.Flow
     */
    @Test
    void testMethodCallNaviToView() {
        WebPage page = enterMethodCallFlow();
        page.guardHttp(() -> page.findElement(By.id("navigate_to_view_node")).click());
        assertDestination(page);
    }

    /**
     * @see jakarta.faces.flow.Flow
     */
    @Test
    void testMethodCallNaviToSwitch() {
        WebPage page = enterMethodCallFlow();
        page.guardHttp(() -> page.findElement(By.id("navigate_to_switch")).click());
        assertDestination(page);
    }

    /**
     * @see jakarta.faces.flow.Flow
     */
    @Test
    void testMethodCallNaviToReturn() {
        WebPage page = enterMethodCallFlow();
        page.guardHttp(() -> page.findElement(By.id("navigate_to_return")).click());
        assertReturnedOutsideFlow(page);
    }

    /**
     * @see jakarta.faces.flow.Flow
     */
    @Test
    void testMethodCallNaviToFlowCall() {
        WebPage page = enterMethodCallFlow();
        page.guardHttp(() -> page.findElement(By.id("navigate_to_flow_call")).click());
        assertDestination(page);
    }

    // --- switch node as source ---

    /**
     * @see jakarta.faces.flow.Flow
     */
    @Test
    void testSwitchNaviToMethodCall() {
        assertDestination(switchNaviTo(0));
    }

    /**
     * @see jakarta.faces.flow.Flow
     */
    @Test
    void testSwitchNaviToView() {
        assertDestination(switchNaviTo(1));
    }

    /**
     * @see jakarta.faces.flow.Flow
     */
    @Test
    void testSwitchNaviToSwitch() {
        assertDestination(switchNaviTo(2));
    }

    /**
     * @see jakarta.faces.flow.Flow
     */
    @Test
    void testSwitchNaviToReturn() {
        assertReturnedOutsideFlow(switchNaviTo(3));
    }

    /**
     * @see jakarta.faces.flow.Flow
     */
    @Test
    void testSwitchNaviToFlowCall() {
        assertDestination(switchNaviTo(4));
    }

    // --- return node as source ---

    /**
     * @see jakarta.faces.flow.Flow
     */
    @Test
    void testReturnNaviToMethodCall() {
        assertDestination(returnNaviTo("MethodCallNodeToBeCalled"));
    }

    /**
     * @see jakarta.faces.flow.Flow
     */
    @Test
    void testReturnNaviToView() {
        assertDestination(returnNaviTo("ViewNodeToBeCalled"));
    }

    /**
     * @see jakarta.faces.flow.Flow
     */
    @Test
    void testReturnNaviToSwitch() {
        assertDestination(returnNaviTo("SwitchNodeToBeCalled"));
    }

    /**
     * @see jakarta.faces.flow.Flow
     */
    @Test
    void testReturnNaviToReturn() {
        assertReturnedOutsideFlow(returnNaviTo("ReturnNodeToBeCalled"));
    }

    /**
     * @see jakarta.faces.flow.Flow
     */
    @Test
    void testReturnNaviToFlowCall() {
        assertDestination(returnNaviTo("FlowCallNodeToBeCalled"));
    }

    // --- helpers ---

    private WebPage enterViewFlow() {
        WebPage page = getPage("index.xhtml");
        page.guardHttp(() -> page.findElement(By.id("go_to_view_to_view_flow")).click());
        return page;
    }

    private WebPage enterMethodCallFlow() {
        WebPage page = getPage("index.xhtml");
        page.guardHttp(() -> page.findElement(By.id("go_to_start_from_method_call_flow")).click());
        return page;
    }

    private WebPage switchNaviTo(int radioIndex) {
        WebPage page = getPage("index.xhtml");
        page.guardHttp(() -> page.findElement(By.id("go_to_start_from_switch_flow")).click());
        page.findElement(By.id("select_destination_node:" + radioIndex)).click();
        page.guardHttp(() -> page.findElement(By.id("navigate_to_initial_swtich_node")).click());
        return page;
    }

    private WebPage returnNaviTo(String parentNodeToBeReturned) {
        WebPage page = getPage("index.xhtml");
        page.guardHttp(() -> page.findElement(By.id("go_to_start_from_return_flow")).click());
        page.guardHttp(() -> page.findElement(By.id("go_to_initial_return_node_flow")).click());
        page.findElement(By.id("parent-node-to-be-returned")).sendKeys(parentNodeToBeReturned);
        page.guardHttp(() -> page.findElement(By.id("return-to-method-call-node")).click());
        return page;
    }

    private void assertDestination(WebPage page) {
        assertTrue(page.containsText(DESTINATION), DESTINATION);
    }

    private void assertReturnedOutsideFlow(WebPage page) {
        assertTrue(page.containsText(RETURNED_NO_FLOW), RETURNED_NO_FLOW);
        assertTrue(page.containsText(RETURNED_EMPTY_FLOW_SCOPE), RETURNED_EMPTY_FLOW_SCOPE);
    }
}
