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

package ee.jakarta.tck.faces.test.javaee8.uiinput;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.html.HtmlButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;
import jakarta.faces.component.UISelectItem;
import jakarta.faces.component.html.HtmlSelectManyCheckbox;

public class Issue4330IT extends ITBase {

    /**
     * @see HtmlSelectManyCheckbox
     * @see UISelectItem#isItemDisabled()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4330
     */
    @Test
    public void testIssue4330() throws Exception {
        HtmlPage page;
        HtmlRadioButtonInput disabledRadio;
        HtmlCheckBoxInput enabledCheckbox;
        HtmlCheckBoxInput disabledCheckbox;
        HtmlButtonInput hack;
        HtmlSubmitInput submit;

        page = getPage("issue4330.xhtml");
        assertTrue(page.getHtmlElementById("form:result").asNormalizedText().isEmpty());

        disabledRadio = page.getHtmlElementById("form:one:1");
        enabledCheckbox = page.getHtmlElementById("form:many:0");
        disabledCheckbox = page.getHtmlElementById("form:many:1");
        hack = page.getHtmlElementById("form:hack");
        submit = page.getHtmlElementById("form:submit");

        assertTrue(disabledRadio.isDisabled());
        assertTrue(disabledCheckbox.isDisabled());

        hack.click();
        webClient.waitForBackgroundJavaScript(1000);

        assertFalse(disabledRadio.isDisabled());
        assertFalse(disabledCheckbox.isDisabled());

        disabledRadio.setChecked(true);
        enabledCheckbox.setChecked(true);
        disabledCheckbox.setChecked(true);

        page = submit.click();
        assertTrue(page.getHtmlElementById("form:result").asNormalizedText().equals("[enabled]")); // Thus not "disabled[enabled, disabled]"
    }

}