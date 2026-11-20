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

package ee.jakarta.tck.faces.faces50.view_scope;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * The implementation supplies the view map through a CDI bean named {@link jakarta.faces.component.UIViewRoot#VIEW_MAP_NAME}: a {@code @Dependent} bean whose
 * bean types include {@link java.util.Map}, and whose instances implement {@link jakarta.faces.component.StateHolder} and are not transient.
 */
class Spec2185IT extends BaseITNG {

    /**
     * @see jakarta.faces.component.UIViewRoot#VIEW_MAP_NAME
     * @see jakarta.faces.component.UIViewRoot#getViewMap(boolean)
     * @see https://github.com/jakartaee/faces/issues/2185
     */
    @Test
    void viewMapBeanIsDependent() {
        assertEquals("true", text("dependent"));
    }

    /**
     * @see jakarta.faces.component.UIViewRoot#VIEW_MAP_NAME
     * @see jakarta.faces.component.UIViewRoot#getViewMap(boolean)
     * @see https://github.com/jakartaee/faces/issues/2185
     */
    @Test
    void viewMapBeanTypesIncludeMap() {
        assertEquals("true", text("map"));
    }

    /**
     * @see jakarta.faces.component.UIViewRoot#VIEW_MAP_NAME
     * @see jakarta.faces.component.UIViewRoot#getViewMap(boolean)
     * @see https://github.com/jakartaee/faces/issues/2185
     */
    @Test
    void viewMapIsStateHolder() {
        assertEquals("true", text("stateHolder"));
    }

    /**
     * @see jakarta.faces.component.UIViewRoot#VIEW_MAP_NAME
     * @see jakarta.faces.component.UIViewRoot#getViewMap(boolean)
     * @see https://github.com/jakartaee/faces/issues/2185
     */
    @Test
    void viewMapIsNotTransient() {
        assertEquals("true", text("notTransient"));
    }

    /**
     * @see jakarta.faces.component.UIViewRoot#VIEW_MAP_NAME
     * @see jakarta.faces.component.UIViewRoot#getViewMap(boolean)
     * @see https://github.com/jakartaee/faces/issues/2185
     */
    @Test
    void eachReferenceIsNewViewMap() {
        assertEquals("true", text("newMapPerReference"));
    }

    private String text(String id) {
        WebPage page = getPage("spec2185.xhtml");
        return page.findElement(By.id(id)).getText();
    }

}
