/*
 * Copyright (c) 2021 Contributors to the Eclipse Foundation.
 * Copyright (c) 2017, 2021 Oracle and/or its affiliates. All rights reserved.
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
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.jakarta.tck.faces.test.javaee8.passthrough;

import static java.lang.System.getProperty;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.net.URL;

import jakarta.faces.component.UIInput;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

@ExtendWith(ArquillianExtension.class)
public class Issue4093IT {

    @ArquillianResource
    private URL webUrl;
    private WebClient webClient;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return create(ZipImporter.class, getProperty("finalName") + ".war")
                .importFrom(new File("target/" + getProperty("finalName") + ".war"))
                .as(WebArchive.class);
    }

  @BeforeEach
  void setUp() {
        webClient = new WebClient();
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.setJavaScriptTimeout(120000);
    }

  /**
   * @see UIInput#isRequired()
     * @see com.sun.faces.facelets.tag.faces.PassThroughAttributeLibrary
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4097
   */
  @Test
  void spec4093RequiredWithoutPassthrough() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "issue4093.xhtml");
        HtmlTextInput input = (HtmlTextInput) page.getElementById("requiredwithoutpassthrough:value");
        input.setAttribute("value", "");

        HtmlSubmitInput button = (HtmlSubmitInput) page.getHtmlElementById("requiredwithoutpassthrough:submit");

        page = button.click();

        String output = page.asNormalizedText();

        assertTrue(output.contains("requiredwithoutpassthrough:value: Validation Error: Value is required."));
    }

  /**
   * @see UIInput#isRequired()
     * @see com.sun.faces.facelets.tag.faces.PassThroughAttributeLibrary
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4097
   */
  @Test
  void spec4093RequiredWithPassthrough() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "issue4093.xhtml");
        HtmlTextInput input = (HtmlTextInput) page.getElementById("requiredwithpassthrough:value");
        input.setAttribute("value", "");

        HtmlSubmitInput button = (HtmlSubmitInput) page.getHtmlElementById("requiredwithpassthrough:submit");

        page = button.click();

        String output = page.asNormalizedText();

        assertFalse(output.contains("Please fill out this field"));
    }

  /**
   * @see UIInput#isRequired()
     * @see com.sun.faces.facelets.tag.faces.PassThroughAttributeLibrary
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4097
   */
  @Test
  void spec4093ValidateWithoutPassthrough() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "issue4093.xhtml");
        HtmlTextInput input = (HtmlTextInput) page.getElementById("validatewithoutpassthrough:value");
        input.setAttribute("value", "");

        HtmlSubmitInput button = (HtmlSubmitInput) page.getHtmlElementById("validatewithoutpassthrough:submit");

        page = button.click();

        String output = page.asNormalizedText();

        assertTrue(output.contains("validatewithoutpassthrough:value: Validation Error: Value is required."));
    }

  /**
   * This test should yield no JSF message response, as the inputText component is using passthrough to HTML.
   * 
   * @see UIInput#isRequired()
     * @see com.sun.faces.facelets.tag.faces.PassThroughAttributeLibrary
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4097
   */
  @Test
  void spec4093ValidateWithPassthrough() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "issue4093.xhtml");
        HtmlTextInput input = (HtmlTextInput) page.getElementById("validatewithpassthrough:value");
        input.setAttribute("value", "");

        HtmlSubmitInput button = (HtmlSubmitInput) page.getHtmlElementById("validatewithpassthrough:submit");

        page = button.click();

        String output = page.asNormalizedText();

        assertFalse(output.contains("Please fill out this field"));
    }

  /**
   * This test should yield no JSF message response, as the inputText component is using passthrough to HTML.
   * 
   * @see UIInput#isRequired()
     * @see com.sun.faces.facelets.tag.faces.PassThroughAttributeLibrary
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4097
   */
  @Test
  void spec4093ValidateWithPassthroughId() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "issue4093.xhtml");
        HtmlTextInput input = (HtmlTextInput) page.getElementById("validatewithpassthrough:value");
        input.setAttribute("value", "");

        HtmlSubmitInput button = (HtmlSubmitInput) page.getHtmlElementById("validatewithpassthrough:submit");

        page = button.click();

        String output = page.asNormalizedText();

        assertFalse(output.contains("Please fill out this field"));
    }

  @AfterEach
  void tearDown() {
        webClient.close();
    }

}
