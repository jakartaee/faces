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

package ee.jakarta.tck.faces.faces23.el;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * The {@code externalContext} EL implicit object resolves to the current
 * {@link jakarta.faces.context.ExternalContext}, i.e. the same instance as
 * {@code facesContext.externalContext}.
 */
public class Spec1322IT extends BaseITNG {

    /**
     * {@code #{externalContext}} is the current request's ExternalContext and exposes its public
     * contract (here {@code requestContextPath}).
     *
     * @see jakarta.faces.context.ExternalContext
     * @see https://github.com/jakartaee/faces/issues/1322
     */
    @Test
    void testExternalContextImplicitObject() {
        WebPage page = getPage("spec1322.xhtml");
        assertEquals("true", page.findElement(By.id("isExternalContext")).getText());
        assertEquals(getContextPath(), page.findElement(By.id("contextPath")).getText());
    }
}
