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
import jakarta.faces.event.PreRenderViewEvent;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Manfred Riem (manfred.riem@oracle.com)
 * <no>Not working</no>
 */
public class Issue2162IT extends BaseITNG {

    /**
     * @see PreRenderViewEvent
     * @see AjaxBehavior
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2166
     */
    @Test
    public void testIssue2162() throws Exception {
        WebPage page = getPage("issue2162.xhtml");
        assertTrue(page.isInPage("Init called\n"));

        WebElement button = page.findElement(By.id("form:submit"));
        button.click();


        page.waitReqJs();
        //  this test is failing on Chrome, probably a Mojarra bug
        //  problem is the ajax update passes, but the init called is not rendered anymore
        //  the rest is, this is very likely a mojarra bug triggered by the chrome engine
        // The page source reveals following, so the viewstate update probably has overwritten the
        // init called text in Chrome:
        /*
        <html xmlns="http://www.w3.org/1999/xhtml"><head id="j_idt3">
            <title>Issue 2162</title><script src="/test-faces22-ajax/jakarta.faces.resource/faces.js.xhtml;jsessionid=987f999fce875c6ec95f00467ca0?ln=jakarta.faces"></script><script type="text/javascript" src="/test-faces22-ajax/jakarta.faces.resource/faces.js.xhtml?ln=jakarta.faces"></script></head>
            <body>
                <form id="form" name="form" method="post" action="/test-faces22-ajax/issue2162.xhtml" enctype="application/x-www-form-urlencoded">
                    <input type="hidden" name="form" value="form">
                    <input id="form:submit" type="submit" name="form:submit" value="Submit" onclick="mojarra.ab(this,event,'click','@form','@all');return false">
                    <input type="hidden" name="jakarta.faces.ViewState" value="-502622467461252732:-9011607885137894041">
                </form>
            </body></html>
         */
        assertTrue(page.isInPage("Init called\nInit called\n"));
        assertFalse(page.isInPage("Init called\nInit called\nInit called"));
    }
}
