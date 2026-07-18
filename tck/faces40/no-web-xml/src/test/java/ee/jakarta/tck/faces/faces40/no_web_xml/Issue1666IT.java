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
package ee.jakarta.tck.faces.faces40.no_web_xml;

import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.webapp.FacesServlet;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue1666IT extends BaseITNG {

    /**
     * With no {@code web.xml}, Jakarta Faces auto-registers the FacesServlet under
     * {@code *.xhtml}, {@code *.jsf} and {@code *.faces}. Each mapping must serve the view,
     * process the postback, and render a {@code jakarta.faces.ViewState} field.
     *
     * @see FacesServlet
     * @see https://github.com/eclipse-ee4j/mojarra/issues/1666
     */
    @Test
    void noWebXmlAutoMapsFacesServlet() {
        assertViewProcessed("issue1666.xhtml");
        assertViewProcessed("issue1666.jsf");
        assertViewProcessed("issue1666.faces");
    }

    private void assertViewProcessed(String view) {
        WebPage page = getPage(view);
        page.guardHttp(page.findElement(By.id("form:command"))::click);
        assertTrue(page.containsText("Good Morning"), "Expected 'Good Morning' via " + view);
        assertTrue(page.containsSource("jakarta.faces.ViewState"), "Expected ViewState field via " + view);
    }
}
