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
package ee.jakarta.tck.faces.test.faces20.flows.basicimplicit;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class BasicimplicitIT extends BaseITNG {

    @Test
    void facesFlowImplicitTest() {
        WebPage page = getPage("faces/index.xhtml");
        assertTrue(page.containsText("Page with link to flow entry"), "Page with link to flow entry");

        WebElement start = findByIdSuffix(page, "start");
        page.guardHttp(start::click);

        assertTrue(page.containsText("First page in the flow"), "First page in the flow");
        assertTrue(page.containsText("basicFlow"), "basicFlow");

        page = getPage("faces/index.xhtml");
        assertTrue(page.containsText("Page with link to flow entry"), "Page with link to flow entry");

        start = findByIdSuffix(page, "start");
        page.guardHttp(start::click);

        assertTrue(page.containsText("First page in the flow"), "First page in the flow");
        assertTrue(page.containsText("basicFlow"), "basicFlow");
    }

    private static WebElement findByIdSuffix(WebPage page, String id) {
        String suffix = ":" + id;
        return page.findElement(By.xpath(
            "//*[@id='" + id + "'"
            + " or substring(@id, string-length(@id) - " + (suffix.length() - 1) + ") = '" + suffix + "']"));
    }
}
