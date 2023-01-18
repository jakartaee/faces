/*
 * Copyright (c) 2021 Contributors to the Eclipse Foundation.
 * Copyright (c) 1997, 2021 Oracle and/or its affiliates. All rights reserved.
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

package ee.jakarta.tck.faces.test.javaee8.ajax_selenium;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;
import jakarta.faces.context.PartialViewContext;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

public class Spec1412IT extends BaseITNG {

    /**
     * @see PartialViewContext#getEvalScripts()
     * @see https://github.com/jakartaee/faces/issues/1412
     */
    @Test
    public void testSpec1412() throws Exception {

        WebPage page = getPage("spec1412.xhtml");
        page.waitForPageToLoad();
        assertTrue(page.isNotInPage("Success!"));

        WebElement button = page.findElement(By.id("form:button"));

        assertTrue(button.getAttribute("value").equals("foo"));

        button.click();
        page.waitReqJs(Duration.ofMillis(60000));
        assertTrue(page.isInPage("Success!"));

        button =  page.findElement(By.id("form:button"));
        assertTrue(button.getAttribute("value").equals("bar"));
    }

}
