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

package ee.jakarta.tck.faces.faces22.composite_component;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue1947IT extends BaseITNG {

    /**
     * A valueChangeListener written directly on an inner component of a composite, bound through a
     * plain object-valued composite:attribute rather than retargeted via cc:editableValueHolder,
     * must fire when the value changes.
     *
     * @see jakarta.faces.event.ValueChangeEvent
     * @see https://github.com/eclipse-ee4j/mojarra/issues/1947
     */
    @Test
    void valueChangeListenerOnInnerSelectOneListboxFires() {
        WebPage page = getPage("issue1947.xhtml");
        new Select(page.findElement(By.id("form:cc:listbox"))).selectByValue("4");
        page.guardHttp(page.findElement(By.id("form:cc:submit"))::click);
        assertEquals("valueChange called", page.findElement(By.id("form:status")).getText());
    }

    /**
     * An action bound through a plain object-valued composite:attribute and passed into a nested
     * composite via cc:insertChildren must be invoked exactly once when its command is clicked, and
     * not at all when an unrelated command submits the same form.
     *
     * @see jakarta.faces.component.UICommand#getAction()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/1947
     */
    @Test
    void actionThroughNestedCompositeInsertChildrenInvokedOnce() {
        WebPage page = getPage("issue1947-action.xhtml");

        page.guardHttp(page.findElement(By.id("form:cc:inner:remove"))::click);
        assertEquals("removeGroup called (1)", page.findElement(By.id("form:status")).getText(),
                "action invoked exactly once");

        page.guardHttp(page.findElement(By.id("form:refresh"))::click);
        assertEquals("", page.findElement(By.id("form:status")).getText(),
                "action not invoked by an unrelated submit");
    }
}
