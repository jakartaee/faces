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

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;
import jakarta.faces.component.behavior.AjaxBehavior;
import jakarta.faces.component.html.HtmlCommandLink;
import jakarta.faces.component.html.HtmlSelectOneRadio;

public class Issue2340IT extends ITBase {

    /**
     * @see AjaxBehavior
     * @see HtmlCommandLink
     * @see HtmlSelectOneRadio
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2344
     */
    @Test
    public void testCommandLinkRadio() throws Exception {
        HtmlPage page = getPage("commandLinkRadio.xhtml");
        HtmlAnchor anchor = (HtmlAnchor)page.getElementById("testLink");
        // This will ensure JavaScript finishes before evaluating the page.
        webClient.waitForBackgroundJavaScript(3000);
        anchor.click();
        webClient.waitForBackgroundJavaScript(3000);
        assertTrue(page.asXml().contains("LINK ACTION"));
        HtmlRadioButtonInput radio1 = (HtmlRadioButtonInput)page.getElementById("testRadio:0");
        HtmlRadioButtonInput radio2 = (HtmlRadioButtonInput)page.getElementById("testRadio:1");
        HtmlRadioButtonInput radio3 = (HtmlRadioButtonInput)page.getElementById("testRadio:2");

        page = radio1.click();
        webClient.waitForBackgroundJavaScript(3000);
        assertTrue(page.asXml().contains("RADIO:red"));

        page = radio2.click();
        webClient.waitForBackgroundJavaScript(3000);
        assertTrue(page.asXml().contains("RADIO:blue"));

        page = radio3.click();
        webClient.waitForBackgroundJavaScript(3000);
        assertTrue(page.asXml().contains("RADIO:white"));
    }
}
