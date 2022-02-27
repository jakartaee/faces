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
import com.gargoylesoftware.htmlunit.html.HtmlSelect;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;
import jakarta.faces.component.behavior.AjaxBehavior;

public class Issue3833IT extends ITBase {

    /**
     * @see AjaxBehavior
     * @see com.sun.faces.facelets.component.UIRepeat
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3837
     */
    @Test
    public void testUIRepeatStateSaving() throws Exception {
        HtmlPage page = getPage("issue3833.xhtml");

        HtmlSelect select1 = (HtmlSelect) page.getElementById("form:repeat:0:list");
        HtmlSelect select2 = (HtmlSelect) page.getElementById("form:repeat:1:list");

        page = select1.getOption(0).click();
        webClient.waitForBackgroundJavaScript(3000);
        assertTrue(page.getElementById("form:message").asNormalizedText().equals("null -> 1"));

        page = select2.getOption(0).click();
        webClient.waitForBackgroundJavaScript(3000);
        assertTrue(page.getElementById("form:message").asNormalizedText().equals("null -> 3"));

        page = select1.getOption(1).click();
        webClient.waitForBackgroundJavaScript(3000);
        assertTrue(page.getElementById("form:message").asNormalizedText().equals("1 -> 2"));

        page = select2.getOption(1).click();
        webClient.waitForBackgroundJavaScript(3000);
        assertTrue(page.getElementById("form:message").asNormalizedText().equals("3 -> 4"));
    }
}
