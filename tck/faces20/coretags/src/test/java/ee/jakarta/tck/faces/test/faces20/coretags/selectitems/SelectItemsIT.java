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
package ee.jakarta.tck.faces.test.faces20.coretags.selectitems;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class SelectItemsIT extends BaseITNG {

    @Test
    void selectItemsValueTest() {
        WebPage page = getPage("faces/selectitems/test_facelet.xhtml");

        for (String caseId : new String[] { "caseOne", "caseTwo" }) {
            assertEquals(3, page.findElements(By.cssSelector("select#" + caseId + " option")).size(),
                    "Expected 3 options rendered for component " + caseId);
        }

        WebElement option = page.findElement(By.cssSelector("select#caseThree option[value='Escape Characters']"));
        String html = option.getAttribute("outerHTML");
        assertTrue(html.contains("&gt;&lt;&amp;"),
                "Unexpected value for Option's label. Expected '&gt;&lt;&amp;' in: " + html);
    }
}
