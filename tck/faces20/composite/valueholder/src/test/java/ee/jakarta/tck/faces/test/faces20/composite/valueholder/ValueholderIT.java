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
package ee.jakarta.tck.faces.test.faces20.composite.valueholder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class ValueholderIT extends BaseITNG {

    /** Input id, value to type, expected round-tripped value (converter title-cases the first letter). */
    private static final String[][] TEST_VALUES = {
            { "firstname", "peter", "Peter" },
            { "lastname", "griffin", "Griffin" } };

    @Test
    void compositeValueHolderTest() {
        // case 1: only 'name' attribute specified
        WebPage pageOne = getPage("faces/caseOne.xhtml");
        testValueHolder(pageOne);

        // case 2: both 'target' and 'name' attributes specified
        WebPage pageTwo = getPage("faces/caseTwo.xhtml");
        testValueHolder(pageTwo);
    }

    private void testValueHolder(WebPage page) {
        for (String[] testValue : TEST_VALUES) {
            WebElement input = findByIdSuffix(page, testValue[0]);
            input.clear();
            input.sendKeys(testValue[1]);
        }

        WebElement button = findByIdSuffix(page, "button");
        page.guardHttp(button::click);

        for (String[] testValue : TEST_VALUES) {
            WebElement input = findByIdSuffix(page, testValue[0]);
            assertEquals(testValue[2], input.getAttribute("value"),
                    "Round-tripped value for " + testValue[0]);
        }
    }

    private static WebElement findByIdSuffix(WebPage page, String id) {
        String suffix = ":" + id;
        return page.findElement(By.xpath(
            "//*[@id='" + id + "'"
            + " or substring(@id, string-length(@id) - " + (suffix.length() - 1) + ") = '" + suffix + "']"));
    }
}
