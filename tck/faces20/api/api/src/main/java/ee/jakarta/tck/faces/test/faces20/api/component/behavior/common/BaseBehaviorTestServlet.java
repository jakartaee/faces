/*
 * Copyright (c) 2011, 2026 Oracle and/or its affiliates. All rights reserved.
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
package ee.jakarta.tck.faces.test.faces20.api.component.behavior.common;

import java.io.IOException;
import java.io.PrintWriter;

import ee.jakarta.tck.faces.test.util.common.util.JSFTestUtil;

import jakarta.faces.component.behavior.BehaviorBase;
import jakarta.faces.event.BehaviorEvent;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class BaseBehaviorTestServlet extends ClientBehaviorBaseTestServlet {

    public void behaviorBroadcastNPETest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        JSFTestUtil.checkForNPE(createBehavior().getClass(), "broadcast",
                new Class<?>[] { BehaviorEvent.class }, new Object[] { null }, out);
    }

    public void behaviorMICInitialStateTest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        BehaviorBase cb = createBehavior();

        cb.markInitialState();
        if (!cb.initialStateMarked()) {
            out.println(JSFTestUtil.FAIL + JSFTestUtil.NL
                    + "Expected State to be true after BehaviorBase.markInitialState() had been called!");
            return;
        }

        cb.clearInitialState();
        if (cb.initialStateMarked()) {
            out.println(JSFTestUtil.FAIL + JSFTestUtil.NL
                    + "Expected State to be false after BehaviorBase.clearInitialState() had been called!");
            return;
        }

        out.println(JSFTestUtil.PASS);
    }

    public void behaviorSITransientTest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        BehaviorBase cb = createBehavior();

        cb.setTransient(true);
        if (!cb.isTransient()) {
            out.println(JSFTestUtil.FAIL + JSFTestUtil.NL
                    + "Unexpected value returned from BehaviorBase.isTransient()!"
                    + JSFTestUtil.NL + "Expected: true" + JSFTestUtil.NL + "Received: " + cb.isTransient());
            return;
        }

        cb.setTransient(false);
        if (cb.isTransient()) {
            out.println(JSFTestUtil.FAIL + JSFTestUtil.NL
                    + "Unexpected value returned from BehaviorBase.isTransient()!"
                    + JSFTestUtil.NL + "Expected: false" + JSFTestUtil.NL + "Received: " + cb.isTransient());
            return;
        }

        out.println(JSFTestUtil.PASS);
    }
}
