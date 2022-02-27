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

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;
import jakarta.faces.component.behavior.AjaxBehavior;

public class Issue3171IT extends ITBase {

    /**
     * @see AjaxBehavior
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3175
     */
    @Test
    public void testExceptionDuringRenderOk() throws Exception {
        HtmlPage page = getPage("exceptionDuringRender.xhtml");
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        String pageText = page.asNormalizedText();
        
        assertTrue(pageText.contains("not an ajax request"));
        
        HtmlSubmitInput button = page.getHtmlElementById("submit");

        page = button.click();
        webClient.waitForBackgroundJavaScript(3000);
        
        pageText = page.asNormalizedText();
        String pageXml = page.asXml();
        HtmlElement span = page.getHtmlElementById("exceptionDuringRender");
        String spanXML = span.asXml();
        
        assertTrue(pageText.contains("not an ajax request"));
        assertTrue(pageText.contains("Error from submit"));
        
    }
}
