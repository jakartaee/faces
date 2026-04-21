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
package ee.jakarta.tck.faces.test.faces20.composite.packaging.webapproot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class WebapprootIT extends BaseITNG {

    @Test
    void compositeWebAppRootPKGTest() {
        WebPage page = getPage("faces/pkgTest.xhtml");

        // case 1: non-versioned composite component under resources/tckcomp/
        WebElement case1 = findByIdSuffix(page, "case1");
        assertEquals("PASSED", case1.getText(), "case1 span value");

        // case 2: versioned composite component under resources/versioned/2_0/
        // (the 1_0 variant returns "FAILED" and must not be picked up).
        WebElement case2 = findByIdSuffix(page, "case2");
        assertEquals("PASSED", case2.getText(), "case2 span value");
    }

    private static WebElement findByIdSuffix(WebPage page, String id) {
        String suffix = ":" + id;
        return page.findElement(By.xpath(
            "//*[@id='" + id + "'"
            + " or substring(@id, string-length(@id) - " + (suffix.length() - 1) + ") = '" + suffix + "']"));
    }
}
