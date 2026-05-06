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

import static org.junit.jupiter.api.Assertions.fail;

import jakarta.faces.context.PartialResponseWriter;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class Spec1296IT extends BaseITNG {

  /**
   * @see PartialResponseWriter
     * @see https://github.com/jakartaee/faces/issues/1296
   */
  @Test
  void partialResponseWriterOutsideFacesServlet() throws Exception {
        WebPage page = getPage("BeforeFilter");
        String pageXml = page.getResponseBody();

        if (pageXml.contains("<p id=\"result\">FAILURE</p>")) {
            fail();
        }
    }
}
