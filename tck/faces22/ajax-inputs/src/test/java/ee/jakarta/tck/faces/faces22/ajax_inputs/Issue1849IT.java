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

package ee.jakarta.tck.faces.faces22.ajax_inputs;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * Verifies that the state of an f:ajax behavior on a selectBooleanCheckbox, which conditionally renders a
 * command button, survives ajax updates and a full non-ajax postback reload while the bean is held in a CDI
 * conversation.
 */
class Issue1849IT extends BaseITNG {

    /**
     * Toggling the checkbox via f:ajax renders the conditional command button into the panelGroup, and that
     * rendered state is preserved across a non-ajax reload because showButton lives in a CDI conversation;
     * toggling it back removes the button, again surviving a reload.
     *
     * @see jakarta.faces.component.behavior.AjaxBehavior
     * @see https://github.com/eclipse-ee4j/mojarra/issues/1849
     */
    @Test
    void testIssue1849() throws Exception {
        WebPage page = getPage("issue1849.xhtml");
        assertFalse(page.containsSource("Click Me"), "Button is not rendered initially");

        WebElement checkbox = page.findElement(By.id("myForm:buttonCheckbox"));
        page.guardAjax(checkbox::click);

        WebElement reload = page.findElement(By.id("myForm:reload"));
        page.guardHttp(reload::click);
        assertTrue(page.containsSource("Click Me"), "Button is rendered and survives non-ajax reload");

        checkbox = page.findElement(By.id("myForm:buttonCheckbox"));
        page.guardAjax(checkbox::click);

        reload = page.findElement(By.id("myForm:reload"));
        page.guardHttp(reload::click);
        assertFalse(page.containsSource("Click Me"), "Button is removed and absence survives non-ajax reload");
    }
}
