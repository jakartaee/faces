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

package ee.jakarta.tck.faces.faces23.el;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue3836IT extends BaseITNG {

    /**
     * A user-supplied value that happens to be an EL expression and is reflected into an h:link
     * outcome query parameter must not be re-evaluated as EL. Submitting #{issue3836Bean.forbidden}
     * must therefore not leak the bean's "Forbidden value." into the rendered link href.
     *
     * @see jakarta.faces.component.html.HtmlOutcomeTargetLink
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3836
     */
    @Test
    void testDoubleEscapeQueryParamForbidden() {
        WebPage page = getPage("issue3836.xhtml");

        WebElement text = page.findElement(By.id("text"));
        text.sendKeys("#{issue3836Bean.forbidden}");

        page.guardHttp(page.findElement(By.id("submit"))::click);

        WebElement link = page.findElement(By.id("link"));
        assertFalse(link.getDomAttribute("href").contains("Forbidden value."));
    }
}
