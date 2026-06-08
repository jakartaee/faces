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
 * A flow-call from one flow into another must resolve regardless of whether the calling and called flows declare
 * inbound or outbound parameters. Each case enters a flow-a variant from outside any flow and then performs a
 * flow-call into flow-b, which declares neither inbound nor outbound parameters.
 */
class FlowCallWithParametersIT extends BaseITNG {

    private static final String CALLEE_FLOW = "flow-b-no-inbound-no-outbound";

    /**
     * Caller and callee both declare no inbound and no outbound parameters.
     *
     * @see jakarta.faces.flow.Flow
     */
    @Test
    void testFlowANoInboundNoOutboundCallsFlowBNoInboundNoOutbound() {
        enterFlowAndCallB("flow-a-no-inbound-no-outbound");
    }

    /**
     * Caller declares an inbound parameter; callee declares none.
     *
     * @see jakarta.faces.flow.Flow
     */
    @Test
    void testFlowAYesInboundNoOutboundCallsFlowBNoInboundNoOutbound() {
        enterFlowAndCallB("flow-a-yes-inbound-no-outbound");
    }

    /**
     * Caller declares an outbound parameter; callee declares none.
     *
     * @see jakarta.faces.flow.Flow
     */
    @Test
    void testFlowANoInboundYesOutboundCallsFlowBNoInboundNoOutbound() {
        enterFlowAndCallB("flow-a-no-inbound-yes-outbound");
    }

    private void enterFlowAndCallB(String callerFlow) {
        WebPage page = getPage("index.xhtml");
        assertTrue(page.containsText("Outside of flow"), "Outside of flow");
        page.guardHttp(() -> page.findElement(By.id(callerFlow)).click());
        assertTrue(page.containsText(callerFlow), callerFlow);
        page.guardHttp(() -> page.findElement(By.id("callB")).click());
        assertTrue(page.containsText(CALLEE_FLOW), CALLEE_FLOW);
    }
}
