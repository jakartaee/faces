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
package ee.jakarta.tck.faces.faces50.misc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.faces.component.UIComponent;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;

/**
 * A <code>rendered</code> expression referring to <code>#{component}</code> resolves to the component itself in every phase, not to its parent.
 */
class Spec1002IT extends BaseITNG {

    @FindBy(id = "form:input")
    private WebElement input;

    @FindBy(id = "form:repeat:0:repeatedInput")
    private WebElement repeatedInput;

    @FindBy(id = "form:submit")
    private WebElement submit;

    @FindBy(id = "form:ajax")
    private WebElement ajax;

    @FindBy(id = "form:repeat:0:ajaxInsideRepeat")
    private WebElement ajaxInsideRepeat;

    @FindBy(id = "value")
    private WebElement value;

    @FindBy(id = "repeatedValue")
    private WebElement repeatedValue;

    /**
     * @see UIComponent#encodeAll(jakarta.faces.context.FacesContext)
     * @see https://github.com/jakartaee/faces/issues/1002
     */
    @Test
    void renderResponse() {
        getPage("spec1002.xhtml");
        assertEquals("", input.getAttribute("value"));
        assertEquals("", value.getText());
        assertEquals("", repeatedInput.getAttribute("value"));
    }

    /**
     * @see UIComponent#processDecodes(jakarta.faces.context.FacesContext)
     * @see UIComponent#processValidators(jakarta.faces.context.FacesContext)
     * @see UIComponent#processUpdates(jakarta.faces.context.FacesContext)
     * @see https://github.com/jakartaee/faces/issues/1002
     */
    @Test
    void submit() {
        var page = getPage("spec1002.xhtml");
        input.sendKeys("submitted");
        page.guardHttp(submit::click);
        assertEquals("submitted", value.getText());
    }

    /**
     * @see UIComponent#processDecodes(jakarta.faces.context.FacesContext)
     * @see UIComponent#processValidators(jakarta.faces.context.FacesContext)
     * @see UIComponent#processUpdates(jakarta.faces.context.FacesContext)
     * @see https://github.com/jakartaee/faces/issues/1002
     */
    @Test
    void submitInsideRepeat() {
        var page = getPage("spec1002.xhtml");
        repeatedInput.sendKeys("submittedInsideRepeat");
        page.guardHttp(submit::click);
        assertEquals("submittedInsideRepeat", repeatedValue.getText());
    }

    /**
     * @see UIComponent#visitTree(jakarta.faces.component.visit.VisitContext, jakarta.faces.component.visit.VisitCallback)
     * @see https://github.com/jakartaee/faces/issues/1002
     */
    @Test
    void ajaxSubmit() {
        var page = getPage("spec1002.xhtml");
        input.sendKeys("ajaxSubmitted");
        page.guardAjax(ajax::click);
        assertEquals("ajaxSubmitted", value.getText());
    }

    /**
     * @see UIComponent#visitTree(jakarta.faces.component.visit.VisitContext, jakarta.faces.component.visit.VisitCallback)
     * @see https://github.com/jakartaee/faces/issues/1002
     */
    @Test
    void ajaxSubmitInsideRepeat() {
        var page = getPage("spec1002.xhtml");
        repeatedInput.sendKeys("ajaxSubmittedInsideRepeat");
        page.guardAjax(ajaxInsideRepeat::click);
        assertEquals("ajaxSubmittedInsideRepeat", repeatedValue.getText());
    }

}
