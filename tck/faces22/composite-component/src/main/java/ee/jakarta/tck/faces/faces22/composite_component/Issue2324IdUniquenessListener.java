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

package ee.jakarta.tck.faces.faces22.composite_component;

import java.util.HashSet;
import java.util.Set;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.application.FacesMessage.Severity;
import jakarta.faces.component.visit.VisitContext;
import jakarta.faces.component.visit.VisitResult;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.PhaseEvent;
import jakarta.faces.event.PhaseId;
import jakarta.faces.event.PhaseListener;

/**
 * Asserts, over the fully built view, that no two components share a client id. Client id uniqueness across the view is equivalent to component id uniqueness
 * within each naming container, so this expresses the specification requirement using only public API.
 */
public class Issue2324IdUniquenessListener implements PhaseListener {

    private static final long serialVersionUID = 1L;

    static final String UNIQUE = "IDS ARE UNIQUE";
    static final String DUPLICATE = "DUPLICATE CLIENT ID: ";

    @Override
    public void beforePhase(PhaseEvent event) {
        FacesContext context = event.getFacesContext();
        Set<String> clientIds = new HashSet<>();
        Set<String> duplicates = new HashSet<>();

        context.getViewRoot().visitTree(VisitContext.createVisitContext(context), (visitContext, target) -> {
            if (!clientIds.add(target.getClientId(context))) {
                duplicates.add(target.getClientId(context));
            }

            return VisitResult.ACCEPT;
        });

        if (duplicates.isEmpty()) {
            context.addMessage(null, new FacesMessage(UNIQUE));
        }
        else {
            for (String duplicate : duplicates) {
                context.addMessage(null, new FacesMessage(Severity.ERROR, DUPLICATE + duplicate, null));
            }
        }
    }

    @Override
    public void afterPhase(PhaseEvent event) {
        // NOOP.
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }

}
