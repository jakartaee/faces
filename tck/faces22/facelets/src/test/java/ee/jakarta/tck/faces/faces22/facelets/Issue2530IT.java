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

package ee.jakarta.tck.faces.faces22.facelets;

import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.validator.BeanValidator;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * Verifies that f:validateBean disabled='true' stays effective across multiple consecutive postbacks, so an empty
 * {@code @Size}-constrained field keeps validating successfully on each submit.
 */
class Issue2530IT extends BaseITNG {

    /**
     * Submits the form repeatedly without entering a value; with bean validation disabled on the component, the
     * empty value must pass through every time, so the success message appears after each postback.
     *
     * @see BeanValidator
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2530
     */
    @Test
    void testValidateBeanDisabled() throws Exception {
        WebPage page = getPage("issue2530.xhtml");

        for (int i = 0; i < 4; i++) {
            page.guardHttp(page.findElement(By.id("form:submit"))::click);
            assertTrue(page.containsSource("SUCCESS! Name set to ''"),
                    "Empty value must pass validation on submit number " + (i + 1));
        }
    }
}
