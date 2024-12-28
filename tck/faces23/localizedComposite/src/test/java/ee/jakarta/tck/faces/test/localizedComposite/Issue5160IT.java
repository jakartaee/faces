/*
 * Copyright (c) 2022 Contributors to the Eclipse Foundation.
 * Copyright (c) 2017, 2021 Oracle and/or its affiliates. All rights reserved.
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
package ee.jakarta.tck.faces.test.composite;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.gargoylesoftware.htmlunit.html.HtmlHeading1;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;

/**
 * Tests if composite component that use resourceBundleMap .properties reflects
 * locale changes.
 * 
 * @see https://github.com/eclipse-ee4j/mojarra/issues/5160
 * @see https://issues.apache.org/jira/browse/MYFACES-4491
 * 
 */
public class Issue5160IT extends ITBase {

  @Test
  void localizedCompositeEn() throws Exception {
        assertLocalizedComposite("en-US", "Application", "My precious button", "Button");
    }

  @Test
  void localizedCompositeEs() throws Exception {
        assertLocalizedComposite("es-ES", "Aplicación", "Mi precioso botón", "Botón");
    }

  @Test
  void localizedCompositePt() throws Exception {
        assertLocalizedComposite("pt", "Application", "My precious button", "Accionador");
    }

  @Test
  void localizedCompositePtBr() throws Exception {
        assertLocalizedComposite("pt-BR", "Application", "My precious button", "Botão");
    }

  @Test
  void localizedCompositePtBrPb() throws Exception {
        assertLocalizedComposite("pt-BR-PB", "Application", "My precious button", "Pitoco");
    }

  @Test
  void localizedCompositePtBrXx() throws Exception {
        assertLocalizedComposite("pt-BR-XX", "Application", "My precious button", "Botão");
    }

  @Test
  void localizedCompositePtBrXxYy() throws Exception {
        assertLocalizedComposite("pt-BR-XX-YY", "Application", "My precious button", "Botão");
    }

  @Test
  void localizedCompositePtXxYy() throws Exception {
        assertLocalizedComposite("pt-XX-YY", "Application", "My precious button", "Accionador");
    }

  @Test
  void localizedCompositePtXx() throws Exception {
        assertLocalizedComposite("pt-XX", "Application", "My precious button", "Accionador");
    }

    private void assertLocalizedComposite(String acceptLanguage, String headerText, String buttonText, String compositeButtonText) throws Exception {
        webClient.addRequestHeader("Accept-Language", acceptLanguage);
        HtmlPage page = webClient.getPage(webUrl + "issue5160.xhtml");

        HtmlHeading1 h1 = (HtmlHeading1) page.getHtmlElementById("header");
        assertEquals(headerText, h1.getTextContent());

        HtmlSubmitInput btn1 = (HtmlSubmitInput) page.getHtmlElementById("frm:btn");
        assertEquals(buttonText, btn1.getAttribute("value"));

        HtmlSubmitInput btn2 = (HtmlSubmitInput) page.getHtmlElementById("frm:btn1:btn");
        assertEquals(compositeButtonText, btn2.getAttribute("value"));
    }
}
