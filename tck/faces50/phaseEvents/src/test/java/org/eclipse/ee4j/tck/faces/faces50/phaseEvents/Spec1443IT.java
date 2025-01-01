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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import jakarta.faces.event.AfterPhase;
import jakarta.faces.event.BeforePhase;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class Spec1443IT extends BaseITNG {

  /**
   * @see AfterPhase
     * @see BeforePhase
     * @see https://github.com/jakartaee/faces/issues/1443
   */
  @Test
  void observedPhases() {
        WebPage page = getPage("spec1443.xhtml");
        WebElement observedPhasesOnInitialRequest = page.findElement(By.id("observedPhases"));
        assertEquals(List.of(
            "beforePhaseDefault: RESTORE_VIEW 1",
            "beforePhaseAny: RESTORE_VIEW 1",
            "beforePhaseRestoreView: RESTORE_VIEW 1",
            "afterPhaseRestoreView: RESTORE_VIEW 1",
            "afterPhaseDefault: RESTORE_VIEW 1",
            "afterPhaseAny: RESTORE_VIEW 1",
            "beforePhaseDefault: RENDER_RESPONSE 6",
            "beforePhaseAny: RENDER_RESPONSE 6",
            "beforePhaseRenderResponse: RENDER_RESPONSE 6").toString(), observedPhasesOnInitialRequest.getText());

        WebElement postback = page.findElement(By.id("form:postback"));
        postback.click();

        WebElement observedPhasesAfterPostback = page.findElement(By.id("observedPhases"));
        assertEquals(List.of(
            "beforePhaseDefault: RESTORE_VIEW 1",
            "beforePhaseAny: RESTORE_VIEW 1",
            "beforePhaseRestoreView: RESTORE_VIEW 1",
            "afterPhaseRestoreView: RESTORE_VIEW 1",
            "afterPhaseDefault: RESTORE_VIEW 1",
            "afterPhaseAny: RESTORE_VIEW 1",
            "beforePhaseDefault: APPLY_REQUEST_VALUES 2",
            "beforePhaseAny: APPLY_REQUEST_VALUES 2",
            "beforePhaseApplyRequestValues: APPLY_REQUEST_VALUES 2",
            "afterPhaseApplyRequestValues: APPLY_REQUEST_VALUES 2",
            "afterPhaseDefault: APPLY_REQUEST_VALUES 2",
            "afterPhaseAny: APPLY_REQUEST_VALUES 2",
            "beforePhaseDefault: PROCESS_VALIDATIONS 3",
            "beforePhaseAny: PROCESS_VALIDATIONS 3",
            "beforePhaseProcessValidations: PROCESS_VALIDATIONS 3",
            "afterPhaseProcessValidations: PROCESS_VALIDATIONS 3",
            "afterPhaseDefault: PROCESS_VALIDATIONS 3",
            "afterPhaseAny: PROCESS_VALIDATIONS 3",
            "beforePhaseDefault: UPDATE_MODEL_VALUES 4",
            "beforePhaseAny: UPDATE_MODEL_VALUES 4",
            "beforePhaseUpdateModelValues: UPDATE_MODEL_VALUES 4",
            "afterPhaseUpdateModelValues: UPDATE_MODEL_VALUES 4",
            "afterPhaseDefault: UPDATE_MODEL_VALUES 4",
            "afterPhaseAny: UPDATE_MODEL_VALUES 4",
            "beforePhaseDefault: INVOKE_APPLICATION 5",
            "beforePhaseAny: INVOKE_APPLICATION 5",
            "beforePhaseInvokeApplication: INVOKE_APPLICATION 5",
            "afterPhaseInvokeApplication: INVOKE_APPLICATION 5",
            "afterPhaseDefault: INVOKE_APPLICATION 5",
            "afterPhaseAny: INVOKE_APPLICATION 5",
            "beforePhaseDefault: RENDER_RESPONSE 6",
            "beforePhaseAny: RENDER_RESPONSE 6",
            "beforePhaseRenderResponse: RENDER_RESPONSE 6").toString(), observedPhasesAfterPostback.getText());
        
    }
}
