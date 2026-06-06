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

import jakarta.faces.context.Flash;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * Verifies that a flash value set during a non-ajax form submit survives a faces-redirect to a page located in a
 * different directory, exercising the chunked flash cookie path.
 */
class Issue2140IT extends BaseITNG {

    /**
     * Submits a value on a form that stores it into the {@link Flash} during the update-model phase, then navigates
     * via a faces-redirect to a page in a different directory and asserts the flash value is still present there.
     *
     * @see Flash
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2140
     */
    @Test
    void testFlashChunkingRedirect() throws Exception {
        WebPage page = getPage("issue2140/issue2140.xhtml");

        WebElement input = page.findElement(By.id("form:input"));
        input.clear();
        input.sendKeys("12345");

        WebElement submit = page.findElement(By.id("form:submit"));
        page.guardHttp(submit::click);

        assertTrue(page.containsText("12345"), "Flash value must survive the faces-redirect to a different directory");
    }
}
