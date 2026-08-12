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

package ee.jakarta.tck.faces.faces23.dynamic_components;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;
import jakarta.faces.component.UIViewRoot;

class Issue2475IT extends BaseITNG {

    /**
     * Component resources removed from the view root during preRenderView stay removed and leave the view
     * postbackable: saving the state of a view whose component resources were dropped must not fail.
     *
     * @see UIViewRoot#removeComponentResource(jakarta.faces.context.FacesContext, jakarta.faces.component.UIComponent, String)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2475
     */
    @Test
    void viewStaysPostbackableAfterRemovingComponentResources() {
        WebPage page = getPage("issue2475.xhtml");

        assertEquals(200, page.getResponseStatus(), "initial render");
        assertFalse(page.containsSource("issue2475.js"), "The head component resource must have been removed.");

        page.guardHttp(page.findElement(By.id("form:submit"))::click);

        assertEquals(200, page.getResponseStatus(), "postback");
        assertEquals("submitted", page.findElement(By.id("form:result")).getText(), "The view must still render.");
    }
}
