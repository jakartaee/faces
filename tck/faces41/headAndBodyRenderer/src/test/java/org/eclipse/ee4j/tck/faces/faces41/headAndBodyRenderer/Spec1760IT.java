/*
 * Copyright (c) 2024 Contributors to Eclipse Foundation.
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
package org.eclipse.ee4j.tck.faces.faces41.headAndBodyRenderer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.faces.render.RenderKit;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class Spec1760IT extends BaseITNG {

  /**
   * @see RenderKit
     * @see https://github.com/jakartaee/faces/issues/1760
   * @see https://github.com/jakartaee/faces/issues/1100
   */
  @Test
  void defaultOutputWithID() {
        WebPage page = getPage("spec1760withIDdefault.xhtml");
        WebElement head = page.findElement(By.tagName("head"));
        WebElement body = page.findElement(By.tagName("body"));
        assertEquals("headId", head.getDomAttribute("id"));
        assertEquals("bodyId", body.getDomAttribute("id"));
    }

  /**
   * @see RenderKit
     * @see https://github.com/jakartaee/faces/issues/1760
   * @see https://github.com/jakartaee/faces/issues/1100
   */
  @Test
  void defaultOutputWithoutID() {
        WebPage page = getPage("spec1760withoutIDdefault.xhtml");
        WebElement head = page.findElement(By.tagName("head"));
        WebElement body = page.findElement(By.tagName("body"));
        assertEquals("", head.getDomAttribute("id"));
        assertEquals("", body.getDomAttribute("id"));
    }

  /**
   * @see RenderKit
     * @see https://github.com/jakartaee/faces/issues/1760
   * @see https://github.com/jakartaee/faces/issues/1100
   */
  @Test
  void xhtmlOutputWithID() {
        WebPage page = getPage("spec1760withIDxhtml.xhtmlAsXhtml");
        WebElement head = page.findElement(By.tagName("head"));
        WebElement body = page.findElement(By.tagName("body"));
        assertEquals("", head.getDomAttribute("id"));
        assertEquals("", body.getDomAttribute("id"));
    }

  /**
   * @see RenderKit
     * @see https://github.com/jakartaee/faces/issues/1760
   * @see https://github.com/jakartaee/faces/issues/1100
   */
  @Test
  void xhtmlOutputWithoutID() {
        WebPage page = getPage("spec1760withoutIDxhtml.xhtmlAsXhtml");
        WebElement head = page.findElement(By.tagName("head"));
        WebElement body = page.findElement(By.tagName("body"));
        assertEquals("", head.getDomAttribute("id"));
        assertEquals("", body.getDomAttribute("id"));
    }

  /**
   * @see RenderKit
     * @see https://github.com/jakartaee/faces/issues/1760
   * @see https://github.com/jakartaee/faces/issues/1100
   */
  @Test
  void xmlOutputWithID() {
        WebPage page = getPage("spec1760withIDxml.xhtmlAsXml");
        WebElement head = page.findElement(By.tagName("head"));
        WebElement body = page.findElement(By.tagName("body"));
        assertEquals("", head.getDomAttribute("id"));
        assertEquals("", body.getDomAttribute("id"));
    }

  /**
   * @see RenderKit
     * @see https://github.com/jakartaee/faces/issues/1760
   * @see https://github.com/jakartaee/faces/issues/1100
   */
  @Test
  void xmlOutputWithoutID() {
        WebPage page = getPage("spec1760withoutIDxml.xhtmlAsXml");
        WebElement head = page.findElement(By.tagName("head"));
        WebElement body = page.findElement(By.tagName("body"));
        assertEquals("", head.getDomAttribute("id"));
        assertEquals("", body.getDomAttribute("id"));
    }
}
