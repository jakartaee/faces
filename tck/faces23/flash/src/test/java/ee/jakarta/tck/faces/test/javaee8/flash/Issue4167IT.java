/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2023 Contributors to Eclipse Foundation.
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

package ee.jakarta.tck.faces.test.javaee8.flash;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.context.Flash;

import org.jboss.arquillian.junit5.ArquillianExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;

@ExtendWith(ArquillianExtension.class)
class Issue4167IT extends ITBase {

  /**
   * @see Flash#keep(String)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4171
   */
  @Test
  void test() throws Exception {
        HtmlPage page = getPage("issue4167.xhtml");

        assertTrue(page.getHtmlElementById("result1").asNormalizedText().isEmpty());
        assertTrue(page.getHtmlElementById("result2").asNormalizedText().isEmpty());
        assertTrue(page.getHtmlElementById("result3").asNormalizedText().isEmpty());

        HtmlSubmitInput button = (HtmlSubmitInput) page.getHtmlElementById("form:button");
        page = button.click();

      assertEquals("issue4167", page.getHtmlElementById("result1").asNormalizedText());
      assertEquals("issue4167", page.getHtmlElementById("result2").asNormalizedText());
      assertEquals("issue4167", page.getHtmlElementById("result3").asNormalizedText());
    }

}
