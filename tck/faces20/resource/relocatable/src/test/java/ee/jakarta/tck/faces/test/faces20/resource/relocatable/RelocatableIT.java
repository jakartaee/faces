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
package ee.jakarta.tck.faces.test.faces20.resource.relocatable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class RelocatableIT extends BaseITNG {

    @Test
    void relocatableHeadTest() {
        WebPage page = getPage("faces/reloc-head.xhtml");
        assertEquals("head", getHelloScriptParentTagName(page));
    }

    @Test
    void relocatableBodyTest() {
        WebPage page = getPage("faces/reloc-body.xhtml");
        assertEquals("body", getHelloScriptParentTagName(page));
    }

    @Test
    void relocatableFormTest() {
        WebPage page = getPage("faces/reloc-form.xhtml");
        assertEquals("form", getHelloScriptParentTagName(page));
    }

    private String getHelloScriptParentTagName(WebPage page) {
        WebElement script = findHelloScript(page);
        assertNotNull(script, "Expected <script> element referencing hello.js");
        return script.findElement(By.xpath("..")).getTagName().toLowerCase();
    }

    private WebElement findHelloScript(WebPage page) {
        for (WebElement script : page.findElements(By.tagName("script"))) {
            String src = script.getAttribute("src");
            if (src != null && src.contains("hello.js")) {
                return script;
            }
        }
        return null;
    }
}
