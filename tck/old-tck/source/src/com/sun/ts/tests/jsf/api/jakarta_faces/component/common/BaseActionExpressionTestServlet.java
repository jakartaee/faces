/*
 * Copyright (c) 2022 Contributors to Eclipse Foundation.
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
package com.sun.ts.tests.jsf.api.jakarta_faces.component.common;

import java.io.IOException;
import java.io.PrintWriter;

import com.sun.ts.tests.jsf.common.util.JSFTestUtil;

import jakarta.el.ExpressionFactory;
import jakarta.el.ELManager;
import jakarta.el.MethodExpression;
import jakarta.faces.component.ActionSource;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class BaseActionExpressionTestServlet
    extends BaseActionSourceTestServlet {

  private ServletContext servletContext;

  /**
   * <p>
   * Initialize this <code>Servlet</code> instance.
   * </p>
   *
   * @param config
   *          the configuration for this <code>Servlet</code>
   *
   * @throws jakarta.servlet.ServletException
   *           indicates initialization failure
   */
  public void init(ServletConfig config) throws ServletException {

    servletContext = config.getServletContext();
    super.init(config);

  } // init

  // ------------------------------------------------------------ Test Methods

  public void actionSourceGetSetActionExpressionTest(
      HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    PrintWriter out = response.getWriter();
    request.setAttribute("bean", new SimpleBean());
    ExpressionFactory factory = ELManager.getExpressionFactory();
    MethodExpression expression = factory.createMethodExpression(
        getFacesContext().getELContext(), "#{bean.action}",
        java.lang.String.class, new Class[] {});

    ActionSource source = (ActionSource) createComponent();

    source.setActionExpression(expression);

    if (source.getActionExpression() != expression) {
      out.println(JSFTestUtil.FAIL + " Unexpected return value from"
          + " getActionExpression() after having just called"
          + " setActionExpression().");
      out.println("Expected: " + expression);
      out.println("Received: " + source.getActionExpression());
      return;
    }

    out.println(JSFTestUtil.PASS);

  } // END actionSourceGetSetMethodExpressionTest

  // ----------------------------------------------------------- Inner Classes

  private static class SimpleBean {

    public String getAction() {

      return "action";

    } // END getAction

  } // END SimpleBean
}
