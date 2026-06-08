/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
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

package ee.jakarta.tck.faces.faces22.composite_component;

import java.io.Serializable;
import java.util.Map;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AbortProcessingException;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.event.ActionListener;
import jakarta.inject.Named;

@Named
@SessionScoped
public class Spec901Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    protected ActionListener a, b, c;

    public Spec901Bean() {
        a = new ActionListenerImpl("a called ");
        b = new ActionListenerImpl("b called ");
        c = new ActionListenerImpl("c called ");
    }

    public ActionListener getLoginEventListener() {
        return a;
    }

    public ActionListener getLoginEventListener2() {
        return b;
    }

    public ActionListener getCancelEventListener() {
        return c;
    }

    private void appendMessage(String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, Object> requestMap = context.getExternalContext().getRequestMap();
        StringBuilder builder = (StringBuilder) requestMap.get("builder");
        if (null == builder) {
            builder = new StringBuilder();
            requestMap.put("builder", builder);
        }
        builder.append(message);
    }

    public String getMessage() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, Object> requestMap = context.getExternalContext().getRequestMap();
        return requestMap.containsKey("builder")
                ? ((StringBuilder) requestMap.get("builder")).toString() : "no message";
    }

    private class ActionListenerImpl implements ActionListener {

        private final String message;

        private ActionListenerImpl(String message) {
            this.message = message;
        }

        @Override
        public void processAction(ActionEvent event) throws AbortProcessingException {
            Spec901Bean.this.appendMessage(message);
        }
    }
}
