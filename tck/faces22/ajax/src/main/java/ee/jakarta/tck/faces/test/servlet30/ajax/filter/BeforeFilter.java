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

package ee.jakarta.tck.faces.test.servlet30.ajax.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Constructor;

import jakarta.faces.context.PartialResponseWriter;
import jakarta.faces.context.ResponseWriter;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;

public class BeforeFilter implements Filter {
    
    private FilterConfig filterConfig = null;
    
    public BeforeFilter() {
    }    
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
        
        try {
            HttpServletResponse resp = (HttpServletResponse) response;
            PrintWriter pw = resp.getWriter();

            ResponseWriter responseWriter;
            Class htmlResponseWriterClass = Class.forName("com.sun.faces.renderkit.html_basic.HtmlResponseWriter");
            Constructor ctor = htmlResponseWriterClass.getConstructor(Writer.class, String.class, String.class);
            responseWriter = (ResponseWriter) ctor.newInstance(pw, "text/xml", "UTF-8");
            PartialResponseWriter partialResponseWriter = new PartialResponseWriter(responseWriter);
//            partialResponseWriter.writePreamble("<?xml version='1.0' encoding='UTF-8'?>\n");
            partialResponseWriter.startDocument();
            partialResponseWriter.startUpdate("foo");
            partialResponseWriter.endUpdate();
            partialResponseWriter.endDocument();
            partialResponseWriter.close();
        } catch (Exception t) {
            HttpServletResponse resp = (HttpServletResponse) response;
            PrintWriter pw = resp.getWriter();
            try {
                pw.print("<html><body><p id=\"result\">FAILURE</p>");
                int indentLevel = 0;
                String indent;
                Throwable cause = t;
                do {
                    StringBuilder indentBuilder = new StringBuilder();
                    for (int i = 0; i < indentLevel; i++) {
                        indentBuilder.append("&nbsp;&nbsp;");
                    }
                    indent = indentBuilder.toString();
                    pw.print("<p>" + indent + " Exception: " + cause.getClass().getName() + "</p>");
                    pw.print("<p>" + indent + " Exception Message: " + cause.getLocalizedMessage() + "</p>");
                        pw.print("<code><pre>");
                        cause.printStackTrace(pw);
                        pw.print("</pre></code>");
                } while (null != (cause = cause.getCause()));
                pw.print("</body></html>");
                resp.setStatus(200);
                pw.close();
            } catch (Exception e) {
            }
        }
        
    }

    public void destroy() {        
    }

    public void init(FilterConfig filterConfig) {        
        this.filterConfig = filterConfig;
    }
    
}
