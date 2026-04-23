/*
 * Copyright (c) 2009, 2026 Oracle and/or its affiliates. All rights reserved.
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
package ee.jakarta.tck.faces.test.faces20.api.application.resourcehandlerEx;

import java.io.IOException;
import java.io.PrintWriter;

import ee.jakarta.tck.faces.test.util.common.servlets.HttpTCKServlet;
import ee.jakarta.tck.faces.test.util.common.util.JSFTestUtil;

import jakarta.faces.application.ResourceHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ResourceHandlerExTestServlet")
public final class ResourceHandlerExTestServlet extends HttpTCKServlet {

  // Validate that a resource with the given excluded extension is listed in the
  // ResourceHandler.RESOURCE_EXCLUDES_DEFAULT_VALUE constant.
  private void checkExcluded(String extension, PrintWriter out) {
    String excludes = ResourceHandler.RESOURCE_EXCLUDES_DEFAULT_VALUE;

    if (excludes != null && excludes.contains(extension)) {
      out.println(JSFTestUtil.PASS);

    } else {
      out.println(JSFTestUtil.FAIL + JSFTestUtil.NL
          + "Expected extension '" + extension + "' to be present in "
          + "ResourceHandler.RESOURCE_EXCLUDES_DEFAULT_VALUE!"
          + JSFTestUtil.NL + "Received: " + excludes);
    }
  }

  public void resourceHandlerExcludePropertiesTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    checkExcluded(".properties", response.getWriter());
  }

  public void resourceHandlerExcludeClassTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    checkExcluded(".class", response.getWriter());
  }

  public void resourceHandlerExcludeJSPXTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    checkExcluded(".jspx", response.getWriter());
  }

  public void resourceHandlerExcludeJSPTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    checkExcluded(".jsp", response.getWriter());
  }

  public void resourceHandlerExcludeXHTMLTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    checkExcluded(".xhtml", response.getWriter());
  }

}
