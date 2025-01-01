/*
 * Copyright (c) 2024 Contributors to Eclipse Foundation.
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
package org.eclipse.ee4j.tck.faces.faces41.uiRepeat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class Spec1263IT extends BaseITNG {

  /**
   * @see UIRepeat
     * @see https://github.com/jakartaee/faces/issues/1263
   */
  @Test
  void uiRepeatRowStatePreserved() {
        WebPage page = getPage("spec1263.xhtml");
        page.findElement(By.id("row:0:cell:0:form:input")).sendKeys("00");
        page.findElement(By.id("row:0:cell:0:form:submit")).click();
        assertEquals("[[00, null], [null, null]]", page.findElement(By.id("output")).getText());

        page.findElement(By.id("row:0:cell:1:form:input")).sendKeys("01");
        page.findElement(By.id("row:0:cell:1:form:submit")).click();
        assertEquals("[[00, 01], [null, null]]", page.findElement(By.id("output")).getText());

        page.findElement(By.id("row:1:cell:0:form:input")).sendKeys("10");
        page.findElement(By.id("row:1:cell:0:form:submit")).click();
        assertEquals("[[00, 01], [10, null]]", page.findElement(By.id("output")).getText());

        page.findElement(By.id("row:1:cell:1:form:input")).sendKeys("11");
        page.findElement(By.id("row:1:cell:1:form:submit")).click();
        assertEquals("[[00, 01], [10, 11]]", page.findElement(By.id("output")).getText());
    }
}
