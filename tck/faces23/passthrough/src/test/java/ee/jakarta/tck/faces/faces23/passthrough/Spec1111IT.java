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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * Plain HTML elements decorated with the {@code jsf:} namespace become full Faces components: their
 * {@code jsf:value} participates in a postback round-trip, their {@code jsf:action} invokes a bean
 * method and their {@code jsf:outcome} navigates, while their non-Faces attributes keep rendering
 * verbatim.
 */
class Spec1111IT extends BaseITNG {

    /**
     * A decorated {@code input} renders its declared type and its non-Faces attributes, and its
     * {@code jsf:value} survives a postback, including the checked state of a checkbox.
     *
     * @see jakarta.faces.view.facelets.TagDecorator
     * @see https://github.com/jakartaee/faces/issues/1111
     */
    @Test
    void inputValueRoundTrip() throws Exception {
        WebPage page = getPage("spec1111-input.xhtml");
        assertInputDefaults(page);

        setValue(page, "form:text", "new text1");
        setValue(page, "form:email", "nobody@example.com");
        setValue(page, "form:number", "12");
        page.findElement(By.id("form:checkbox")).click();

        page.guardHttp(page.findElement(By.id("form:submit"))::click);

        assertEquals("text", getAttribute(page, "form:text", "type"), "text type after postback");
        assertEquals("new text1", getAttribute(page, "form:text", "value"), "text value after postback");
        assertEquals("email", getAttribute(page, "form:email", "type"), "email type after postback");
        assertEquals("nobody@example.com", getAttribute(page, "form:email", "value"), "email value after postback");
        assertEquals("number", getAttribute(page, "form:number", "type"), "number type after postback");
        assertEquals("12", getAttribute(page, "form:number", "value"), "number value after postback");
        assertEquals("[0-9]*", getAttribute(page, "form:number", "pattern"), "number pattern after postback");
        assertTrue(page.findElement(By.id("form:checkbox")).isSelected(), "checkbox checked after postback");
    }

    /**
     * A decorated {@code select} renders its size and multiple attributes and its default selection,
     * and its {@code jsf:value} survives a postback for single and multiple selections alike.
     *
     * @see jakarta.faces.view.facelets.TagDecorator
     * @see https://github.com/jakartaee/faces/issues/1111
     */
    @Test
    void selectValueRoundTrip() throws Exception {
        WebPage page = getPage("spec1111-select.xhtml");
        assertSelectDefaults(page);

        select(page, "form:selectOne", "3");
        select(page, "form:selectOneSize2", "5");
        select(page, "form:selectMany", "1", "2");

        page.guardHttp(page.findElement(By.id("form:submit"))::click);

        assertSelectAttributes(page);
        assertSelection(page, "form:selectOne", "3");
        assertSelection(page, "form:selectOneSize2", "5");
        assertSelection(page, "form:selectMany", "1", "2");
    }

    /**
     * A decorated {@code textarea} renders its non-Faces attributes and the text of its
     * {@code jsf:value}, which survives a postback.
     *
     * @see jakarta.faces.view.facelets.TagDecorator
     * @see https://github.com/jakartaee/faces/issues/1111
     */
    @Test
    void textareaValueRoundTrip() throws Exception {
        WebPage page = getPage("spec1111-textarea.xhtml");
        assertEquals("Long text", getAttribute(page, "form:textarea", "value"), "textarea default value");
        assertTrue(page.containsSource("autofocus=\"autofocus\""), "autofocus rendered verbatim");

        setValue(page, "form:textarea", "Very long text");
        page.guardHttp(page.findElement(By.id("form:submit"))::click);

        assertEquals("Very long text", getAttribute(page, "form:textarea", "value"), "textarea value after postback");
        assertTrue(page.containsSource("autofocus=\"autofocus\""), "autofocus rendered verbatim after postback");
    }

    /**
     * A decorated {@code button} invokes the bean method of its {@code jsf:action}, and navigates to
     * the view of its {@code jsf:outcome}.
     *
     * @see jakarta.faces.view.facelets.TagDecorator
     * @see https://github.com/jakartaee/faces/issues/1111
     */
    @Test
    void buttonActionAndOutcome() throws Exception {
        WebPage page = getPage("spec1111-button.xhtml");
        assertActionsAndOutcome(page, "outcome");
    }

    /**
     * A decorated {@code a} invokes the bean method of its {@code jsf:action}, and navigates to the
     * view of its {@code jsf:outcome}.
     *
     * @see jakarta.faces.view.facelets.TagDecorator
     * @see https://github.com/jakartaee/faces/issues/1111
     */
    @Test
    void linkActionAndOutcome() throws Exception {
        WebPage page = getPage("spec1111-links.xhtml");
        assertActionsAndOutcome(page, "form:outcome");
    }

    /**
     * Decorating a tag which already is a Faces component is invalid and must fail the view build.
     *
     * @see jakarta.faces.view.facelets.TagDecorator
     * @see https://github.com/jakartaee/faces/issues/1111
     */
    @Test
    void invalidDecoratedElementFails() throws Exception {
        assertEquals(500, getPage("spec1111-error.xhtml").getResponseStatus(), "response status");
    }

    private void assertActionsAndOutcome(WebPage page, String outcomeId) {
        assertEquals("", page.findElement(By.id("form:lastAction")).getText(), "no action invoked yet");

        page.guardHttp(page.findElement(By.id("form:action1"))::click);
        assertEquals("action1", page.findElement(By.id("form:lastAction")).getText(), "action1 invoked");

        page.guardHttp(page.findElement(By.id("form:action2"))::click);
        assertEquals("action2", page.findElement(By.id("form:lastAction")).getText(), "action2 invoked");

        page.guardHttp(page.findElement(By.id(outcomeId))::click);
        assertEquals("spec1111-outcome", page.findElement(By.id("lastOutcome")).getText(), "outcome navigated");
    }

    private void assertInputDefaults(WebPage page) {
        assertEquals("text", getAttribute(page, "form:text", "type"), "text type");
        assertEquals("text1", getAttribute(page, "form:text", "value"), "text default value");
        assertEquals("email", getAttribute(page, "form:email", "type"), "email type");
        assertEquals("anybody@example.com", getAttribute(page, "form:email", "value"), "email default value");
        assertEquals("number", getAttribute(page, "form:number", "type"), "number type");
        assertEquals("10", getAttribute(page, "form:number", "value"), "number default value");
        assertEquals("[0-9]*", getAttribute(page, "form:number", "pattern"), "number pattern");
        assertEquals("checkbox", getAttribute(page, "form:checkbox", "type"), "checkbox type");
        assertFalse(page.findElement(By.id("form:checkbox")).isSelected(), "checkbox default state");
    }

    private void assertSelectDefaults(WebPage page) {
        assertSelectAttributes(page);
        assertSelection(page, "form:selectOne", "2");
        assertSelection(page, "form:selectOneSize2", "3");
        assertSelection(page, "form:selectMany", "4", "6");
    }

    private void assertSelectAttributes(WebPage page) {
        assertEquals("1", getAttribute(page, "form:selectOne", "size"), "selectOne size");
        assertFalse(new Select(page.findElement(By.id("form:selectOne"))).isMultiple(), "selectOne multiple");
        assertEquals("2", getAttribute(page, "form:selectOneSize2", "size"), "selectOneSize2 size");
        assertFalse(new Select(page.findElement(By.id("form:selectOneSize2"))).isMultiple(), "selectOneSize2 multiple");
        assertEquals("7", getAttribute(page, "form:selectMany", "size"), "selectMany size defaults to select item count");
        assertTrue(new Select(page.findElement(By.id("form:selectMany"))).isMultiple(), "selectMany multiple");
    }

    private void assertSelection(WebPage page, String id, String... values) {
        List<String> selected = new Select(page.findElement(By.id(id))).getAllSelectedOptions().stream()
                .map(option -> option.getAttribute("value")).toList();
        assertEquals(List.of(values), selected, id + " selection");
    }

    private void select(WebPage page, String id, String... values) {
        Select select = new Select(page.findElement(By.id(id)));

        if (select.isMultiple()) {
            select.deselectAll();
        }

        for (String value : values) {
            select.selectByValue(value);
        }
    }

    private void setValue(WebPage page, String id, String value) {
        WebElement element = page.findElement(By.id(id));
        element.clear();
        element.sendKeys(value);
    }

    private String getAttribute(WebPage page, String id, String name) {
        return page.findElement(By.id(id)).getAttribute(name);
    }
}
