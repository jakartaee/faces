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

package ee.jakarta.tck.faces.test.servlet30.ajax;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;
import jakarta.faces.component.behavior.AjaxBehavior;
import jakarta.faces.context.PartialViewContext;

public class Issue1284IT extends ITBase {

    /**
     * @see AjaxBehavior
     * @see PartialViewContext#getEvalScripts()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/1289
     */
    @Test
    public void testCdataEscape5() throws Exception {
        HtmlPage page = getPage("issue1284.xhtml");
        assertTrue(page.getHtmlElementById("form1:out1").asNormalizedText().equals("")); 
        assertTrue(page.getHtmlElementById("form1:in1").asNormalizedText().equals("")); 

        HtmlTextInput in1 = (HtmlTextInput) page.getHtmlElementById("form1:in1");
        in1.type("[");

        // Submit the ajax request
        HtmlSubmitInput button1 = (HtmlSubmitInput) page.getHtmlElementById("form1:button1");
        page = (HtmlPage) button1.click();
        webClient.waitForBackgroundJavaScript(3000);

        // Check that the ajax request succeeds
        assertTrue(page.getHtmlElementById("form1:out1").asNormalizedText().equals("[")); 
    }

    /**
     * @see AjaxBehavior
     * @see PartialViewContext#getEvalScripts()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/1289
     */
    @Test
    public void testCdataEscape6() throws Exception {
        HtmlPage page = getPage("issue1284.xhtml");
        assertTrue(page.getHtmlElementById("form1:out1").asNormalizedText().equals("")); 
        assertTrue(page.getHtmlElementById("form1:in1").asNormalizedText().equals("")); 

        HtmlTextInput in1 = (HtmlTextInput) page.getHtmlElementById("form1:in1");
        in1.type("var a=[");

        // Submit the ajax request
        HtmlSubmitInput button1 = (HtmlSubmitInput) page.getHtmlElementById("form1:button1");
        page = (HtmlPage) button1.click();
        webClient.waitForBackgroundJavaScript(3000);

        // Check that the ajax request succeeds
        assertTrue(page.getHtmlElementById("form1:out1").asNormalizedText().equals("var a=[")); 
    }
}
