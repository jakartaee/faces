/*
 * Copyright (c) 2021 Contributors to the Eclipse Foundation.
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
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
package ee.jakarta.tck.faces.test.javaee6web.viewscope;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.view.ViewScoped;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

public class Issue2641IT extends BaseITNG {

  /**
   * @see ViewScoped
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2645
   */
  @Test
  void viewScope() throws Exception {
        WebPage page = getPage("faces/viewScoped.xhtml");
        int previousCount = 0;
        int count = Integer.parseInt(page.findElement(By.id("count")).getText());
        assertTrue(previousCount < count);
        previousCount = count;

        WebElement button = page.findElement(By.id("stay"));
        button.click();
        count = Integer.parseInt(page.findElement(By.id("count")).getText());
        assertEquals(previousCount, count);

        button = page.findElement(By.id("stay"));
        button.click();
        count = Integer.parseInt(page.findElement(By.id("count")).getText());
        assertEquals(previousCount, count);

        button = page.findElement(By.id("go"));
        button.click();
        count = Integer.parseInt(page.findElement(By.id("count")).getText());
        assertTrue(previousCount < count);
        previousCount = count;

        button = page.findElement(By.id("stay"));
        button.click();
        count = Integer.parseInt(page.findElement(By.id("count")).getText());
        assertEquals(previousCount, count);

        button = page.findElement(By.id("stay"));
        button.click();
        count = Integer.parseInt(page.findElement(By.id("count")).getText());
        assertEquals(previousCount, count);

        button = page.findElement(By.id("go"));
        button.click();
        count = Integer.parseInt(page.findElement(By.id("count")).getText());
        assertTrue(previousCount < count);
        previousCount = count;

        button = page.findElement(By.id("stay"));
        button.click();
        count = Integer.parseInt(page.findElement(By.id("count")).getText());
        assertEquals(previousCount, count);

        button = page.findElement(By.id("stay"));
        button.click();
        count = Integer.parseInt(page.findElement(By.id("count")).getText());
        assertEquals(previousCount, count);
    }

  /**
   * @see ViewScoped
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2645
   */
  @Test
  void invalidatedSession() throws Exception {
        WebPage page = getPage("faces/invalidatedSession.xhtml");

        assertTrue(page.getPageSource().contains("This is from the @PostConstruct"));
        getPage("faces/invalidatedPerform.xhtml");
        page = getPage("faces/invalidatedVerify.xhtml");
        assertTrue(page.getPageSource().contains("true"));
    }

  /**
   * @see ViewScoped
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2645
   */
  @Test
  void viewScopedInput() throws Exception {
        WebPage page = getPage("faces/viewScopedInput.xhtml");
        WebElement input = page.findElement(By.id("input"));
        String value = "" + System.currentTimeMillis();
        input.sendKeys(value);
        WebElement button = page.findElement(By.id("stay"));
        button.click();
        WebElement output = page.findElement(By.id("output"));
        assertTrue(output.getText().contains(value));
    }
}
