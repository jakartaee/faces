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

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;
import jakarta.faces.component.behavior.AjaxBehavior;

public class Issue2752IT extends ITBase {

    /**
     * @see AjaxBehavior
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2756
     */
    @Test
    public void testInsertFromResponse() throws Exception {
        HtmlPage page = getPage("insertElement.xhtml");
        HtmlSubmitInput beforeButton = page.getHtmlElementById("beforeButton");
        page = beforeButton.click();
        // This will ensure JavaScript finishes before evaluating the page.
        webClient.waitForBackgroundJavaScript(3000);
        assertTrue(page.asNormalizedText().contains("This is before textalpha"));
        HtmlSubmitInput afterButton = page.getHtmlElementById("afterButton");
        page = afterButton.click();
        // This will ensure JavaScript finishes before evaluating the page.
        webClient.waitForBackgroundJavaScript(3000);
        assertTrue(page.asNormalizedText().contains("This is before textalphaThis is after text"));
    }

}
