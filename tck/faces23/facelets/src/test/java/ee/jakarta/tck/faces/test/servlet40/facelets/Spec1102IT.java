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

package ee.jakarta.tck.faces.test.servlet40.facelets;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;

class Spec1102IT extends ITBase {

  /**
   * @see com.sun.faces.facelets.component.UIRepeat
     * @see https://github.com/jakartaee/faces/issues/1102
   * @see https://github.com/jakartaee/faces/issues/1717
   */
  @Test
  void spec1102() throws Exception {
        HtmlPage page = getPage("faces/spec1102.xhtml");

      assertEquals("123", page.getHtmlElementById("repeat1").asNormalizedText());
// Outcommented for #1717       assertTrue(page.getHtmlElementById("repeat2").asNormalizedText().equals("-3-2-10123"));
// Outcommented for #1717       assertTrue(page.getHtmlElementById("repeat3").asNormalizedText().equals("3210-1-2-3"));
// Outcommented for #1717       assertTrue(page.getHtmlElementById("repeat4").asNormalizedText().equals("-3-113"));
// Outcommented for #1717       assertTrue(page.getHtmlElementById("repeat5").asNormalizedText().equals("-3-2"));
// Outcommented for #1717       assertTrue(page.getHtmlElementById("repeat6").asNormalizedText().equals("-3"));
      assertEquals("3", page.getHtmlElementById("repeat7").asNormalizedText());
// Outcommented for #1717       assertTrue(page.getHtmlElementById("repeat8").asNormalizedText().equals("0123"));
    }

}
