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
package ee.jakarta.tck.faces.test.faces20.renderkit.outputstyle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class OutputstyleIT extends BaseITNG {

    private static final String STYLESHEET = "stylesheet";

    @Test
    void outputStyleEncodeTest() {
        // case 1 + case 4: two <link> elements rendered in the head with expected media attributes.
        // Assert these BEFORE navigating away — subsequent getPage() calls invalidate WebElements from pageOne.
        WebPage pageOne = getPage("faces/outputstyle/encodetest.xhtml");
        List<WebElement> links = pageOne.findElements(By.tagName("link"));
        assertEquals(2, links.size(), "number of link elements");
        assertEquals("screen", links.get(0).getDomAttribute("media"), "link[0] media");
        assertEquals("all", links.get(1).getDomAttribute("media"), "link[1] media");

        // case 2: stylesheet with no library
        WebPage pageTwo = getPage("faces/outputstyle/encodetest_1.xhtml");
        WebElement linkTwo = pageTwo.findElements(By.tagName("link")).get(0);
        verifyLink(linkTwo, "jakarta.faces.resource/night.css");

        // case 3: stylesheet with library specified
        WebPage pageThree = getPage("faces/outputstyle/encodetest_2.xhtml");
        WebElement linkThree = pageThree.findElements(By.tagName("link")).get(0);
        verifyLink(linkThree, "jakarta.faces.resource/morning.css");
        assertTrue(linkThree.getDomAttribute("href").contains("ln=cssLibrary"), "link href contains ln=cssLibrary");
    }

    private static void verifyLink(WebElement link, String hrefFragment) {
        assertTrue(link.getDomAttribute("href").contains(hrefFragment), "link href contains " + hrefFragment);
        assertEquals(STYLESHEET, link.getDomAttribute("rel"), "link rel");
    }
}
