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
package ee.jakarta.tck.faces.test.faces20.flows.basicmultipage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class BasicmultipageIT extends BaseITNG {

    @Test
    void facesFlowEntryExitTest() {
        WebPage page = getPage("faces/index.xhtml");
        assertTrue(page.isInPageText("Page with link to flow entry"), "Page with link to flow entry");

        WebElement start = findByIdSuffix(page, "start");
        page.guardHttp(start::click);

        assertTrue(page.isInPageText("First page in the flow"), "First page in the flow");
        assertTrue(page.isInPageText("basicFlow"), "basicFlow");

        page = getPage("faces/index.xhtml");
        assertTrue(page.isInPageText("Page with link to flow entry"), "Page with link to flow entry");

        start = findByIdSuffix(page, "start");
        page.guardHttp(start::click);

        assertTrue(page.isInPageText("First page in the flow"), "First page in the flow");
        assertTrue(page.isInPageText("basicFlow"), "basicFlow");
    }

    @Test
    void facesFlowScopeTest() {
        WebPage page = getPage("faces/index.xhtml");
        assertTrue(page.isInPageText("Page with link to flow entry"), "Page with link to flow entry");

        WebElement start = findByIdSuffix(page, "start");
        page.guardHttp(start::click);

        assertTrue(page.isInPageText("First page in the flow"), "First page in the flow");
        assertTrue(page.isInPageText("basicFlow"), "basicFlow");

        WebElement nextA = findByIdSuffix(page, "next_a");
        page.guardHttp(nextA::click);

        WebElement input = findByIdSuffix(page, "input");
        final String flowScopeValue = "Value in faces flow scope";
        input.sendKeys(flowScopeValue);

        WebElement next = findByIdSuffix(page, "next");
        page.guardHttp(next::click);

        assertTrue(page.isInPageText(flowScopeValue), "flowScopeValue visible");

        WebElement returnButton = findByIdSuffix(page, "return");
        page.guardHttp(returnButton::click);

        assertTrue(page.isInPageText("return page"), "return page");
        assertFalse((page.getPageText() + page.getInputValues()).contains(flowScopeValue), "flowScopeValue not visible");
    }

    private static WebElement findByIdSuffix(WebPage page, String id) {
        String suffix = ":" + id;
        return page.findElement(By.xpath(
            "//*[@id='" + id + "'"
            + " or substring(@id, string-length(@id) - " + (suffix.length() - 1) + ") = '" + suffix + "']"));
    }
}
