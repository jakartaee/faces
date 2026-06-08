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

package ee.jakarta.tck.faces.faces23.facelets;

import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.PhaseEvent;
import jakarta.faces.event.PhaseId;
import jakarta.faces.event.PhaseListener;
import jakarta.inject.Named;

@Named
@RequestScoped
public class Issue2852Bean {

    private final PhaseListener listener = new PhaseListener() {
        private static final long serialVersionUID = 1L;

        @Override
        public void afterPhase(PhaseEvent event) {
        }

        @Override
        public void beforePhase(PhaseEvent event) {
        }

        @Override
        public PhaseId getPhaseId() {
            return PhaseId.ANY_PHASE;
        }
    };

    public void submit() {
        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot viewRoot = context.getViewRoot();
        List<PhaseListener> listeners = viewRoot.getPhaseListeners();

        if (listeners == null || listeners.isEmpty()) {
            String message = "ERROR: No listeners registered";
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
        }
        else if (listeners.size() > 1) {
            String message = "ERROR: Expected one registered listener but found: " + listeners.size();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
        }
    }

    public PhaseListener getListener() {
        return listener;
    }
}
