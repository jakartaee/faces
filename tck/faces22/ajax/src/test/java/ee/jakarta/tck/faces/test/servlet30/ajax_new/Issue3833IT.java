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
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import static org.junit.Assert.assertTrue;

public class Issue3833IT extends BaseITNG {

    /**
     * @see AjaxBehavior
     * @see com.sun.faces.facelets.component.UIRepeat
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3837
     */
    @Test
    public void testUIRepeatStateSaving() throws Exception {
        WebPage page = getPage("issue3833.xhtml");

        Select select1 = new Select(page.findElement(By.id("form:repeat:0:list")));
        Select select2 = new Select(page.findElement(By.id("form:repeat:1:list")));


        select1.selectByIndex(0);
        page.waitForCurrentRequestEnd();
        Thread.sleep(3000);
        assertTrue(page.findElement(By.id("form:message")).getText().equals("null -> 1"));

        select2.selectByIndex(0);
        page.waitForCurrentRequestEnd();
        Thread.sleep(3000);
        assertTrue(page.findElement(By.id("form:message")).getText().equals("null -> 3"));

        select1.selectByIndex(1);
        page.waitForCurrentRequestEnd();
        Thread.sleep(3000);
        assertTrue(page.findElement(By.id("form:message")).getText().equals("1 -> 2"));

        select2.selectByIndex(1);
        page.waitForCurrentRequestEnd();
        Thread.sleep(3000);
        assertTrue(page.findElement(By.id("form:message")).getText().equals("3 -> 4"));
    }
}
