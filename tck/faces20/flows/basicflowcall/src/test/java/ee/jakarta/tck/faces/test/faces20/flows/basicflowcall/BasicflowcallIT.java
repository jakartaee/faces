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
package ee.jakarta.tck.faces.test.faces20.flows.basicflowcall;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class BasicflowcallIT extends BaseITNG {

    @Test
    void facesFlowCallTest() {
        // Outside the Flow structure. (/index.xhtml)
        WebPage page = getPage("faces/index.xhtml");
        assertTrue(page.isInPageText("Outside of flow"), "Outside of flow");

        // First page of Flow. (flow-a/flow-a.xhtml)
        WebElement startA = findByIdSuffix(page, "start_a");
        page.guardHttp(startA::click);

        assertTrue(page.isInPageText("First page in the flow"), "First page in the flow");
        assertTrue(page.isInPageText("Flow_a_Bean"), "Flow_a_Bean");
        assertTrue(page.matchesPageText("(?s).*Has a flow:\\s+true\\..*"), "Has a flow: true");
        assertEquals("", findByIdSuffix(page, "param1FromFlowB").getText(), "param1FromFlowB");
        assertEquals("", findByIdSuffix(page, "param2FromFlowB").getText(), "param2FromFlowB");

        // Enter the second page of the Flow. (flow-a/next-a.xhtml)
        WebElement nextA = findByIdSuffix(page, "next_a");
        page.guardHttp(nextA::click);

        assertTrue(page.isInPageText("Second page in the flow"), "Second page in the flow");

        WebElement input = findByIdSuffix(page, "input");
        String value = "" + System.currentTimeMillis();
        input.sendKeys(value);

        // Enter Last Page of Flow. ((flow-a/next-b.xhtml))
        WebElement next = findByIdSuffix(page, "next");
        page.guardHttp(next::click);

        assertTrue(page.isInPageText(value), "value");

        // Enter flow-b, passing parameters.
        WebElement callB = findByIdSuffix(page, "callB");
        page.guardHttp(callB::click);

        assertTrue(page.isInPageText("Flow_b_Bean"), "Flow_b_Bean");
        assertTrue(page.isNotInPageText("Flow_a_Bean"), "Not Flow_a_Bean");
        assertEquals("param1Value", findByIdSuffix(page, "param1FromFlowA").getText(), "param1FromFlowA");
        assertEquals("param2Value", findByIdSuffix(page, "param2FromFlowA").getText(), "param2FromFlowA");

        // Enter second page of Flow-b
        nextA = findByIdSuffix(page, "next_a");
        page.guardHttp(nextA::click);

        assertTrue(page.isInPageText("Second page in the flow"), "Second page in the flow (flow-b)");

        input = findByIdSuffix(page, "input");
        value = "" + System.currentTimeMillis();
        input.sendKeys(value);

        // Enter last page of Flow-b
        next = findByIdSuffix(page, "next");
        page.guardHttp(next::click);

        assertTrue(page.isInPageText(value), "value (flow-b)");

        // Enter flow-a, passing parameters.
        WebElement callA = findByIdSuffix(page, "callA");
        page.guardHttp(callA::click);

        assertEquals("param1Value", findByIdSuffix(page, "param1FromFlowB").getText(), "param1FromFlowB (reentered)");
        assertEquals("param2Value", findByIdSuffix(page, "param2FromFlowB").getText(), "param2FromFlowB (reentered)");

        // Enter second page of Flow-a
        nextA = findByIdSuffix(page, "next_a");
        page.guardHttp(nextA::click);

        assertTrue(page.isInPageText("Second page in the flow"), "Second page in the flow (flow-a reentered)");

        // Enter last page of Flow-a
        next = findByIdSuffix(page, "next");
        page.guardHttp(next::click);

        WebElement returnButton = findByIdSuffix(page, "return");
        page.guardHttp(returnButton::click);
    }

    private static WebElement findByIdSuffix(WebPage page, String id) {
        String suffix = ":" + id;
        return page.findElement(By.xpath(
            "//*[@id='" + id + "'"
            + " or substring(@id, string-length(@id) - " + (suffix.length() - 1) + ") = '" + suffix + "']"));
    }
}
