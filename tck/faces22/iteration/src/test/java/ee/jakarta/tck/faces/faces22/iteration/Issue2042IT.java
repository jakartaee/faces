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

package ee.jakarta.tck.faces.faces22.iteration;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * f:setPropertyActionListener nested in a command link inside a ui:repeat resolves the current row's
 * varStatus.first when the link is invoked.
 */
class Issue2042IT extends BaseITNG {

    /**
     * Invoking the command link of the first repeat row sets the target to varStatus.first == true, and invoking
     * the command link of the second row sets it to false.
     *
     * @see jakarta.faces.event.ActionListener
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2042
     */
    @Test
    void testRepeatPropertyActionListener() {
        WebPage page = getPage("issue2042.xhtml");
        page.guardHttp(() -> page.findElement(By.id("form:repeat:0:link")).click());
        assertTrue(page.containsText("Triggered first: true"), "First row resolves varStatus.first to true");
        page.guardHttp(() -> page.findElement(By.id("form:repeat:1:link")).click());
        assertTrue(page.containsText("Triggered first: false"), "Second row resolves varStatus.first to false");
    }
}
