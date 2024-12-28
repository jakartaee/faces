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

package ee.jakarta.tck.faces.test.javaee8.cdi;

import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.annotation.RequestParameterMap;
import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;

/**
 * Tests the availability of the request parameter map via CDI
 *
 */
public class Spec1390IT extends ITBase {

  /**
   * @see Inject
     * @see RequestParameterMap
     * @see https://github.com/jakartaee/faces/issues/1390
   */
  @Test
  void requestParameterMap() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "injectRequestParameterMap.xhtml?foo=bar");

        // Request parameter value should be printed on the page
        assertTrue(page.asXml().contains("foo:bar"));
    }

}
