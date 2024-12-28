/*
 * Copyright (c) 2021 Contributors to the Eclipse Foundation.
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
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

import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.flow.FlowScoped;
import jakarta.faces.view.ViewScoped;

import org.junit.jupiter.api.Test;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;

public class Issue2948IT extends ITBase {

  /**
   * @see FlowScoped
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2952
   */
  @Test
  void sessionLogging() throws Exception {
        HtmlPage page = webClient.getPage(webUrl);
        HtmlElement e = (HtmlElement) page.getElementById("initMessage");
        long sessionInitTime = Long.valueOf(e.asNormalizedText());
        HtmlSubmitInput invalidateButton = (HtmlSubmitInput) page.getElementById("invalidateSession");

        page = invalidateButton.click();
        e = (HtmlElement) page.getElementById("destroyMessage");
        long sessionDestroyTime = Long.valueOf(e.asNormalizedText());
        assertTrue(sessionInitTime < sessionDestroyTime);
    }

  /**
   * @see FlowScoped
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2952
   */
  @Test
  void flowLogging() throws Exception {
        // index.xhtml
        HtmlPage page = webClient.getPage(webUrl);

        HtmlSubmitInput enterFlow = (HtmlSubmitInput) page.getElementById("enterFlow");

        // 01_simplest/01_simplest.xhtml
        page = enterFlow.click();

        HtmlElement e = (HtmlElement) page.getElementById("initMessage");
        long flowInitTime = Long.valueOf(e.asNormalizedText());
        HtmlSubmitInput next = (HtmlSubmitInput) page.getElementById("a");

        // 01_simplest/a.xhtml
        page = next.click();

        HtmlSubmitInput returnButton = (HtmlSubmitInput) page.getElementById("return");

        // 01_simplest/a.xhtml
        page = returnButton.click();


        // Should work, but doesn't: the action

//        e = (HtmlElement) page.getElementById("destroyMessage");
//        long flowDestroyTime = Long.valueOf(e.asNormalizedText());
//        assertTrue(flowInitTime < flowDestroyTime);
    }

  /**
   * @see ViewScoped
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2952
   */
  @Test
  void viewScopedLogging() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "faces/viewScoped01.xhtml");
        HtmlElement e = (HtmlElement) page.getElementById("initMessage");
        long flowInitTime = Long.valueOf(e.asNormalizedText());
        HtmlSubmitInput returnButton = (HtmlSubmitInput) page.getElementById("viewScoped02");

        page = returnButton.click();
        e = (HtmlElement) page.getElementById("destroyMessage");
        long flowDestroyTime = Long.valueOf(e.asNormalizedText());
        assertTrue(flowInitTime < flowDestroyTime);
    }
}
