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
package ee.jakarta.tck.faces.test.faces20.webapp.factoryfinderaltlast;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.faces.FactoryFinder;
import jakarta.faces.context.FacesContextFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter pw = response.getWriter();
        try {
            FacesContextFactory fcf = (FacesContextFactory) FactoryFinder
                    .getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
            String factName = fcf.getWrapped().getClass().getCanonicalName();

            if (factName.contains("TCKContextFactoryTwo")) {
                pw.println("Test PASSED");
            } else {
                pw.println("Test FAILED. Wrong FacesContextFactory Being Used");
                pw.println("Found: " + factName);
                pw.println("Expected: TCKContextFactoryTwo");
            }
        } catch (Exception e) {
            pw.println("Test FAILED.");
            pw.println(e.toString());
        }
    }
}
