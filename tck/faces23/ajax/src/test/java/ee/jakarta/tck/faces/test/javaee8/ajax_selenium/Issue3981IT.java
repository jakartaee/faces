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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.component.html.HtmlOutputText;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;


class Issue3981IT extends BaseITNG {

  /**
   * @see HtmlOutputText#setEscape(boolean)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3985
   */
  @Test
  void issue3981() throws Exception {
        WebPage page = getPage("issue3981.xhtml");
        assertTrue(page.findElement(By.id("form:result")).getText().trim().isEmpty());

        WebElement button = page.findElement(By.id("form:button"));
        page.guardAjax(button::click);

      assertEquals("Success!", page.findElement(By.id("form:result")).getText().trim());
    }

}
