/*
 * Copyright (c) 2023 Contributors to the Eclipse Foundation.
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
package ee.jakarta.tck.faces.test.faces20.ajax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.openqa.selenium.Keys.TAB;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class TagWrapperIT extends BaseITNG {

    @Test
    void ajaxTagWrappingTest() {
        WebPage page = getPage("faces/ajaxTagWrap.xhtml");

        WebElement span1 = page.findElement(By.id("out1"));
        assertEquals("0", span1.getText());

        WebElement span2 = page.findElement(By.id("checkedvalue"));
        assertEquals("false", span2.getText());

        WebElement span3 = page.findElement(By.id("outtext"));
        assertEquals("", span3.getText());

        WebElement button1 = page.findElement(By.id("button1"));
        assertNotNull(button1);
        page.guardAjax(button1::click);

        span1 = page.findElement(By.id("out1"));
        assertEquals("1", span1.getText());

        page.guardAjax(() -> {
            WebElement intext = page.findElement(By.id("intext"));
            intext.sendKeys("test");
            intext.sendKeys(TAB);
        });
        assertEquals("test", page.findElement(By.id("outtext")).getText());

        WebElement checkbox = page.findElement(By.id("checkbox"));
        page.guardAjax(checkbox::click);
        span2 = page.findElement(By.id("checkedvalue"));
        assertEquals("true", span2.getText());
    }
}
