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
package ee.jakarta.tck.faces.faces20.el;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.faces.event.AbortProcessingException;
import jakarta.faces.event.ValueChangeEvent;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue2401IT extends BaseITNG {

    /**
     * A RuntimeException thrown from a ValueChangeListener must not be swallowed:
     * it propagates and results in a 500 response.
     *
     * @see ValueChangeEvent
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2401
     */
    @Test
    void exceptionDuringValueChangeEL() {
        WebPage page = getPage("issue2401-valuechange.xhtml");
        page.findElement(By.id("form:input")).sendKeys("changed");
        page.findElement(By.id("form:submit")).click();
        assertEquals(500, page.getResponseStatus());
    }

    /**
     * A RuntimeException thrown from an ActionListener method expression must not be
     * swallowed: it propagates and results in a 500 response.
     *
     * @see jakarta.faces.event.ActionListener
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2401
     */
    @Test
    void exceptionDuringMethodExpressionEL() {
        WebPage page = getPage("issue2401-methodexpression.xhtml");
        page.findElement(By.id("form:submit")).click();
        assertEquals(500, page.getResponseStatus());
    }

    /**
     * An AbortProcessingException thrown from an ActionListener method expression must be
     * swallowed: processing continues and results in a normal 200 response.
     *
     * @see AbortProcessingException
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2401
     */
    @Test
    void abortDuringMethodExpressionEL() {
        WebPage page = getPage("issue2401-abort.xhtml");
        page.guardHttp(page.findElement(By.id("form:submit"))::click);
        assertEquals(200, page.getResponseStatus());
    }
}
