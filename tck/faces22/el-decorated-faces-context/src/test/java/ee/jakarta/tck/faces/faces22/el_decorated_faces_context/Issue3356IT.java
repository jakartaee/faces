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

package ee.jakarta.tck.faces.faces22.el_decorated_faces_context;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * When a faces-config-declared factory decorates the faces context and the exception handler, the
 * EL implicit object must expose the decorators, not the objects they wrap.
 */
public class Issue3356IT extends BaseITNG {

    /**
     * #{facesContext} resolves to the decorated faces context and #{facesContext.exceptionHandler}
     * to the decorated exception handler.
     *
     * @see jakarta.faces.context.FacesContextWrapper
     * @see jakarta.faces.context.ExceptionHandlerWrapper
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3356
     */
    @Test
    void elFacesContextResolvesToDecoratedContext() {
        WebPage page = getPage("issue3356.xhtml");

        assertEquals(Issue3356FacesContext.class.getSimpleName(), page.findElement(By.id("facesContext")).getText());
        assertEquals(Issue3356ExceptionHandler.class.getSimpleName(), page.findElement(By.id("exceptionHandler")).getText());
    }
}
