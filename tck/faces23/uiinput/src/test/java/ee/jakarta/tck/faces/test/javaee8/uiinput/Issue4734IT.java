/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2020, 2023 Contributors to the Eclipse Foundation.
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
package ee.jakarta.tck.faces.test.javaee8.uiinput;

import jakarta.faces.component.UIViewParameter;
import jakarta.faces.component.behavior.AjaxBehavior;

import org.jboss.arquillian.junit5.ArquillianExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;

@ExtendWith(ArquillianExtension.class)
class Issue4734IT extends ITBase {

  /**
   * @see UIViewParameter
     * @see AjaxBehavior
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4734
   */
  @Test
  void issue4734() throws Exception {
        HtmlPage page = getPage("issue4734.xhtml");
        HtmlSubmitInput submit = page.getHtmlElementById("form:submit");

        submit.click(); // The first click is expected to work fine.

        submit.click(); // Before the fix, the second click failed with com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException: 500 Internal Server Error

        submit.click(); // A third one, just to be sure it keeps working! ;)
    }

}