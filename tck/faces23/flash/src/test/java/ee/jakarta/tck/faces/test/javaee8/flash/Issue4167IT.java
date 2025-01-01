/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2023 Contributors to Eclipse Foundation.
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

package ee.jakarta.tck.faces.test.javaee8.flash;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.context.Flash;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class Issue4167IT extends BaseITNG {

  /**
   * @see Flash#keep(String)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4171
   */
  @Test
  void test() throws Exception {
        WebPage page = getPage("issue4167.xhtml");

        assertTrue(page.findElement(By.id("result1")).getText().isEmpty());
        assertTrue(page.findElement(By.id("result2")).getText().isEmpty());
        assertTrue(page.findElement(By.id("result3")).getText().isEmpty());

        WebElement button = page.findElement(By.id("form:button"));
        button.click();

      assertEquals("issue4167", page.findElement(By.id("result1")).getText());
      assertEquals("issue4167", page.findElement(By.id("result2")).getText());
      assertEquals("issue4167", page.findElement(By.id("result3")).getText());
    }

}
