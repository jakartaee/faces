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
package ee.jakarta.tck.faces.test.faces20.api.validator.common;

import java.io.IOException;
import java.io.PrintWriter;

import ee.jakarta.tck.faces.test.util.common.util.JSFTestUtil;

import jakarta.faces.component.PartialStateHolder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class PartialStateHolderTestServlet extends BaseStateHolderTestServlet {

    public void validatorPartialStateTest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        PartialStateHolder psh;
        if (createValidator() instanceof PartialStateHolder) {
            psh = (PartialStateHolder) createValidator();
        } else {
            out.println("The Specific Validator that you are trying to test "
                    + "does not implement the PartialStateHolder interface!");
            return;
        }

        psh.markInitialState();
        if (!psh.initialStateMarked()) {
            out.println("Test FAILED." + JSFTestUtil.NL
                    + "Unexpected state returned when calling "
                    + "initialStateMarked after markInitialState() was called"
                    + JSFTestUtil.NL + "Expected: true" + JSFTestUtil.NL + "Received: false");
            return;
        }

        psh.clearInitialState();
        if (psh.initialStateMarked()) {
            out.println("Test FAILED." + JSFTestUtil.NL
                    + "Unexpected state returned when calling "
                    + "IntialStateMarked() after clearInitialState() was called"
                    + JSFTestUtil.NL + "Expected: false" + JSFTestUtil.NL + "Received: true");
            return;
        }

        out.println(JSFTestUtil.PASS);
    }
}
