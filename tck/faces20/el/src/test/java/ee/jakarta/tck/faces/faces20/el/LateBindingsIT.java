/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
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
package ee.jakarta.tck.faces.faces20.el;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class LateBindingsIT extends BaseITNG {

    /**
     * A converter or validator supplied through the {@code binding} attribute is re-resolved on each
     * postback, so the instance that runs can change between requests. Two successive submits invoke
     * the alternating implementations the backing bean hands out.
     *
     * @see jakarta.faces.view.facelets.ConverterHandler
     * @see jakarta.faces.view.facelets.ValidatorHandler
     */
    @Test
    void testLateBindings() {
        WebPage page = getPage("latebindings.xhtml");
        assertFalse(page.containsText("Custom Converter") && page.containsText("Custom Validator"),
                "no converter/validator invoked yet");

        page.guardHttp(page.findElement(By.id("form:submit"))::click);
        assertTrue(page.containsText("CustomConverter1 invoked"), "CustomConverter1 invoked");
        assertTrue(page.containsText("CustomValidator1 invoked"), "CustomValidator1 invoked");
        assertFalse(page.containsText("CustomConverter2 invoked"), "CustomConverter2 not invoked");
        assertFalse(page.containsText("CustomValidator2 invoked"), "CustomValidator2 not invoked");

        page.guardHttp(page.findElement(By.id("form:submit"))::click);
        assertTrue(page.containsText("CustomConverter2 invoked"), "CustomConverter2 invoked");
        assertTrue(page.containsText("CustomValidator2 invoked"), "CustomValidator2 invoked");
        assertFalse(page.containsText("CustomConverter1 invoked"), "CustomConverter1 not invoked");
        assertFalse(page.containsText("CustomValidator1 invoked"), "CustomValidator1 not invoked");
    }
}
