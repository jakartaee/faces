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
package ee.jakarta.tck.faces.test.faces20.api.component.behavior.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ee.jakarta.tck.faces.test.faces20.api.common.beans.AlbumBean;
import ee.jakarta.tck.faces.test.faces20.api.common.listener.TCKBehaviorListener;
import ee.jakarta.tck.faces.test.faces20.api.component.behavior.common.BaseBehaviorTestServlet;
import ee.jakarta.tck.faces.test.util.common.util.JSFTestUtil;

import jakarta.el.ExpressionFactory;
import jakarta.el.ValueExpression;
import jakarta.faces.component.UICommand;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.component.behavior.AjaxBehavior;
import jakarta.faces.component.behavior.ClientBehaviorBase;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.event.AjaxBehaviorListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AjaxBehaviorTestServlet")
public class AjaxBehaviorTestServlet extends BaseBehaviorTestServlet {

    @Override
    protected ClientBehaviorBase createBehavior() {
        return new AjaxBehavior();
    }

    public void ajaxBehaviorBroadcastTest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        FacesContext context = getFacesContext();

        AjaxBehavior behavior = (AjaxBehavior) createBehavior();
        UIComponent component = new UICommand();

        AjaxBehaviorEvent event = new AjaxBehaviorEvent(component, behavior);

        TCKBehaviorListener.trace(null);

        behavior.addAjaxBehaviorListener(TCKBehaviorListener.withID("AP0"));
        behavior.addAjaxBehaviorListener(TCKBehaviorListener.withID("AP1"));
        behavior.addAjaxBehaviorListener(TCKBehaviorListener.withID("AP2"));

        UIViewRoot root = new UIViewRoot();
        root.getChildren().add(component);
        component.queueEvent(event);
        root.processDecodes(context);
        root.processValidators(context);
        root.processApplication(context);

        String traceExpected = "/AP0@ANY_PHASE/AP1@ANY_PHASE/AP2@ANY_PHASE";
        String traceReceived = TCKBehaviorListener.trace();
        if (!traceExpected.equals(traceReceived)) {
            out.println(JSFTestUtil.FAIL + JSFTestUtil.NL
                    + "Listeners not invoked in the expected order or were invoked too many times."
                    + JSFTestUtil.NL + "Listener trace expected: " + traceExpected
                    + JSFTestUtil.NL + "Listener trace received: " + traceReceived);
            return;
        }

        out.println(JSFTestUtil.PASS);
    }

    public void ajaxBehaviorAddListenerNPETest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        JSFTestUtil.checkForNPE(createBehavior().getClass(), "addAjaxBehaviorListener",
                new Class<?>[] { AjaxBehaviorListener.class }, new Object[] { null }, out);
    }

    public void ajaxBehaviorGetSetDelayTest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        AjaxBehavior behavior = (AjaxBehavior) createBehavior();

        behavior.setDelay("1000");

        if (!"1000".equals(behavior.getDelay())) {
            out.println(JSFTestUtil.FAIL + " Unexpected value for Delay!"
                    + JSFTestUtil.NL + "Expected: 1000" + JSFTestUtil.NL + "Received: " + behavior.getDelay());
            return;
        }

        behavior.setDelay("none");
        if (!"none".equals(behavior.getDelay())) {
            out.println(JSFTestUtil.FAIL + " Unexpected value for Delay!"
                    + JSFTestUtil.NL + "Expected: none" + JSFTestUtil.NL + "Received: " + behavior.getDelay());
            return;
        }

        out.println(JSFTestUtil.PASS);
    }

    public void ajaxBehaviorGetSetExecuteTest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        AjaxBehavior behavior = (AjaxBehavior) createBehavior();

        List<String> compIds = new ArrayList<>();
        compIds.add("ID0");
        compIds.add("ID1");
        compIds.add("ID2");

        behavior.setExecute(compIds);

        Collection<String> ids = behavior.getExecute();

        for (String id : compIds) {
            if (!ids.contains(id)) {
                out.println(JSFTestUtil.FAIL + "Unexpected result from setExecute or getExecute!"
                        + JSFTestUtil.NL + "Expected: ID0, ID1, ID2"
                        + JSFTestUtil.NL + "Received: " + ids);
                return;
            }
        }

        out.println(JSFTestUtil.PASS);
    }

    public void ajaxBehaviorSetIsDisabledTest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        AjaxBehavior behavior = (AjaxBehavior) createBehavior();

        behavior.setDisabled(true);

        if (!behavior.isDisabled()) {
            out.println(JSFTestUtil.FAIL + " Unexpected value for 'Disabled'!"
                    + JSFTestUtil.NL + "Expected: true" + JSFTestUtil.NL + "Received: " + behavior.isDisabled());
            return;
        }

        out.println(JSFTestUtil.PASS);
    }

    public void ajaxBehaviorSetIsImmediateTest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        AjaxBehavior behavior = (AjaxBehavior) createBehavior();

        behavior.setImmediate(true);

        if (!behavior.isImmediate()) {
            out.println(JSFTestUtil.FAIL + " Unexpected value for 'Immediate'!"
                    + JSFTestUtil.NL + "Expected: true" + JSFTestUtil.NL + "Received: " + behavior.isImmediate());
            return;
        }

        out.println(JSFTestUtil.PASS);
    }

    public void ajaxBehaviorGetSetValueExpressionNPETest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        JSFTestUtil.checkForNPE(createBehavior().getClass(), "getValueExpression",
                new Class<?>[] { String.class }, new Object[] { null }, out);

        ExpressionFactory factory = JSFTestUtil.getExpressionFactory(servletContext);
        ValueExpression literalExpr = factory.createValueExpression(
                getFacesContext().getELContext(), "literalValue", String.class);

        JSFTestUtil.checkForNPE(createBehavior().getClass(), "setValueExpression",
                new Class<?>[] { String.class, ValueExpression.class },
                new Object[] { null, literalExpr }, out);
    }

    public void ajaxBehaviorAddRemoveBehaviorListenerNPETest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        JSFTestUtil.checkForNPE(createBehavior().getClass(), "addAjaxBehaviorListener",
                new Class<?>[] { AjaxBehaviorListener.class }, new Object[] { null }, out);

        JSFTestUtil.checkForNPE(createBehavior().getClass(), "removeAjaxBehaviorListener",
                new Class<?>[] { AjaxBehaviorListener.class }, new Object[] { null }, out);
    }

    public void ajaxBehaviorGetSetOnerrorTest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        AjaxBehavior behavior = (AjaxBehavior) createBehavior();
        String golden = "error.js";

        behavior.setOnerror(golden);
        String result = behavior.getOnerror();

        if (!golden.equals(result)) {
            out.println(JSFTestUtil.FAIL + JSFTestUtil.NL
                    + "Unexpected value returned from getOnerror()" + JSFTestUtil.NL
                    + "Expected: " + golden + JSFTestUtil.NL + "Received: " + result);
            return;
        }

        out.println(JSFTestUtil.PASS);
    }

    public void ajaxBehaviorGetSetOnventTest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        AjaxBehavior behavior = (AjaxBehavior) createBehavior();
        String golden = "TCKSystemEvent";

        behavior.setOnevent(golden);
        String result = behavior.getOnevent();

        if (!golden.equals(result)) {
            out.println(JSFTestUtil.FAIL + JSFTestUtil.NL
                    + "Unexpected value returned from getOnevent()" + JSFTestUtil.NL
                    + "Expected: " + golden + JSFTestUtil.NL + "Received: " + result);
            return;
        }

        out.println(JSFTestUtil.PASS);
    }

    public void ajaxBehaviorGetSetRenderTest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        AjaxBehavior behavior = (AjaxBehavior) createBehavior();
        String golden = "TCKComp";

        Collection<String> rend = new ArrayList<>();
        rend.add(golden);

        behavior.setRender(rend);
        Collection<String> result = behavior.getRender();

        if (!result.contains(golden)) {
            out.println(JSFTestUtil.FAIL + JSFTestUtil.NL
                    + "Unexpected value returned from getRender()" + JSFTestUtil.NL
                    + "Expected: " + golden + JSFTestUtil.NL + "Received: " + result);
            return;
        }

        out.println(JSFTestUtil.PASS);
    }

    public void ajaxBehaviorGetSetValueExpressionTest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        AjaxBehavior behavior = (AjaxBehavior) createBehavior();
        request.setAttribute("lp", new AlbumBean());

        ExpressionFactory factory = JSFTestUtil.getExpressionFactory(servletContext);
        ValueExpression expression = factory.createValueExpression(
                getFacesContext().getELContext(), "#{lp.album}", String.class);

        behavior.setValueExpression("bean", expression);

        if (behavior.getValueExpression("bean") != expression) {
            out.println(JSFTestUtil.FAIL + JSFTestUtil.NL
                    + "Expected getValueExpression() to return the non-literal ValueExpression set via setValueExpression()."
                    + JSFTestUtil.NL + "Expected: " + expression
                    + JSFTestUtil.NL + "Received: " + behavior.getValueExpression("bean"));
            return;
        }

        out.println(JSFTestUtil.PASS);
    }

    public void ajaxBehaviorIsSetResetValuesTest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        AjaxBehavior behavior = (AjaxBehavior) createBehavior();

        behavior.setResetValues(false);

        if (behavior.isResetValues()) {
            out.println(JSFTestUtil.FAIL + " Unexpected value for isResetValues!"
                    + JSFTestUtil.NL + "Expected: false" + JSFTestUtil.NL + "Received: " + behavior.isResetValues());
            return;
        }

        behavior.setResetValues(true);
        if (!behavior.isResetValues()) {
            out.println(JSFTestUtil.FAIL + " Unexpected value for setResetValues!"
                    + JSFTestUtil.NL + "Expected: true" + JSFTestUtil.NL + "Received: " + behavior.isResetValues());
            return;
        }

        if (!behavior.isResetValuesSet()) {
            out.println(JSFTestUtil.FAIL + " Unexpected value for isResetValuesSet!"
                    + JSFTestUtil.NL + "Expected: true" + JSFTestUtil.NL + "Received: " + behavior.isResetValuesSet());
            return;
        }

        out.println(JSFTestUtil.PASS);
    }
}
