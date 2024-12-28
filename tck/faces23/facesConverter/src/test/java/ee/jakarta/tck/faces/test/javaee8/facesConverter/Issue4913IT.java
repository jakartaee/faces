/*
 * Copyright (c) 2021 Contributors to the Eclipse Foundation.
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
package ee.jakarta.tck.faces.test.javaee8.facesConverter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.faces.application.ResourceDependency;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;

public class Issue4913IT extends ITBase {

  /**
   * @see Inject
     * @see ResourceDependency
     * @see FacesConverter#managed()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4913
   */
  @Test
  void test() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "issue4913.xhtml");
        validateMarkup(page);

        // Refresh page 
        page = webClient.getPage(webUrl + "issue4913.xhtml");
        validateMarkup(page);
    }
    
    private static void validateMarkup(HtmlPage page) {
        DomElement issue4913Converter = page.getElementById("issue4913Converter");
        assertEquals("value is successfully converted in a managed converter", issue4913Converter.asNormalizedText(), "Converter is invoked");

        DomElement issue4913ResourceDependency = page.getElementById("issue4913ResourceDependency");
        assertEquals("resource dependency is successfully injected via a managed converter", issue4913ResourceDependency.asNormalizedText(), "Resource dependency is injected");
    }
}
