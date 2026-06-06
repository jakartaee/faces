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

package ee.jakarta.tck.faces.faces22.phase_listener;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIForm;
import jakarta.faces.component.UIPanel;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.PhaseEvent;
import jakarta.faces.event.PhaseId;
import jakarta.faces.event.PhaseListener;

public class Issue2375PhaseListener implements PhaseListener {

    private static final long serialVersionUID = 1L;

    private boolean afterInvokeAppPhase = false;

    @Override
    public void afterPhase(PhaseEvent event) {
        FacesContext context = event.getFacesContext();
        if (!ViewIds.isRequestedView(context, "transientParent2375.xhtml")) {
            return;
        }

        if (event.getPhaseId() == PhaseId.INVOKE_APPLICATION) {
            afterInvokeAppPhase = true;
            addMessage(context, "ia", event.getPhaseId());
        }
        else if (event.getPhaseId() == PhaseId.RESTORE_VIEW && afterInvokeAppPhase) {
            addMessage(context, "rv", event.getPhaseId());
            afterInvokeAppPhase = false;
        }
    }

    @Override
    public void beforePhase(PhaseEvent event) {
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }

    private void addMessage(FacesContext context, String clientId, PhaseId phaseId) {
        String existence = transientSubTreeExists(context) ? "Exists" : "Does Not Exist";
        String message = " After " + phaseId + " Transient Subtree " + existence;
        context.addMessage(clientId, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
    }

    private boolean transientSubTreeExists(FacesContext context) {
        UIViewRoot root = context.getViewRoot();
        UIForm form = (UIForm) root.findComponent("helloForm");
        if (form == null) {
            return false;
        }
        UIPanel panel = (UIPanel) form.findComponent("addto");
        return panel != null && !panel.getChildren().isEmpty();
    }
}
