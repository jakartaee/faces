/*
 * Copyright (c) 2022 Contributors to Eclipse Foundation.
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

package ee.jakarta.tck.faces.test.javaee8.xhtmlNamespaces;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;

class Issue4281IT extends ITBase {

  /**
   * @see https://github.com/eclipse-ee4j/mojarra/issues/4281
   */
  @Test
  void test() throws Exception {
        HtmlPage page = getPage("issue4281.xhtml");
        DomElement panelGroup = page.getElementById("panelGroup");
        DomElement outputText = page.getElementById("outputText");
        assertTrue(outputText != null, "outputText does exist");
        assertTrue(panelGroup.asNormalizedText().contains("paragraph"), "panelGroup does contain parargaph");
        assertFalse(panelGroup.asNormalizedText().contains("outputText"), "panelGroup may not contain outputText");
        assertTrue(page.getBody().asNormalizedText().contains("outputText"), "body does contain outputText");
    }

}