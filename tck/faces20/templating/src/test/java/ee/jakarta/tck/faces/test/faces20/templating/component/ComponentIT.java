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
package ee.jakarta.tck.faces.test.faces20.templating.component;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class ComponentIT extends BaseITNG {

    @Test
    void uicomponentVisableTest() {
        WebPage page = getPage("faces/component/visableTest.xhtml");
        assertTrue(containsSpanWithText(page, "rendered"),
            "Expected <span> with text 'rendered'");
    }

    @Test
    void uicomponentNotVisableTest() {
        WebPage page = getPage("faces/component/notVisableTest.xhtml");
        assertEquals(0, page.findElements(By.tagName("span")).size(),
            "Expected no <span> elements when rendered='false'");
    }

    @Test
    void uicomponentBindTest() {
        WebPage page = getPage("faces/component/component_binding.xhtml");
        assertTrue(containsSpanWithText(page, "Vidtily Chernobyl"),
            "Expected <span> bound to Character.name");
    }

    private boolean containsSpanWithText(WebPage page, String text) {
        List<WebElement> spans = page.findElements(By.tagName("span"));
        return spans.stream().anyMatch(s -> text.equals(s.getText()));
    }
}
