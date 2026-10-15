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

package ee.jakarta.tck.faces.faces23.dynamic_components;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * A panel holding an input is replaced in an action by a dynamically added panel holding a new input with the same id. On every following postback the new
 * input must be restored under the new panel, so that it processes its submitted value and is rendered again.
 */
public class Issue6060IT extends BaseITNG {

    /**
     * The replaced panel and its input were dynamically added as well.
     *
     * @see jakarta.faces.component.UIComponent#getChildren()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/6060
     */
    @Test
    void testDynamicPanelReplacedByDynamicPanelWithSameChildId() {
        WebPage page = getPage("issue6060Dynamic.xhtml");
        assertFieldIn(page, "form:panel1");
        assertReplacedFieldSubmits(page, "form:panel2");
    }

    /**
     * The replaced panel and its input were created by the view declaration.
     *
     * @see jakarta.faces.component.UIComponent#getChildren()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/6060
     */
    @Test
    void testStaticPanelReplacedByDynamicPanelWithSameChildId() {
        WebPage page = getPage("issue6060Static.xhtml");
        assertFieldIn(page, "form:panel0");
        assertReplacedFieldSubmits(page, "form:panel1");
    }

    private static void assertReplacedFieldSubmits(WebPage page, String panelId) {
        WebElement rebuild = page.findElement(By.id("form:rebuild"));
        page.guardHttp(rebuild::click);
        assertFieldIn(page, panelId);

        assertFieldSubmits(page, panelId, "first");
        assertFieldSubmits(page, panelId, "second");
    }

    private static void assertFieldSubmits(WebPage page, String panelId, String text) {
        WebElement field = page.findElement(By.id("form:field"));
        field.clear();
        field.sendKeys(text);
        WebElement submit = page.findElement(By.id("form:submit"));
        page.guardHttp(submit::click);
        assertFieldIn(page, panelId);
        assertEquals(text, page.findElement(By.id("form:field")).getAttribute("value"), "rendered field value");
        assertEquals(text, page.findElement(By.id("form:value")).getText(), "model value");
    }

    private static void assertFieldIn(WebPage page, String panelId) {
        assertEquals(1, page.findElements(By.cssSelector("[id='" + panelId + "'] [id='form:field']")).size(), "form:field inside " + panelId);
    }

}
