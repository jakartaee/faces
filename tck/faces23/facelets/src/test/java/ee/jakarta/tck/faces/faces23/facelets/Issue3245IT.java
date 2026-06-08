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

package ee.jakarta.tck.faces.faces23.facelets;

import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.component.UIInput;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue3245IT extends BaseITNG {

    /**
     * A validator added programmatically to an input component must survive in the component
     * state across postbacks: once added it fires its required message on every subsequent
     * submit, not just the first.
     *
     * @see UIInput#addValidator(jakarta.faces.validator.Validator)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3245
     */
    @Test
    void addValidator() {
        WebPage page = getPage("issue3245.xhtml");

        page.guardHttp(page.findElement(By.id("form:add"))::click);

        page.guardHttp(page.findElement(By.id("form:submit"))::click);
        assertTrue(page.containsText("Validation Error: Value is required."));

        page.guardHttp(page.findElement(By.id("form:submit"))::click);
        assertTrue(page.containsText("Validation Error: Value is required."));
    }
}
