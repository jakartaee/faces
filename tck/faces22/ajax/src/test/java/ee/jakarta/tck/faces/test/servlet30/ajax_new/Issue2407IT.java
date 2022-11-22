/*
 * Copyright (c) 2021 Contributors to the Eclipse Foundation.
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

package ee.jakarta.tck.faces.test.servlet30.ajax_new;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;
import jakarta.faces.component.behavior.AjaxBehavior;
import jakarta.faces.component.html.HtmlCommandButton;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertTrue;

public class Issue2407IT extends BaseITNG {

    /**
     * This test verifies that an attribute named 'value' can be successfully updated
     * from a partial response (over Ajax).
     *
     * @see AjaxBehavior
     * @see HtmlCommandButton#getValue()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2411
     */
    @Test
    public void testUpdateAttributeNamedValue() throws Exception {
        WebPage page = getPage("attributeNameIsValue.xhtml");
        assertTrue(page.isCondition(webDriver1 -> page.findElement(By.id("form1:foo")).getAttribute("value").equals("foo")));
        WebElement button = page.findElement(By.id("form1:button"));
        button.click();
        assertTrue(page.isCondition(webDriver1 -> page.findElement(By.id("form1:foo")).getAttribute("value").equals("bar")));
    }
}
