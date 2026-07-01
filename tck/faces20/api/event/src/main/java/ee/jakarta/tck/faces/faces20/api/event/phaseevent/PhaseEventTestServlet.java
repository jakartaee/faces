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
package ee.jakarta.tck.faces.faces20.api.event.phaseevent;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import ee.jakarta.tck.faces.util.servlets.HttpTCKServlet;
import ee.jakarta.tck.faces.util.JSFTestUtil;

import jakarta.faces.FactoryFinder;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.PhaseEvent;
import jakarta.faces.event.PhaseId;
import jakarta.faces.lifecycle.LifecycleFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/PhaseEventTestServlet")
public final class PhaseEventTestServlet extends HttpTCKServlet {

  public void phaseEventGetPhaseIdTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter out = response.getWriter();

    FacesContext context = getFacesContext();
    LifecycleFactory factory = (LifecycleFactory) FactoryFinder
        .getFactory(FactoryFinder.LIFECYCLE_FACTORY);

    PhaseEvent pe = new PhaseEvent(context, PhaseId.ANY_PHASE,
        factory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE));

    String golden = PhaseId.ANY_PHASE.getName();
    String result = pe.getPhaseId().getName();
    if (golden.equals(result)) {
      out.println(JSFTestUtil.PASS);

    } else {
      out.println(JSFTestUtil.FAIL
          + " Unexpected result from PhaseEvent.getPhaseId!" + JSFTestUtil.NL
          + "Expected: " + golden + JSFTestUtil.NL + "Received: " + result);
    }

  } // End phaseEventGetPhaseIdTest

  public void phaseEventGetFacesContextTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter out = response.getWriter();

    String summary = "TCKTest";
    FacesContext context = getFacesContext();
    context.addMessage("cid", new FacesMessage(summary));

    LifecycleFactory factory = (LifecycleFactory) FactoryFinder
        .getFactory(FactoryFinder.LIFECYCLE_FACTORY);

    PhaseEvent pe = new PhaseEvent(context, PhaseId.ANY_PHASE,
        factory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE));

    FacesContext myContext = pe.getFacesContext();
    List<FacesMessage> messages = myContext.getMessageList("cid");
    Iterator<FacesMessage> i = messages.iterator();

    while (i.hasNext()) {
      FacesMessage fm = i.next();

      if (summary.equals(fm.getSummary())) {
        out.println(JSFTestUtil.PASS);
        return;

      } else {
        out.println(JSFTestUtil.FAIL
            + " Unable to find Message added to FacesContext!");

      }
    }
  } // End phaseEventGetFacesContextTest

  public void phaseIdGetOrdinalTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter out = response.getWriter();

    // The Faces specification defines PhaseId.getOrdinal() in lifecycle phase order,
    // independent of the declaration order of the (enum) constants.
    StringBuilder errors = new StringBuilder();
    assertOrdinal(PhaseId.ANY_PHASE, 0, errors);
    assertOrdinal(PhaseId.RESTORE_VIEW, 1, errors);
    assertOrdinal(PhaseId.APPLY_REQUEST_VALUES, 2, errors);
    assertOrdinal(PhaseId.PROCESS_VALIDATIONS, 3, errors);
    assertOrdinal(PhaseId.UPDATE_MODEL_VALUES, 4, errors);
    assertOrdinal(PhaseId.INVOKE_APPLICATION, 5, errors);
    assertOrdinal(PhaseId.RENDER_RESPONSE, 6, errors);

    if (errors.length() == 0) {
      out.println(JSFTestUtil.PASS);

    } else {
      out.println(JSFTestUtil.FAIL
          + " Unexpected result from PhaseId.getOrdinal!" + JSFTestUtil.NL + errors);
    }

  } // End phaseIdGetOrdinalTest

  private static void assertOrdinal(PhaseId phaseId, int expected,
      StringBuilder errors) {
    int actual = phaseId.getOrdinal();
    if (actual != expected) {
      errors.append(phaseId.getName()).append(": expected ").append(expected)
          .append(" but received ").append(actual).append(JSFTestUtil.NL);
    }
  }

} // End TestServlet
