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
package ee.jakarta.tck.faces.faces20.api.context.facescontext;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.faces.context.FacesContext;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class FacesContextGetRenderKitIT extends BaseITNG {

    /**
     * Verifies the {@code FacesContext.getRenderKit} contract: a null or unknown render kit id on the view root yields a
     * null RenderKit, while the HTML_BASIC render kit id yields a non-null RenderKit.
     *
     * @see FacesContext#getRenderKit()
     * @see jakarta.faces.render.RenderKitFactory#HTML_BASIC_RENDER_KIT
     */
    @Test
    void testGetRenderKit() {
        WebPage page = getPage("facesContextRenderKit.xhtml");
        assertEquals("SUCCESS", page.findElement(By.id("form:result")).getText(),
                "FacesContext.getRenderKit contract must hold for null, unknown and HTML_BASIC render kit ids");
    }
}
