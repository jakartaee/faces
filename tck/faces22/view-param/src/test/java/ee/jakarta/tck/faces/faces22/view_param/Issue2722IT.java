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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.component.html.HtmlOutcomeTargetButton;
import jakarta.faces.component.html.HtmlOutcomeTargetLink;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * Verifies that an h:link/h:button rendered with includeViewParams="true" carries the f:viewParam values
 * (input1=1 and input2=2) of the current view into the generated query string when navigating between views.
 */
class Issue2722IT extends BaseITNG {

    private static final String QUERY_STRING = "input1=1&input2=2";

    /**
     * Navigates from the start page via an h:link whose outcome supplies the request parameters input1=1 and
     * input2=2, which the target view picks up via f:viewParam. The target view then exposes an h:button with
     * includeViewParams="true", whose generated outcome URL must carry those same view parameters back into the
     * query string, and a subsequent navigation to the next view must again render the parameters.
     *
     * @see HtmlOutcomeTargetLink
     * @see HtmlOutcomeTargetButton
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2722
     */
    @Test
    void testIncludeViewParams() {
        WebPage page = getPage("issue2722.xhtml");

        WebElement link = page.findElement(By.id("link"));
        page.guardHttp(link::click);

        assertEquals("1", page.findElement(By.id("text1")).getText(), "input1 view param picked up on second page");
        assertEquals("2", page.findElement(By.id("text2")).getText(), "input2 view param picked up on second page");
        assertTrue(page.getSource().contains(QUERY_STRING),
                "Second page's includeViewParams button carries the view params in its query string");

        WebElement firstButton = page.findElement(By.id("firstButton"));
        page.guardHttp(firstButton::click);

        assertEquals("1", page.findElement(By.id("text1")).getText(), "input1 view param carried over to third page");
        assertEquals("2", page.findElement(By.id("text2")).getText(), "input2 view param carried over to third page");
        assertTrue(page.getSource().contains(QUERY_STRING),
                "Third page's includeViewParams button carries the view params in its query string");
    }
}
