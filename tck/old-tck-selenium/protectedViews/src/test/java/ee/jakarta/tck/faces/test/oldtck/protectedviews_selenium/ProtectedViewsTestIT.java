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

package ee.jakarta.tck.faces.test.oldtck.protectedviews_selenium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

public class ProtectedViewsTestIT extends BaseITNG {

 /**
   * @testName: viewProtectedViewNonAccessPointTest
   * 
   * @assertion_ids: PENDING
   * 
   * @test_Strategy: Validate that a that we can not gain access to a Protected
   *                 View from out side that views web-app.
   * 
   * @since 2.2
   */
  @Test
  public void viewProtectedViewNonAccessPointTest() throws Exception {

    WebPage page = getPage("faces/views/protected.xhtml");

    assertTrue("Illegal Access of a Protected View!", page.findElements​(By.id("messOne")).size() == 0);

    assertTrue("Expected a ProtectedViewException when accessing a protected view", page.isInPageText("jakarta.faces.application.ProtectedViewException"));

  } 

  /**
   * @testName: viewProtectedViewSameWebAppAccessTest
   * 
   * @assertion_ids: PENDING
   * 
   * @test_Strategy: Validate that we are able to gain access to a protected
   *                 view from inside the same web-app through a non-protected
   *                 view.
   * 
   * @since 2.2
   */
  @Test
  public void viewProtectedViewSameWebAppAccessTest() throws Exception {

    String expected = "This is a Protected View!";

    WebPage page = getPage("faces/views/public.xhtml");

    WebElement anchor = page.findElement(By.id("form1:linkOne"));

    anchor.click();
    page.waitReqJs();
    assertEquals(expected, page.findElement(By.id("messOne")).getText());

  } 

}
