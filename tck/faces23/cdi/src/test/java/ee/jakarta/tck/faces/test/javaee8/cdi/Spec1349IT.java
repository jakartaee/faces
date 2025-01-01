/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
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

package ee.jakarta.tck.faces.test.javaee8.cdi;

import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

public class Spec1349IT extends BaseITNG {

    /**
     * @see Inject
     * @see FacesConverter
     * @see https://github.com/jakartaee/faces/issues/1349
     */
    @Test
    void injectConverter() throws Exception {
        WebPage page = getPage("faces/injectConverter.xhtml");
        assertTrue(page.findElement(By.id("messages")).getText().contains("InjectConverter#getAsString() was called"));
        WebElement submit = page.findElement(By.id("form:submit"));
        submit.click();
        assertTrue(page.findElement(By.id("messages")).getText().contains("InjectConverter#getAsObject() was called"));
    }

    /**
     * @see Inject
     * @see FacesConverter
     * @see https://github.com/jakartaee/faces/issues/1349
     */
    @Test
    void injectConverter2() throws Exception {
        WebPage page = getPage("faces/injectConverter2.xhtml");
        WebElement submit = page.findElement(By.id("form:submit"));
        submit.click();
        assertTrue(page.getPageSource().contains("InjectConverter2 was called"));
    }

    /**
     * @see Inject
     * @see FacesConverter
     * @see https://github.com/jakartaee/faces/issues/1349
     */
    @Test
    void injectConverter3() throws Exception {
        WebPage page = getPage("faces/injectConverter3.xhtml");
        WebElement submit = page.findElement(By.id("form:submit"));
        submit.click();
        assertTrue(page.getPageSource().contains("InjectConverter3 was called"));
    }
}
