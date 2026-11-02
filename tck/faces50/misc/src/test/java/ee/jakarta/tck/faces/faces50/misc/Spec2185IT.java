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

package ee.jakarta.tck.faces.faces50.misc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * {@link jakarta.faces.application.ViewHandler#createViewMap(jakarta.faces.context.FacesContext)} supplies the view map: a new
 * {@link jakarta.faces.component.StateHolder} on each call, not transient, of a public class with a public no-argument constructor.
 * {@link jakarta.faces.component.UIViewRoot} obtains its view map from there.
 */
class Spec2185IT extends BaseITNG {

    /**
     * @see jakarta.faces.application.ViewHandler#createViewMap(jakarta.faces.context.FacesContext)
     * @see https://github.com/jakartaee/faces/issues/2185
     */
    @Test
    void viewMapIsStateHolder() {
        assertEquals("true", text("spec2185.xhtml", "stateHolder"));
    }

    /**
     * @see jakarta.faces.application.ViewHandler#createViewMap(jakarta.faces.context.FacesContext)
     * @see https://github.com/jakartaee/faces/issues/2185
     */
    @Test
    void viewMapIsNotTransient() {
        assertEquals("true", text("spec2185.xhtml", "notTransient"));
    }

    /**
     * @see jakarta.faces.application.ViewHandler#createViewMap(jakarta.faces.context.FacesContext)
     * @see https://github.com/jakartaee/faces/issues/2185
     */
    @Test
    void eachCallCreatesNewViewMap() {
        assertEquals("true", text("spec2185.xhtml", "newMapPerCall"));
    }

    /**
     * @see jakarta.faces.application.ViewHandler#createViewMap(jakarta.faces.context.FacesContext)
     * @see https://github.com/jakartaee/faces/issues/2185
     */
    @Test
    void viewMapIsPublicClassWithPublicNoArgConstructor() {
        assertEquals("true", text("spec2185.xhtml", "publicClassWithPublicNoArgConstructor"));
    }

    /**
     * The view root obtains its view map from a decorated view handler.
     *
     * @see jakarta.faces.component.UIViewRoot#getViewMap(boolean)
     * @see https://github.com/jakartaee/faces/issues/2185
     */
    @Test
    void viewRootObtainsViewMapFromViewHandler() {
        assertEquals("true", text("spec2185-viewroot.xhtml", "viewMapFromViewHandler"));
    }

    /**
     * The view map is restored on postback and reconnects to its view scope, so a view scoped bean survives.
     *
     * @see jakarta.faces.application.ViewHandler#createViewMap(jakarta.faces.context.FacesContext)
     * @see jakarta.faces.component.UIViewRoot#getViewMap(boolean)
     * @see https://github.com/jakartaee/faces/issues/2185
     */
    @Test
    void restoredViewMapReconnectsToViewScope() {
        WebPage page = getPage("spec2185-postback.xhtml");
        String viewScopedId = page.findElement(By.id("viewScopedId")).getText();
        String viewMapClassName = page.findElement(By.id("viewMapClassName")).getText();

        page.guardHttp(page.findElement(By.id("form:submit"))::click);

        assertEquals(viewScopedId, page.findElement(By.id("viewScopedId")).getText());
        assertEquals(viewMapClassName, page.findElement(By.id("viewMapClassName")).getText());
    }

    private String text(String page, String id) {
        WebPage webPage = getPage(page);
        return webPage.findElement(By.id(id)).getText();
    }

}
