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
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class Issue2906IT extends BaseITNG {

    /**
     * @see AjaxBehavior
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2910
     */
    @Test
    public void testCommandLinksInRepeat() throws Exception {
        WebPage page = getPage("issue2906.xhtml");


        assertTrue(page.matchesPageText(".*(2\\s+){9}2.*"));

        List<WebElement> anchors = page.getAnchors();
        WebElement anchor = anchors.get(9);
        anchor.click();
        page.waitReqJs();
        anchors = page.getAnchors();
        assertTrue(page.matchesPageText(".*(3\\s+){8}3.*"));
        assertTrue(anchors.size() == 9);

        anchor = anchors.get(8);
        anchor.click();
        page.waitReqJs(); 

        anchors = page.getAnchors();
        assertTrue(page.matchesPageText(".*(4\\s+){7}4.*"));
        assertTrue(anchors.size() == 8);

        anchor = anchors.get(7);
        anchor.click();
        page.waitReqJs(); 

        anchors = page.getAnchors();
        assertTrue(page.matchesPageText(".*(5\\s+){6}5.*"));
        assertTrue(anchors.size() == 7);

        anchor = anchors.get(0);
        anchor.click();
        page.waitReqJs(); 

        anchors = page.getAnchors();
        assertTrue(page.matchesPageText(".*(6\\s+){5}6.*"));
        assertTrue(anchors.size() == 6);

        anchor = anchors.get(2);
        anchor.click();
        page.waitReqJs(); 

        anchors = page.getAnchors();
        assertTrue(page.matchesPageText(".*(7\\s+){4}7.*"));
        assertTrue(anchors.size() == 5);
    }


}
