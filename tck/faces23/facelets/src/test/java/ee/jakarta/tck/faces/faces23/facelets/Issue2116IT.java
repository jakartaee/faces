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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue2116IT extends BaseITNG {

    /**
     * Script markup submitted into an inputTextarea must be echoed back HTML-escaped, not executed,
     * when the value is re-rendered. The leading {@code "?>} sequence must not break out of the
     * surrounding markup.
     *
     * @see jakarta.faces.component.html.HtmlInputTextarea
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2116
     */
    @Test
    void outputScript1() throws Exception {
        WebPage page = getPage("issue2116OutputTextScript1.xhtml");
        String inputValue = '"' + "?><script>alert('JSF Security hole!!')</script>";

        WebElement textarea = page.findElement(By.id("testForm:textarea"));
        textarea.clear();
        textarea.sendKeys(inputValue);

        page.guardHttp(page.findElement(By.id("testForm:button"))::click);

        assertTrue(page.containsText("Put the following input and JavaScript will be executed."), "static instruction text is rendered");
        assertEquals(inputValue, page.findElement(By.id("testForm:textarea")).getAttribute("value"), "submitted script is echoed back verbatim, not executed");
    }

    /**
     * Script markup submitted into an inputTextarea must be echoed back HTML-escaped, not executed,
     * when the value is re-rendered. The leading {@code -->} sequence must not break out of a
     * surrounding comment.
     *
     * @see jakarta.faces.component.html.HtmlInputTextarea
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2116
     */
    @Test
    void outputScript2() throws Exception {
        WebPage page = getPage("issue2116OutputTextScript2.xhtml");
        String inputValue = "--><script>alert('JSF Security hole!!')</script>";

        WebElement textarea = page.findElement(By.id("testForm:textarea"));
        textarea.clear();
        textarea.sendKeys(inputValue);

        page.guardHttp(page.findElement(By.id("testForm:button"))::click);

        assertTrue(page.containsText("Put the following input and JavaScript will be executed."), "static instruction text is rendered");
        assertEquals(inputValue, page.findElement(By.id("testForm:textarea")).getAttribute("value"), "submitted script is echoed back verbatim, not executed");
    }
}
