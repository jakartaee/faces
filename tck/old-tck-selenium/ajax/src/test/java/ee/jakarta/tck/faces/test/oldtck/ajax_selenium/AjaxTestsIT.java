/*
 * Copyright (c) 2023 Contributors to the Eclipse Foundation.
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

package ee.jakarta.tck.faces.test.oldtck.ajax_selenium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.Keys.TAB;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

public class AjaxTestsIT extends BaseITNG {


    @Test
    public void ajaxTagWrappingTest() throws Exception {
      WebPage page = getPage("faces/tagwrapper/ajaxTagWrap.xhtml");

      // First we'll check the first page was output correctly
      WebElement span1 = page.findElement(By.id("out1"));
      assertEquals("0", span1.getText());

      WebElement span2 = page.findElement(By.id("checkedvalue"));
      assertEquals("false", span2.getText());

      WebElement span3 = page.findElement(By.id("outtext"));
      assertEquals("", span3.getText());
  
      // Submit the ajax request
      WebElement button1 = page.findElement(By.id("button1"));
      assertNotNull(button1);
      page.guardAjax(button1::click);

      // Check that the ajax request succeeds
      span1 = page.findElement(By.id("out1"));
      assertEquals("1", span1.getText());
  
      // Check on the text field
      page.guardAjax(() -> {
          WebElement intext = page.findElement(By.id("intext"));
          intext.sendKeys("test");
          intext.sendKeys(TAB); // click out of intext to force onchange event
      });
      assertTrue(page.findElement(By.id("outtext")).getText().equals("test"));
  
      // Check the checkbox
      WebElement checkbox = page.findElement(By.id("checkbox"));
      page.guardAjax(checkbox::click);
      span2 = page.findElement(By.id("checkedvalue"));
      assertEquals("true", span2.getText());
  
    }// End ajaxAllKeywordTest

  /**
   * @testName: ajaxAllKeywordTest
   * @assertion_ids: PENDING
   * @test_Strategy: Unsure the keyword 'all' works correctly with the f:ajax
   *                 tag as value to 'execute' and 'render' attributes.
   * 
   * @since 2.0
   */
  @Test
  public void ajaxAllKeywordTest() throws Exception {

    String EXPECTED = "testtext";

    List<String> urls = new ArrayList<String>();
    urls.add("faces/keyword/ajaxAllKeyword1.xhtml");
    urls.add("faces/keyword/ajaxAllKeyword2.xhtml");
    urls.add("faces/keyword/ajaxAllKeyword3.xhtml");

    String buttonId = "form:allKeyword";
    String spanId = "form:out";

    this.validateKeyword(urls, buttonId, spanId, EXPECTED);

  }// End ajaxAllKeywordTest

  /**
   * @testName: ajaxThisKeywordTest
   * @assertion_ids: PENDING
   * @test_Strategy: Unsure the keyword 'this' works correctly with the f:ajax
   *                 tag as value to 'execute' and 'render' attributes.
   * 
   * @since 2.0
   */
  @Test
  public void ajaxThisKeywordTest() throws Exception {

    String EXPECTED = "testtext";

    List<String> urls = new ArrayList<String>();
    urls.add("faces/keyword/ajaxThisKeyword1.xhtml");
    urls.add("faces/keyword/ajaxThisKeyword2.xhtml");
    urls.add("faces/keyword/ajaxThisKeyword3.xhtml");

    String buttonId = "form:thisKeyword";
    String spanId = "form:out";

    this.validateKeyword(urls, buttonId, spanId, EXPECTED);
  } // End ajaxThisKeywordTest

  /**
   * @testName: ajaxFormKeywordTest
   * @assertion_ids: PENDING
   * @test_Strategy: Unsure the keyword 'form' works correctly with the f:ajax
   *                 tag as value to 'execute' and 'render' attributes.
   * 
   * @since 2.0
   */
  @Test
  public void ajaxFormKeywordTest() throws Exception {

    String EXPECTED = "testtext";

    List<String> urls = new ArrayList<String>();
    urls.add("faces/keyword/ajaxFormKeyword1.xhtml");
    urls.add("faces/keyword/ajaxFormKeyword2.xhtml");
    urls.add("faces/keyword/ajaxFormKeyword3.xhtml");

    String buttonId = "form:formKeyword";
    String spanId = "form:out";

    this.validateKeyword(urls, buttonId, spanId, EXPECTED);
  } // End ajaxThisKeywordTest

   /**
   * @testName: ajaxNoneKeywordTest
   * @assertion_ids: PENDING
   * @test_Strategy: Unsure the keyword 'none' works correctly with the f:ajax
   *                 tag as value to 'execute' and 'render' attributes.
   * 
   * @since 2.0
   */
  @Test
  public void ajaxNoneKeywordTest() throws Exception {

    String EXPECTED = "testtext";

    List<String> urls = new ArrayList<String>();
    urls.add("faces/keyword/ajaxNoneKeyword1.xhtml");
    urls.add("faces/keyword/ajaxNoneKeyword2.xhtml");
    urls.add("faces/keyword/ajaxNoneKeyword3.xhtml");

    String buttonId = "form:noneKeyword";
    String spanId = "form:out";

    this.validateKeyword(urls, buttonId, spanId, EXPECTED);
  } // End ajaxThisKeywordTest

  /**
   * @testName: ajaxPDLResourceTest
   * 
   * @assertion_ids: JSF:SPEC:225; JSF:SPEC:226; JSF:SPEC:227
   * 
   * @test_Strategy: Validate that the jsf.js Resource is available via the
   *                 "Page Declaration Language Approach".
   * 
   * @since 2.0
   */
  @Ignore("Skipped in  Old TCK for EE10")
  @Test
  public void ajaxPDLResourceTest() throws Exception {

    WebPage page = getPage("faces/jsresource/pdlApproach.xhtml");
    WebElement script = page.findElement(By.tagName("script"));

    // Verify Resource name.
    assertTrue(script.getDomAttribute​("src").contains("faces.js"));
    // Verify Resource Library name.
    assertTrue(script.getDomAttribute​("src").contains("jakarta.faces"));

  }// End ajaxPDLResourceTest


  // HELPER METHODS
  private void validateKeyword(List<String> urls, String buttonId,
      String spanId, String expectedValue) throws Exception {

      for (String url : urls) {
        WebPage page = getPage(url);

        WebElement output = page.findElement(By.id(spanId));

        // First we'll check the first page was output correctly
        assertEquals(expectedValue, output.getText());

        // Submit the ajax request
        WebElement button = page.findElement(By.id(buttonId));
        page.guardAjax(button::click);

        // Check that the ajax request succeeds - if the page is rewritten,
        // this will be the same
        output = page.findElement(By.id(spanId));
        assertEquals(expectedValue, output.getText());
      }
    }

}
