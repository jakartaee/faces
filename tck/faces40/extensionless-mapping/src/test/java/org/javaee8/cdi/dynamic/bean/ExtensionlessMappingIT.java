/*
 * Copyright (c) 2021 Contributors to Eclipse Foundation.
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
package org.javaee8.cdi.dynamic.bean;

import static java.lang.System.getProperty;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import jakarta.faces.application.ViewHandler;
import jakarta.faces.webapp.FacesServlet;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
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

/**
 *
 * @author Arjan Tijms
 *
 */
@ExtendWith(ArquillianExtension.class)
public class ExtensionlessMappingIT {

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
   * @see FacesServlet#AUTOMATIC_EXTENSIONLESS_MAPPING_PARAM_NAME
     * @see ViewHandler#getViews(jakarta.faces.context.FacesContext, String, jakarta.faces.application.ViewVisitOption...)
     * @see https://github.com/jakartaee/faces/issues/1508
   */
  @Test
  @RunAsClient
  void extensionlessMappingFoo() throws IOException {
        HtmlPage page = webClient.getPage(webUrl + "foo");
        String content = page.asXml();

        System.out.println("\nContent for `"+ webUrl + "foo" + "` :\n" + content + "\n");

        assertTrue(content.contains("This is page foo"));

        assertTrue(page.getElementById("barxhtmllink").getAttribute("href").endsWith("/bar"));
        assertTrue(page.getElementById("barlink").getAttribute("href").endsWith("/bar"));
    }

  /**
   * @see FacesServlet#AUTOMATIC_EXTENSIONLESS_MAPPING_PARAM_NAME
     * @see ViewHandler#getViews(jakarta.faces.context.FacesContext, String, jakarta.faces.application.ViewVisitOption...)
     * @see https://github.com/jakartaee/faces/issues/1508
   */
  @Test
  @RunAsClient
  void extensionlessMappingBar() throws IOException {
        HtmlPage page = webClient.getPage(webUrl + "bar");
        String content = page.asXml();

        System.out.println("\nContent for `"+ webUrl + "bar" + "` :\n" + content + "\n");

        assertTrue(content.contains("This is page bar"));
    }

  /**
   * @see FacesServlet#AUTOMATIC_EXTENSIONLESS_MAPPING_PARAM_NAME
     * @see ViewHandler#getViews(jakarta.faces.context.FacesContext, String, jakarta.faces.application.ViewVisitOption...)
     * @see https://github.com/jakartaee/faces/issues/1508
   */
  @Test
  @RunAsClient
  void extensionlessMappingSubBar() throws IOException {
        HtmlPage page = webClient.getPage(webUrl + "sub/bar");
        String content = page.asXml();

        System.out.println("\nContent for `"+ webUrl + "sub/bar" + "` :\n" + content + "\n");

        assertTrue(content.contains("This is page sub-bar"));
    }

}
