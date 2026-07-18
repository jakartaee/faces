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

package ee.jakarta.tck.faces.faces22.composite_component;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue2217IT extends BaseITNG {

    /**
     * A custom ClientBehavior attached to the composite tag must be retargeted onto the inner
     * command button named by cc:clientBehavior targets.
     *
     * @see jakarta.faces.component.behavior.ClientBehavior
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2217
     */
    @Test
    void customClientBehaviorRetargetedOntoDirectChild() {
        assertRetargetedOnto("form:compositeTest:cancel");
    }

    /**
     * A custom ClientBehavior attached to the composite tag must be retargeted onto an inner command
     * button that sits inside a nested naming container.
     *
     * @see jakarta.faces.component.behavior.ClientBehavior
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2217
     */
    @Test
    void customClientBehaviorRetargetedOntoNestedCompositeChild() {
        assertRetargetedOnto("form:compositeTest:sub:command");
    }

    /**
     * A cc:clientBehavior whose name, event and targets all come from EL must retarget the custom
     * ClientBehavior onto the inner command button just as the literal declaration does.
     *
     * @see jakarta.faces.component.behavior.ClientBehavior
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2217
     */
    @Test
    void customClientBehaviorRetargetedViaELDeclaredClientBehavior() {
        assertRetargetedOnto("form:compositeTestEL:cancelEL");
    }

    /**
     * An EL-declared cc:clientBehavior must retarget the custom ClientBehavior onto an inner command
     * button inside a nested naming container.
     *
     * @see jakarta.faces.component.behavior.ClientBehavior
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2217
     */
    @Test
    void customClientBehaviorRetargetedViaELOntoNestedChild() {
        assertRetargetedOnto("form:compositeTestEL:sub:commandEL");
    }

    /**
     * Clicks the button with the given client id. The behavior's script records the client id of the
     * component it was retargeted onto into the marker input, which the submit round-trips into the
     * bean, so the resulting status pins which component actually carried the behavior.
     */
    private void assertRetargetedOnto(String clientId) {
        WebPage page = getPage("issue2217.xhtml");
        page.guardHttp(page.findElement(By.id(clientId))::click);
        assertEquals(clientId, page.findElement(By.id("form:status")).getText(),
                "custom behavior ran on " + clientId);
    }
}
