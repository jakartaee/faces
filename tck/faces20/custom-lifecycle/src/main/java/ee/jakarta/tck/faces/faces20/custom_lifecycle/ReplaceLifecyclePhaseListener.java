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

package ee.jakarta.tck.faces.faces20.custom_lifecycle;

import java.util.Map;

import jakarta.faces.event.PhaseEvent;
import jakarta.faces.event.PhaseId;
import jakarta.faces.event.PhaseListener;

/**
 * Records, in the request map, that its {@code beforePhase} ran and how many times, so a test can
 * assert the listener still fires exactly once under the replaced lifecycle.
 */
public class ReplaceLifecyclePhaseListener implements PhaseListener {

    private static final long serialVersionUID = 1L;

    static final String BEFORE_PHASE = "beforePhase";
    static final String BEFORE_PHASE_COUNT = "beforePhaseCount";

    @Override
    public void beforePhase(PhaseEvent event) {
        Map<String, Object> requestMap = event.getFacesContext().getExternalContext().getRequestMap();
        requestMap.put(BEFORE_PHASE, BEFORE_PHASE);
        Integer count = (Integer) requestMap.getOrDefault(BEFORE_PHASE_COUNT, 0);
        requestMap.put(BEFORE_PHASE_COUNT, count + 1);
    }

    @Override
    public void afterPhase(PhaseEvent event) {
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }
}
