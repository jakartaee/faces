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
package ee.jakarta.tck.faces.faces23.build_time_component_handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class ComponentHandlerInBuildTimeIfIT extends BaseITNG {

    /**
     * A custom ComponentHandler nested inside a build-time c:if must only have its apply()
     * invoked while the condition evaluates to true, and must stop being applied once the
     * condition flips back to false.
     *
     * @see jakarta.faces.view.facelets.ComponentHandler
     * @see jakarta.faces.view.facelets.FaceletContext
     */
    @Test
    void handlerAppliedOnlyWhenBuildTimeConditionTrue() {
        WebPage page = getPage("componentHandlerInBuildTimeIf.xhtml");
        assertEquals("", page.findElement(By.id("message")).getText(), "handler must not be applied initially");

        page.findElement(By.id("form:checkbox")).click();
        page.guardHttp(page.findElement(By.id("form:button"))::click);
        assertEquals(ComponentHandlerInBuildTimeIfHandler.MESSAGE,
                page.findElement(By.id("message")).getText(), "handler must be applied when condition is true");

        page.findElement(By.id("form:checkbox")).click();
        page.guardHttp(page.findElement(By.id("form:button"))::click);
        // An extra submit covers partialStateSaving=false, where the view is rebuilt before restore.
        page.guardHttp(page.findElement(By.id("form:button"))::click);
        assertEquals("", page.findElement(By.id("message")).getText(), "handler must not be applied once condition is false");
    }
}
