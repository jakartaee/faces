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
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

public class Issue4913IT extends BaseITNG {

  /**
   * @see Inject
     * @see ResourceDependency
     * @see FacesConverter#managed()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4913
   */
  @Test
  void test() throws Exception {
        WebPage page = getPage("issue4913.xhtml");
        validateMarkup(page);

        // Refresh page 
        page = getPage("issue4913.xhtml");
        validateMarkup(page);
    }
    
    private static void validateMarkup(WebPage page) {
        WebElement issue4913Converter = page.findElement(By.id("issue4913Converter"));
        assertEquals("value is successfully converted in a managed converter", issue4913Converter.getText(), "Converter is invoked");

        WebElement issue4913ResourceDependency = page.findElement(By.id("issue4913ResourceDependency"));
        assertEquals("resource dependency is successfully injected via a managed converter", issue4913ResourceDependency.getText(), "Resource dependency is injected");
    }
}
