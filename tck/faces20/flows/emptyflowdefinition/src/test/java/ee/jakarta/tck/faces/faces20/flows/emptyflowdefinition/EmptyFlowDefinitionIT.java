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
package ee.jakarta.tck.faces.faces20.flows.emptyflowdefinition;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * An empty (0-byte) {@code <flowname>-flow.xml} still defines a bounded task flow by convention:
 * the runtime must not choke on the empty definition file and must derive the flow and its start
 * node from the directory name.
 *
 * @see <a href="https://github.com/eclipse-ee4j/glassfish/issues">GLASSFISH-19937</a>
 */
class EmptyFlowDefinitionIT extends BaseITNG {

    /**
     * Entering the flow via its entry node reaches the flow's start node, and re-entering works the
     * same way a second time.
     *
     * @see jakarta.faces.flow.FlowHandler
     * @see jakarta.faces.flow.FlowScoped
     */
    @Test
    void facesFlowEntryExitTest() {
        WebPage page = getPage("index.xhtml");
        assertTrue(page.containsText("Page with link to flow entry"), "Page with link to flow entry");

        page.guardHttp(page.findElement(By.id("form:start"))::click);
        assertTrue(page.containsText("First page in the flow"), "First page in the flow");
        assertTrue(page.containsText("basicFlow"), "basicFlow");

        page = getPage("index.xhtml");
        assertTrue(page.containsText("Page with link to flow entry"), "Page with link to flow entry");

        page.guardHttp(page.findElement(By.id("form:start"))::click);
        assertTrue(page.containsText("First page in the flow"), "First page in the flow");
        assertTrue(page.containsText("basicFlow"), "basicFlow");
    }

    /**
     * A value stored in flow scope survives navigation within the flow and is cleared on flow exit.
     *
     * @see jakarta.faces.flow.FlowScoped
     * @see jakarta.faces.flow.FlowHandler
     */
    @Test
    void facesFlowScopeTest() {
        WebPage page = getPage("index.xhtml");
        page.guardHttp(page.findElement(By.id("form:start"))::click);
        assertTrue(page.containsText("First page in the flow"), "First page in the flow");
        assertTrue(page.containsText("basicFlow"), "basicFlow");

        page.guardHttp(page.findElement(By.id("form:next_a"))::click);

        final String flowScopeValue = "Value in faces flow scope";
        page.findElement(By.id("form:input")).sendKeys(flowScopeValue);

        page.guardHttp(page.findElement(By.id("form:next"))::click);
        assertTrue(page.containsText(flowScopeValue), "flowScopeValue visible");

        page.guardHttp(page.findElement(By.id("form:return"))::click);
        assertTrue(page.containsText("return page"), "return page");
        assertFalse(page.containsText(flowScopeValue), "flowScopeValue not visible");
    }

}
