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

package ee.jakarta.tck.faces.faces20.api.application.statemanager;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ee.jakarta.tck.faces.util.JSFTestUtil;
import ee.jakarta.tck.faces.util.servlets.HttpTCKServlet;

@WebServlet("/StateManagerTestServlet")
public final class StateManagerTestServlet extends HttpTCKServlet {

    /**
     * <p>
     * Initializes this {@link jakarta.servlet.Servlet}.
     * </p>
     *
     * @param config this Servlet's configuration
     * @throws jakarta.servlet.ServletException if an error occurs
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    // ---------------------------------------------------------------- Test
    // Methods

    // Validation of return value will be performed on the client side.
    public void stateManagerIsSavingStateInClientTest(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();
        out.println(
            getApplication().getStateManager()
                .isSavingStateInClient(getFacesContext())
        );
    }

    // Validate NPE is thrown when context is null
    public void stateManagerIsSavingStateInClientNPETest(
        HttpServletRequest request, HttpServletResponse response
    )
        throws ServletException, IOException
    {

        PrintWriter out = response.getWriter();

        try {
            getApplication().getStateManager().isSavingStateInClient(null);
            out.println("Test FAILED  No Exception Thrown!");

        }
        catch (NullPointerException npe) {
            out.println(JSFTestUtil.PASS);
        }
        catch (Exception e) {
            out.println(
                "Test FAILED  Unexpected Exception thrown." + JSFTestUtil.NL
                    + "Expected: NullPointerException" + JSFTestUtil.NL + "Received: "
                    + JSFTestUtil.NL + e.toString()
            );
        }
    }

}
