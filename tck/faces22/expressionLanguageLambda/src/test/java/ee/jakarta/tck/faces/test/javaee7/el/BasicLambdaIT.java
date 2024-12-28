/*
 * Copyright (c) 2021 Contributors to the Eclipse Foundation.
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

package ee.jakarta.tck.faces.test.javaee7.el;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.application.Application;

import org.junit.jupiter.api.Test;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;

public class BasicLambdaIT extends ITBase {

  /**
   * @see Application#getELResolver()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3032
   */
  @Test
  void index() throws Exception {
        HtmlPage page = webClient.getPage(webUrl);
        HtmlElement out = page.getHtmlElementById("output");
        assertEquals("20", out.asNormalizedText());

        HtmlTextInput input = (HtmlTextInput) page.getElementById("input");
        input.setValueAttribute("1");
        HtmlSubmitInput button = (HtmlSubmitInput) page.getElementById("button");
        page = button.click();
        out = page.getHtmlElementById("output");
        assertEquals("40", out.asNormalizedText());

        input = (HtmlTextInput) page.getElementById("input");
        input.setValueAttribute("2");
        button = (HtmlSubmitInput) page.getElementById("button");
        page = button.click();
        out = page.getHtmlElementById("output");
        assertEquals("60", out.asNormalizedText());
    }

  /**
   * @see Application#getELResolver()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3032
   */
  @Test
  void bookTable() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "faces/bookTable.xhtml");
        assertTrue(page.asNormalizedText().contains("At Swim Two Birds"));
        assertTrue(page.asNormalizedText().contains("The Third Policeman"));
        
        HtmlElement out = page.getHtmlElementById("output2");
        assertEquals("The Picture of Dorian Gray", out.asNormalizedText());
    }
}
