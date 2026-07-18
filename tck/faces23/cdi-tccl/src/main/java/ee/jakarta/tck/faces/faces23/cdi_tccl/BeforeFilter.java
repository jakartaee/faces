/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
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
package ee.jakarta.tck.faces.faces23.cdi_tccl;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLClassLoader;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Wraps every request in a thread context class loader that is a fresh child of the original. The
 * request must still complete normally: if CDI resolution is not resilient to a replaced TCCL, the
 * downstream Faces render throws and this filter renders a FAILURE marker instead.
 */
public class BeforeFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        Thread thread = Thread.currentThread();
        ClassLoader tccl = thread.getContextClassLoader();
        thread.setContextClassLoader(new URLClassLoader(new URL[0], tccl));

        try {
            chain.doFilter(request, response);
        } catch (Exception t) {
            HttpServletResponse resp = (HttpServletResponse) response;
            PrintWriter pw = resp.getWriter();
            pw.print("<html><body><p id=\"result\">FAILURE</p>");
            Throwable cause = t;
            do {
                pw.print("<p>Exception: " + cause.getClass().getName() + "</p>");
                pw.print("<p>Exception Message: " + cause.getLocalizedMessage() + "</p>");
            } while ((cause = cause.getCause()) != null);
            pw.print("</body></html>");
            resp.setStatus(200);
            pw.close();
        } finally {
            thread.setContextClassLoader(tccl);
        }
    }
}
