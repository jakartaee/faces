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
package ee.jakarta.tck.faces.faces20.view_handler_action_url;

import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.application.ViewHandler;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class ViewHandlerUnitIT extends BaseITNG {

    /**
     * When the current request came in through a prefix mapping, {@code ViewHandler.getActionURL} must prepend the
     * mapping prefix to the view id.
     *
     * @see ViewHandler#getActionURL(jakarta.faces.context.FacesContext, String)
     */
    @Test
    void testGetActionURLPrefixMapping() {
        WebPage page = getPage("faces/viewHandlerActionUrl.xhtml");
        String actionUrl = page.findElement(By.id("form:actionUrl")).getText();
        assertTrue(actionUrl.endsWith("/faces/foo.xhtml"),
                "Prefix mapping must prepend the mapping prefix; got " + actionUrl);
    }

    /**
     * When the current request came in through an extension mapping, {@code ViewHandler.getActionURL} must replace the
     * view id extension with the mapping extension.
     *
     * @see ViewHandler#getActionURL(jakarta.faces.context.FacesContext, String)
     */
    @Test
    void testGetActionURLExtensionMapping() {
        WebPage page = getPage("viewHandlerActionUrl.jsf");
        String actionUrl = page.findElement(By.id("form:actionUrl")).getText();
        assertTrue(actionUrl.endsWith("/foo.jsf"),
                "Extension mapping must replace the view id extension; got " + actionUrl);
    }
}
