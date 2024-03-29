/*
 * Copyright (c) 2009, 2020 Oracle and/or its affiliates. All rights reserved.
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
 * $Id$
 */
package com.sun.ts.tests.jsf.api.jakarta_faces.application.applicationWrapperISE;

import java.io.IOException;
import java.io.PrintWriter;

import com.sun.ts.tests.jsf.common.resolver.TCKELResolver;
import com.sun.ts.tests.jsf.common.servlets.HttpTCKServlet;
import com.sun.ts.tests.jsf.common.statemanager.TCKStateManager;
import com.sun.ts.tests.jsf.common.util.JSFTestUtil;
import com.sun.ts.tests.jsf.common.viewhandler.TCKViewHandler;

import jakarta.el.ELResolver;
import jakarta.faces.application.Application;
import jakarta.faces.application.ApplicationWrapper;
import jakarta.faces.application.ResourceHandler;
import jakarta.faces.application.StateManager;
import jakarta.faces.application.ViewHandler;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TestServlet extends HttpTCKServlet {

  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    config.getServletContext();
  }

  public void applicationWrapperAddELResolverISETest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    Application application = new TCKAppWrapper().getWrapped();

    JSFTestUtil.checkForISE(application, "addELResolver",
        new Class<?>[] { ELResolver.class },
        new Object[] { new TCKELResolver() }, out);

  }

  // Test for ApplicationWrapper.setResourceHandler() throws
  // IllegalStateException
  public void applicationWrapperSetResourceHandlerISETest(
      HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    Application application = new TCKAppWrapper().getWrapped();

    JSFTestUtil.checkForISE(application, "setResourceHandler",
        new Class<?>[] { ResourceHandler.class },
        new Object[] { application.getResourceHandler() }, out);
  }

  // Test for ApplicationWrapper.setStateManager() throws
  // IllegalStateException
  public void applicationWrapperSetStateManagerISETest(
      HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    Application application = new TCKAppWrapper().getWrapped();

    JSFTestUtil.checkForISE(application, "setStateManager",
        new Class<?>[] { StateManager.class },
        new Object[] { new TCKStateManager(application.getStateManager()) }, out);
  }

  public void applicationWrapperSetViewHandlerISETest(
      HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    Application application = new TCKAppWrapper().getWrapped();

    JSFTestUtil.checkForISE(application, "setViewHandler",
        new Class<?>[] { ViewHandler.class },
        new Object[] { new TCKViewHandler() }, out);

  }

  // ------------------------ Private Implementations ------------------------

  private class TCKAppWrapper extends ApplicationWrapper {

    @Override
    public Application getWrapped() {
      return getApplication();
    }

  }

}
