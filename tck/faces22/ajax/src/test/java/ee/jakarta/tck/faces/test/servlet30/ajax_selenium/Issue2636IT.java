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

import jakarta.faces.component.behavior.AjaxBehavior;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class Issue2636IT extends BaseITNG {

  /**
   * @see AjaxBehavior
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2640
   */
  @Test
  void commandLinksInRepeat() throws Exception {
        WebPage page = getPage("issue2636.xhtml");
        List<WebElement> anchors = page.getAnchors();

        WebElement anchor1 = anchors.get(0);
        page.guardAjax(anchor1::click);
        assertTrue(page.isInPage("linkAction1"));

        anchors = page.getAnchors();

        WebElement anchor2 = anchors.get(1);
        page.guardAjax(anchor2::click);

        assertTrue(page.isInPage("linkAction2"));

        anchors = page.getAnchors();

        anchor1 = anchors.get(0);
        page.guardAjax(anchor1::click);

        assertTrue(page.isInPage("linkAction1"));
    }
}
