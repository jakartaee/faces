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

import static java.lang.System.getProperty;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.net.URL;

import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

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

@ExtendWith(ArquillianExtension.class)
public class Spec1349IT {

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
   * @see Inject
     * @see FacesConverter
     * @see https://github.com/jakartaee/faces/issues/1349
   */
  @Test
  void injectConverter() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "faces/injectConverter.xhtml");
        assertTrue(page.getElementById("messages").getTextContent().contains("InjectConverter#getAsString() was called"));
        HtmlElement submit = page.getHtmlElementById("form:submit");
        page = submit.click();
        assertTrue(page.getElementById("messages").getTextContent().contains("InjectConverter#getAsObject() was called"));
    }

  /**
   * @see Inject
     * @see FacesConverter
     * @see https://github.com/jakartaee/faces/issues/1349
   */
  @Test
  void injectConverter2() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "faces/injectConverter2.xhtml");
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        HtmlElement submit = page.getHtmlElementById("form:submit");
        page = submit.click();
        assertTrue(page.asXml().contains("InjectConverter2 was called"));
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(true);
    }

  /**
   * @see Inject
     * @see FacesConverter
     * @see https://github.com/jakartaee/faces/issues/1349
   */
  @Test
  void injectConverter3() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "faces/injectConverter3.xhtml");
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        HtmlElement submit = page.getHtmlElementById("form:submit");
        page = submit.click();
        assertTrue(page.asXml().contains("InjectConverter3 was called"));
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(true);
    }
}
