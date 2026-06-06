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

package ee.jakarta.tck.faces.faces23.flash;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * A flash value put on the source view survives navigation to the target view for each of the four navigation
 * component types: {@code h:button}, {@code h:link}, {@code h:commandLink} and {@code h:commandButton}. The
 * target view renders the surviving flash value, which must read {@code foo = bar} in every case.
 */
class Issue1282IT extends BaseITNG {

    private static final String EXPECTED_RESULT = "foo = bar";

    /**
     * Clicks the {@code h:button} (GET navigation carrying the value as a request parameter bound into
     * {@code flash.now}) and verifies the flash value survives onto the target view.
     *
     * @see jakarta.faces.context.Flash
     * @see https://github.com/eclipse-ee4j/mojarra/issues/1282
     */
    @Test
    void testFlashSurvivesButton() {
        assertFlashSurvives("nextForm:nextButton");
    }

    /**
     * Clicks the {@code h:link} (GET navigation carrying the value as a request parameter bound into
     * {@code flash.now}) and verifies the flash value survives onto the target view.
     *
     * @see jakarta.faces.context.Flash
     * @see https://github.com/eclipse-ee4j/mojarra/issues/1282
     */
    @Test
    void testFlashSurvivesLink() {
        assertFlashSurvives("nextForm:nextLink");
    }

    /**
     * Clicks the {@code h:commandLink} (POST navigation redirected, with the flash bridging the redirect) and
     * verifies the flash value survives onto the target view.
     *
     * @see jakarta.faces.context.Flash
     * @see https://github.com/eclipse-ee4j/mojarra/issues/1282
     */
    @Test
    void testFlashSurvivesCommandLink() {
        assertFlashSurvives("nextForm:nextCommandLink");
    }

    /**
     * Clicks the {@code h:commandButton} (POST navigation redirected, with the flash bridging the redirect) and
     * verifies the flash value survives onto the target view.
     *
     * @see jakarta.faces.context.Flash
     * @see https://github.com/eclipse-ee4j/mojarra/issues/1282
     */
    @Test
    void testFlashSurvivesCommandButton() {
        assertFlashSurvives("nextForm:nextCommandButton");
    }

    private void assertFlashSurvives(String componentId) {
        WebPage page = getPage("issue1282.xhtml");
        page.guardHttp(() -> page.findElement(By.id(componentId)).click());
        assertEquals(EXPECTED_RESULT, page.findElement(By.id("result")).getText(),
                "Flash value must survive navigation via " + componentId);
    }
}
