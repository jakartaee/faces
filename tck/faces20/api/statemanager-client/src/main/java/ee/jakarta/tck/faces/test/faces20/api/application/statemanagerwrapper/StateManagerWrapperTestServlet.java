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
package ee.jakarta.tck.faces.test.faces20.api.application.statemanagerwrapper;

import java.io.IOException;
import java.io.PrintWriter;

import ee.jakarta.tck.faces.test.util.common.servlets.HttpTCKServlet;

import jakarta.faces.application.StateManager;
import jakarta.faces.application.StateManagerWrapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/StateManagerWrapperTestServlet")
public final class StateManagerWrapperTestServlet extends HttpTCKServlet {

    public void stateManagerIsSavingStateInClientTest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        StateManager manager = new SimpleStateManagerWrapper(
                getApplication().getStateManager());
        out.println(manager.isSavingStateInClient(getFacesContext()));
    }

    private static class SimpleStateManagerWrapper extends StateManagerWrapper {

        private final StateManager manager;

        SimpleStateManagerWrapper(StateManager manager) {
            super(manager);
            if (manager == null) {
                throw new IllegalArgumentException("StateManager argument cannot be null.");
            }
            this.manager = manager;
        }

        @Override
        public StateManager getWrapped() {
            return manager;
        }
    }
}
