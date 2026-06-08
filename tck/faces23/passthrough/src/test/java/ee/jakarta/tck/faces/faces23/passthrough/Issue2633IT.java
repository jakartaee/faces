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

/**
 * An f:ajax behavior attached to a passthrough (jsf:) fieldset or label element fires the full Ajax event
 * lifecycle (begin, complete, success) when the configured event is triggered.
 */
class Issue2633IT extends BaseITNG {

    private void fireMouseOver(WebPage page, String id) {
        page.guardAjax(() -> page.executeScript(
                "document.getElementById(arguments[0]).dispatchEvent(new MouseEvent('mouseover', {bubbles: true}))", id));
    }

    /**
     * An f:ajax behavior on a passthrough fieldset element fires the full Ajax event lifecycle
     * (begin, complete, success) on click.
     *
     * @see jakarta.faces.component.behavior.AjaxBehavior
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2633
     */
    @Test
    void testFieldSetAjaxBehavior() throws Exception {
        WebPage page = getPage("issue2633-fieldset.xhtml");

        WebElement fieldset = page.findElement(By.id("fieldset4"));
        page.guardAjax(fieldset::click);

        String status = page.findElement(By.id("statusArea")).getAttribute("value");
        assertTrue(status.contains("fieldset4 Event: begin"), "Ajax begin event fired");
        assertTrue(status.contains("fieldset4 Event: complete"), "Ajax complete event fired");
        assertTrue(status.contains("fieldset4 Event: success"), "Ajax success event fired");
    }

    /**
     * An f:ajax behavior with event="mouseover" on a passthrough label element fires the full Ajax event
     * lifecycle (begin, complete, success) on mouseover.
     *
     * @see jakarta.faces.component.behavior.AjaxBehavior
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2633
     */
    @Test
    void testLabelAjaxBehavior() throws Exception {
        WebPage page = getPage("issue2633-label.xhtml");

        fireMouseOver(page, "label2");

        String status = page.findElement(By.id("statusArea")).getAttribute("value");
        assertTrue(status.contains("label2 Event: begin"), "Ajax begin event fired");
        assertTrue(status.contains("label2 Event: complete"), "Ajax complete event fired");
        assertTrue(status.contains("label2 Event: success"), "Ajax success event fired");
    }
}
