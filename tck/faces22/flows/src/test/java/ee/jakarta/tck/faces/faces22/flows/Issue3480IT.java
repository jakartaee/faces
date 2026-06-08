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
 * An outbound parameter passed by a flow-call node must arrive as the inbound parameter of a
 * called flow whose start node is a method-call node, and be readable from the flow scope.
 */
class Issue3480IT extends BaseITNG {

    /**
     * @see jakarta.faces.flow.Flow#getInboundParameters()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3480
     */
    @Test
    void testFlowCallNaviToMethodCallWithCorrectParameter() {
        WebPage page = getPage("index.xhtml");
        page.guardHttp(() -> page.findElement(By.id("go_to_issue3480_start_from_flow_call_node")).click());
        page.guardHttp(() -> page.findElement(By.id("navigate_to_method_call_node")).click());
        assertTrue(page.containsText("The inbound parameter should be ExpectedString"),
                "inbound parameter delivered to the method-call started child flow");
    }
}
