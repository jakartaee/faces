/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2022, 2023 Contributors to the Eclipse Foundation.
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;
import jakarta.faces.component.UIInput;
import jakarta.faces.component.UISelectMany;
import jakarta.faces.component.behavior.AjaxBehavior;

@RunWith(Arquillian.class)
public class Issue5081IT extends ITBase {

    /**
     * @see UISelectMany
     * @see AjaxBehavior
     * @see UIInput#EMPTY_STRING_AS_NULL_PARAM_NAME
     * @see https://github.com/eclipse-ee4j/mojarra/issues/5081
     */
    @Test
    public void testIssue4734() throws Exception {
        HtmlPage page = getPage("issue5081.xhtml");
        assertTrue(page.getHtmlElementById("INTERPRET_EMPTY_STRING_SUBMITTED_VALUES_AS_NULL").asNormalizedText().equals("true"));

        HtmlTextInput input = page.getHtmlElementById("form:input");
        input.type("text");
        input.blur();
        webClient.waitForBackgroundJavaScript(3000);
        HtmlSubmitInput submit = page.getHtmlElementById("form:submit");
        page = submit.click();
        HtmlElement message = page.getHtmlElementById("form:message_for_selectmany");
        assertEquals("There is a required message", "form:selectmany: Validation Error: Value is required.", message.getTextContent());

        input = page.getHtmlElementById("form:input");
        input.type("more");
        input.blur(); // Before the fix, the second blur failed with java.lang.ClassCastException: class java.lang.String cannot be cast to class [Ljava.lang.Object;
        webClient.waitForBackgroundJavaScript(3000);
        submit = page.getHtmlElementById("form:submit");
        page = submit.click();
        message = page.getHtmlElementById("form:message_for_selectmany");
        assertEquals("There is a still required message", "form:selectmany: Validation Error: Value is required.", message.getTextContent());
    }

}