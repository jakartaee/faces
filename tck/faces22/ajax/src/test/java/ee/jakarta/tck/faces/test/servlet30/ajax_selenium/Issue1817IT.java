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

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.faces.component.behavior.AjaxBehavior;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class Issue1817IT extends BaseITNG {

  /**
   * @see AjaxBehavior
     * @see com.sun.faces.facelets.component.UIRepeat
     * @see https://github.com/eclipse-ee4j/mojarra/issues/1821
   */
  @Test
  void ajaxUIRepeat() throws Exception {
        WebPage page = getPage("issue1817.xhtml");
        final List<WebElement> elements = page.findElements(By.tagName("a"));
        List<String[]> ids = elements.stream().map(element -> new String[]{element.getDomAttribute("id"), element.getText()})
                .collect(Collectors.toList());
        for (String[] id : ids) {
            WebElement anchor = page.findElement(By.id(id[0]));
            page.guardAjax(anchor::click);
            String expectedText = "Triggered item: " + id[1];
            assertTrue(page.isInPage(expectedText));
        }
    }
}

