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
package ee.jakarta.tck.faces.test.javaee7.cdimultitenantsetstccl.cdimultitenantsetstccl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.FactoryFinder;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;

@Disabled("Because this is Mojarra specific: https://github.com/jakartaee/faces/issues/1679")
public class Issue3341IT extends ITBase {

  /**
   * @see FactoryFinder
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3345
   */
  @Test
  void tcclReplacementResilience() throws Exception {
        HtmlPage page = webClient.getPage(webUrl);

        String pageText = page.getBody().asNormalizedText();

        // If the BeforeFilter is configured to
        if (pageText.matches("(?s).*SUCCESS.*")) {
            assertTrue(true);
        } else {
            assertTrue(pageText.matches("(?s).*Duke.*submit.*"));
            assertTrue(pageText.matches("(?s).*First name:\\s*Duke.*"));
            assertTrue(pageText.matches("(?s).*BeforeServlet init found Lifecycle:\\s*TRUE.*"));
            assertTrue(pageText.matches("(?s).*BeforeServlet init found FacesContext:\\sTRUE.*"));
            assertTrue(pageText.matches("(?s).*BeforeServlet request found Lifecycle:\\s*TRUE.*"));
            // Yes, the FacesContext.getCurrentInstance() should not be found
            // because this is in a Filter before the run of the FacesServlet.service().
            assertTrue(pageText.matches("(?s).*BeforeServlet request found FacesContext:\\s*FALSE.*"));
        }
    }
}
