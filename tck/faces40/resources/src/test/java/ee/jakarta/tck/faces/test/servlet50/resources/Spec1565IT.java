/*
 * Copyright (c) 2021 Contributors to Eclipse Foundation.
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

package ee.jakarta.tck.faces.test.servlet50.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.faces.component.UIOutput;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

public class Spec1565IT extends BaseITNG {

    /**
     * @see UIOutput
     * @see com.sun.faces.renderkit.html_basic.ScriptStyleBaseRenderer
     * @see https://github.com/jakartaee/faces/issues/1565
     */
    @Test
    void html5() throws Exception {
        WebPage page = getPage("spec1565IT-HTML5.xhtml");

        for (WebElement element : page.findElements(By.tagName("script"))) {
            assertEquals(null, element.getDomAttribute("type"), "Script element has no type attribute");
        }

        for (WebElement element : page.findElements(By.tagName("link"))) {
            assertEquals(null, element.getDomAttribute("type"), "Link element has no type attribute");
        }

        for (WebElement element : page.findElements(By.tagName("style"))) {
            assertEquals(null, element.getDomAttribute("type"), "Style element has no type attribute");
        }
    }

    /**
     * @see UIOutput
     * @see com.sun.faces.renderkit.html_basic.ScriptStyleBaseRenderer
     * @see https://github.com/jakartaee/faces/issues/1565
     */
    @Test
    void html4() throws Exception {
        WebPage page = getPage("spec1565IT-HTML4.xhtml");

        for (WebElement element : page.findElements(By.tagName("script"))) {
            assertEquals("text/javascript", element.getDomAttribute("type"), "Script element has a type='text/javascript' attribute");
        }

        for (WebElement element : page.findElements(By.tagName("link"))) {
            assertEquals("text/css", element.getDomAttribute("type"), "Link element has a type='text/css' attribute");
        }

        for (WebElement element : page.findElements(By.tagName("style"))) {
            assertEquals("text/css", element.getDomAttribute("type"), "Style element has a type='text/css' attribute");
        }
    }

}
