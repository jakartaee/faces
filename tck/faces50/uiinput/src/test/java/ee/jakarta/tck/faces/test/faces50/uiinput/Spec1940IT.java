/*
 * Copyright (c) Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the Apache License, Version 2.0
 * which is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GPL-2.0 with Classpath-exception-2.0 which
 * is available at https://openjdk.java.net/legal/gplv2+ce.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0 or Apache-2.0
 */
package ee.jakarta.tck.faces.test.faces50.uiinput;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

/**
 * https://github.com/jakartaee/faces/issues/1940
 */
class Spec1940IT extends BaseITNG {

    @FindBy(id = "form:input")
    private WebElement input;

    @FindBy(id = "form:submitAndRerender")
    private WebElement submitAndRerender;

    @FindBy(id = "form:rerenderWithoutResetValues")
    private WebElement rerenderWithoutResetValues;

    @FindBy(id = "form:rerenderWithResetValuesAttribute")
    private WebElement rerenderWithResetValuesAttribute;

    @FindBy(id = "form:rerenderWithResetValuesAndClearModelAttributes")
    private WebElement rerenderWithResetValuesAndClearModelAttributes;

    @FindBy(id = "form:rerenderWithClearModelAttributeOnly")
    private WebElement rerenderWithClearModelAttributeOnly;

    @FindBy(id = "form:rerenderWithResetValuesTag")
    private WebElement rerenderWithResetValuesTag;

    @FindBy(id = "form:rerenderWithResetValuesTagAndClearModelAttribute")
    private WebElement rerenderWithResetValuesTagAndClearModelAttribute;

    @FindBy(id = "form:inputSubmittedValue")
    private WebElement inputSubmittedValue;

    @FindBy(id = "form:inputValid")
    private WebElement inputValid;

    @FindBy(id = "form:inputLocalValueSet")
    private WebElement inputLocalValueSet;

    @FindBy(id = "form:inputValue")
    private WebElement inputValue;

    @FindBy(id = "form:modelValue")
    private WebElement modelValue;

    @FindBy(id = "form:message")
    private WebElement message;

    @Test
    void submitEmptyInputAndRerenderWithoutResetValues() {
        submitEmptyInput().guardAjax(rerenderWithoutResetValues::click);

        assertEquals("", input.getDomProperty("value"));
        assertEquals("", inputSubmittedValue.getText());
        assertEquals("false", inputValid.getText());
        assertEquals("false", inputLocalValueSet.getText());
        assertEquals("", inputValue.getText());
        assertEquals("", modelValue.getText());
        assertEquals("", message.getText());
    }

    @Test
    void submitFilledInputAndRerenderWithoutResetValues() {
        submitFilledInput().guardAjax(rerenderWithoutResetValues::click);

        assertEquals("value", input.getDomProperty("value"));
        assertEquals("", inputSubmittedValue.getText());
        assertEquals("true", inputValid.getText());
        assertEquals("false", inputLocalValueSet.getText());
        assertEquals("value", inputValue.getText());
        assertEquals("value", modelValue.getText());
        assertEquals("", message.getText());
    }

    @Test
    void submitEmptyInputAndRerenderWithResetValuesAttribute() {
        submitEmptyInput().guardAjax(rerenderWithResetValuesAttribute::click);

        assertEquals("", input.getDomProperty("value"));
        assertEquals("", inputSubmittedValue.getText());
        assertEquals("true", inputValid.getText());
        assertEquals("false", inputLocalValueSet.getText());
        assertEquals("", inputValue.getText());
        assertEquals("", modelValue.getText());
        assertEquals("", message.getText());
    }

    @Test
    void submitFilledInputAndRerenderWithResetValuesAttribute() {
        submitFilledInput().guardAjax(rerenderWithResetValuesAttribute::click);

        assertEquals("value", input.getDomProperty("value"));
        assertEquals("", inputSubmittedValue.getText());
        assertEquals("true", inputValid.getText());
        assertEquals("false", inputLocalValueSet.getText());
        assertEquals("value", inputValue.getText());
        assertEquals("value", modelValue.getText());
        assertEquals("", message.getText());
    }

    @Test
    void submitEmptyInputAndRerenderWithResetValuesAndClearModelAttributes() {
        submitEmptyInput().guardAjax(rerenderWithResetValuesAndClearModelAttributes::click);

        assertEquals("", input.getDomProperty("value"));
        assertEquals("", inputSubmittedValue.getText());
        assertEquals("true", inputValid.getText());
        assertEquals("false", inputLocalValueSet.getText());
        assertEquals("", inputValue.getText());
        assertEquals("", modelValue.getText());
        assertEquals("", message.getText());
    }

    @Test
    void submitFilledInputAndRerenderWithResetValuesAndClearModelAttributes() {
        submitFilledInput().guardAjax(rerenderWithResetValuesAndClearModelAttributes::click);

        assertEquals("", input.getDomProperty("value"));
        assertEquals("", inputSubmittedValue.getText());
        assertEquals("true", inputValid.getText());
        assertEquals("false", inputLocalValueSet.getText());
        assertEquals("", inputValue.getText());
        assertEquals("", modelValue.getText());
        assertEquals("", message.getText());
    }

    @Test
    void submitEmptyInputAndRerenderWithClearModelAttributeOnly() {
        submitEmptyInput().guardAjax(rerenderWithClearModelAttributeOnly::click);

        assertEquals("", input.getDomProperty("value"));
        assertEquals("", inputSubmittedValue.getText());
        assertEquals("false", inputValid.getText());
        assertEquals("false", inputLocalValueSet.getText());
        assertEquals("", inputValue.getText());
        assertEquals("", modelValue.getText());
        assertEquals("", message.getText());
    }

    @Test
    void submitFilledInputAndRerenderWithClearModelAttributeOnly() {
        submitFilledInput().guardAjax(rerenderWithClearModelAttributeOnly::click);

        assertEquals("value", input.getDomProperty("value"));
        assertEquals("", inputSubmittedValue.getText());
        assertEquals("true", inputValid.getText());
        assertEquals("false", inputLocalValueSet.getText());
        assertEquals("value", inputValue.getText());
        assertEquals("value", modelValue.getText());
        assertEquals("", message.getText());
    }

    @Test
    void submitEmptyInputAndRerenderWithResetValuesTag() {
        submitEmptyInput().guardAjax(rerenderWithResetValuesTag::click);

        assertEquals("", input.getDomProperty("value"));
        assertEquals("", inputSubmittedValue.getText());
        assertEquals("true", inputValid.getText());
        assertEquals("false", inputLocalValueSet.getText());
        assertEquals("", inputValue.getText());
        assertEquals("", modelValue.getText());
        assertEquals("", message.getText());
    }

    @Test
    void submitFilledInputAndRerenderWithResetValuesTag() {
        submitFilledInput().guardAjax(rerenderWithResetValuesTag::click);

        assertEquals("value", input.getDomProperty("value"));
        assertEquals("", inputSubmittedValue.getText());
        assertEquals("true", inputValid.getText());
        assertEquals("false", inputLocalValueSet.getText());
        assertEquals("value", inputValue.getText());
        assertEquals("value", modelValue.getText());
        assertEquals("", message.getText());
    }

    @Test
    void submitEmptyInputAndRerenderWithResetValuesTagAndClearModelAttribute() {
        submitEmptyInput().guardAjax(rerenderWithResetValuesTagAndClearModelAttribute::click);

        assertEquals("", input.getDomProperty("value"));
        assertEquals("", inputSubmittedValue.getText());
        assertEquals("true", inputValid.getText());
        assertEquals("false", inputLocalValueSet.getText());
        assertEquals("", inputValue.getText());
        assertEquals("", modelValue.getText());
        assertEquals("", message.getText());
    }

    @Test
    void submitFilledInputAndRerenderWithResetValuesTagAndClearModelAttribute() {
        submitFilledInput().guardAjax(rerenderWithResetValuesTagAndClearModelAttribute::click);

        assertEquals("", input.getDomProperty("value"));
        assertEquals("", inputSubmittedValue.getText());
        assertEquals("true", inputValid.getText());
        assertEquals("false", inputLocalValueSet.getText());
        assertEquals("", inputValue.getText());
        assertEquals("", modelValue.getText());
        assertEquals("", message.getText());
    }

    private WebPage submitEmptyInput() {
        var page = getPage("spec1940.xhtml");
        assertEquals("", input.getDomProperty("value"));
        assertEquals("", inputSubmittedValue.getText());
        assertEquals("true", inputValid.getText());
        assertEquals("false", inputLocalValueSet.getText());
        assertEquals("", inputValue.getText());
        assertEquals("", modelValue.getText());
        assertEquals("", message.getText());

        page.guardAjax(submitAndRerender::click);

        assertEquals("", input.getDomProperty("value"));
        assertEquals("", inputSubmittedValue.getText());
        assertEquals("false", inputValid.getText());
        assertEquals("false", inputLocalValueSet.getText());
        assertEquals("", inputValue.getText());
        assertEquals("", modelValue.getText());
        assertFalse(message.getText().isEmpty());

        return page;
    }

    private WebPage submitFilledInput() {
        var page = getPage("spec1940.xhtml");
        assertEquals("", input.getDomProperty("value"));
        assertEquals("", inputSubmittedValue.getText());
        assertEquals("true", inputValid.getText());
        assertEquals("false", inputLocalValueSet.getText());
        assertEquals("", inputValue.getText());
        assertEquals("", modelValue.getText());
        assertEquals("", message.getText());

        input.sendKeys("value");
        page.guardAjax(submitAndRerender::click);

        assertEquals("value", input.getDomProperty("value"));
        assertEquals("", inputSubmittedValue.getText());
        assertEquals("true", inputValid.getText());
        assertEquals("false", inputLocalValueSet.getText());
        assertEquals("value", inputValue.getText());
        assertEquals("value", modelValue.getText());
        assertEquals("", message.getText());

        return page;
    }

}
