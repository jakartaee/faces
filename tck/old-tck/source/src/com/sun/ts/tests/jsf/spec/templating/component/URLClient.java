/*
 * Copyright (c) 2001, 2018 Oracle and/or its affiliates. All rights reserved.
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

/*
 * $Id$
 */
package com.sun.ts.tests.jsf.spec.templating.component;

import java.io.PrintWriter;
import java.util.Formatter;

import org.htmlunit.html.HtmlPage;
import org.htmlunit.html.HtmlSpan;
import com.sun.javatest.Status;
import com.sun.ts.tests.jsf.common.client.BaseHtmlUnitClient;

public class URLClient extends BaseHtmlUnitClient {

  private static final String CONTEXT_ROOT = "/jsf_templating_component_web";

  public static void main(String[] args) {
    URLClient theTests = new URLClient();
    Status s = theTests.run(args, new PrintWriter(System.out, true),
        new PrintWriter(System.err, true));
    s.exit();
  }

  public Status run(String[] args, PrintWriter out, PrintWriter err) {
    return super.run(args, out, err);
  }

  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */

  /**
   * @testName: uicomponentVisableTest
   * @assertion_ids: PENDING
   * @test_Strategy: Test ui:component 'rendered' attribute with value of true.
   * 
   * @since 2.2
   */
  public void uicomponentVisableTest() throws Fault {

    StringBuilder messages = new StringBuilder(128);
    Formatter formatter = new Formatter(messages);

    HtmlPage page = getPage(CONTEXT_ROOT + "/faces/visableTest.xhtml");

    HtmlSpan spanOne = (HtmlSpan) getElementOfTypeWithValue(page, "span",
        "rendered");

    if (!validateExistence("rendered", "span", spanOne, formatter)) {
      handleTestStatus(messages);
      return;
    }

    handleTestStatus(messages);
    formatter.close();

  } // END uicomponentVisableTest

  /**
   * @testName: uicomponentNotVisableTest
   * @assertion_ids: PENDING
   * @test_Strategy: Test ui:component 'rendered' attribute with value of false.
   * 
   * @since 2.2
   */
  public void uicomponentNotVisableTest() throws Fault {

    StringBuilder messages = new StringBuilder(128);
    Formatter formatter = new Formatter(messages);

    HtmlPage page = getPage(CONTEXT_ROOT + "/faces/notVisableTest.xhtml");

    if (page.getElementsByTagName("span").getLength() > 0) {
      formatter.format(
          "Unexpected '%s' element found when '%s' attribute on ui:compnent is "
              + "set to '%s' ! ",
          "span", "rendered", "false");
    }

    handleTestStatus(messages);
    formatter.close();

  } // END uicomponentNotVisableTest

  /**
   * @testName: uicomponentBindTest
   * @assertion_ids: PENDING
   * @test_Strategy: Test ui:component 'binding' attribute to make sure we bind
   *                 correctly to a backend bean.
   * 
   * @since 2.2
   */
  public void uicomponentBindTest() throws Fault {

    StringBuilder messages = new StringBuilder(128);
    Formatter formatter = new Formatter(messages);

    HtmlPage page = getPage(CONTEXT_ROOT + "/faces/component_binding.xhtml");

    HtmlSpan spanOne = (HtmlSpan) getElementOfTypeWithValue(page, "span",
        "Vidtily Chernobyl");

    if (!validateExistence("bind", "span", spanOne, formatter)) {
      handleTestStatus(messages);
      return;
    }

    handleTestStatus(messages);
    formatter.close();

  } // END uicomponentNotVisableTest

} // END URLClient
