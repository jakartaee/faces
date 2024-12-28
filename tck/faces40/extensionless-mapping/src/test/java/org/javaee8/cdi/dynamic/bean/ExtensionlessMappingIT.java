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

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import jakarta.faces.application.ViewHandler;
import jakarta.faces.webapp.FacesServlet;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.junit.jupiter.api.Test;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;

/**
 *
 * @author Arjan Tijms
 *
 */
public class ExtensionlessMappingIT extends ITBase {

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
