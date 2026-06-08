/*
 * Copyright (c) 2026 Contributors to Eclipse Foundation.
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

package ee.jakarta.tck.faces.faces23.ajax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue4340IT extends BaseITNG {

    /**
     * An ajax submit with {@code render="@all"} that navigates to a page declaring an
     * {@code h:outputScript} targeted at the body must relocate that script so the
     * client-side JS actually runs after the ajax update.
     *
     * @see jakarta.faces.component.html.HtmlBody
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4340
     */
    @Test
    void testAjaxJSResourceAddedToBody() throws Exception {
        WebPage page = getPage("issue4340start.xhtml");

        WebElement button = page.findElement(By.id("form:issue4340Button"));
        page.guardAjax(button::click);

        WebElement result = page.findElement(By.id("issue4340Result"));
        assertNotNull(result);
        assertEquals("SUCCESS", result.getText());
    }

}
