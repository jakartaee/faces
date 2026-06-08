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

package ee.jakarta.tck.faces.faces22.default_validator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * Adding a validator to an editableValueHolder inside a composite component (via f:validator/f:validateLength
 * with for=) must register it exactly once, so a failed validation yields a single message for the composite's input.
 */
class Issue3099IT extends BaseITNG {

    private static final String EXPECTED_MESSAGE = "form:myCompositeInput:input: Validation failed.";

    /**
     * Submits short text so the custom validator fails, then verifies the composite's input produces exactly one
     * validation message (i.e. the validator was not registered twice on the retargeted editableValueHolder).
     *
     * @see jakarta.faces.view.facelets.FaceletContext
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3099
     */
    @Test
    void testDoubleValidator() throws Exception {
        WebPage page = getPage("issue3099.xhtml");
        page.guardHttp(() -> page.findElement(By.id("form:submit")).click());

        String source = page.getSource();
        int first = source.indexOf(EXPECTED_MESSAGE);
        int second = source.indexOf(EXPECTED_MESSAGE, first + EXPECTED_MESSAGE.length());

        assertEquals(-1, second, "Composite input validation must produce exactly one message, not a duplicate");
    }
}
