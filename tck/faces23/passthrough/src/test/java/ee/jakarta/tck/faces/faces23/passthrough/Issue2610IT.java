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

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue2610IT extends BaseITNG {

    private void fireMouseOver(WebPage page, String id) {
        page.guardAjax(() -> page.executeScript(
                "document.getElementById(arguments[0]).dispatchEvent(new MouseEvent('mouseover', {bubbles: true}))", id));
    }

    /**
     * The {@code jsf:} namespace on an HTML5 {@code article} element renders a real article
     * element keeping its id, and an attached {@code f:ajax} behavior fires the full Ajax event
     * lifecycle (begin, complete, success).
     *
     * @see jakarta.faces.component.behavior.AjaxBehavior
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2610
     */
    @Test
    void article() throws Exception {
        WebPage page = getPage("issue2610Article.xhtml");

        String article1 = page.findElement(By.id("article1")).getAttribute("outerHTML");
        assertTrue(article1.contains("<article"), "article1 is rendered as an article element");
        assertTrue(article1.contains("id=\"article1\""), "article1 keeps its id");

        WebElement article2 = page.findElement(By.id("article2"));
        assertTrue(article2.getAttribute("outerHTML").contains("<article"), "article2 is rendered as an article element");
        assertTrue(article2.getAttribute("outerHTML").contains("id=\"article2\""), "article2 keeps its id");


        fireMouseOver(page, "article2");

        String status = page.findElement(By.id("statusArea")).getAttribute("value");
        assertTrue(status.contains("article2 Event: begin"), "Ajax begin event fired");
        assertTrue(status.contains("article2 Event: complete"), "Ajax complete event fired");
        assertTrue(status.contains("article2 Event: success"), "Ajax success event fired");
    }

    /**
     * The {@code jsf:} namespace on an HTML5 {@code aside} element renders a real aside element
     * keeping its id, and an attached {@code f:ajax} behavior fires the full Ajax event
     * lifecycle (begin, complete, success).
     *
     * @see jakarta.faces.component.behavior.AjaxBehavior
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2610
     */
    @Test
    void aside() throws Exception {
        WebPage page = getPage("issue2610Aside.xhtml");

        String aside1 = page.findElement(By.id("aside1")).getAttribute("outerHTML");
        assertTrue(aside1.contains("<aside"), "aside1 is rendered as an aside element");
        assertTrue(aside1.contains("id=\"aside1\""), "aside1 keeps its id");

        WebElement aside2 = page.findElement(By.id("aside2"));
        assertTrue(aside2.getAttribute("outerHTML").contains("<aside"), "aside2 is rendered as an aside element");
        assertTrue(aside2.getAttribute("outerHTML").contains("id=\"aside2\""), "aside2 keeps its id");

        page.guardAjax(aside2::click);

        String status = page.findElement(By.id("statusArea")).getAttribute("value");
        assertTrue(status.contains("aside2 Event: begin"), "Ajax begin event fired");
        assertTrue(status.contains("aside2 Event: complete"), "Ajax complete event fired");
        assertTrue(status.contains("aside2 Event: success"), "Ajax success event fired");
    }

    /**
     * The {@code jsf:} namespace on an HTML5 {@code nav} element renders a real nav element
     * keeping its id, and an attached {@code f:ajax} behavior fires the full Ajax event
     * lifecycle (begin, complete, success).
     *
     * @see jakarta.faces.component.behavior.AjaxBehavior
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2610
     */
    @Test
    void nav() throws Exception {
        WebPage page = getPage("issue2610Nav.xhtml");

        String nav1 = page.findElement(By.id("nav1")).getAttribute("outerHTML");
        assertTrue(nav1.contains("<nav"), "nav1 is rendered as a nav element");
        assertTrue(nav1.contains("id=\"nav1\""), "nav1 keeps its id");

        WebElement nav2 = page.findElement(By.id("nav2"));
        assertTrue(nav2.getAttribute("outerHTML").contains("<nav"), "nav2 is rendered as a nav element");
        assertTrue(nav2.getAttribute("outerHTML").contains("id=\"nav2\""), "nav2 keeps its id");

        fireMouseOver(page, "nav2");

        String status = page.findElement(By.id("statusArea")).getAttribute("value");
        assertTrue(status.contains("nav2 Event: begin"), "Ajax begin event fired");
        assertTrue(status.contains("nav2 Event: complete"), "Ajax complete event fired");
        assertTrue(status.contains("nav2 Event: success"), "Ajax success event fired");
    }

    /**
     * The {@code jsf:} namespace on an HTML5 {@code section} element renders a real section
     * element keeping its id, and an attached {@code f:ajax} behavior fires the full
     * Ajax event lifecycle (begin, complete, success).
     *
     * @see jakarta.faces.component.behavior.AjaxBehavior
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2610
     */
    @Test
    void section() throws Exception {
        WebPage page = getPage("issue2610Section.xhtml");

        String section1 = page.findElement(By.id("section1")).getAttribute("outerHTML");
        assertTrue(section1.contains("<section"), "section1 is rendered as a section element");
        assertTrue(section1.contains("id=\"section1\""), "section1 keeps its id");

        WebElement section2 = page.findElement(By.id("section2"));
        assertTrue(section2.getAttribute("outerHTML").contains("<section"), "section2 is rendered as a section element");
        assertTrue(section2.getAttribute("outerHTML").contains("id=\"section2\""), "section2 keeps its id");

        page.guardAjax(section2::click);

        String status = page.findElement(By.id("statusArea")).getAttribute("value");
        assertTrue(status.contains("section2 Event: begin"), "Ajax begin event fired");
        assertTrue(status.contains("section2 Event: complete"), "Ajax complete event fired");
        assertTrue(status.contains("section2 Event: success"), "Ajax success event fired");
    }

    /**
     * The {@code jsf:} namespace on HTML5 heading elements {@code h1}-{@code h6} renders real
     * heading elements keeping their ids, and an attached {@code f:ajax} behavior on the
     * {@code h2} fires the full Ajax event lifecycle (begin, complete, success).
     *
     * @see jakarta.faces.component.behavior.AjaxBehavior
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2610
     */
    @Test
    void headers() throws Exception {
        WebPage page = getPage("issue2610Headers.xhtml");

        String h1 = page.findElement(By.id("header1")).getAttribute("outerHTML");
        assertTrue(h1.contains("<h1"), "header1 is rendered as an h1 element");
        assertTrue(h1.contains("id=\"header1\""), "header1 keeps its id");

        WebElement h2El = page.findElement(By.id("header2"));
        String h2 = h2El.getAttribute("outerHTML");
        assertTrue(h2.contains("<h2"), "header2 is rendered as an h2 element");
        assertTrue(h2.contains("id=\"header2\""), "header2 keeps its id");

        fireMouseOver(page, "header2");

        String status = page.findElement(By.id("statusArea")).getAttribute("value");
        assertTrue(status.contains("header2 Event: begin"), "Ajax begin event fired");
        assertTrue(status.contains("header2 Event: complete"), "Ajax complete event fired");
        assertTrue(status.contains("header2 Event: success"), "Ajax success event fired");

        String h3 = page.findElement(By.id("header3")).getAttribute("outerHTML");
        assertTrue(h3.contains("<h3"), "header3 is rendered as an h3 element");
        assertTrue(h3.contains("id=\"header3\""), "header3 keeps its id");

        String h4 = page.findElement(By.id("header4")).getAttribute("outerHTML");
        assertTrue(h4.contains("<h4"), "header4 is rendered as an h4 element");
        assertTrue(h4.contains("id=\"header4\""), "header4 keeps its id");

        String h5 = page.findElement(By.id("header5")).getAttribute("outerHTML");
        assertTrue(h5.contains("<h5"), "header5 is rendered as an h5 element");
        assertTrue(h5.contains("id=\"header5\""), "header5 keeps its id");

        String h6 = page.findElement(By.id("header6")).getAttribute("outerHTML");
        assertTrue(h6.contains("<h6"), "header6 is rendered as an h6 element");
        assertTrue(h6.contains("id=\"header6\""), "header6 keeps its id");
    }

    /**
     * The {@code jsf:} namespace on an HTML5 {@code hgroup} element renders a real hgroup element
     * keeping its id.
     *
     * @see jakarta.faces.component.behavior.AjaxBehavior
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2610
     */
    @Test
    void headerGroup() throws Exception {
        WebPage page = getPage("issue2610HGroup.xhtml");

        String hgroup = page.findElement(By.id("hgroup")).getAttribute("outerHTML");
        assertTrue(hgroup.contains("<hgroup"), "hgroup is rendered as an hgroup element");
        assertTrue(hgroup.contains("id=\"hgroup\""), "hgroup keeps its id");
    }
}
