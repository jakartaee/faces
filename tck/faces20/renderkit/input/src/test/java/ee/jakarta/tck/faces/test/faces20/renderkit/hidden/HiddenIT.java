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
package ee.jakarta.tck.faces.test.faces20.renderkit.hidden;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class HiddenIT extends BaseITNG {

    @Test
    void hiddenRenderEncodeTest() {
        WebPage page = getPage("faces/hidden/encodetest.xhtml");

        WebElement hidden1 = findByIdSuffix(page, "hidden1");
        assertEquals(hidden1.getDomAttribute("id"), hidden1.getDomAttribute("name"), "hidden1 id equals name");
        assertEquals("value", hidden1.getDomAttribute("value"), "hidden1 value");

        WebElement hidden2 = findByIdSuffix(page, "Invisible");
        assertEquals(hidden2.getDomAttribute("id"), hidden2.getDomAttribute("name"), "hidden2 id equals name");
        assertEquals("Invisible", hidden2.getDomAttribute("value"), "hidden2 value");
    }

    @Test
    void hiddenRenderDecodeTest() {
        WebPage page = getPage("faces/hidden/decodetest.xhtml");

        WebElement hidden1 = findByIdSuffix(page, "hidden1");
        page.getJSExecutor().executeScript("arguments[0].value='newSubmittedValue'", hidden1);

        WebElement button1 = findByIdSuffix(page, "button1");
        page.guardHttp(button1::click);

        WebElement result = findByIdSuffix(page, "result");
        assertEquals("newSubmittedValue", result.getDomAttribute("value"), "submitted value for hidden1");
    }

    private static WebElement findByIdSuffix(WebPage page, String id) {
        String suffix = ":" + id;
        return page.findElement(By.xpath(
            "//*[@id='" + id + "'"
            + " or substring(@id, string-length(@id) - " + (suffix.length() - 1) + ") = '" + suffix + "']"));
    }
}
