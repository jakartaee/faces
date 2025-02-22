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
package ee.jakarta.tck.faces.test.faces50.facesConfigContextParam;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;

/**
 * https://github.com/jakartaee/faces/issues/1416
 */
class Spec1416IT extends BaseITNG {

    @FindBy(id = "projectStage")
    WebElement projectStage;

    @FindBy(id = "alwaysPerformValidationWhenRequiredIsTrue")
    WebElement alwaysPerformValidationWhenRequiredIsTrue;

    @FindBy(id = "datetimeConverterDefaultTimezoneIsSystemTimezone")
    WebElement datetimeConverterDefaultTimezoneIsSystemTimezone;

    @FindBy(id = "enableValidateWholeBean")
    WebElement enableValidateWholeBean;

    @FindBy(id = "disableDefaultBeanValidator")
    WebElement disableDefaultBeanValidator;

    @FindBy(id = "automaticExtensionlessMapping")
    WebElement automaticExtensionlessMapping;

    @FindBy(id = "faceletsSkipComments")
    WebElement faceletsSkipComments;
    
    @FindBy(id = "faceletsRefreshPeriod")
    WebElement faceletsRefreshPeriod;

    @Test
    void testContextParams() {
        getPage("spec1416.xhtml");
        assertAll(
            () -> assertEquals("Development", projectStage.getText(), "projectStage"),
            () -> assertEquals("true", alwaysPerformValidationWhenRequiredIsTrue.getText(), "alwaysPerformValidationWhenRequiredIsTrue"),
            () -> assertEquals("false", datetimeConverterDefaultTimezoneIsSystemTimezone.getText(), "datetimeConverterDefaultTimezoneIsSystemTimezone"),
            () -> assertEquals("false", enableValidateWholeBean.getText(), "enableValidateWholeBean"),
            () -> assertEquals("false", disableDefaultBeanValidator.getText(), "disableDefaultBeanValidator"),
            () -> assertEquals("true", automaticExtensionlessMapping.getText(), "automaticExtensionlessMapping"),
            () -> assertEquals("false", faceletsSkipComments.getText(), "faceletsSkipComments"),
            () -> assertEquals("0", faceletsRefreshPeriod.getText(), "faceletsRefreshPeriod")
        );
    }
}
