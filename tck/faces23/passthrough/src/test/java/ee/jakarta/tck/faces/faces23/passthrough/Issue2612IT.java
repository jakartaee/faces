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

package ee.jakarta.tck.faces.faces23.passthrough;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue2612IT extends BaseITNG {

    /**
     * A {@code p:placeholder} pass-through attribute on {@code h:inputText} renders as a verbatim
     * placeholder attribute on the host input element.
     *
     * @see jakarta.faces.component.UIComponent#getPassThroughAttributes()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2612
     */
    @Test
    void placeholder() throws Exception {
        WebPage page = getPage("issue2612Input1.xhtml");
        WebElement input = page.findElement(By.id("input1"));
        assertEquals("input1", input.getAttribute("id"), "id");
        assertEquals("text", input.getAttribute("type"), "type");
        assertEquals("input1", input.getAttribute("name"), "name");
        assertEquals("Enter text here", input.getAttribute("placeholder"), "placeholder pass-through attribute");
    }

    /**
     * A {@code p:autocomplete} pass-through attribute on {@code h:inputText} renders as a verbatim
     * autocomplete attribute on the host input element.
     *
     * @see jakarta.faces.component.UIComponent#getPassThroughAttributes()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2612
     */
    @Test
    void autocomplete() throws Exception {
        WebPage page = getPage("issue2612Input1.xhtml");
        WebElement input = page.findElement(By.id("input2"));
        assertEquals("input2", input.getAttribute("id"), "id");
        assertEquals("text", input.getAttribute("type"), "type");
        assertEquals("input2", input.getAttribute("name"), "name");
        assertEquals("on", input.getAttribute("autocomplete"), "autocomplete pass-through attribute");
    }

    /**
     * A {@code p:autofocus} pass-through attribute on {@code h:inputText} renders as a verbatim
     * autofocus attribute on the host input element.
     *
     * @see jakarta.faces.component.UIComponent#getPassThroughAttributes()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2612
     */
    @Test
    void autofocus() throws Exception {
        WebPage page = getPage("issue2612Input1.xhtml");
        WebElement input = page.findElement(By.id("input3"));
        assertEquals("input3", input.getAttribute("id"), "id");
        assertEquals("text", input.getAttribute("type"), "type");
        assertEquals("input3", input.getAttribute("name"), "name");
        assertTrue(page.containsSource("autofocus=\"autofocus\""), "autofocus pass-through attribute rendered verbatim");
    }

    /**
     * A {@code p:list} pass-through attribute on {@code h:inputText} renders as a verbatim
     * list attribute on the host input element.
     *
     * @see jakarta.faces.component.UIComponent#getPassThroughAttributes()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2612
     */
    @Test
    void list() throws Exception {
        WebPage page = getPage("issue2612Input1.xhtml");
        WebElement input = page.findElement(By.id("input4"));
        assertEquals("input4", input.getAttribute("id"), "id");
        assertEquals("text", input.getAttribute("type"), "type");
        assertEquals("input4", input.getAttribute("name"), "name");
        assertEquals("mydatalist", input.getAttribute("list"), "list pass-through attribute");
    }

    /**
     * A {@code p:pattern} pass-through attribute on {@code h:inputText} renders as a verbatim
     * pattern attribute on the host input element.
     *
     * @see jakarta.faces.component.UIComponent#getPassThroughAttributes()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2612
     */
    @Test
    void pattern() throws Exception {
        WebPage page = getPage("issue2612Input1.xhtml");
        WebElement input = page.findElement(By.id("input5"));
        assertEquals("input5", input.getAttribute("id"), "id");
        assertEquals("text", input.getAttribute("type"), "type");
        assertEquals("input5", input.getAttribute("name"), "name");
        assertEquals("[A-Za-z]{3}", input.getAttribute("pattern"), "pattern pass-through attribute");
    }

    /**
     * A {@code p:required} pass-through attribute on {@code h:inputText} renders as a verbatim
     * required attribute on the host input element.
     *
     * @see jakarta.faces.component.UIComponent#getPassThroughAttributes()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2612
     */
    @Test
    void required() throws Exception {
        WebPage page = getPage("issue2612Input1.xhtml");
        WebElement input = page.findElement(By.id("input6"));
        assertEquals("input6", input.getAttribute("id"), "id");
        assertEquals("text", input.getAttribute("type"), "type");
        assertEquals("input6", input.getAttribute("name"), "name");
        assertTrue(page.containsSource("required=\"required\""), "required pass-through attribute rendered verbatim");
    }

    /**
     * A {@code p:dirname} pass-through attribute on {@code h:inputText} renders as a verbatim
     * dirname attribute on the host input element.
     *
     * @see jakarta.faces.component.UIComponent#getPassThroughAttributes()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2612
     */
    @Test
    void dirname() throws Exception {
        WebPage page = getPage("issue2612Input1.xhtml");
        WebElement input = page.findElement(By.id("input7"));
        assertEquals("input7", input.getAttribute("id"), "id");
        assertEquals("text", input.getAttribute("type"), "type");
        assertEquals("input7", input.getAttribute("name"), "name");
        assertEquals("input7.dir", input.getAttribute("dirname"), "dirname pass-through attribute");
    }
}
