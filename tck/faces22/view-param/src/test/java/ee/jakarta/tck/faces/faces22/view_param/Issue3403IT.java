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

package ee.jakarta.tck.faces.faces22.view_param;

import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.component.UIViewParameter;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * Submitting the source view redirects via a navigation-case with a redirect-param, and the target view's
 * f:viewParam receives the redirected parameter and renders it.
 */
class Issue3403IT extends BaseITNG {

    /**
     * Verifies that a non-ajax command submit follows a redirect navigation-case carrying a redirect-param
     * (info=Welcome), and that the target view's {@link UIViewParameter} ("info") makes the value available so
     * the page renders "Welcome".
     *
     * @see UIViewParameter
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3403
     */
    @Test
    void testViewParamRedirect() throws Exception {
        WebPage page = getPage("issue3403.xhtml");

        WebElement submit = page.findElement(By.id("form:submit"));
        page.guardHttp(submit::click);

        assertTrue(page.containsText("Welcome"), "Target view renders the redirected view parameter value");
    }
}
