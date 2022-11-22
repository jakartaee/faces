/*
 * Copyright (c) 2021 Contributors to the Eclipse Foundation.
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

package ee.jakarta.tck.faces.test.servlet30.ajax_new;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;
import jakarta.faces.component.behavior.AjaxBehavior;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class Issue2439IT extends BaseITNG {

    /**
     * @see AjaxBehavior#isDisabled()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2443
     */
    @Test
    public void testUpdateAttributeNamedValue() {
        // change in the rendering due to a different engine, we probably would be
        // better off checking the dom elements directly
        String expectedString1 = "<input id=\"form1:input1\" type=\"text\" name=\"form1:input1\">";
        String expectedString2 = "<input id=\"form1:input2\" type=\"text\" name=\"form1:input2\" onchange=\"faces.util.chain(this,event,'mojarra.ab(this,event,\\'valueChange\\',\\'@this\\',\\'@all\\')')\">";
        String expectedString3 = "<input id=\"form1:input3\" type=\"text\" name=\"form1:input3\" onchange=\"faces.util.chain(this,event,'alert(\\'Hello, World!\\');')\">";

        WebPage page = getPage("disabledBehaviors.xhtml");
        assertTrue(page.isInPage(expectedString1));
        assertTrue(page.isInPage(expectedString2));
        assertTrue(page.isInPage(expectedString3));
    }
}

