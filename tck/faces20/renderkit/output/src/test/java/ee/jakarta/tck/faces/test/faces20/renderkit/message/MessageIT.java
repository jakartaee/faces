/*
 * Copyright (c) 2009, 2026 Oracle and/or its affiliates. All rights reserved.
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
package ee.jakarta.tck.faces.test.faces20.renderkit.message;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class MessageIT extends BaseITNG {

    @Test
    void messageRenderEncodeTest() {
        // Each case lives in its own h:form; open a fresh page per case to avoid form state bleed-through.

        // Case 1: showSummary=true, showDetail=true -> both summary and detail are rendered.
        submitAndAssertText(1, "form1:input1", "INFO",
            "INFO: Summary Message INFO: Detailed Message");

        // Case 2: styleClass="underline" is rendered as class attribute.
        submitAndAssertAttribute(2, "form2:input2", "INFO", "class", "underline");

        // Case 3: infoStyle+infoClass apply for INFO severity.
        submitAndAssertAttributes(3, "form3:input3", "INFO",
            Map.of("style", "Color: blue;", "class", "underline"));

        // Case 4: warnStyle+warnClass apply for WARN severity.
        submitAndAssertAttributes(4, "form4:input4", "WARN",
            Map.of("style", "Color: green;", "class", "underline"));

        // Case 5: errorStyle+errorClass apply for ERROR severity.
        submitAndAssertAttributes(5, "form5:input5", "ERROR",
            Map.of("style", "Color: yellow;", "class", "underline"));

        // Case 6: fatalStyle+fatalClass apply for FATAL severity.
        submitAndAssertAttributes(6, "form6:input6", "FATAL",
            Map.of("style", "Color: red;", "class", "underline"));

        // Case 7: each severity picks the matching *Style attribute.
        Map<String, String> severityStyles = new LinkedHashMap<>();
        severityStyles.put("INFO", "Color: blue;");
        severityStyles.put("WARN", "Color: green;");
        severityStyles.put("ERROR", "Color: yellow;");
        severityStyles.put("FATAL", "Color: red;");
        severityStyles.forEach((severity, expectedStyle) ->
            submitAndAssertAttribute(7, "form7:severity7", severity, "style", expectedStyle));

        // Case 8: each severity picks the matching *Class attribute.
        Map<String, String> severityClasses = new LinkedHashMap<>();
        severityClasses.put("INFO", "class_info");
        severityClasses.put("WARN", "class_warn");
        severityClasses.put("ERROR", "class_error");
        severityClasses.put("FATAL", "class_fatal");
        severityClasses.forEach((severity, expectedClass) ->
            submitAndAssertAttribute(8, "form8:severity8", severity, "class", expectedClass));

        // Case 9: showSummary=true, showDetail=true -> both rendered.
        submitAndAssertText(9, "form9:severity9", "INFO",
            "INFO: Summary Message INFO: Detailed Message");

        // Case 10: showSummary=true, showDetail=false -> only summary.
        submitAndAssertText(10, "form10:severity10", "INFO", "INFO: Summary Message");

        // Case 11: showSummary=false, showDetail=true -> only detail.
        submitAndAssertText(11, "form11:severity11", "INFO", "INFO: Detailed Message");

        // Case 12: showSummary=false, showDetail=false -> empty message.
        submitAndAssertText(12, "form12:severity12", "INFO", "");
    }

    @Test
    void messageRenderPassthroughTest() {
        Map<String, String> control = new LinkedHashMap<>();
        control.put("dir", "LTR");
        control.put("lang", "en");
        control.put("style", "Color: red;");
        control.put("title", "title");

        verifyPassthrough(getPage("faces/message/passthroughtest.xhtml"), control);

        Map<String, String> facelet = new LinkedHashMap<>(control);
        facelet.put("foo", "bar");
        facelet.put("singleatt", "singleAtt");
        facelet.put("manyattone", "manyOne");
        facelet.put("manyatttwo", "manyTwo");
        facelet.put("manyattthree", "manyThree");
        verifyPassthrough(getPage("faces/message/passthroughtest_facelet.xhtml"), facelet);
    }

    private void submitAndAssertText(int caseNumber, String inputId, String severity, String expectedText) {
        WebElement messageSpan = submitCase(caseNumber, inputId, severity);
        assertEquals(expectedText, messageSpan.getText().trim(),
            "Case " + caseNumber + " message text");
    }

    private void submitAndAssertAttribute(int caseNumber, String inputId, String severity,
                                          String attribute, String expectedValue) {
        WebElement messageSpan = submitCase(caseNumber, inputId, severity);
        assertEquals(expectedValue, messageSpan.getDomAttribute(attribute),
            "Case " + caseNumber + " message " + attribute);
    }

    private void submitAndAssertAttributes(int caseNumber, String inputId, String severity,
                                           Map<String, String> expectedAttributes) {
        WebElement messageSpan = submitCase(caseNumber, inputId, severity);
        expectedAttributes.forEach((name, value) ->
            assertEquals(value, messageSpan.getDomAttribute(name),
                "Case " + caseNumber + " message " + name));
    }

    private WebElement submitCase(int caseNumber, String inputId, String severity) {
        WebPage page = getPage("faces/message/encodetest.xhtml");
        String idField = "id" + caseNumber;
        String severityField = caseNumber >= 7 ? inputId.substring(inputId.indexOf(':') + 1) : "input" + caseNumber;

        fillAndSubmit(page, idField, inputId, severityField, severity, "button" + caseNumber);
        return findByIdSuffix(page, "message" + caseNumber);
    }

    private static void fillAndSubmit(WebPage page, String idField, String forValue,
                                      String severityField, String severityValue, String buttonId) {
        WebElement idInput = findByIdSuffix(page, idField);
        idInput.clear();
        idInput.sendKeys(forValue);

        WebElement severityInput = findByIdSuffix(page, severityField);
        severityInput.clear();
        severityInput.sendKeys(severityValue);

        WebElement button = findByIdSuffix(page, buttonId);
        page.guardHttp(button::click);
    }

    private static void verifyPassthrough(WebPage page, Map<String, String> expected) {
        WebElement button = findByIdSuffix(page, "button1");
        page.guardHttp(button::click);
        WebElement message = findByIdSuffix(page, "message");
        expected.forEach((name, value) ->
            assertEquals(value, message.getDomAttribute(name), "message attribute " + name));
    }

    private static WebElement findByIdSuffix(WebPage page, String id) {
        String suffix = ":" + id;
        return page.findElement(By.xpath(
            "//*[@id='" + id + "'"
            + " or substring(@id, string-length(@id) - " + (suffix.length() - 1) + ") = '" + suffix + "']"));
    }
}
