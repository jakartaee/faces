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

import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.component.html.HtmlInputFile;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue3133IT extends BaseITNG {

    /**
     * A multipart form whose submit button is wrapped in an enclosing {@code f:ajax}
     * must submit the {@code h:inputFile} and navigate to the action's target page.
     *
     * @see HtmlInputFile
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3133
     */
    @Test
    void testAjaxInputFile() throws Exception {
        WebPage page = getPage("issue3133-ajax.xhtml");

        page.findElement(By.id("test")).sendKeys("CB");

        page.guardAjax(page.findElement(By.id("mybutton"))::click);

        assertTrue(page.containsText("This is the next page"));
    }

    /**
     * A multipart form with a non-ajax submit button (only the {@code h:inputText} has an
     * {@code f:ajax}) must submit the {@code h:inputFile} and navigate to the action's
     * target page.
     *
     * @see HtmlInputFile
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3133
     */
    @Test
    void testNonAjaxInputFile() throws Exception {
        WebPage page = getPage("issue3133-nonajax.xhtml");

        page.findElement(By.id("test")).sendKeys("CB");

        page.guardHttp(page.findElement(By.id("mybutton"))::click);

        assertTrue(page.containsText("This is the next page"));
    }

}
