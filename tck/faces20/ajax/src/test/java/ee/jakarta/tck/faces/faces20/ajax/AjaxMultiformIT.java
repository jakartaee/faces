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

package ee.jakarta.tck.faces.faces20.ajax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * An ajax request submitted from one of several sibling forms must re-render only the region it
 * renders, and leave the sibling forms and the page content outside of them untouched.
 */
class AjaxMultiformIT extends BaseITNG {

    /**
     * @see jakarta.faces.component.behavior.AjaxBehavior
     */
    @Test
    void ajaxUpdatesOnlySubmittingFormRegion() throws Exception {
        WebPage page = getPage("ajaxMultiform.xhtml");
        assertCount(page, "countForm1:out1", "0");
        assertCount(page, "countForm2:out1", "1");
        assertCount(page, "countForm3:out1", "2");
        assertCount(page, "out2", "3");

        page.guardAjax(page.findElement(By.id("countForm1:button1"))::click);
        assertCount(page, "countForm1:out1", "4");
        assertCount(page, "countForm2:out1", "1");
        assertCount(page, "countForm3:out1", "2");
        assertCount(page, "out2", "3");

        page.guardAjax(page.findElement(By.id("countForm2:button1"))::click);
        assertCount(page, "countForm1:out1", "4");
        assertCount(page, "countForm2:out1", "5");
        assertCount(page, "countForm3:out1", "2");
        assertCount(page, "out2", "3");
    }

    private void assertCount(WebPage page, String clientId, String expected) {
        assertEquals(expected, page.findElement(By.id(clientId)).getText(), clientId);
    }
}
