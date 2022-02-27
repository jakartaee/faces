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

import com.gargoylesoftware.htmlunit.ScriptException;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;
import jakarta.faces.component.behavior.AjaxBehavior;

public class Issue3020IT extends ITBase {

    /**
     * @see AjaxBehavior
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3024
     */
    @Test
    public void testDelayPositive() throws Exception {
        HtmlPage page = getPage("issue3020Positive.xhtml");

        HtmlTextInput in1 = (HtmlTextInput) page.getHtmlElementById("input");
        in1.type("a");

        webClient.waitForBackgroundJavaScript(3000);

        // Check that the ajax request succeeds
        assertTrue(page.getHtmlElementById("result").asNormalizedText().contains("aaaaaaaaaa")); 
    }
    
    /**
     * @see AjaxBehavior
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3024
     */
    @Test
    public void testDelayNegative() throws Exception {
        HtmlPage page = getPage("issue3020Negative.xhtml");

        HtmlTextInput in1 = (HtmlTextInput) page.getHtmlElementById("input");
        boolean exceptionCaught = false;
        try {
            in1.type("a");
            
            webClient.waitForBackgroundJavaScript(3000);
        } catch (ScriptException se) {
            assertTrue(se.getMessage().contains("NaN"));
            exceptionCaught = true;
        }

        // Check that the ajax request does not succeed
        assertTrue(exceptionCaught);
    }
    

}
