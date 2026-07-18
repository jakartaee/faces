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

package ee.jakarta.tck.faces.faces20.el;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * A {@code @ViewScoped} bean must retain the same instance across a postback of the same view, so
 * EL that resolves to it is not re-evaluated against a freshly created bean.
 */
class Issue1710IT extends BaseITNG {

    /**
     * The bean's identity hash is identical before and after a no-op postback.
     *
     * @see https://github.com/eclipse-ee4j/mojarra/issues/1710
     * @see jakarta.faces.view.ViewScoped
     */
    @Test
    void viewScopedBeanSurvivesPostback() {
        WebPage page = getPage("issue1710.xhtml");
        String before = page.findElement(By.id("form:hash")).getText();

        page.guardHttp(page.findElement(By.id("form:button"))::click);
        String after = page.findElement(By.id("form:hash")).getText();

        assertEquals(before, after, "view scoped bean identity across postback");
    }
}
