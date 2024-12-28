/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2020 Contributors to Eclipse Foundation.
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

package ee.jakarta.tck.faces.test.servlet40.el;

import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.el.ResourceBundleELResolver;

import org.junit.jupiter.api.Test;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;

public class Spec1337IT extends ITBase {

  /**
   * @see ResourceBundleELResolver
     * @see https://github.com/jakartaee/faces/issues/1337
   */
  @Test
  void resourceEL1() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "faces/resourceEL1.xhtml");
        assertTrue(page.asXml().contains("/jakarta.faces.resource/resourceEL1.gif"));
    }

  /**
   * @see ResourceBundleELResolver
     * @see https://github.com/jakartaee/faces/issues/1337
   */
  @Test
  void resourceEL2() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "faces/resourceEL2.xhtml");
        assertTrue(page.asXml().contains("/jakarta.faces.resource/resourceEL2.gif"));
        assertTrue(page.asXml().contains("?ln=resourceEL2"));
    }

  /**
   * @see ResourceBundleELResolver
     * @see https://github.com/jakartaee/faces/issues/1337
   */
  @Test
  void resourceEL3() throws Exception {
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setPrintContentOnFailingStatusCode(false);
        HtmlPage page = webClient.getPage(webUrl + "faces/resourceEL3.xhtml");
        assertTrue(page.asXml().contains("jakarta.el.ELException"));
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(true);
        webClient.getOptions().setPrintContentOnFailingStatusCode(true);
    }
}
