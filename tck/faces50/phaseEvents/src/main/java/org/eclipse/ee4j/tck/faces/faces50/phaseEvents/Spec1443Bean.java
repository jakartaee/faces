/*
 * Copyright (c) 2024 Contributors to Eclipse Foundation.
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
package org.eclipse.ee4j.tck.faces.faces50.phaseEvents;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Observes;
import jakarta.faces.event.AfterPhase;
import jakarta.faces.event.BeforePhase;
import jakarta.faces.event.PhaseEvent;
import jakarta.faces.event.PhaseId;
import jakarta.inject.Named;

@Named
@RequestScoped
public class Spec1443Bean {

    private List<String> observedPhases = new ArrayList<>();

    public void observeBeforePhaseDefault(@Observes @BeforePhase PhaseEvent event) {
        observedPhases.add("beforePhaseDefault: " + event.getPhaseId());
    }

    public void observeBeforePhaseRestoreView(@Observes @BeforePhase(PhaseId.RESTORE_VIEW) PhaseEvent event) {
        observedPhases.add("beforePhaseRestoreView: " + event.getPhaseId());
    }

    public void observeBeforePhaseApplyRequestValues(@Observes @BeforePhase(PhaseId.APPLY_REQUEST_VALUES) PhaseEvent event) {
        observedPhases.add("beforePhaseApplyRequestValues: " + event.getPhaseId());
    }

    public void observeBeforePhaseProcessValidations(@Observes @BeforePhase(PhaseId.PROCESS_VALIDATIONS) PhaseEvent event) {
        observedPhases.add("beforePhaseProcessValidations: " + event.getPhaseId());
    }

    public void observeBeforePhaseInvokeApplication(@Observes @BeforePhase(PhaseId.INVOKE_APPLICATION) PhaseEvent event) {
        observedPhases.add("beforePhaseInvokeApplication: " + event.getPhaseId());
    }

    public void observeBeforePhaseUpdateModelValues(@Observes @BeforePhase(PhaseId.UPDATE_MODEL_VALUES) PhaseEvent event) {
        observedPhases.add("beforePhaseUpdateModelValues: " + event.getPhaseId());
    }

    public void observeBeforePhaseRenderResponse(@Observes @BeforePhase(PhaseId.RENDER_RESPONSE) PhaseEvent event) {
        observedPhases.add("beforePhaseRenderResponse: " + event.getPhaseId());
    }

    public void observeBeforePhaseAny(@Observes @BeforePhase(PhaseId.ANY_PHASE) PhaseEvent event) {
        observedPhases.add("beforeBeforePhaseAny: " + event.getPhaseId());
    }

    public void observeAfterPhaseDefault(@Observes @AfterPhase PhaseEvent event) {
        observedPhases.add("afterPhaseDefault: " + event.getPhaseId());
    }

    public void observeAfterPhaseRestoreView(@Observes @AfterPhase(PhaseId.RESTORE_VIEW) PhaseEvent event) {
        observedPhases.add("afterPhaseRestoreView: " + event.getPhaseId());
    }

    public void observeAfterPhaseApplyRequestValues(@Observes @AfterPhase(PhaseId.APPLY_REQUEST_VALUES) PhaseEvent event) {
        observedPhases.add("afterPhaseApplyRequestValues: " + event.getPhaseId());
    }

    public void observeAfterPhaseProcessValidations(@Observes @AfterPhase(PhaseId.PROCESS_VALIDATIONS) PhaseEvent event) {
        observedPhases.add("afterPhaseProcessValidations: " + event.getPhaseId());
    }

    public void observeAfterPhaseInvokeApplication(@Observes @AfterPhase(PhaseId.INVOKE_APPLICATION) PhaseEvent event) {
        observedPhases.add("afterPhaseInvokeApplication: " + event.getPhaseId());
    }

    public void observeAfterPhaseUpdateModelValues(@Observes @AfterPhase(PhaseId.UPDATE_MODEL_VALUES) PhaseEvent event) {
        observedPhases.add("afterPhaseUpdateModelValuesL " + event.getPhaseId());
    }

    public void observeAfterPhaseRenderResponse(@Observes @AfterPhase(PhaseId.RENDER_RESPONSE) PhaseEvent event) {
        observedPhases.add("afterPhaseRenderResponseL " + event.getPhaseId());
    }

    public void observeAfterPhaseAny(@Observes @AfterPhase(PhaseId.ANY_PHASE) PhaseEvent event) {
        observedPhases.add("afterPhaseAny: " + event.getPhaseId());
    }

    public List<String> getObservedPhases() {
        return observedPhases;
    }
}
