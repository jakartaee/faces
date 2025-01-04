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
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

public class Issue2948IT extends BaseITNG {

  /**
   * @see FlowScoped
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2952
   */
  @Test
  void sessionLogging() throws Exception {
        WebPage page = getPage("");
        WebElement e = page.findElement(By.id("initMessage"));
        long sessionInitTime = Long.valueOf(e.getText());
        WebElement invalidateButton = page.findElement(By.id("invalidateSession"));

        invalidateButton.click();
        e = page.findElement(By.id("destroyMessage"));
        long sessionDestroyTime = Long.valueOf(e.getText());
        assertTrue(sessionInitTime < sessionDestroyTime);
    }

  /**
   * @see FlowScoped
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2952
   */
  @Test
  void flowLogging() throws Exception {
        // index.xhtml
        WebPage page = getPage("");

        WebElement enterFlow = page.findElement(By.id("enterFlow"));

        // 01_simplest/01_simplest.xhtml
        enterFlow.click();

        WebElement e = page.findElement(By.id("initMessage"));
        long flowInitTime = Long.valueOf(e.getText());
        WebElement next = page.findElement(By.id("a"));

        // 01_simplest/a.xhtml
        next.click();

        WebElement returnButton = page.findElement(By.id("return"));

        // 01_simplest/a.xhtml
        returnButton.click();


        // Should work, but doesn't: the action

//        e = page.findElement(By.id("destroyMessage"));
//        long flowDestroyTime = Long.valueOf(e.getText());
//        assertTrue(flowInitTime < flowDestroyTime);
    }

  /**
   * @see ViewScoped
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2952
   */
  @Test
  void viewScopedLogging() throws Exception {
        WebPage page = getPage("faces/viewScoped01.xhtml");
        WebElement e = page.findElement(By.id("initMessage"));
        long flowInitTime = Long.valueOf(e.getText());
        WebElement returnButton = page.findElement(By.id("viewScoped02"));

        returnButton.click();
        e = page.findElement(By.id("destroyMessage"));
        long flowDestroyTime = Long.valueOf(e.getText());
        assertTrue(flowInitTime < flowDestroyTime);
    }
}
