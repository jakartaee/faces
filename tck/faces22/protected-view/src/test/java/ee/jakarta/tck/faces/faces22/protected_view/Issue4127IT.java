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
package ee.jakarta.tck.faces.faces22.protected_view;

import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.application.ViewHandler;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

public class Issue4127IT extends BaseITNG {

    /**
     * @see ViewHandler#restoreView(jakarta.faces.context.FacesContext, String)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4127
     */
    @Test
    void page2ReachableViaLinkWithFParamToken() throws Exception {
        WebPage page = getPage("issue4127-page1.xhtml");
        WebElement link = page.findElement(By.id("get_parameter_fparam"));
        link.click();

        String pageXml = page.getSource();
        assertTrue(pageXml.contains("foo bar"));
    }

    /**
     * @see ViewHandler#restoreView(jakarta.faces.context.FacesContext, String)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4127
     */
    @Test
    void page2ReachableViaLinkWithOutcomeAttribute() throws Exception {
        WebPage page = getPage("issue4127-page1.xhtml");
        WebElement link = page.findElement(By.id("get_parameter_outcome"));

        link.click();

        String pageXml = page.getSource();
        assertTrue(pageXml.contains("foo bar"));
    }

    /**
     * @see ViewHandler#restoreView(jakarta.faces.context.FacesContext, String)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4127
     */
    @Test
    void page2ReachableViaLinkWithoutQueryParameters() throws Exception {
        WebPage page = getPage("issue4127-page1.xhtml");
        WebElement link = page.findElement(By.id("get_parameter_none"));

        link.click();

        String pageXml = page.getSource();
        assertTrue(pageXml.contains("Welcome to Page2"));
    }

    /**
     * @see ViewHandler#restoreView(jakarta.faces.context.FacesContext, String)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4127
     */
    @Test
    void page2ReachableViaCommandButton() throws Exception {
        WebPage page = getPage("issue4127-page1.xhtml");
        WebElement button = page.findElement(By.id("button_to_page2"));
        button.click();

        String pageXml = page.getSource();
        assertTrue(pageXml.contains("Welcome to Page2"));
    }

    /**
     * @see ViewHandler#restoreView(jakarta.faces.context.FacesContext, String)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4127
     */
    @Test
    void page3ReachableViaLinkWithFParamToken() throws Exception {
        WebPage page = getPage("issue4127-page1.xhtml");
        WebElement link = page.findElement(By.id("page3_get_parameter_fparam"));
        link.click();

        String pageXml = page.getSource();
        assertTrue(pageXml.contains("foo bar"));
    }

    /**
     * @see ViewHandler#restoreView(jakarta.faces.context.FacesContext, String)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4127
     */
    @Test
    void page3ReachableViaLinkWithOutcomeAttribute() throws Exception {
        WebPage page = getPage("issue4127-page1.xhtml");
        WebElement link = page.findElement(By.id("page3_get_parameter_outcome"));

        link.click();

        String pageXml = page.getSource();
        assertTrue(pageXml.contains("foo bar"));
    }

    /**
     * @see ViewHandler#restoreView(jakarta.faces.context.FacesContext, String)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4127
     */
    @Test
    void page3ReachableViaLinkWithoutQueryParameters() throws Exception {
        WebPage page = getPage("issue4127-page1.xhtml");
        WebElement link = page.findElement(By.id("page3_get_parameter_none"));

        link.click();

        String pageXml = page.getSource();
        assertTrue(pageXml.contains("Welcome to Page2"));
    }

    /**
     * @see ViewHandler#restoreView(jakarta.faces.context.FacesContext, String)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4127
     */
    @Test
    void page3ReachableViaCommandButton() throws Exception {
        WebPage page = getPage("issue4127-page1.xhtml");
        WebElement button = page.findElement(By.id("page3_button_to_page2"));
        button.click();

        String pageXml = page.getSource();
        assertTrue(pageXml.contains("Welcome to Page2"));
    }
}
