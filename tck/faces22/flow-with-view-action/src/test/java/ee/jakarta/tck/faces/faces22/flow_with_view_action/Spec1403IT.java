/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
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
package ee.jakarta.tck.faces.faces22.flow_with_view_action;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Spec1403IT extends BaseITNG {

    /**
     * A flow entered via {@code f:viewAction} and exited via a {@code f:viewAction} that returns from
     * the flow leaves the flow scope inactive; a page rendered after the exit that still references a
     * flow-scoped bean must be handled gracefully (error page) rather than leaking a raw exception.
     *
     * @see https://github.com/jakartaee/faces/issues/1403
     * @see jakarta.faces.flow.FlowScoped
     */
    @Test
    void testFlowEntryExit() {
        WebPage page = getPage("index.xhtml");
        assertTrue(page.containsText("First page in the flow."), "First page in the flow.");
        assertTrue(page.containsText("flowA"), "flowA");

        page.guardHttp(page.findElement(By.id("form:flowA-next"))::click);
        assertFalse(page.containsText("flowA"), "flowA no longer present");
        assertTrue(page.containsText("Gracefully handle ContextNotActive"), "graceful error page");
    }

    /**
     * Entering a nested flow (flowB) from flowA and returning must restore flowA; exiting flowA
     * afterwards again yields the graceful ContextNotActive handling.
     *
     * @see https://github.com/jakartaee/faces/issues/1403
     * @see jakarta.faces.flow.FlowScoped
     */
    @Test
    void testNestedFlowEntryExit() {
        WebPage page = getPage("index.xhtml");
        assertTrue(page.containsText("First page in the flow."), "First page in the flow.");
        assertTrue(page.containsText("flowA"), "flowA");

        page.guardHttp(page.findElement(By.id("form:flowB"))::click);
        assertTrue(page.containsText("called from flowA"), "called from flowA");
        assertTrue(page.containsText("flowB"), "flowB");

        page.guardHttp(page.findElement(By.id("form:flowB-next"))::click);
        assertTrue(page.containsText("First page in the flow."), "back in flowA");
        assertTrue(page.containsText("flowA"), "flowA");

        page.guardHttp(page.findElement(By.id("form:flowA-next"))::click);
        assertFalse(page.containsText("flowA"), "flowA no longer present");
        assertTrue(page.containsText("Gracefully handle ContextNotActive"), "graceful error page");
    }

}
