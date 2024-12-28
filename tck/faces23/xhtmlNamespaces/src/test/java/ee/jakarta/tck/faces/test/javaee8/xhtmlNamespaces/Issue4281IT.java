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

package ee.jakarta.tck.faces.test.javaee8.xhtmlNamespaces;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class Issue4281IT extends BaseITNG {

  /**
   * @see https://github.com/eclipse-ee4j/mojarra/issues/4281
   */
  @Test
  void test() throws Exception {
        WebPage page = getPage("issue4281.xhtml");
        WebElement panelGroup = page.findElement(By.id("panelGroup"));
        WebElement outputText = page.findElement(By.id("outputText"));
        assertTrue(outputText != null, "outputText does exist");
        assertTrue(panelGroup.getText().contains("paragraph"), "panelGroup does contain parargaph");
        assertFalse(panelGroup.getText().contains("outputText"), "panelGroup may not contain outputText");
        assertTrue(page.getPageSource().contains("outputText"), "body does contain outputText");
    }

}