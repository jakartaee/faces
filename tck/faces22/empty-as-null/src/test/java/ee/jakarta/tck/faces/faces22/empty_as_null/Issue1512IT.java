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

package ee.jakarta.tck.faces.faces22.empty_as_null;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * When {@code jakarta.faces.INTERPRET_EMPTY_STRING_SUBMITTED_VALUES_AS_NULL} is
 * {@code true}, an empty submitted value is interpreted as {@code null} but the
 * registered validator is still invoked.
 */
class Issue1512IT extends BaseITNG {

    /**
     * @see jakarta.faces.validator.Validator
     * @see https://github.com/eclipse-ee4j/mojarra/issues/1512
     */
    @Test
    void testValidateEmptyFields() throws Exception {
        WebPage page = getPage("issue1512.xhtml");

        WebElement button = page.findElement(By.id("form:submitButton"));
        page.guardHttp(button::click);

        assertTrue(page.containsText("We got called!"));
    }
}
