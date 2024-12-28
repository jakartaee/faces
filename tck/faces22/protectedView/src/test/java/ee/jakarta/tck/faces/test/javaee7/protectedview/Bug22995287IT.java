/*
 * Copyright (c) 1997, 2021 Oracle and/or its affiliates. All rights reserved.
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
package ee.jakarta.tck.faces.test.javaee7.protectedview;

import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.application.ViewHandler;

import org.junit.jupiter.api.Test;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;

public class Bug22995287IT extends ITBase {

  /**
   * @see ViewHandler#restoreView(jakarta.faces.context.FacesContext, String)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4127
   */
  @Test
  void page2CanBeDisplayed1() throws Exception {
        HtmlPage page = webClient.getPage(webUrl);
        HtmlAnchor link = page.getHtmlElementById("get_parameter_fparam");
        page = link.click();

        String pageXml = page.getBody().asXml();
        assertTrue(pageXml.contains("foo bar"));
    }

  /**
   * @see ViewHandler#restoreView(jakarta.faces.context.FacesContext, String)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4127
   */
  @Test
  void page2CanBeDisplayed2() throws Exception {
        HtmlPage page = webClient.getPage(webUrl);
        HtmlAnchor link = page.getHtmlElementById("get_parameter_outcome");

        page = link.click();

        String pageXml = page.getBody().asXml();
        assertTrue(pageXml.contains("foo bar"));
    }

  /**
   * @see ViewHandler#restoreView(jakarta.faces.context.FacesContext, String)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4127
   */
  @Test
  void page2CanBeDisplayed3() throws Exception {
        HtmlPage page = webClient.getPage(webUrl);
        HtmlAnchor link = page.getHtmlElementById("get_parameter_none");

        page = link.click();

        String pageXml = page.getBody().asXml();
        assertTrue(pageXml.contains("Welcome to Page2"));
    }

  /**
   * @see ViewHandler#restoreView(jakarta.faces.context.FacesContext, String)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4127
   */
  @Test
  void page2CanBeDisplayed4() throws Exception {
        HtmlPage page = webClient.getPage(webUrl);
        HtmlSubmitInput button = (HtmlSubmitInput) page.getElementById("button_to_page2");
        page = button.click();

        String pageXml = page.getBody().asXml();
        assertTrue(pageXml.contains("Welcome to Page2"));
    }

  /**
   * @see ViewHandler#restoreView(jakarta.faces.context.FacesContext, String)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4127
   */
  @Test
  void page3CanBeDisplayed1() throws Exception {
        HtmlPage page = webClient.getPage(webUrl);
        try {
            HtmlAnchor link = page.getHtmlElementById("page3_get_parameter_fparam");
            page = link.click();

            String pageXml = page.getBody().asXml();
            assertTrue(pageXml.contains("foo bar"));
        } catch (AssertionError e) {
            System.out.println(page.asXml());

            throw e;
        }
    }

  /**
   * @see ViewHandler#restoreView(jakarta.faces.context.FacesContext, String)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4127
   */
  @Test
  void page3CanBeDisplayed2() throws Exception {
        HtmlPage page = webClient.getPage(webUrl);
        HtmlAnchor link = page.getHtmlElementById("page3_get_parameter_outcome");

        page = link.click();

        String pageXml = page.getBody().asXml();
        assertTrue(pageXml.contains("foo bar"));
    }

  /**
   * @see ViewHandler#restoreView(jakarta.faces.context.FacesContext, String)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4127
   */
  @Test
  void page3CanBeDisplayed3() throws Exception {
        HtmlPage page = webClient.getPage(webUrl);
        HtmlAnchor link = page.getHtmlElementById("page3_get_parameter_none");

        page = link.click();

        String pageXml = page.getBody().asXml();
        assertTrue(pageXml.contains("Welcome to Page2"));
    }

  /**
   * @see ViewHandler#restoreView(jakarta.faces.context.FacesContext, String)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4127
   */
  @Test
  void page3CanBeDisplayed4() throws Exception {
        HtmlPage page = webClient.getPage(webUrl);
        HtmlSubmitInput button = (HtmlSubmitInput) page.getElementById("page3_button_to_page2");
        page = button.click();

        String pageXml = page.getBody().asXml();
        assertTrue(pageXml.contains("Welcome to Page2"));
    }
}
