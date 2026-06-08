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

package ee.jakarta.tck.faces.faces22.component_events;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * Verifies that {@code @ListenerFor}/{@code @ListenersFor} system event listeners declared on custom
 * UIComponent subclasses fire at the correct lifecycle phases.
 */
class Issue3323IT extends BaseITNG {

    /**
     * On initial render the PreRenderComponentEvent listener fires, and on postback the PreValidateEvent and
     * PostValidateEvent listeners fire during the validation phase.
     *
     * @see jakarta.faces.event.ListenerFor
     * @see jakarta.faces.event.ListenersFor
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3323
     */
    @Test
    void testEventListener() {
        WebPage page = getPage("issue3323.xhtml");
        assertTrue(page.containsText("preRenderComponentEvent"),
                "PreRenderComponentEvent listener fires during initial render");

        WebElement button = page.findElement(By.id("button"));
        page.guardHttp(button::click);

        assertTrue(page.containsText("preValidateEvent"),
                "PreValidateEvent listener fires during validation phase on postback");
        assertTrue(page.containsText("postValidateEvent"),
                "PostValidateEvent listener fires during validation phase on postback");
    }
}
