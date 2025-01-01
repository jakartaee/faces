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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import jakarta.faces.component.behavior.AjaxBehavior;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class Issue2906IT extends BaseITNG {

  /**
   * @see AjaxBehavior
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2910
   */
  @Test
  void commandLinksInRepeat() throws Exception {
        WebPage page = getPage("issue2906.xhtml");


        assertTrue(page.matchesPageTextReduced(".*(2\\s+){9}2.*"));

        List<WebElement> anchors = page.getAnchors();
        WebElement anchor = anchors.get(9);
        page.guardAjax(anchor::click);
        anchors = page.getAnchors();
        assertTrue(page.matchesPageTextReduced(".*(3\\s+){8}3.*"));
      assertEquals(9, anchors.size());

        anchor = anchors.get(8);
        page.guardAjax(anchor::click);

        anchors = page.getAnchors();
        assertTrue(page.matchesPageTextReduced(".*(4\\s+){7}4.*"));
      assertEquals(8, anchors.size());

        anchor = anchors.get(7);
        page.guardAjax(anchor::click);

        anchors = page.getAnchors();
        assertTrue(page.matchesPageTextReduced(".*(5\\s+){6}5.*"));
      assertEquals(7, anchors.size());

        anchor = anchors.get(0);
        page.guardAjax(anchor::click);

        anchors = page.getAnchors();
        assertTrue(page.matchesPageTextReduced(".*(6\\s+){5}6.*"));
      assertEquals(6, anchors.size());

        anchor = anchors.get(2);
        page.guardAjax(anchor::click);

        anchors = page.getAnchors();
        assertTrue(page.matchesPageTextReduced(".*(7\\s+){4}7.*"));
      assertEquals(5, anchors.size());
    }
}
