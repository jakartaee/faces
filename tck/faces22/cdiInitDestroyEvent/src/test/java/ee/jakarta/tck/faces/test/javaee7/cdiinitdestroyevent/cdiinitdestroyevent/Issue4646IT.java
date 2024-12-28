/*
 * Copyright (c) 2021 Contributors to the Eclipse Foundation.
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
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

package ee.jakarta.tck.faces.test.javaee7.cdiinitdestroyevent.cdiinitdestroyevent;

import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.faces.view.ViewScoped;

import org.junit.jupiter.api.Test;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;

public class Issue4646IT extends ITBase {

  /**
   * @see ViewScoped
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4646
   */
  @Test
  void preDestroyEventIssue4646() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "faces/issue4646.xhtml");
        HtmlElement counterElement = (HtmlElement) page.getElementById("counterMessage");
        int currentCount = parseInt(counterElement.asNormalizedText());
        
        // +1
        page = webClient.getPage(webUrl + "faces/issue4646.xhtml");
        counterElement = (HtmlElement) page.getElementById("counterMessage");
        assertEquals(currentCount + 1, parseInt(counterElement.asNormalizedText()), "+1 should be the objects created");
        
        // +2
        page = webClient.getPage(webUrl + "faces/issue4646.xhtml");
        counterElement = (HtmlElement) page.getElementById("counterMessage");
        assertEquals(currentCount + 2, parseInt(counterElement.asNormalizedText()), "+2 should be the objects created");
        
        // invalidate
        HtmlSubmitInput invalidateButton = (HtmlSubmitInput) page.getElementById("invalidateSession");
        invalidateButton.click();
        
        // should be the initial count
        page = webClient.getPage(webUrl + "faces/issue4646.xhtml");
        counterElement = (HtmlElement) page.getElementById("counterMessage");
        assertEquals(currentCount, parseInt(counterElement.asNormalizedText()), "The initial count should be again");
        
        // invalidate again
        invalidateButton = (HtmlSubmitInput) page.getElementById("invalidateSession");
        invalidateButton.click();
    }
}
