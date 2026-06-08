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

package ee.jakarta.tck.faces.faces23.misc_additive_config;

import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.component.behavior.ClientBehavior;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue1455IT extends BaseITNG {

    /**
     * When two custom {@code ClientBehavior}s are attached to a single command button on two
     * different events ({@code click} and {@code action}), both behavior scripts must be rendered
     * and wired so that activating the button runs both. Each behavior writes a distinctive marker
     * into a dedicated output element, so both markers becoming visible proves both scripts ran.
     *
     * @see ClientBehavior
     * @see https://github.com/eclipse-ee4j/mojarra/issues/1455
     */
    @Test
    void testBothActionAndClickRendered() {
        WebPage page = getPage("issue1455.xhtml");

        page.findElement(By.id("myButton")).click();

        assertTrue(page.containsText("CustomBehavior called"), "click behavior script ran");
        assertTrue(page.containsText("CustomBehavior2 called"), "action behavior script ran");
    }
}
