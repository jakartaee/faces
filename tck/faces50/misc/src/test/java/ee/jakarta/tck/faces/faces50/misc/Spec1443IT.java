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
package ee.jakarta.tck.faces.faces50.misc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.faces.event.AfterPhase;
import jakarta.faces.event.BeforePhase;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

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
        assertObservedPhases(List.of(
            "beforePhaseDefault: RESTORE_VIEW 1",
            "beforePhaseAny: RESTORE_VIEW 1",
            "beforePhaseRestoreView: RESTORE_VIEW 1",
            "afterPhaseRestoreView: RESTORE_VIEW 1",
            "afterPhaseAny: RESTORE_VIEW 1",
            "afterPhaseDefault: RESTORE_VIEW 1",
            "beforePhaseDefault: RENDER_RESPONSE 6",
            "beforePhaseAny: RENDER_RESPONSE 6",
            "beforePhaseRenderResponse: RENDER_RESPONSE 6"), observedPhasesOnInitialRequest.getText());

        WebElement postback = page.findElement(By.id("form:postback"));
        postback.click();

        WebElement observedPhasesAfterPostback = page.findElement(By.id("observedPhases"));
        assertObservedPhases(List.of(
            "beforePhaseDefault: RESTORE_VIEW 1",
            "beforePhaseAny: RESTORE_VIEW 1",
            "beforePhaseRestoreView: RESTORE_VIEW 1",
            "afterPhaseRestoreView: RESTORE_VIEW 1",
            "afterPhaseAny: RESTORE_VIEW 1",
            "afterPhaseDefault: RESTORE_VIEW 1",
            "beforePhaseDefault: APPLY_REQUEST_VALUES 2",
            "beforePhaseAny: APPLY_REQUEST_VALUES 2",
            "beforePhaseApplyRequestValues: APPLY_REQUEST_VALUES 2",
            "afterPhaseApplyRequestValues: APPLY_REQUEST_VALUES 2",
            "afterPhaseAny: APPLY_REQUEST_VALUES 2",
            "afterPhaseDefault: APPLY_REQUEST_VALUES 2",
            "beforePhaseDefault: PROCESS_VALIDATIONS 3",
            "beforePhaseAny: PROCESS_VALIDATIONS 3",
            "beforePhaseProcessValidations: PROCESS_VALIDATIONS 3",
            "afterPhaseProcessValidations: PROCESS_VALIDATIONS 3",
            "afterPhaseAny: PROCESS_VALIDATIONS 3",
            "afterPhaseDefault: PROCESS_VALIDATIONS 3",
            "beforePhaseDefault: UPDATE_MODEL_VALUES 4",
            "beforePhaseAny: UPDATE_MODEL_VALUES 4",
            "beforePhaseUpdateModelValues: UPDATE_MODEL_VALUES 4",
            "afterPhaseUpdateModelValues: UPDATE_MODEL_VALUES 4",
            "afterPhaseAny: UPDATE_MODEL_VALUES 4",
            "afterPhaseDefault: UPDATE_MODEL_VALUES 4",
            "beforePhaseDefault: INVOKE_APPLICATION 5",
            "beforePhaseAny: INVOKE_APPLICATION 5",
            "beforePhaseInvokeApplication: INVOKE_APPLICATION 5",
            "afterPhaseInvokeApplication: INVOKE_APPLICATION 5",
            "afterPhaseAny: INVOKE_APPLICATION 5",
            "afterPhaseDefault: INVOKE_APPLICATION 5",
            "beforePhaseDefault: RENDER_RESPONSE 6",
            "beforePhaseAny: RENDER_RESPONSE 6",
            "beforePhaseRenderResponse: RENDER_RESPONSE 6"), observedPhasesAfterPostback.getText());

    }

    private static void assertObservedPhases(List<String> expected, String observed) {
        assertEquals(canonicalize(expected), canonicalize(parseList(observed)));
    }

    private static List<String> parseList(String s) {
        return new ArrayList<>(Arrays.asList(s.replaceAll("^\\[|\\]$", "").split(", ")));
    }

    /**
     * Adjacent {@code *Default}/{@code *Any} entries observe the same CDI qualifier — both
     * {@code @AfterPhase} (default) and {@code @AfterPhase(ANY_PHASE)} resolve to the same
     * {@link AfterPhase.Literal#INSTANCE}, so a single fire notifies both, and CDI 4.0
     * §10.4.4 leaves intra-fire order unspecified absent {@code @Priority}. Canonicalize
     * the pair (alphabetical: Any before Default) before comparing.
     */
    private static List<String> canonicalize(List<String> entries) {
        List<String> out = new ArrayList<>(entries);
        for (int i = 0; i + 1 < out.size(); i++) {
            if (isAdjacentDefaultAnyPair(out.get(i), out.get(i + 1))
                    && out.get(i).compareTo(out.get(i + 1)) > 0) {
                Collections.swap(out, i, i + 1);
            }
        }
        return out;
    }

    private static final Pattern ENTRY = Pattern.compile("(.*Phase)(Default|Any)(:.*)");

    private static boolean isAdjacentDefaultAnyPair(String a, String b) {
        Matcher ma = ENTRY.matcher(a);
        Matcher mb = ENTRY.matcher(b);
        return ma.matches() && mb.matches()
            && ma.group(1).equals(mb.group(1))
            && ma.group(3).equals(mb.group(3))
            && !ma.group(2).equals(mb.group(2));
    }
}
