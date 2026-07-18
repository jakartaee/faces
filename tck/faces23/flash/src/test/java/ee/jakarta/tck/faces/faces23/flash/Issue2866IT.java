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

package ee.jakarta.tck.faces.faces23.flash;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * Once the flash has been consumed, the cookie that carried it must be dropped again instead of
 * lingering in the browser. The cookie's name is implementation-specific, so the drop is observed as
 * a strict decrease of the browser's cookie count over the two hops that consume the flash.
 */
class Issue2866IT extends BaseITNG {

    /**
     * The first view writes the flash and renders a message; neither the second nor the third view
     * reads the flash, and by the third view the flash cookie is gone.
     *
     * @see jakarta.faces.context.Flash
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2866
     */
    @Test
    void testFlashCookieIsDroppedAfterConsumption() {
        WebPage page = getPage("issue2866.xhtml");
        page.findElement(By.id("form:input")).sendKeys("test");

        int cookiesWhileFlashIsSet = getWebDriver().manage().getCookies().size();

        page.guardHttp(page.findElement(By.id("form:submit"))::click);
        page.guardHttp(page.findElement(By.id("link"))::click);

        assertTrue(getWebDriver().manage().getCookies().size() < cookiesWhileFlashIsSet,
                "The flash cookie must be dropped once the flash has been consumed");
    }
}
