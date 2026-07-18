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

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue2199IT extends BaseITNG {

    /**
     * A composite:attribute declaring a method-signature must be usable as the listener of an f:ajax
     * nested in the composite's implementation.
     *
     * @see jakarta.faces.event.AjaxBehaviorEvent
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2199
     */
    @Test
    void ajaxListenerFromMethodSignatureAttribute() {
        WebPage page = getPage("issue2199-ajax.xhtml");
        page.guardAjax(page.findElement(By.id("form:cc_ajax:button"))::click);
        assertEquals("processAjaxBehavior called", page.findElement(By.id("form:cc_ajax:out")).getText());
    }

    /**
     * A composite:attribute declaring a method-signature must be usable as the listener of an
     * f:event in the composite's implementation, with the event type itself also coming from
     * cc.attrs.
     *
     * @see jakarta.faces.event.ComponentSystemEvent
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2199
     */
    @Test
    void eventListenerFromMethodSignatureAttribute() {
        WebPage page = getPage("issue2199-event.xhtml");
        page.guardHttp(page.findElement(By.id("form:button"))::click);
        assertEquals("listener called", page.findElement(By.id("form:status")).getText());
    }

    /**
     * A composite:attribute declaring a method-signature must be usable as the valueChangeListener
     * of an h:inputText in the composite's implementation.
     *
     * @see jakarta.faces.event.ValueChangeEvent
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2199
     */
    @Test
    void valueChangeListenerFromMethodSignatureAttribute() {
        WebPage page = getPage("issue2199-valueChangeListener.xhtml");
        page.findElement(By.id("form:cc_value:input")).sendKeys("Foo");
        page.guardHttp(page.findElement(By.id("form:button"))::click);
        assertEquals("valueChange called", page.findElement(By.id("form:status")).getText());
    }
}
