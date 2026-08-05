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

package ee.jakarta.tck.faces.faces23.passthrough;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;
import jakarta.faces.view.facelets.TagDecorator;

class Issue3029IT extends BaseITNG {

    /**
     * A plain HTML element decorated with jsf:id keeps a class attribute of its own: promoting the element
     * to a component must not let class collide with the styleClass the renderer writes out.
     *
     * @see TagDecorator
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3029
     */
    @Test
    void classAttributeSurvivesPromotionToAComponent() {
        WebPage page = getPage("issue3029.xhtml");

        assertEquals(200, page.getResponseStatus(), "The view must render.");
        assertEquals("myclass", page.findElement(By.id("bar")).getDomAttribute("class"),
                "The class attribute must be rendered verbatim on the promoted element.");
    }
}
