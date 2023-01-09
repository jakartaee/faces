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

package ee.jakarta.tck.faces.test.javaee8.namespacedView_selenium;


import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;

import static org.junit.Assert.assertTrue;


public class Spec790WithNamespacedViewIT extends BaseITNG {



    /**
     * @see NamingContainer
     * @see StateManager#getViewState(jakarta.faces.context.FacesContext)
     * @see https://github.com/jakartaee/faces/issues/790
     */
    @Test
    public void testSpec790WithNamespacedView() throws Exception {

        WebPage page = getPage("spec790WithNamespacedView.xhtml");
        //we do not have the viewroot in the head atm
        String namingContainerPrefix = getNamingContainerId(page);
        WebElement form1 = page.findElement(By.id(namingContainerPrefix + "form1"));
        WebElement form1ViewState = form1.findElement(By.name(namingContainerPrefix +  "jakarta.faces.ViewState"));
        WebElement form2 = page.findElement(By.id(namingContainerPrefix + "form2"));
        WebElement form2ViewState = form2.findElement(By.name( namingContainerPrefix + "jakarta.faces.ViewState"));
        WebElement form3 = page.findElement(By.id((namingContainerPrefix + "form3")));
        WebElement form3ViewState = form3.findElement(By.name( namingContainerPrefix +  "jakarta.faces.ViewState"));
        assertTrue(!form1ViewState.getAttribute("value").isEmpty());
        assertTrue(!form2ViewState.getAttribute("value").isEmpty());
        assertTrue(!form3ViewState.getAttribute("value").isEmpty());

        WebElement form1Button =  page.findElement(By.id(namingContainerPrefix + "form1:button"));
        form1Button.click();
        page.waitReqJs(Duration.ofMillis(3000));
        namingContainerPrefix = getNamingContainerId(page);
        form1 =  page.findElement(By.id(namingContainerPrefix + "form1"));
        form1ViewState =  form1.findElement(By.name( namingContainerPrefix +  "jakarta.faces.ViewState"));
        form2 =  page.findElement(By.id(namingContainerPrefix + "form2"));
        form2ViewState =  form2.findElement(By.name( namingContainerPrefix + "jakarta.faces.ViewState"));
        form3 =  page.findElement(By.id(namingContainerPrefix + "form3"));
        form3ViewState =  form3.findElement(By.name(  namingContainerPrefix + "jakarta.faces.ViewState"));
        assertTrue(!form1ViewState.getAttribute("value").isEmpty());
        assertTrue(!form2ViewState.getAttribute("value").isEmpty());
        assertTrue(!form3ViewState.getAttribute("value").isEmpty());

        WebElement form2Link =  page.findElement(By.id(namingContainerPrefix + "form2:link"));
        form2Link.click();
        page.waitReqJs(Duration.ofMillis(3000));
        namingContainerPrefix = getNamingContainerId(page);
        form1 =  page.findElement(By.id(namingContainerPrefix + "form1"));
        form1ViewState =  form1.findElement(By.name( namingContainerPrefix + "jakarta.faces.ViewState"));
        form2 =  page.findElement(By.id(namingContainerPrefix + "form2"));
        form2ViewState =  form2.findElement(By.name( namingContainerPrefix + "jakarta.faces.ViewState"));
        form3 =  page.findElement(By.id(namingContainerPrefix + "form3"));
        form3ViewState =  form3.findElement(By.name( namingContainerPrefix + "jakarta.faces.ViewState"));
        assertTrue(!form1ViewState.getAttribute("value").isEmpty());
        assertTrue(!form2ViewState.getAttribute("value").isEmpty());
        assertTrue(!form3ViewState.getAttribute("value").isEmpty());

        WebElement form3Link =  page.findElement(By.id(namingContainerPrefix + "form3:link"));
        form3Link.click();
        page.waitReqJs(Duration.ofMillis(3000));
        namingContainerPrefix = getNamingContainerId(page);
        form1 =  page.findElement(By.id(namingContainerPrefix + "form1"));
        form1ViewState =  form1.findElement(By.name( namingContainerPrefix + "jakarta.faces.ViewState"));
        form2 =  page.findElement(By.id(namingContainerPrefix + "form2"));
        form2ViewState =  form2.findElement(By.name( namingContainerPrefix + "jakarta.faces.ViewState"));
        form3 =  page.findElement(By.id(namingContainerPrefix + "form3"));
        form3ViewState =  form3.findElement(By.name( namingContainerPrefix + "jakarta.faces.ViewState"));
        assertTrue(!form1ViewState.getAttribute("value").isEmpty());
        assertTrue(!form2ViewState.getAttribute("value").isEmpty());
        assertTrue(!form3ViewState.getAttribute("value").isEmpty());
    }


    /**
     * @see NamingContainer
     * @see NavigationHandler#handleNavigation(jakarta.faces.context.FacesContext, String, String, String)
     * @see StateManager#getViewState(jakarta.faces.context.FacesContext)
     * @see https://github.com/jakartaee/faces/issues/790
     */
    @Test
    public void testSpec790WithNamespacedViewAjaxNavigation() throws Exception {

        WebPage page = getPage("spec790WithNamespacedViewAjaxNavigation.xhtml");
        
        String namingContainerPrefix = getNamingContainerId(page);
        WebElement form =  page.findElement(By.id(namingContainerPrefix + "form"));
        WebElement formViewState =  form.findElement(By.name( namingContainerPrefix + "jakarta.faces.ViewState"));
        assertTrue(!formViewState.getAttribute("value").isEmpty());

        WebElement button = page.findElement(By.id(namingContainerPrefix + "form:button"));
        button.click();
        page.waitReqJs(Duration.ofMillis(10000));
        
        namingContainerPrefix = getNamingContainerId(page);
        WebElement form1 =  page.findElement(By.id(namingContainerPrefix + "form1"));
        WebElement form1ViewState =  form1.findElement(By.name( namingContainerPrefix + "jakarta.faces.ViewState"));
        WebElement form2 =  page.findElement(By.id(namingContainerPrefix + "form2"));
        WebElement form2ViewState =  form2.findElement(By.name( namingContainerPrefix + "jakarta.faces.ViewState"));
        assertTrue(!form1ViewState.getAttribute("value").isEmpty());
        assertTrue(!form2ViewState.getAttribute("value").isEmpty());
    }

    private static String getNamingContainerId(WebPage page) {
        return page.findElement(By.cssSelector("body > div, body > form")).getAttribute("id").split("(?<=:)", 2)[0];
    }


}
