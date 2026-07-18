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

package ee.jakarta.tck.faces.faces20.bogus_render_kit_id;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * The application's configured {@code default-render-kit-id} is a bogus, non-existent render kit.
 * A view that overrides its render kit per-view via {@code f:view renderKitId} renders fine and its
 * {@code UIViewRoot} reports the override, while {@code ViewHandler.calculateRenderKitId} still
 * reports the configured default. A view that does NOT override falls back to the bogus configured
 * render kit and fails to render, yielding an HTTP 500.
 */
class BogusRenderKitIdIT extends BaseITNG {

    private static final String CONFIGURED_RENDER_KIT_ID = "org.apache.myfaces.trinidad.coreBAD";

    /**
     * @see jakarta.faces.application.ViewHandler#calculateRenderKitId(jakarta.faces.context.FacesContext)
     * @see jakarta.faces.component.UIViewRoot#getRenderKitId()
     */
    @Test
    void testPerViewRenderKitIdOverride() {
        WebPage page = getPage("bogusRenderKitIdBasic.xhtml");
        assertEquals(CONFIGURED_RENDER_KIT_ID, page.findElement(By.id("form:configured")).getText(),
                "calculateRenderKitId must report the configured default render-kit-id");
        assertEquals("HTML_BASIC", page.findElement(By.id("form:viewRoot")).getText(),
                "the per-view f:view renderKitId override must win for the view root");
    }

    /**
     * @see jakarta.faces.component.UIViewRoot#getRenderKitId()
     * @see jakarta.faces.render.RenderKitFactory
     */
    @Test
    void testConfiguredRenderKitIdFailsToRender() {
        assertEquals(500, getPage("bogusRenderKitIdConfigured.xhtml").getResponseStatus(),
                "a view rendered with the bogus configured render kit must fail to render");
    }
}
