/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
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

package ee.jakarta.tck.faces.test.servlet30.ajax_selenium;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;
import jakarta.faces.component.behavior.AjaxBehavior;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

public class Issue2479IT extends BaseITNG {

    /**
     * @see AjaxBehavior
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2483
     */
    @Test
    public void testSelectDataTable() throws Exception {
        WebPage page = getPage("selectOneMenuDataTable.xhtml");
        WebElement span1 = page.findElement(By.id("table:0:inCity"));
        assertTrue((span1.getText()).equals("alpha"));
        WebElement span2 = page.findElement(By.id("table:1:inCity"));
        assertTrue((span2.getText()).equals("alpha"));
        WebElement span3 = page.findElement(By.id("table:2:inCity"));
        assertTrue((span3.getText()).equals("alpha"));
        Select select = new Select(page.findElement(By.id("selectMenu")));
        select.selectByValue("beta");
        page.waitForCurrentRequestEnd();
        //failing
        page.waitForBackgroundJavascript(Duration.ofMillis(3000));
        span1 = page.findElement(By.id("table:0:inCity"));
        assertTrue((span1.getText()).equals("beta"));
        span2 = page.findElement(By.id("table:1:inCity"));
        assertTrue((span2.getText()).equals("beta"));
        span3 = page.findElement(By.id("table:2:inCity"));
        assertTrue((span3.getText()).equals("beta"));
    }
}

