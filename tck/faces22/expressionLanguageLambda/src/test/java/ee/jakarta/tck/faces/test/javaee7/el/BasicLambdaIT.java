/*
 * Copyright (c) 2021 Contributors to the Eclipse Foundation.
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

package ee.jakarta.tck.faces.test.javaee7.el;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.application.Application;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

public class BasicLambdaIT extends BaseITNG {

    /**
     * @see Application#getELResolver()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3032
     */
    @Test
    void index() throws Exception {
        WebPage page = getPage("");
        WebElement out = page.findElement(By.id("output"));
        assertEquals("20", out.getText());

        WebElement input = page.findElement(By.id("input"));
        input.sendKeys("1");
        WebElement button = page.findElement(By.id("button"));
        button.click();
        out = page.findElement(By.id("output"));
        assertEquals("40", out.getText());

        input = page.findElement(By.id("input"));
        input.clear();
        input.sendKeys("2");
        button = page.findElement(By.id("button"));
        button.click();
        out = page.findElement(By.id("output"));
        assertEquals("60", out.getText());
    }

    /**
     * @see Application#getELResolver()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3032
     */
    @Test
    void bookTable() throws Exception {
        WebPage page = getPage("faces/bookTable.xhtml");
        assertTrue(page.getPageSource().contains("At Swim Two Birds"));
        assertTrue(page.getPageSource().contains("The Third Policeman"));

        WebElement out = page.findElement(By.id("output2"));
        assertEquals("The Picture of Dorian Gray", out.getText());
    }
}
