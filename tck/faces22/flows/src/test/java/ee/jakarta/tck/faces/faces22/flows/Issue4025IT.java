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
 * Variables placed in the flow scope must be revoked when the flow is exited, and an
 * inner (nested) flow's flow scope must be isolated from its outer flow's flow scope.
 */
class Issue4025IT extends BaseITNG {

    /**
     * @see jakarta.faces.flow.FlowHandler#getCurrentFlowScope()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4025
     */
    @Test
    void testVariablesInFlowMapRevokedAfterFlowExit() {
        WebPage page = getPage("index.xhtml");
        page.guardHttp(() -> page.findElement(By.id("go_to_flow_for_issue4025")).click());
        page.guardHttp(() -> page.findElement(By.id("init_foo_id")).click());
        page.guardHttp(() -> page.findElement(By.id("next_id")).click());
        assertTrue(page.containsText("foo:bar"), "foo:bar present inside the flow");

        page.guardHttp(() -> page.findElement(By.id("exit_id")).click());
        assertFalse(page.containsText("foo:bar"), "foo:bar revoked after flow exit");
    }

    /**
     * @see jakarta.faces.flow.FlowHandler#getCurrentFlowScope()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4025
     */
    @Test
    void testVariablesInFlowMapRevokedInNestedCase() {
        WebPage page = getPage("index.xhtml");
        page.guardHttp(() -> page.findElement(By.id("go_to_flow_for_issue4025")).click());
        page.guardHttp(() -> page.findElement(By.id("init_foo_id")).click());
        page.guardHttp(() -> page.findElement(By.id("next_id")).click());
        assertTrue(page.containsText("foo:bar"), "foo:bar present in outer flow");

        page.guardHttp(() -> page.findElement(By.id("call_nested_flow_id")).click());
        page.guardHttp(() -> page.findElement(By.id("nested_init_foo_id")).click());
        page.guardHttp(() -> page.findElement(By.id("nested_next_id")).click());
        assertTrue(page.containsText("foo:barx"), "inner flow has its own foo:barx");

        page.guardHttp(() -> page.findElement(By.id("nested_exit_id")).click());
        assertTrue(page.containsText("foo:bar"), "outer flow value restored after inner flow exit");
        assertFalse(page.containsText("foo:barx"), "inner flow value gone after inner flow exit");
    }
}
