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

package ee.jakarta.tck.faces.faces22.flow_defining_document_id;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * Two JARs each define a flow with the same flow id "bounded-task-flow", each under its own
 * defining document id ("flow01" respectively "flow02"). A flow is identified by the pair of both,
 * so the runtime must enter the flow of the defining document named by the navigation case's
 * to-flow-document-id, and keep the flow scopes of the two apart.
 */
class Issue2584IT extends BaseITNG {

    /**
     * @see jakarta.faces.flow.Flow#getDefiningDocumentId()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2584
     */
    @Test
    void testFlowOfFirstDefiningDocument() {
        assertFlow("flow01");
    }

    /**
     * @see jakarta.faces.flow.Flow#getDefiningDocumentId()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2584
     */
    @Test
    void testFlowOfSecondDefiningDocument() {
        assertFlow("flow02");
    }

    /**
     * Enters the flow of the given defining document from the entry page and walks it to its return
     * node, asserting on every view that the flow-scoped bean of that very defining document backs
     * it, and that the value put in flow scope survives the walk but not the return.
     */
    private void assertFlow(String definingDocumentId) {
        WebPage page = getPage("issue2584.xhtml");
        page.guardHttp(page.findElement(By.id("form:" + definingDocumentId))::click);
        assertName(page, definingDocumentId);

        page.guardHttp(page.findElement(By.id("form:next_a"))::click);
        assertName(page, definingDocumentId);

        String value = "value of " + definingDocumentId;
        WebElement input = page.findElement(By.id("form:input"));
        input.clear();
        input.sendKeys(value);
        page.guardHttp(page.findElement(By.id("form:next"))::click);
        assertName(page, definingDocumentId);
        assertEquals(value, page.findElement(By.id("form:value")).getText(), "Flow scoped value");

        page.guardHttp(page.findElement(By.id("form:return"))::click);
        assertEquals("return page", page.findElement(By.id("form:status")).getText(), "Return page");
        assertEquals("", page.findElement(By.id("form:flowScopeValue")).getText(), "Flow scope after return");
    }

    private void assertName(WebPage page, String definingDocumentId) {
        assertEquals(definingDocumentId + " name", page.findElement(By.id("form:name")).getText(),
                "Flow scoped bean of defining document " + definingDocumentId);
    }
}
