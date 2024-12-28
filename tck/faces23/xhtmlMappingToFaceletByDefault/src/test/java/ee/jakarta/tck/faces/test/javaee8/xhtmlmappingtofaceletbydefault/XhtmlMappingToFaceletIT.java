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

package ee.jakarta.tck.faces.test.javaee8.xhtmlmappingtofaceletbydefault;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;

public class XhtmlMappingToFaceletIT extends ITBase {

  @Test
  void verifyFacesMappingtoXhtmlByDefault() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "index.xhtml");

        assertTrue(page.getBody().asXml().indexOf("ViewState") != -1);
    }

  @Test
  void verifyMinimalXhtmlWithoutXmlProlog() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "withoutxmlprolog.xhtml");

        assertTrue(page.getBody().asXml().indexOf("This is a minimal") != -1);
    }
}
