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
package ee.jakarta.tck.faces.faces50.facelets;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * The view rendered by a postback reflects the build time decisions as they stand at render time, also when the invoked action builds another view in the same
 * request.
 *
 * @see <a href="https://github.com/eclipse-ee4j/mojarra/issues/6189">issue 6189</a>
 */
class Issue6189IT extends BaseITNG {

    private WebPage page;

    /**
     * The include follows the step after each ajax postback which advances it.
     */
    @Test
    void testIncludeFollowsStep() {
        assertIncludeFollowsStep("form:next");
    }

    /**
     * The include follows the step after each ajax postback which advances it and builds another view.
     */
    @Test
    void testIncludeFollowsStepWhenActionBuildsOtherView() {
        assertIncludeFollowsStep("form:nextAndBuildOtherView");
    }

    private void assertIncludeFollowsStep(String button) {
        page = getPage("issue6189-include.xhtml");
        assertStep("step 1");

        page.guardAjax(page.findElement(By.id(button))::click);
        assertStep("step 2");

        page.guardAjax(page.findElement(By.id(button))::click);
        assertStep("step 3");
    }

    private void assertStep(String expected) {
        assertEquals(expected, page.findElement(By.id("form:step")).getText(), "EL");
        assertEquals(expected, page.findElement(By.id("form:include")).getText(), "include");
    }

}
