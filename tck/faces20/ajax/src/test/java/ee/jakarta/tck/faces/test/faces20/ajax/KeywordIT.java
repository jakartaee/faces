/*
 * Copyright (c) 2023 Contributors to the Eclipse Foundation.
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
package ee.jakarta.tck.faces.test.faces20.ajax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class KeywordIT extends BaseITNG {

    private static final String EXPECTED = "testtext";

    @Test
    void ajaxAllKeywordTest() {
        validateKeyword(
                Arrays.asList("faces/ajaxAllKeyword1.xhtml", "faces/ajaxAllKeyword2.xhtml", "faces/ajaxAllKeyword3.xhtml"),
                "form:allKeyword",
                "form:out",
                EXPECTED);
    }

    @Test
    void ajaxThisKeywordTest() {
        validateKeyword(
                Arrays.asList("faces/ajaxThisKeyword1.xhtml", "faces/ajaxThisKeyword2.xhtml", "faces/ajaxThisKeyword3.xhtml"),
                "form:thisKeyword",
                "form:out",
                EXPECTED);
    }

    @Test
    void ajaxFormKeywordTest() {
        validateKeyword(
                Arrays.asList("faces/ajaxFormKeyword1.xhtml", "faces/ajaxFormKeyword2.xhtml", "faces/ajaxFormKeyword3.xhtml"),
                "form:formKeyword",
                "form:out",
                EXPECTED);
    }

    @Test
    void ajaxNoneKeywordTest() {
        validateKeyword(
                Arrays.asList("faces/ajaxNoneKeyword1.xhtml", "faces/ajaxNoneKeyword2.xhtml", "faces/ajaxNoneKeyword3.xhtml"),
                "form:noneKeyword",
                "form:out",
                EXPECTED);
    }

    private void validateKeyword(List<String> urls, String buttonId, String spanId, String expectedValue) {
        for (String url : urls) {
            WebPage page = getPage(url);

            WebElement output = page.findElement(By.id(spanId));
            assertEquals(expectedValue, output.getText());

            WebElement button = page.findElement(By.id(buttonId));
            page.guardAjax(button::click);

            output = page.findElement(By.id(spanId));
            assertEquals(expectedValue, output.getText());
        }
    }
}
