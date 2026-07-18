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

package ee.jakarta.tck.faces.faces22.view_action_view_params;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.event.ActionListener;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * Actions and action listeners for the view action outcome and broadcast tests.
 */
@Named
@RequestScoped
public class Spec758Bean {

    @Inject
    private Spec758TraceBean traceBean;

    private String message;

    /**
     * Outcome resolving to another view, so the view action must navigate away by redirecting.
     */
    public String action() {
        return "spec758-result";
    }

    /**
     * Outcome resolving to a case which explicitly declares a redirect back to the very same view.
     */
    public String explicitRedirect() {
        return "spec758-explicit-redirect";
    }

    /**
     * The empty outcome must not navigate, hence the view renders itself with this message.
     */
    public String returnEmpty() {
        message = "empty action";
        return "";
    }

    /**
     * The null outcome must not navigate, hence the view renders itself with this message.
     */
    public String returnNull() {
        message = "null action";
        return null;
    }

    public ActionListener getActionListener1() {
        return traceListener("1");
    }

    public ActionListener getActionListener2() {
        return traceListener("2");
    }

    public void actionListenerMethod(ActionEvent event) {
        traceListener("method").processAction(event);
    }

    private ActionListener traceListener(String listenerId) {
        return event -> traceBean.append(listenerId, event.getComponent().getId());
    }

    public String getMessage() {
        return message;
    }
}
