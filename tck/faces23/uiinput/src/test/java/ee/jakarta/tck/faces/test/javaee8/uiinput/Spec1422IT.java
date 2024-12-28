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

import java.util.Collection;

import jakarta.faces.application.Application;
import jakarta.faces.component.UISelectItems;
import jakarta.faces.component.UISelectMany;

import org.junit.jupiter.api.Test;

import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;

class Spec1422IT extends ITBase {

  /**
   * @see UISelectMany
     * @see UISelectItems
     * @see Collection
     * @see Application#createConverter(Class)
     * @see https://github.com/jakartaee/faces/issues/1422
   */
  @Test
  void spec1422() throws Exception {
        HtmlPage page;
        HtmlCheckBoxInput item1;
        HtmlCheckBoxInput item2;
        HtmlCheckBoxInput item3;
        HtmlCheckBoxInput number1;
        HtmlCheckBoxInput number2;
        HtmlCheckBoxInput number3;
        HtmlCheckBoxInput number4;
        HtmlCheckBoxInput number5;
        HtmlCheckBoxInput number6;
        HtmlCheckBoxInput number7;
        HtmlSubmitInput button;

        page = getPage("spec1422.xhtml");
        assertTrue(page.getHtmlElementById("form:result").asNormalizedText().isEmpty());

        item1 = (HtmlCheckBoxInput) page.getHtmlElementById("form:items:0");
        item2 = (HtmlCheckBoxInput) page.getHtmlElementById("form:items:1");
        item3 = (HtmlCheckBoxInput) page.getHtmlElementById("form:items:2");
        number1 = (HtmlCheckBoxInput) page.getHtmlElementById("form:numbers:0");
        number2 = (HtmlCheckBoxInput) page.getHtmlElementById("form:numbers:1");
        number3 = (HtmlCheckBoxInput) page.getHtmlElementById("form:numbers:2");
        number4 = (HtmlCheckBoxInput) page.getHtmlElementById("form:numbers:3");
        number5 = (HtmlCheckBoxInput) page.getHtmlElementById("form:numbers:4");
        number6 = (HtmlCheckBoxInput) page.getHtmlElementById("form:numbers:5");
        number7 = (HtmlCheckBoxInput) page.getHtmlElementById("form:numbers:6");
        button = (HtmlSubmitInput) page.getHtmlElementById("form:button");
        item1.setChecked(true);
        item2.setChecked(true);
        item3.setChecked(true);
        number1.setChecked(true);
        number2.setChecked(true);
        number3.setChecked(true);
        number4.setChecked(true);
        number5.setChecked(true);
        number6.setChecked(true);
        number7.setChecked(true);
        page = button.click();
      assertEquals("[ONE, TWO, THREE][null, 1, 2, 3, 4.5, 6.7, 8.9]", page.getHtmlElementById("form:result").asNormalizedText());
    }

}
