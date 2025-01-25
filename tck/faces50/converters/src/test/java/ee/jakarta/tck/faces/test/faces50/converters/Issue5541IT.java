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
package ee.jakarta.tck.faces.test.faces50.converters;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;

class Issue5541IT extends BaseITNG {

    @FindBy(id = "form:inputDate")
    private WebElement inputDate;

    @FindBy(id = "form:inputDateTime")
    private WebElement inputDateTime;

    @FindBy(id = "form:inputLocalDate")
    private WebElement inputLocalDate;

    @FindBy(id = "form:inputLocalTime")
    private WebElement inputLocalTime;

    @FindBy(id = "form:inputLocalDateTime")
    private WebElement inputLocalDateTime;

    @FindBy(id = "form:inputZonedDateTime")
    private WebElement inputZonedDateTime;

    @FindBy(id = "form:submit")
    private WebElement submit;

    @FindBy(id = "form:outputDate")
    private WebElement outputDate;

    @FindBy(id = "form:outputDateTime")
    private WebElement outputDateTime;

    @FindBy(id = "form:outputLocalDate")
    private WebElement outputLocalDate;

    @FindBy(id = "form:outputLocalTime")
    private WebElement outputLocalTime;

    @FindBy(id = "form:outputLocalDateTime")
    private WebElement outputLocalDateTime;

    @FindBy(id = "form:outputZonedDateTime")
    private WebElement outputZonedDateTime;

    @FindBy(id = "form:messages")
    private WebElement messages;

    /**
     * https://github.com/eclipse-ee4j/mojarra/issues/5541
     */
    @Test
    void testValidDate() {
        var page = getPage("issue5541.xhtml");
        inputDate.sendKeys("2024-06-30");
        page.guardHttp(submit::click);
        assertAll(
            () -> assertEquals("2024-06-30", outputDate.getText()),
            () -> assertEquals("", messages.getText())
        );
    }
    /**
     * https://github.com/eclipse-ee4j/mojarra/issues/5541
     */
    @Test
    void testInvalidDate() {
        var page = getPage("issue5541.xhtml");
        inputDate.sendKeys("2024-06-31");
        page.guardHttp(submit::click);
        assertAll(
            () -> assertEquals("", outputDate.getText()),
            () -> assertEquals("form:inputDate: '2024-06-31' could not be understood as a date.", messages.getText())
        );
    }

    /**
     * https://github.com/eclipse-ee4j/mojarra/issues/5541
     */
    @Test
    void testValidDateTime() {
        var page = getPage("issue5541.xhtml");
        inputDateTime.sendKeys("2024-06-30 12:34:56");
        page.guardHttp(submit::click);
        assertAll(
            () -> assertEquals("2024-06-30 12:34:56", outputDateTime.getText()),
            () -> assertEquals("", messages.getText())
        );
    }

    /**
     * https://github.com/eclipse-ee4j/mojarra/issues/5541
     */
    @Test
    void testInvalidDateTime() {
        var page = getPage("issue5541.xhtml");
        inputDateTime.sendKeys("2024-06-30 23:45:67");
        page.guardHttp(submit::click);
        assertAll(
            () -> assertEquals("", outputDateTime.getText()),
            () -> assertEquals("form:inputDateTime: '2024-06-30 23:45:67' could not be understood as a date.", messages.getText())
        );
    }

    /**
     * https://github.com/eclipse-ee4j/mojarra/issues/5541
     */
    @Test
    void testValidLocalDate() {
        var page = getPage("issue5541.xhtml");
        inputLocalDate.sendKeys("2024-06-30");
        page.guardHttp(submit::click);
        assertAll(
            () -> assertEquals("2024-06-30", outputLocalDate.getText()),
            () -> assertEquals("", messages.getText())
        );
    }

    /**
     * https://github.com/eclipse-ee4j/mojarra/issues/5541
     */
    @Test
    void testValidLocalDateBce() {
        var page = getPage("issue5541.xhtml?acceptBce=true");
        inputLocalDate.sendKeys("2024-06-30");
        page.guardHttp(submit::click);
        assertAll(
            () -> assertEquals("2024-06-30", outputLocalDate.getText()),
            () -> assertEquals("", messages.getText())
        );
    }

    /**
     * https://github.com/eclipse-ee4j/mojarra/issues/5541
     */
    @Test
    void testValidLocalDateBceNegative() {
        var page = getPage("issue5541.xhtml?acceptBce=true");
        inputLocalDate.sendKeys("-2024-06-30");
        page.guardHttp(submit::click);
        assertAll(
            () -> assertEquals("-2024-06-30", outputLocalDate.getText()),
            () -> assertEquals("", messages.getText())
        );
    }

    /**
     * https://github.com/eclipse-ee4j/mojarra/issues/5541
     */
    @Test
    void testInvalidLocalDate() {
        var page = getPage("issue5541.xhtml");
        inputLocalDate.sendKeys("2024-06-31");
        page.guardHttp(submit::click);
        assertAll(
            () -> assertEquals("", outputLocalDate.getText()),
            () -> assertEquals("form:inputLocalDate: '2024-06-31' could not be understood as a date.", messages.getText())
        );
    }

    /**
     * https://github.com/eclipse-ee4j/mojarra/issues/5541
     */
    @Test
    void testInvalidLocalDateBce() {
        var page = getPage("issue5541.xhtml?acceptBce=true");
        inputLocalDate.sendKeys("2024-06-31");
        page.guardHttp(submit::click);
        assertAll(
            () -> assertEquals("", outputLocalDate.getText()),
            () -> assertEquals("form:inputLocalDate: '2024-06-31' could not be understood as a date.", messages.getText())
        );
    }

    /**
     * https://github.com/eclipse-ee4j/mojarra/issues/5541
     */
    @Test
    void testInvalidLocalDateBceNegative() {
        var page = getPage("issue5541.xhtml?acceptBce=true");
        inputLocalDate.sendKeys("-2024-06-31");
        page.guardHttp(submit::click);
        assertAll(
            () -> assertEquals("", outputLocalDate.getText()),
            () -> assertEquals("form:inputLocalDate: '-2024-06-31' could not be understood as a date.", messages.getText())
        );
    }

    /**
     * https://github.com/eclipse-ee4j/mojarra/issues/5541
     */
    @Test
    void testValidLocalTime() {
        var page = getPage("issue5541.xhtml");
        inputLocalTime.sendKeys("12:34:56");
        page.guardHttp(submit::click);
        assertAll(
            () -> assertEquals("12:34:56", outputLocalTime.getText()),
            () -> assertEquals("", messages.getText())
        );
    }

    /**
     * https://github.com/eclipse-ee4j/mojarra/issues/5541
     */
    @Test
    void testInvalidLocalTime() {
        var page = getPage("issue5541.xhtml");
        inputLocalTime.sendKeys("23:45:67");
        page.guardHttp(submit::click);
        assertAll(
            () -> assertEquals("", outputLocalTime.getText()),
            () -> assertEquals("form:inputLocalTime: '23:45:67' could not be understood as a time.", messages.getText())
        );
    }

    /**
     * https://github.com/eclipse-ee4j/mojarra/issues/5541
     */
    @Test
    void testValidLocalDateTime() {
        var page = getPage("issue5541.xhtml");
        inputLocalDateTime.sendKeys("2024-06-30 12:34:56");
        page.guardHttp(submit::click);
        assertAll(
            () -> assertEquals("2024-06-30 12:34:56", outputLocalDateTime.getText()),
            () -> assertEquals("", messages.getText())
        );
    }

    /**
     * https://github.com/eclipse-ee4j/mojarra/issues/5541
     */
    @Test
    void testValidLocalDateTimeBce() {
        var page = getPage("issue5541.xhtml?acceptBce=true");
        inputLocalDateTime.sendKeys("2024-06-30 12:34:56");
        page.guardHttp(submit::click);
        assertAll(
            () -> assertEquals("2024-06-30 12:34:56", outputLocalDateTime.getText()),
            () -> assertEquals("", messages.getText())
        );
    }

    /**
     * https://github.com/eclipse-ee4j/mojarra/issues/5541
     */
    @Test
    void testValidLocalDateTimeBceNegative() {
        var page = getPage("issue5541.xhtml?acceptBce=true");
        inputLocalDateTime.sendKeys("-2024-06-30 12:34:56");
        page.guardHttp(submit::click);
        assertAll(
            () -> assertEquals("-2024-06-30 12:34:56", outputLocalDateTime.getText()),
            () -> assertEquals("", messages.getText())
        );
    }

    /**
     * https://github.com/eclipse-ee4j/mojarra/issues/5541
     */
    @Test
    void testInvalidLocalDateTime() {
        var page = getPage("issue5541.xhtml");
        inputLocalDateTime.sendKeys("2024-06-30 23:45:67");
        page.guardHttp(submit::click);
        assertAll(
            () -> assertEquals("", outputLocalDateTime.getText()),
            () -> assertEquals("form:inputLocalDateTime: '2024-06-30 23:45:67' could not be understood as a date and time.", messages.getText())
        );
    }

    /**
     * https://github.com/eclipse-ee4j/mojarra/issues/5541
     */
    @Test
    void testInvalidLocalDateTimeBce() {
        var page = getPage("issue5541.xhtml?acceptBce=true");
        inputLocalDateTime.sendKeys("2024-06-30 23:45:67");
        page.guardHttp(submit::click);
        assertAll(
            () -> assertEquals("", outputLocalDateTime.getText()),
            () -> assertEquals("form:inputLocalDateTime: '2024-06-30 23:45:67' could not be understood as a date and time.", messages.getText())
        );
    }

    /**
     * https://github.com/eclipse-ee4j/mojarra/issues/5541
     */
    @Test
    void testInvalidLocalDateTimeBceNegative() {
        var page = getPage("issue5541.xhtml?acceptBce=true");
        inputLocalDateTime.sendKeys("-2024-06-30 23:45:67");
        page.guardHttp(submit::click);
        assertAll(
            () -> assertEquals("", outputLocalDateTime.getText()),
            () -> assertEquals("form:inputLocalDateTime: '-2024-06-30 23:45:67' could not be understood as a date and time.", messages.getText())
        );
    }

    /**
     * https://github.com/eclipse-ee4j/mojarra/issues/5541
     */
    @Test
    void testValidZonedDateTime() {
        var page = getPage("issue5541.xhtml");
        inputZonedDateTime.sendKeys("2024-06-30 12 uur 34 min +0130");
        page.guardHttp(submit::click);
        assertAll(
            () -> assertEquals("2024-06-30 12 uur 34 min +0130", outputZonedDateTime.getText()),
            () -> assertEquals("", messages.getText())
        );
    }

    /**
     * https://github.com/eclipse-ee4j/mojarra/issues/5541
     */
    @Test
    void testValidZonedDateTimeBce() {
        var page = getPage("issue5541.xhtml?acceptBce=true");
        inputZonedDateTime.sendKeys("2024-06-30 12 uur 34 min +0130");
        page.guardHttp(submit::click);
        assertAll(
            () -> assertEquals("2024-06-30 12 uur 34 min +0130", outputZonedDateTime.getText()),
            () -> assertEquals("", messages.getText())
        );
    }

    /**
     * https://github.com/eclipse-ee4j/mojarra/issues/5541
     */
    @Test
    void testValidZonedDateTimeBceNegative() {
        var page = getPage("issue5541.xhtml?acceptBce=true");
        inputZonedDateTime.sendKeys("-2024-06-30 12 uur 34 min +0130");
        page.guardHttp(submit::click);
        assertAll(
            () -> assertEquals("-2024-06-30 12 uur 34 min +0130", outputZonedDateTime.getText()),
            () -> assertEquals("", messages.getText())
        );
    }

    /**
     * https://github.com/eclipse-ee4j/mojarra/issues/5541
     */
    @Test
    void testInvalidZonedDateTime() {
        var page = getPage("issue5541.xhtml");
        inputZonedDateTime.sendKeys("2024-06-31 12 uur 34 min +0130");
        page.guardHttp(submit::click);
        assertAll(
            () -> assertEquals("", outputZonedDateTime.getText()),
            () -> assertEquals("form:inputZonedDateTime: '2024-06-31 12 uur 34 min +0130' could not be understood as a date and time.", messages.getText())
        );
    }

    /**
     * https://github.com/eclipse-ee4j/mojarra/issues/5541
     */
    @Test
    void testInvalidZonedDateTimeBce() {
        var page = getPage("issue5541.xhtml?acceptBce=true");
        inputZonedDateTime.sendKeys("2024-06-31 12 uur 34 min +0130");
        page.guardHttp(submit::click);
        assertAll(
            () -> assertEquals("", outputZonedDateTime.getText()),
            () -> assertEquals("form:inputZonedDateTime: '2024-06-31 12 uur 34 min +0130' could not be understood as a date and time.", messages.getText())
        );
    }

    /**
     * https://github.com/eclipse-ee4j/mojarra/issues/5541
     */
    @Test
    void testInvalidZonedDateTimeBceNegative() {
        var page = getPage("issue5541.xhtml?acceptBce=true");
        inputZonedDateTime.sendKeys("-2024-06-31 12 uur 34 min +0130");
        page.guardHttp(submit::click);
        assertAll(
            () -> assertEquals("", outputZonedDateTime.getText()),
            () -> assertEquals("form:inputZonedDateTime: '-2024-06-31 12 uur 34 min +0130' could not be understood as a date and time.", messages.getText())
        );
    }
}
