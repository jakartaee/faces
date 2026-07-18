/*
 * Copyright (c) 2026 Contributors to Eclipse Foundation.
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
package ee.jakarta.tck.faces.faces22.never_unwrap_exceptions;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.faces.webapp.FacesServlet;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

/**
 * Wraps {@link FacesServlet} to capture the {@link ServletException} it throws when a bean getter fails during
 * rendering, so the test can assert that Faces did not unwrap the original exception chain.
 */
public class CatchExceptionServlet implements Servlet {

    private final FacesServlet wrapped = new FacesServlet();

    @Override
    public void init(ServletConfig config) throws ServletException {
        wrapped.init(config);
    }

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        try {
            wrapped.service(request, response);
        } catch (ServletException e) {
            if (!response.isCommitted()) {
                response.reset();
            }
            response.setContentType("text/plain");
            PrintWriter writer = response.getWriter();
            writer.println("Exception class: " + e.getClass().getName());
            writer.println("Root cause: " + (e.getCause() == null ? "null" : e.getCause().getClass().getName()));
            writer.println("Exception message: " + e.getMessage());
        }
    }

    @Override
    public ServletConfig getServletConfig() {
        return wrapped.getServletConfig();
    }

    @Override
    public String getServletInfo() {
        return wrapped.getServletInfo();
    }

    @Override
    public void destroy() {
        wrapped.destroy();
    }
}
