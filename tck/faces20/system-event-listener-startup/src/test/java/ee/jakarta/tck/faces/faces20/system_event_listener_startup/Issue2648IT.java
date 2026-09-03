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

package ee.jakarta.tck.faces.faces20.system_event_listener_startup;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.faces.component.UIViewRoot;
import jakarta.faces.event.SystemEventListener;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * The module registers a SystemEventListener in faces-config whose constructor reaches for the view map of the current view root. A listener declared this way
 * is instantiated for the whole application, hence the dedicated module.
 */
class Issue2648IT extends BaseITNG {

    /**
     * A SystemEventListener declared in faces-config is constructed while the application is still starting, when there is no request and hence no view root to
     * take a view map from. The application must nevertheless deploy and serve its views.
     *
     * @see SystemEventListener
     * @see UIViewRoot#getViewMap()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2648
     */
    @Test
    void applicationStartsAndServesViewsWithSuchAListener() {
        WebPage page = getPage("issue2648.xhtml");

        assertEquals(200, page.getResponseStatus(), "initial render");
        assertEquals("served", page.findElement(By.id("form:result")).getText(), "The view must be served.");

        page.guardHttp(page.findElement(By.id("form:submit"))::click);

        assertEquals(200, page.getResponseStatus(), "postback");
        assertEquals("served", page.findElement(By.id("form:result")).getText(), "The view must survive a postback.");
    }

}
