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

import java.util.List;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;
import jakarta.faces.component.behavior.AjaxBehavior;

public class Issue2906IT extends ITBase {

    /**
     * @see AjaxBehavior
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2910
     */
    @Test
    public void testCommandLinksInRepeat() throws Exception {
        HtmlPage page = getPage("issue2906.xhtml");
        assertTrue(page.asNormalizedText().contains("2   2   2   2   2   2   2   2   2   2"));

        List anchors = page.getAnchors();
        HtmlAnchor anchor = (HtmlAnchor) anchors.get(9);
        page = anchor.click();
        webClient.waitForBackgroundJavaScript(3000);
        anchors = page.getAnchors();
        assertTrue(page.asNormalizedText().contains("3   3   3   3   3   3   3   3   3"));
        assertTrue(anchors.size() == 9);

        anchor = (HtmlAnchor) anchors.get(8);
        page = anchor.click();
        webClient.waitForBackgroundJavaScript(3000);
        
        anchors = page.getAnchors();
        assertTrue(page.asNormalizedText().contains("4   4   4   4   4   4   4   4"));
        assertTrue(anchors.size() == 8);

        anchor = (HtmlAnchor) anchors.get(7);
        page = anchor.click();
        webClient.waitForBackgroundJavaScript(3000);
        
        anchors = page.getAnchors();
        assertTrue(page.asNormalizedText().contains("5   5   5   5   5   5   5"));
        assertTrue(anchors.size() == 7);

        anchor = (HtmlAnchor) anchors.get(0);
        page = anchor.click();
        webClient.waitForBackgroundJavaScript(3000);
        
        anchors = page.getAnchors();
        assertTrue(page.asNormalizedText().contains("6   6   6   6   6   6"));
        assertTrue(anchors.size() == 6);

        anchor = (HtmlAnchor) anchors.get(2);
        page = anchor.click();
        webClient.waitForBackgroundJavaScript(3000);
        
        anchors = page.getAnchors();
        assertTrue(page.asNormalizedText().contains("7   7   7   7   7"));
        assertTrue(anchors.size() == 5);
    }
}
