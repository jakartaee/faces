/*
 * Copyright (c) 2022 Contributors to Eclipse Foundation.
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

package ee.jakarta.tck.faces.test.servlet50.beanValidation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class Issue5171IT extends BaseITNG {

  /**
   * @see https://github.com/eclipse-ee4j/mojarra/issues/5171
   */
  @Test
  void test() throws Exception {
        WebPage page = getPage("issue5171.xhtml");
        page.findElement(By.id("form:submit")).click();
        String simpleInputMessage = page.findElement(By.id("form:simpleInputMessage")).getText();
        String compositeInputMessage = page.findElement(By.id("form:compositeInputMessage")).getText();
        assertTrue(simpleInputMessage.endsWith("must not be empty"), "simple input must trigger bean validation and show message");
        assertTrue(compositeInputMessage.endsWith("must not be empty"), "composite input must trigger bean validation and show message");

        WebElement simpleInput = page.findElement(By.id("form:simpleInput"));
        WebElement compositeInput = page.findElement(By.id("form:composite:input"));
        simpleInput.sendKeys("not empty");
        compositeInput.sendKeys("not empty");
        page.findElement(By.id("form:submit")).click();
        simpleInputMessage = page.findElement(By.id("form:simpleInputMessage")).getText();
        compositeInputMessage = page.findElement(By.id("form:compositeInputMessage")).getText();
        assertEquals("", simpleInputMessage, "simple input must pass bean validation and clear out message");
        assertEquals("", compositeInputMessage, "composite input must pass bean validation and clear out message");
    }
}
