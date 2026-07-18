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

package ee.jakarta.tck.faces.faces20.custom_view_handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * A custom {@code ViewHandler} whose {@code deriveViewId} appends the {@code .xhtml} suffix lets an
 * extensionless request resolve to the corresponding Facelet.
 */
class Issue3488IT extends BaseITNG {

    /**
     * @see jakarta.faces.application.ViewHandler#deriveViewId(jakarta.faces.context.FacesContext, String)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3488
     */
    @Test
    void testViewId() {
        WebPage page = getPage("faces/issue3488");
        assertEquals("This text should be displayed", page.findElement(By.id("message")).getText(),
                "the extensionless request must resolve to issue3488.xhtml via the custom ViewHandler");
    }
}
