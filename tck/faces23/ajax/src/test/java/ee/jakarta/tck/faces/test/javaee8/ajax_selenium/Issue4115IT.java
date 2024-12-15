/*
 * Copyright (c) 2021 Contributors to the Eclipse Foundation.
 * Copyright (c) 1997, 2021 Oracle and/or its affiliates. All rights reserved.
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

package ee.jakarta.tck.faces.test.javaee8.ajax_selenium;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.ExtendedTextInput;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;
import jakarta.faces.component.behavior.AjaxBehavior;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

public class Issue4115IT extends BaseITNG {

    /**
     * @see AjaxBehavior#getExecute()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4119
     */
    @Test
    public void testSpec1412() throws Exception {

        WebPage page = getPage("issue4115.xhtml");
        assertTrue(page.findElement(By.id("form:output")).getText().isEmpty());

        ExtendedTextInput input = new ExtendedTextInput(getWebDriver(), page.findElement(By.id("form:input")));
        input.setValue("execute");
        WebElement link =  page.findElement(By.id("form:link"));
        page.guardAjax(link::click);
        assertTrue(page.findElement(By.id("form:output")).getText().trim().equals("executeParamValue"));
    }
}
