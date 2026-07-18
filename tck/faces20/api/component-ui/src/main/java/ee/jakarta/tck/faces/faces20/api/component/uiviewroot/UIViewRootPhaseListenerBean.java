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
package ee.jakarta.tck.faces.faces20.api.component.uiviewroot;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.event.PhaseEvent;
import jakarta.faces.event.PhaseId;
import jakarta.inject.Named;

/**
 * Target of the {@code beforePhase} and {@code afterPhase} method expressions of the view, logging
 * each phase it is invoked for. Request scoped on purpose: each log covers exactly one lifecycle
 * run, so a postback's log is not polluted by the initial request's.
 */
// The name is explicit because CDI derives it by lowercasing only the first character, which would
// yield the easily mistyped "uIViewRootPhaseListenerBean".
@Named("uiViewRootPhaseListenerBean")
@RequestScoped
public class UIViewRootPhaseListenerBean {

    private final StringBuilder beforePhases = new StringBuilder();
    private final StringBuilder afterPhases = new StringBuilder();

    public void beforePhase(PhaseEvent event) {
        log(beforePhases, event);
    }

    public void afterPhase(PhaseEvent event) {
        log(afterPhases, event);
    }

    private static void log(StringBuilder phases, PhaseEvent event) {
        PhaseId phaseId = event.getPhaseId();

        if (phases.length() > 0) {
            phases.append(' ');
        }

        phases.append(phaseId.getName()).append(' ').append(phaseId.getOrdinal());
    }

    public String getBeforePhases() {
        return beforePhases.toString();
    }

    public String getAfterPhases() {
        return afterPhases.toString();
    }
}
