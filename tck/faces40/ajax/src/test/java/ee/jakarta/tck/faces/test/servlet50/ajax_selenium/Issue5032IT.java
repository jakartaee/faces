/*
 * Copyright (c) 2022 Contributors to the Eclipse Foundation.
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

package ee.jakarta.tck.faces.test.servlet50.ajax_selenium;


import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.ExtendedTextInput;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;
import org.junit.Test;
import org.openqa.selenium.By;

import static org.junit.Assert.assertEquals;


public class Issue5032IT extends BaseITNG {

    /**
     * @see AjaxBehavior#getExecute()
     * @see UIComponent#getCompositeComponentParent(UIComponent)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/5032
     */
    @Test
    public void testImplicitThis() throws Exception {
        WebPage page = getPage("issue5032IT.xhtml");

        ExtendedTextInput form1input2 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form1:inputs:input2")));

        form1input2.setValue("1");
        form1input2.fireEvent("change");
        page.waitReqJs();
        String form1messages = page.findElement(By.id("form1:messages")).getText();
        assertEquals("there are no validation messages coming from required field form1:input1", "", form1messages);
    }

    /**
     * @see AjaxBehavior#getExecute()
     * @see UIComponent#getCompositeComponentParent(UIComponent)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/5032
     */
    @Test
    public void testExplicitThis() throws Exception {
        WebPage page = getPage("issue5032IT.xhtml");

        ExtendedTextInput form2input2 = new ExtendedTextInput( getWebDriver(), page.findElement(By.id("form2:inputs:input2")));

        form2input2.setValue("1");
        form2input2.fireEvent("change");
        page.waitReqJs();
        String form2messages = page.findElement(By.id("form2:messages")).getText();
        assertEquals("there are no validation messages coming from required field form2:input1", "", form2messages);
    }

}
