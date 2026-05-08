/*
 * Copyright (c) 2021 Contributors to Eclipse Foundation.
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
package ee.jakarta.tck.faces.faces40.extensionless_mapping;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import jakarta.faces.application.ViewHandler;
import jakarta.faces.webapp.FacesServlet;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

public class Spec1508IT extends BaseITNG {

    /**
     * @see FacesServlet#AUTOMATIC_EXTENSIONLESS_MAPPING_PARAM_NAME
     * @see ViewHandler#getViews(jakarta.faces.context.FacesContext, String, jakarta.faces.application.ViewVisitOption...)
     * @see https://github.com/jakartaee/faces/issues/1508
     */
    @Test
    @RunAsClient
    void extensionlessMappingFoo() throws IOException {
        WebPage page = getPage("spec1508-1");
        String content = page.getSource();
        assertTrue(content.contains("This is page spec1508-1"));
        assertTrue(page.findElement(By.id("spec1508-2xhtmllink")).getDomAttribute("href").endsWith("/spec1508-2"));
        assertTrue(page.findElement(By.id("spec1508-2link")).getDomAttribute("href").endsWith("/spec1508-2"));
    }

    /**
     * @see FacesServlet#AUTOMATIC_EXTENSIONLESS_MAPPING_PARAM_NAME
     * @see ViewHandler#getViews(jakarta.faces.context.FacesContext, String, jakarta.faces.application.ViewVisitOption...)
     * @see https://github.com/jakartaee/faces/issues/1508
     */
    @Test
    @RunAsClient
    void extensionlessMappingBar() throws IOException {
        WebPage page = getPage("spec1508-2");
        String content = page.getSource();
        assertTrue(content.contains("This is page spec1508-2"));
    }

    /**
     * @see FacesServlet#AUTOMATIC_EXTENSIONLESS_MAPPING_PARAM_NAME
     * @see ViewHandler#getViews(jakarta.faces.context.FacesContext, String, jakarta.faces.application.ViewVisitOption...)
     * @see https://github.com/jakartaee/faces/issues/1508
     */
    @Test
    @RunAsClient
    void extensionlessMappingSubBar() throws IOException {
        WebPage page = getPage("sub/spec1508-3");
        String content = page.getSource();
        assertTrue(content.contains("This is page sub-spec1508-3"));
    }

}
