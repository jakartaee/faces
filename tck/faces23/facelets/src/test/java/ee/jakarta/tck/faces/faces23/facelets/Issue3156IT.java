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

import static org.junit.jupiter.api.Assertions.assertFalse;

import jakarta.faces.context.PartialViewContext;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue3156IT extends BaseITNG {

    /**
     * When an ajax request renders only a subset of the view, a {@code ui:repeat} that is not
     * among the render targets must not have its {@code value} expression re-evaluated.
     *
     * @see PartialViewContext#getRenderIds
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3156
     */
    @Test
    void repeatAjaxEvaluate() {
        WebPage page = getPage("issue3156.xhtml");
        page.guardAjax(page.findElement(By.id("form:submit"))::click);
        assertFalse(page.containsText("Evaluated 1"));
    }
}
