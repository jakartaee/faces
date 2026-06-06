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

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * Navigating via an action with faces-redirect=true and includeViewParams=true carries the f:viewParam value to
 * the redirected target view.
 */
class Issue2264IT extends BaseITNG {

    /**
     * Submits a form whose value is bound to the same property as an f:viewParam, then navigates by an action
     * outcome carrying faces-redirect=true and includeViewParams=true. The view parameter must be appended to
     * the redirect URL so the target view, declaring a matching f:viewParam, renders the submitted value.
     *
     * @see jakarta.faces.component.UIViewParameter
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2264
     */
    @Test
    void testViewParamPassing() throws Exception {
        WebPage page = getPage("issue2264.xhtml");

        WebElement textField = page.findElement(By.id("form:input"));
        textField.clear();
        textField.sendKeys("testing");

        WebElement button = page.findElement(By.id("form:button"));
        page.guardHttp(button::click);

        assertTrue(page.containsText("testing"), "View parameter value is carried to the redirected target view");
    }
}
