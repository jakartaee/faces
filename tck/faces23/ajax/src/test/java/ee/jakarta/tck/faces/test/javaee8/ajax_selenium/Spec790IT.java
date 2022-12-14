/*
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
import jakarta.faces.application.NavigationHandler;
import jakarta.faces.application.StateManager;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;

import static org.junit.Assert.assertTrue;


public class Spec790IT extends BaseITNG {



    /**
     * @see StateManager#getViewState(jakarta.faces.context.FacesContext)
     * @see https://github.com/jakartaee/faces/issues/790
     */
    @Test
    public void testSpec790() throws Exception {

        WebPage page = getPage("spec790.xhtml");
        WebElement form1 = page.findElement(By.id("form1"));

        ExtendedTextInput form1ViewState = new ExtendedTextInput( getWebDriver(), form1.findElement(By.name("jakarta.faces.ViewState")));
        WebElement form2 = page.findElement(By.id("form2"));
        ExtendedTextInput form2ViewState =  new ExtendedTextInput( getWebDriver(), form2.findElement(By.name("jakarta.faces.ViewState")));
        WebElement form3 =  page.findElement(By.id("form3"));
        ExtendedTextInput form3ViewState = form2ViewState =  new ExtendedTextInput( getWebDriver(), form3.findElement(By.name("jakarta.faces.ViewState")));
        assertTrue(!form1ViewState.getValue().isEmpty());
        assertTrue(!form2ViewState.getValue().isEmpty());
        assertTrue(!form3ViewState.getValue().isEmpty());

        WebElement form1Button = page.findElement(By.id("form1:button"));
        form1Button.click();
        page.waitReqJs(Duration.ofMillis(6000));

        form1 =  page.findElement(By.id("form1"));
        form1ViewState = new ExtendedTextInput( getWebDriver(), form1.findElement(By.name("jakarta.faces.ViewState")));
        form2 =  page.findElement(By.id("form2"));
        form2ViewState = new ExtendedTextInput( getWebDriver(), form2.findElement(By.name("jakarta.faces.ViewState")));
        form3 =  page.findElement(By.id("form3"));
        form3ViewState = new ExtendedTextInput( getWebDriver(), form3.findElement(By.name("jakarta.faces.ViewState")));
        assertTrue(!form1ViewState.getValue().isEmpty());
        assertTrue(!form2ViewState.getValue().isEmpty());
        assertTrue(!form3ViewState.getValue().isEmpty());

        WebElement form2Link =  page.findElement(By.id("form2:link"));
        form2Link.click();
        page.waitReqJs(Duration.ofMillis(60000));
        form1 =  page.findElement(By.id("form1"));
        form1ViewState = new ExtendedTextInput( getWebDriver(), form1.findElement(By.name("jakarta.faces.ViewState")));
        form2 =  page.findElement(By.id("form2"));
        form2ViewState = new ExtendedTextInput( getWebDriver(),form2.findElement(By.name("jakarta.faces.ViewState")));
        form3 =  page.findElement(By.id("form3"));
        form3ViewState = new ExtendedTextInput( getWebDriver(),form3.findElement(By.name("jakarta.faces.ViewState")));
        assertTrue(!form1ViewState.getValue().isEmpty());
        assertTrue(!form2ViewState.getValue().isEmpty());
        assertTrue(!form3ViewState.getValue().isEmpty());

        WebElement form3Link =  page.findElement(By.id("form3:link"));
        form3Link.click();
        page.waitReqJs(Duration.ofMillis(60000));
        form1 =  page.findElement(By.id("form1"));
        form1ViewState = new ExtendedTextInput( getWebDriver(), form1.findElement(By.name("jakarta.faces.ViewState")));
        form2 =  page.findElement(By.id("form2"));
        form2ViewState = new ExtendedTextInput( getWebDriver(), form2.findElement(By.name("jakarta.faces.ViewState")));
        form3 =  page.findElement(By.id("form3"));
        form3ViewState = new ExtendedTextInput( getWebDriver(), form3.findElement(By.name("jakarta.faces.ViewState")));
        assertTrue(!form1ViewState.getValue().isEmpty());
        assertTrue(!form2ViewState.getValue().isEmpty());
        assertTrue(!form3ViewState.getValue().isEmpty());
    }

    /**
     * @see NavigationHandler#handleNavigation(jakarta.faces.context.FacesContext, String, String, String)
     * @see StateManager#getViewState(jakarta.faces.context.FacesContext)
     * @see https://github.com/jakartaee/faces/issues/790
     */
    @Test
    public void testSpec790AjaxNavigation() throws Exception {
        

        WebPage page = getPage("spec790AjaxNavigation.xhtml");
        WebElement form =  page.findElement(By.id("form"));
        ExtendedTextInput formViewState = new ExtendedTextInput( getWebDriver(), form.findElement(By.name("jakarta.faces.ViewState")));
        assertTrue(!formViewState.getValue().isEmpty());

        WebElement button = page.findElement(By.id("form:button"));
        button.click();
        page.waitReqJs(Duration.ofMillis(60000));
        System.out.println(page.getRequestData());
        System.out.println(page.getResponseBody());
        System.out.println(page.getPageSource());
        WebElement form1 =  page.findElement(By.id("form1"));
        ExtendedTextInput form1ViewState = new ExtendedTextInput( getWebDriver(), form1.findElement(By.name("jakarta.faces.ViewState")));
        WebElement form2 =  page.findElement(By.id("form2"));
        ExtendedTextInput form2ViewState = new ExtendedTextInput( getWebDriver(), form2.findElement(By.name("jakarta.faces.ViewState")));
        assertTrue(!form1ViewState.getValue().isEmpty());
        assertTrue(!form2ViewState.getValue().isEmpty());
    }



}
