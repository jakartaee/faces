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
package ee.jakarta.tck.faces.faces20.api.context.facescontext;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class PostBackIT extends BaseITNG {

    /**
     * FacesContext.isPostback() returns false for an initial GET request.
     *
     * @see jakarta.faces.context.FacesContext#isPostback()
     * @see jakarta.faces.context.FacesContext
     */
    @Test
    void notPostbackOnInitialRequest() {
        WebPage page = getPage("postback1.xhtml");
        assertEquals("PASSED", page.findElement(By.id("result")).getText(), "initial GET must not be a postback");
    }

    /**
     * FacesContext.isPostback() returns true after a form submit.
     *
     * @see jakarta.faces.context.FacesContext#isPostback()
     * @see jakarta.faces.context.FacesContext
     */
    @Test
    void postbackOnFormSubmit() {
        WebPage page = getPage("postback2.xhtml");
        assertEquals("", page.findElement(By.id("form:result")).getText(), "initial render must not be a postback");
        page.guardHttp(page.findElement(By.id("form:submit"))::click);
        assertEquals("PASSED", page.findElement(By.id("form:result")).getText(), "form submit must be a postback");
    }
}
