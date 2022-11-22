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
import jakarta.faces.component.html.HtmlSelectOneRadio;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertTrue;

/**
 * @author Manfred Riem (manfred.riem@oracle.com)
 */
public class Issue1533IT extends BaseITNG {

    /**
     * @see AjaxBehavior
     * @see HtmlSelectOneRadio
     * @see https://github.com/eclipse-ee4j/mojarra/issues/1537
     */
    @Test
    public void testIssue1533() throws Exception {
        WebPage page = getPage("issue1533.xhtml");
        WebElement input = page.findElement(By.id("form:vip:0"));
        input.click();
        Thread.sleep(3000);
        assertTrue(page.getPageSource().indexOf("form:vip-true") != -1);
        input = page.findElement(By.id("form:vip:1"));
        input.click();
        Thread.sleep(3000);
        assertTrue(page.getPageSource().indexOf("form:vip-false") != -1);
    }
}
