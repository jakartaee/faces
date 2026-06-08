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

package ee.jakarta.tck.faces.faces23.facelets;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.component.html.HtmlInputFile;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue3978IT extends BaseITNG {

    /**
     * An ajax submit with {@code execute="@form"} of a multipart form containing an
     * {@code h:inputFile} must succeed without a server-side exception and navigate to
     * the action's target page.
     *
     * @see HtmlInputFile
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3978
     */
    @Test
    void testFormWithInputFileAndAjaxButton() throws Exception {
        WebPage page = getPage("issue3978.xhtml");

        page.findElement(By.id("test")).sendKeys("CB");

        page.guardAjax(page.findElement(By.id("mybutton"))::click);

        assertFalse(page.containsText("Exception"),
            "Server should not throw an exception");
        assertTrue(page.containsText("This is the next page"));
    }

}
