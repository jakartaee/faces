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

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * An {@link jakarta.faces.convert.EnumConverter} bound to a selectOneMenu that offers a
 * {@code noSelectionOption} with a {@code null} value must accept the empty submission: submitting
 * "No selection" leaves the value null without raising a "must be convertible to an enum" conversion
 * error.
 */
public class Issue2923IT extends BaseITNG {

    private static final String CONVERSION_ERROR = "must be convertible to an enum.";

    /**
     * Submitting the no-selection option yields a null value and no enum conversion error.
     *
     * @see jakarta.faces.convert.EnumConverter#getAsObject(jakarta.faces.context.FacesContext, jakarta.faces.component.UIComponent, String)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2923
     */
    @Test
    void testNullValueEnumConverter() {
        WebPage page = getPage("issue2923.xhtml");
        assertFalse(page.containsText(CONVERSION_ERROR), "Unexpected conversion error on initial render");

        page.guardHttp(page.findElement(By.id("form:submit"))::click);
        assertFalse(page.containsText(CONVERSION_ERROR), "Unexpected conversion error after submit");
        assertEquals("Selected value = NULL", page.findElement(By.id("form:status")).getText());
    }
}
