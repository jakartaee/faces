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

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * Returning from a nested flow (flow1 calls flow2) must behave identically whether or not
 * {@code ConfigurableNavigationHandler.getNavigationCase} was invoked first: that lookup must
 * not have a side effect on the flow stack. Both return paths must end back on the flow1 start page.
 */
class Issue4279IT extends BaseITNG {

    /**
     * @see jakarta.faces.application.ConfigurableNavigationHandler#getNavigationCase(jakarta.faces.context.FacesContext, String, String)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4279
     */
    @Test
    void testIssue4279GetNavigationCaseNotIdempotent() {
        doTest("call_getNavigationCase_and_returnFromFlow2");
        doTest("returnFromFlow2");
    }

    private void doTest(String returnFromFlow2ButtonId) {
        WebPage page = getPage("index.xhtml");
        page.guardHttp(() -> page.findElement(By.id("flow1")).click());
        page.guardHttp(() -> page.findElement(By.id("callFlow2")).click());
        page.guardHttp(() -> page.findElement(By.id(returnFromFlow2ButtonId)).click());

        // Back in flow1 at returnFromFlow2.xhtml; now exit flow1 entirely.
        page.guardHttp(() -> page.findElement(By.id("returnFromFlow1")).click());

        // Out of all flows, back on the index page that offers the flow1 entry button.
        assertFalse(page.findElements(By.id("flow1")).isEmpty(),
                "returned to flow1 entry page after exiting nested flows via " + returnFromFlow2ButtonId);
    }
}
