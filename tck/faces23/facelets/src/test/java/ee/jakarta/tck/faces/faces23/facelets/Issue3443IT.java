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

import jakarta.faces.component.UIComponent;
import jakarta.faces.component.html.HtmlInputText;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue3443IT extends BaseITNG {

    /**
     * Verifies that {@code UIComponent.getCurrentComponent} during Update Model Values returns the input component whose
     * value is being applied, so the bean setter observes the {@code HtmlInputText} as the current component.
     *
     * @see UIComponent#getCurrentComponent(jakarta.faces.context.FacesContext)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3443
     */
    @Test
    void testInputTextCurrentComponent() {
        WebPage page = getPage("issue3443.xhtml");

        WebElement input = page.findElement(By.id("form:inputText"));
        input.sendKeys("x");
        page.guardHttp(page.findElement(By.id("form:submit"))::click);

        assertEquals(HtmlInputText.class.getName(),
                page.findElement(By.id("form:inputText")).getAttribute("value"),
                "Current component seen by the setter must be the HtmlInputText being updated");
    }
}
