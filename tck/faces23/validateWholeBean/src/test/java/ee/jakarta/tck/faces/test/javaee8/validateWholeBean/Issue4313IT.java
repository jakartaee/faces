/*
 * Copyright (c) 2021, 2023 Contributors to the Eclipse Foundation.
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

package ee.jakarta.tck.faces.test.javaee8.validateWholeBean;

import static java.lang.System.getProperty;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

@ExtendWith(ArquillianExtension.class)
public class Issue4313IT {

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

  @BeforeAll
  static void setUpClass() {
    }

  @AfterAll
  static void tearDownClass() {
    }

  /**
   * @see com.sun.faces.ext.component.UIValidateWholeBean
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4313
   */
  @Test
  void validateWholeBean() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "faces/Issue4313.xhtml");

        HtmlPasswordInput password1 = (HtmlPasswordInput) page.getHtmlElementById("form:password1");
        password1.setValueAttribute("mike@!2016GO");

        HtmlPasswordInput password2 = (HtmlPasswordInput) page.getHtmlElementById("form:password2");
        password2.setValueAttribute("mike@!2015G0");

        HtmlSubmitInput submit = (HtmlSubmitInput) page.getHtmlElementById("form:submit");
        HtmlPage page1 = submit.click();

        assertTrue(page1.getElementById("form:err").getElementsByTagName("li").get(0).asNormalizedText().contains("Password fields must match"), "Validation message not found!");
    }
}