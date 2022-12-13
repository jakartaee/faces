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
/**
 * @see PreRenderViewEvent
 * @see AjaxBehavior
 * @see https://github.com/eclipse-ee4j/mojarra/issues/2166
 */


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;
import jakarta.faces.component.behavior.AjaxBehavior;
import jakarta.faces.event.PreRenderViewEvent;

/**
 * @author Manfred Riem (manfred.riem@oracle.com)
 */
public class Issue2162IT extends ITBase {
    @Test
    public void testIssue2162() throws Exception {
        HtmlPage page = getPage("issue2162.xhtml");

        assertTrue(page.asXml().indexOf("Init called\n") != -1);

        HtmlSubmitInput button = (HtmlSubmitInput) page.getHtmlElementById("form:submit");
        page = button.click();
 
        webClient.waitForBackgroundJavaScript(3000); 
        
        assertTrue(page.asXml().indexOf("Init called\nInit called\n") != -1);
        assertFalse(page.asXml().indexOf("Init called\nInit called\nInit called") != -1);
    }
}
