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
package ee.jakarta.tck.faces.test.faces20.renderkit.messages;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class MessagesIT extends BaseITNG {

    private static final String SUMMARY_INFO = "INFO: Summary Message";
    private static final String DETAIL_INFO = "INFO: Detailed Message";

    @Test
    void messagesRenderEncodeTest() {
        // Cases 1-11 render as ul/li (default layout); Cases 12-16 render as table/tr/td (layout="table").

        // Case 1: summary + detail rendered for both messages.
        assertListTexts(submitListCase(1, "form1:input1", "MESSAGES_INFO"),
            SUMMARY_INFO + "_One " + DETAIL_INFO + "_One",
            SUMMARY_INFO + "_Two " + DETAIL_INFO + "_Two");

        // Case 2: styleClass="underline" applied to the <ul>.
        assertEquals("underline",
            submitListCase(2, "form2:input2", "MESSAGES_INFO").getDomAttribute("class"),
            "Case 2 ul class");

        // Case 3: infoStyle+infoClass applied to each <li>.
        assertListItemAttributes(submitListCase(3, "form3:input3", "MESSAGES_INFO"),
            "Color: blue;", "underline");

        // Case 4: warnStyle+warnClass applied to each <li>.
        assertListItemAttributes(submitListCase(4, "form4:input4", "MESSAGES_WARN"),
            "Color: green;", "underline");

        // Case 5: errorStyle+errorClass applied to each <li>.
        assertListItemAttributes(submitListCase(5, "form5:input5", "MESSAGES_ERROR"),
            "Color: yellow;", "underline");

        // Case 6: fatalStyle+fatalClass applied to each <li>.
        assertListItemAttributes(submitListCase(6, "form6:input6", "MESSAGES_FATAL"),
            "Color: red;", "underline");

        // Case 7: matching *Style attribute applied to each <li> per severity.
        assertListItemStyleBySeverity(7, "form7:severity7", Map.of(
            "MESSAGES_INFO", "Color: blue;",
            "MESSAGES_WARN", "Color: green;",
            "MESSAGES_ERROR", "Color: yellow;",
            "MESSAGES_FATAL", "Color: red;"));

        // Case 8: matching *Class attribute applied to each <li> per severity.
        assertListItemClassBySeverity(8, "form8:severity8", Map.of(
            "MESSAGES_INFO", "class_info",
            "MESSAGES_WARN", "class_warn",
            "MESSAGES_ERROR", "class_error",
            "MESSAGES_FATAL", "class_fatal"));

        // Case 9: showSummary=true, showDetail=false -> only summary lines.
        assertListTexts(submitListCase(9, "form9:severity9", "MESSAGES_INFO"),
            SUMMARY_INFO + "_One", SUMMARY_INFO + "_Two");

        // Case 10: showSummary=false, showDetail=true -> only detail lines.
        assertListTexts(submitListCase(10, "form10:severity10", "MESSAGES_INFO"),
            DETAIL_INFO + "_One", DETAIL_INFO + "_Two");

        // Case 11: showSummary=false, showDetail=false -> empty li entries.
        assertListTexts(submitListCase(11, "form11:severity11", "MESSAGES_INFO"), "", "");

        // Case 12: layout="table" with summary+detail.
        assertTableTexts(submitTableCase(12, "form12:input12", "MESSAGES_INFO"),
            SUMMARY_INFO + "_One " + DETAIL_INFO + "_One",
            SUMMARY_INFO + "_Two " + DETAIL_INFO + "_Two");

        // Case 13: styleClass="underline" applied to the <table>.
        assertEquals("underline",
            submitTableCase(13, "form13:input13", "MESSAGES_INFO").getDomAttribute("class"),
            "Case 13 table class");

        // Case 14: layout="table" with summary only.
        assertTableTexts(submitTableCase(14, "form14:input14", "MESSAGES_INFO"),
            SUMMARY_INFO + "_One", SUMMARY_INFO + "_Two");

        // Case 15: layout="table" with detail only.
        assertTableTexts(submitTableCase(15, "form15:input15", "MESSAGES_INFO"),
            DETAIL_INFO + "_One", DETAIL_INFO + "_Two");

        // Case 16: layout="table" with neither -> empty cells.
        assertTableTexts(submitTableCase(16, "form16:input16", "MESSAGES_INFO"), "", "");
    }

    @Test
    void messagesRenderPassthroughTest() {
        Map<String, String> control = new LinkedHashMap<>();
        control.put("dir", "LTR");
        control.put("lang", "en");
        control.put("style", "Color: red;");
        control.put("title", "title");

        verifyPassthrough(getPage("faces/messages/passthroughtest.xhtml"), control);

        Map<String, String> facelet = new LinkedHashMap<>(control);
        facelet.put("foo", "bar");
        facelet.put("singleatt", "singleAtt");
        facelet.put("manyattone", "manyOne");
        facelet.put("manyatttwo", "manyTwo");
        facelet.put("manyattthree", "manyThree");
        verifyPassthrough(getPage("faces/messages/passthroughtest_facelet.xhtml"), facelet);
    }

    private WebElement submitListCase(int caseNumber, String forValue, String severity) {
        return submitCase(caseNumber, forValue, severity, "ul");
    }

    private WebElement submitTableCase(int caseNumber, String forValue, String severity) {
        return submitCase(caseNumber, forValue, severity, "table");
    }

    private WebElement submitCase(int caseNumber, String forValue, String severity, String expectedTagName) {
        WebPage page = getPage("faces/messages/encodetest.xhtml");
        String severityField = caseNumber >= 7 && caseNumber <= 11
            ? "severity" + caseNumber
            : "input" + caseNumber;

        WebElement idInput = findByIdSuffix(page, "id" + caseNumber);
        idInput.clear();
        idInput.sendKeys(forValue);

        WebElement severityInput = findByIdSuffix(page, severityField);
        severityInput.clear();
        severityInput.sendKeys(severity);

        WebElement button = findByIdSuffix(page, "button" + caseNumber);
        page.guardHttp(button::click);

        WebElement messages = findByIdSuffix(page, "message" + caseNumber);
        assertEquals(expectedTagName, messages.getTagName(),
            "Case " + caseNumber + " messages tag");
        return messages;
    }

    private static void assertListTexts(WebElement ul, String firstExpected, String secondExpected) {
        Set<String> actual = ul.findElements(By.tagName("li")).stream()
            .map(WebElement::getText).map(String::trim)
            .collect(Collectors.toCollection(TreeSet::new));
        Set<String> expected = new TreeSet<>();
        expected.add(firstExpected);
        expected.add(secondExpected);
        assertTrue(actual.size() <= 2, "More than two list items rendered: " + actual);
        assertEquals(expected, actual, "list item texts");
    }

    private static void assertListItemAttributes(WebElement ul, String expectedStyle, String expectedClass) {
        List<WebElement> items = ul.findElements(By.tagName("li"));
        for (int i = 0; i < items.size(); i++) {
            WebElement li = items.get(i);
            assertEquals(expectedStyle, li.getDomAttribute("style"), "li[" + i + "] style");
            assertEquals(expectedClass, li.getDomAttribute("class"), "li[" + i + "] class");
        }
    }

    private void assertListItemStyleBySeverity(int caseNumber, String severityFieldId, Map<String, String> styleBySeverity) {
        styleBySeverity.forEach((severity, expectedStyle) -> {
            WebElement ul = submitListCase(caseNumber, severityFieldId, severity);
            for (WebElement li : ul.findElements(By.tagName("li"))) {
                assertEquals(expectedStyle, li.getDomAttribute("style"),
                    "Case " + caseNumber + " " + severity + " li style");
            }
        });
    }

    private void assertListItemClassBySeverity(int caseNumber, String severityFieldId, Map<String, String> classBySeverity) {
        classBySeverity.forEach((severity, expectedClass) -> {
            WebElement ul = submitListCase(caseNumber, severityFieldId, severity);
            for (WebElement li : ul.findElements(By.tagName("li"))) {
                assertEquals(expectedClass, li.getDomAttribute("class"),
                    "Case " + caseNumber + " " + severity + " li class");
            }
        });
    }

    private static void assertTableTexts(WebElement table, String firstExpected, String secondExpected) {
        Set<String> actual = table.findElements(By.tagName("td")).stream()
            .map(WebElement::getText).map(String::trim)
            .collect(Collectors.toCollection(TreeSet::new));
        Set<String> expected = new TreeSet<>();
        expected.add(firstExpected);
        expected.add(secondExpected);
        assertTrue(actual.size() <= 2, "More than two table cells rendered: " + actual);
        assertEquals(expected, actual, "table cell texts");
    }

    private static void verifyPassthrough(WebPage page, Map<String, String> expected) {
        WebElement button = findByIdSuffix(page, "button1");
        page.guardHttp(button::click);
        WebElement ul = findByIdSuffix(page, "messages");
        expected.forEach((name, value) ->
            assertEquals(value, ul.getDomAttribute(name), "messages attribute " + name));
    }

    private static WebElement findByIdSuffix(WebPage page, String id) {
        String suffix = ":" + id;
        return page.findElement(By.xpath(
            "//*[@id='" + id + "'"
            + " or substring(@id, string-length(@id) - " + (suffix.length() - 1) + ") = '" + suffix + "']"));
    }
}
