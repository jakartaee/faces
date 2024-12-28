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

package ee.jakarta.tck.faces.test.javaee7.cdimethodvalidation.cdimethodvalidation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.validation.ConstraintValidator;

import org.junit.jupiter.api.Test;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;

public class MethodValidationIT extends ITBase {

  /**
   * @see ConstraintValidator
     * @see https://github.com/javaee/mojarra/commit/40f15d1fc99e0aac9af9f5b5607c8ac61a85adc6
   */
  @Test
  void incorrectUsage() throws Exception {
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        HtmlPage page = webClient.getPage(webUrl);
        HtmlTextInput input = (HtmlTextInput) page.getElementById("firstName");
        input.setValueAttribute("notfoo");

        HtmlSubmitInput button = (HtmlSubmitInput) page.getElementById("button");
        page = button.click();
        String text = page.asNormalizedText();

        assertTrue(text.contains("FooConstraint"));
        assertTrue(text.contains("my message"));
        assertEquals(500, page.getWebResponse().getStatusCode());
    }

  /**
   * @see ConstraintValidator
     * @see https://github.com/javaee/mojarra/commit/40f15d1fc99e0aac9af9f5b5607c8ac61a85adc6
   */
  @Test
  void correctUsage1() throws Exception {
        HtmlPage page = webClient.getPage(webUrl);
        HtmlTextInput input = (HtmlTextInput) page.getElementById("lastName");
        input.setValueAttribute("notfoo");

        HtmlSubmitInput button = (HtmlSubmitInput) page.getElementById("button");
        page = button.click();
        String text = page.asNormalizedText();

      assertFalse(text.contains("FooConstraint"));
        assertTrue(text.contains("my message"));
        assertEquals(200, page.getWebResponse().getStatusCode());
    }

  /**
   * @see ConstraintValidator
     * @see https://github.com/javaee/mojarra/commit/40f15d1fc99e0aac9af9f5b5607c8ac61a85adc6
   */
  @Test
  void correctUsage2() throws Exception {
        HtmlPage page = webClient.getPage(webUrl);
        HtmlTextInput input = (HtmlTextInput) page.getElementById("requestValue");
        input.setValueAttribute("bar");

        HtmlSubmitInput button = (HtmlSubmitInput) page.getElementById("button");
        page = button.click();
        String text = page.asNormalizedText();

        assertTrue(text.contains("FooConstraint"));
        assertTrue(text.contains("my message"));
        assertEquals(200, page.getWebResponse().getStatusCode());
    }
}
