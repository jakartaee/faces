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

import static java.lang.System.getProperty;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.net.URL;

import jakarta.faces.application.Application;

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
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

@ExtendWith(ArquillianExtension.class)
public class BasicLambdaIT {

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
    }

  @AfterEach
  void tearDown() {
        webClient.close();
    }

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
