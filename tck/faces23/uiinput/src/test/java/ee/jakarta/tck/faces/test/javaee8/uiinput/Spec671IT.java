/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
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

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;
import jakarta.faces.component.UIInput;

public class Spec671IT extends ITBase {

    /**
     * @see UIInput#EMPTY_STRING_AS_NULL_PARAM_NAME
     * @see https://github.com/jakartaee/faces/issues/671
     */
    @Test
    public void testSpec671() throws Exception {
        HtmlPage page;
        HtmlTextInput text;
        HtmlSubmitInput button;

        page = getPage("spec671.xhtml");
        assertTrue(page.getHtmlElementById("param").asNormalizedText().equals("true"));

        text = (HtmlTextInput) page.getHtmlElementById("form:input");
        assertTrue(text.getValueAttribute().isEmpty());

        text.setValueAttribute("foo");
        button = (HtmlSubmitInput) page.getHtmlElementById("form:button");
        page = button.click();
        text = (HtmlTextInput) page.getHtmlElementById("form:input");
        assertTrue(text.getValueAttribute().equals("foo"));

        text.setValueAttribute("");
        button = (HtmlSubmitInput) page.getHtmlElementById("form:button");
        page = button.click();
        text = (HtmlTextInput) page.getHtmlElementById("form:input");
        assertTrue(text.getValueAttribute().isEmpty());
    }

}
