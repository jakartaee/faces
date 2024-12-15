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
import jakarta.faces.component.behavior.AjaxBehavior;
import jakarta.faces.component.html.HtmlBody;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertTrue;

public class Issue2381IT extends BaseITNG {

    /**
     * This test verifies that the page contains updated information from an
     * Ajax response.  The response is updated in <code>UpdateBean</code>.
     * 
     * @see AjaxBehavior
     * @see HtmlBody
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2385
     */
    @Test
    public void testBodyAttributesAfterUpdate() throws Exception {
        WebPage page = getPage("updateBody.xhtml");
        WebElement button = page.findElement(By.id("form1:bodytag"));
        page.guardAjax(button::click);
        
        assertTrue(page.isInPage("BODY CLASS:foo"));
    }

}
