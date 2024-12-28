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
package ee.jakarta.tck.sun.faces.test.javaee7.cdinobeansxml;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.faces.flow.FlowScoped;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

public class NoBeansXmlIT extends BaseITNG {

  /**
   * @see FlowScoped
     * @see https://github.com/javaee/mojarra/commit/2b7dba5430eb0e9837d074ad5383669e0f47d2d1
   */
  @Test
  void flowWithNoBeansXml() throws Exception {
        WebPage page = getPage("");
        WebElement button = page.findElement(By.id("enterFlow"));
        button.click();
        
        WebElement input = page.findElement(By.id("input"));
        String message = "" + System.currentTimeMillis();
        input.sendKeys(message);
        button = page.findElement(By.id("a"));
        button.click();
        
        WebElement e = page.findElement(By.id("value"));
        
        assertEquals(e.getText(), message);
    }    
}
