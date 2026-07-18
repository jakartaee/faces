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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * Re-entering a flow which was returned out of earlier in the same session starts with a fresh flow
 * scope, it does not carry over the state of the previous run.
 */
class Issue3867IT extends BaseITNG {

    private static final String FLOW_SCOPE_VALUE = "Value in faces flow scope";

    /**
     * Runs the bounded task flow to completion with a value put into the flow scope, returns out of
     * it and enters it again. The flow scope of the second run must not see the value of the first.
     *
     * @see jakarta.faces.flow.FlowHandler#getCurrentFlowScope()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3867
     */
    @Test
    void testFlowScopeIsResetOnReentry() {
        WebPage page = getPage("index.xhtml");
        enterFlowAndOpenInputPage(page);

        page.findElement(By.id("input")).sendKeys(FLOW_SCOPE_VALUE);
        page.guardHttp(page.findElement(By.id("next"))::click);
        page.guardHttp(page.findElement(By.id("return"))::click);

        page = getPage("index.xhtml");
        enterFlowAndOpenInputPage(page);
        assertEquals("", page.findElement(By.id("input")).getAttribute("value"),
                "flow scope value of the previous run of the flow is gone on re-entry");

        page.guardHttp(page.findElement(By.id("next"))::click);
        assertFalse(page.containsText(FLOW_SCOPE_VALUE),
                "flow scope value of the previous run of the flow is gone on the next page as well");
    }

    private static void enterFlowAndOpenInputPage(WebPage page) {
        page.guardHttp(page.findElement(By.id("start"))::click);
        page.guardHttp(page.findElement(By.id("next_a"))::click);
    }
}
