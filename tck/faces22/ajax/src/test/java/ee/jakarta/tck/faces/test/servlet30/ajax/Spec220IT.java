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

import static junit.framework.Assert.assertTrue;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;
import jakarta.faces.component.behavior.AjaxBehavior;
import jakarta.faces.render.ResponseStateManager;

public class Spec220IT extends ITBase {

    /**
     * @see AjaxBehavior
     * @see ResponseStateManager#VIEW_STATE_PARAM
     * @see https://github.com/jakartaee/faces/issues/220
     */
    @Test
    public void testViewState() throws Exception {
        HtmlPage page = getPage("viewState1.xhtml");
        HtmlTextInput textField = (HtmlTextInput) page.getElementById("firstName");
        textField.setValueAttribute("ajaxFirstName");
        
        HtmlSubmitInput button = (HtmlSubmitInput) page.getElementById("submitAjax");
        page = button.click();
        Thread.sleep(2000);
        String pageText = page.asNormalizedText();
        assertTrue(pageText.contains("|ajaxFirstName|"));

        textField = (HtmlTextInput) page.getElementById("firstName");
        textField.setValueAttribute("nonAjaxFirstName");
        
        button = (HtmlSubmitInput) page.getElementById("submitNonAjax");
        page = button.click();
        Thread.sleep(2000);
        pageText = page.asNormalizedText();
        assertTrue(pageText.contains("|nonAjaxFirstName|"));
    }
}
