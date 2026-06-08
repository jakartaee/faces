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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import jakarta.faces.component.html.HtmlInputText;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * Verifies that submitting a required h:inputText with a custom converter while leaving it empty fires required
 * validation and does not apply the converted (empty) value to the model.
 */
class Issue3271IT extends BaseITNG {

    /**
     * Submits the form with the required, converter-backed h:inputText left empty and asserts that the model retains
     * its seed value (required validation failed, so the converted empty value was not applied).
     *
     * @see HtmlInputText
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3271
     */
    @Test
    void testInputTextSet() throws Exception {
        WebPage page = getPage("issue3271.xhtml");

        WebElement inputText = page.findElement(By.id("form:inputText"));
        inputText.clear();

        WebElement submit = page.findElement(By.id("form:submit"));
        page.guardHttp(submit::click);

        String result = page.findElement(By.id("form:result")).getText();
        assertFalse(result.contains("No tags"), "Converted empty value must not be applied to the model");
        assertEquals("'seed'", result, "Model must retain its seed value after failed required validation");
    }
}
