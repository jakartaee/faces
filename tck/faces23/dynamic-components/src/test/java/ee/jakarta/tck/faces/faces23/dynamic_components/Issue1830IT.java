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

package ee.jakarta.tck.faces.faces23.dynamic_components;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * Children added programmatically to a dynamic component during the pre-render-view event must
 * render in the order they were added and survive a postback (state persistence in the view).
 */
public class Issue1830IT extends BaseITNG {

    /**
     * The dynamically added child must render between the renderer's encodeBegin and encodeEnd
     * markers, both initially and after a postback.
     *
     * @see jakarta.faces.event.PreRenderViewEvent
     * @see https://github.com/eclipse-ee4j/mojarra/issues/1830
     */
    @Test
    void testAddComponent() throws Exception {
        WebPage page = getPage("add1830.xhtml");
        assertAddedChildInPlace(page);

        WebElement button = page.findElement(By.id("button"));
        page.guardHttp(button::click);
        assertAddedChildInPlace(page);
    }

    private void assertAddedChildInPlace(WebPage page) {
        String source = page.getSource();
        assertTrue(source.indexOf("encodeBegin") < source.indexOf("Dynamically added child"));
        assertTrue(source.indexOf("Dynamically added child") < source.indexOf("encodeEnd"));
    }

    /**
     * Three dynamically added input components must keep their order; the third is required, so a
     * postback yields a validation error and the order remains stable.
     *
     * @see jakarta.faces.event.PreRenderViewEvent
     * @see https://github.com/eclipse-ee4j/mojarra/issues/1830
     */
    @Test
    void testStable() throws Exception {
        String inputValue1 = "value=\"1\"";
        String inputValue2 = "value=\"2\"";
        String idText3 = "id=\"text3\"";

        WebPage page = getPage("stable1830.xhtml");
        assertStableOrder(page, inputValue1, inputValue2, idText3);

        WebElement button = page.findElement(By.id("button"));
        page.guardHttp(button::click);
        assertTrue(page.containsText("text3: Validation Error: Value is required."));
        assertStableOrder(page, inputValue1, inputValue2, idText3);
    }

    private void assertStableOrder(WebPage page, String inputValue1, String inputValue2, String idText3) {
        String source = page.getSource();
        assertTrue(source.indexOf("encodeBegin") < source.indexOf(inputValue1));
        assertTrue(source.indexOf(inputValue1) < source.indexOf(inputValue2));
        assertTrue(source.indexOf(inputValue2) < source.indexOf(idText3));
        assertTrue(source.indexOf("text3") < source.indexOf("encodeEnd"));
    }

    /**
     * A dynamically created data table must render its values in order, both initially and after a
     * postback.
     *
     * @see jakarta.faces.component.html.HtmlDataTable
     * @see https://github.com/eclipse-ee4j/mojarra/issues/1830
     */
    @Test
    void testTable() throws Exception {
        WebPage page = getPage("table1830.xhtml");
        assertTableOrder(page);

        WebElement button = page.findElement(By.id("button"));
        page.guardHttp(button::click);
        assertTableOrder(page);
    }

    private void assertTableOrder(WebPage page) {
        String source = page.getSource();
        assertTrue(source.indexOf("encodeBegin") < source.indexOf("Foo"));
        assertTrue(source.indexOf("Foo") < source.indexOf("Bar"));
        assertTrue(source.indexOf("Bar") < source.indexOf("Baz"));
        assertTrue(source.indexOf("Baz") < source.indexOf("encodeEnd"));
    }

    /**
     * A dynamic component that nests another dynamic component must render both, in order, before
     * and after a postback.
     *
     * @see jakarta.faces.event.PreRenderViewEvent
     * @see https://github.com/eclipse-ee4j/mojarra/issues/1830
     */
    @Test
    void testRecursive() throws Exception {
        WebPage page = getPage("recursive1830.xhtml");
        assertNested(page);

        WebElement button = page.findElement(By.id("button"));
        page.guardHttp(button::click);
        assertNested(page);
    }

    private void assertNested(WebPage page) {
        String source = page.getSource();
        int first = source.indexOf("Dynamically added child");
        int next = source.indexOf("Dynamically added child", first + "Dynamically added child".length());
        assertTrue(first >= 0);
        assertTrue(first < next);
    }
}
