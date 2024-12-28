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

package ee.jakarta.tck.faces.test.javaee8.uidecorate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;

class Issue5140IT extends ITBase {

  /**
   * @see https://github.com/eclipse-ee4j/mojarra/issues/5140
   */
  @Test
  void test() throws Exception {
        HtmlPage page = getPage("issue5140.xhtml");
        DomElement unexpectedElement = page.getElementById("Field");
        DomElement expectedElement = page.getElementById("testInputIdField");
      assertNull(unexpectedElement, "unexpected element may not exist");
        assertTrue(expectedElement != null, "expected element exists");
        assertEquals("ui:insert content", expectedElement.asNormalizedText(), "ui:insert content is present");
    }

}