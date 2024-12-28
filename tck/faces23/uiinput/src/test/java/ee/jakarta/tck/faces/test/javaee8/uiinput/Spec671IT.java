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

package ee.jakarta.tck.faces.test.javaee8.uiinput;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.component.UIInput;

import org.jboss.arquillian.junit5.ArquillianExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;

@ExtendWith(ArquillianExtension.class)
class Spec671IT extends ITBase {

  /**
   * @see UIInput#EMPTY_STRING_AS_NULL_PARAM_NAME
     * @see https://github.com/jakartaee/faces/issues/671
   */
  @Test
  void spec671() throws Exception {
        HtmlPage page;
        HtmlTextInput text;
        HtmlSubmitInput button;

        page = getPage("spec671.xhtml");
      assertEquals("true", page.getHtmlElementById("param").asNormalizedText());

        text = (HtmlTextInput) page.getHtmlElementById("form:input");
        assertTrue(text.getValueAttribute().isEmpty());

        text.setValueAttribute("foo");
        button = (HtmlSubmitInput) page.getHtmlElementById("form:button");
        page = button.click();
        text = (HtmlTextInput) page.getHtmlElementById("form:input");
      assertEquals("foo", text.getValueAttribute());

        text.setValueAttribute("");
        button = (HtmlSubmitInput) page.getHtmlElementById("form:button");
        page = button.click();
        text = (HtmlTextInput) page.getHtmlElementById("form:input");
        assertTrue(text.getValueAttribute().isEmpty());
    }

}
