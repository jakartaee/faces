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

package ee.jakarta.tck.faces.test.servlet30.ajax_selenium;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.behavior.AjaxBehavior;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertEquals;

/**
 * @author Manfred Riem (manfred.riem@oracle.com)
 */
public class Issue1825IT extends BaseITNG {

    /**
     * @see AjaxBehavior
     * @see UIComponent#getCurrentCompositeComponent(jakarta.faces.context.FacesContext)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/1829
     */
    @Test
    public void testIssue1825() throws Exception {
        WebPage page = getPage("issue1825.xhtml");
        WebElement button = page.findElement(By.id("form:button"));
        page.guardAjax(button::click);
        assertEquals(200, page.getResponseStatus());
        button =  page.findElement(By.id("form:button"));
        page.guardAjax(button::click);
        assertEquals(200, page.getResponseStatus());
    }
}
