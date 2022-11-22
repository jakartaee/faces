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
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertTrue;


public class Issue2697IT extends BaseITNG {

    /**
     * @see AjaxBehavior
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2701
     */
    @Test
    public void testAjaxViewScope() throws Exception {
        WebPage page = getPage("viewScope.xhtml");

        WebElement button = page.findElement(By.id("form:reset"));
        button.click();
        page.waitReqJs();

        button = page.findElement(By.id("form:ajax"));
        button.click();
        assertTrue(page.isInPage("VIEWSCOPEBEAN() CALLED"));

        // Assert that second Ajax request does not execute the bean constructor again.
        button = page.findElement(By.id("form:ajax")); //we need to reload the button due to detachement
        button.click();
        page.waitReqJs();

        // this looks like a RI bug exposed by chrome, not following this any further because the test is so basic
        // this text is exactly like it should not be in the page, very likely similar to Issue2162IT
        assertTrue(page.isNotInPageText("VIEWSCOPEBEAN() CALLED VIEWSCOPEBEAN() CALLED"));
    }
}
