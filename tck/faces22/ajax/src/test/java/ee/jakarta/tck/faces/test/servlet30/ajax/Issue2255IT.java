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

import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.behavior.AjaxBehavior;

public class Issue2255IT extends ITBase {

    /**
     * @see AjaxBehavior
     * @see UIComponent#getCurrentCompositeComponent(jakarta.faces.context.FacesContext)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2259
     */
    @Test
    public void testBehaviorState() throws Exception {
        HtmlPage page = getPage("divInComposite.xhtml");
        assertTrue(page.asNormalizedText().contains("false"));
        HtmlCheckBoxInput cbox = (HtmlCheckBoxInput) page.getElementById("cc:form:test");
        page = cbox.click();
        webClient.waitForBackgroundJavaScript(3000);
        assertTrue(page.asNormalizedText().contains("true"));
        page = cbox.click();
        webClient.waitForBackgroundJavaScript(3000);
        assertTrue(page.asNormalizedText().contains("false"));
        page = cbox.click();
        webClient.waitForBackgroundJavaScript(3000);
        assertTrue(page.asNormalizedText().contains("true"));
        page = cbox.click();
        webClient.waitForBackgroundJavaScript(3000);
        assertTrue(page.asNormalizedText().contains("false"));
    }
}
