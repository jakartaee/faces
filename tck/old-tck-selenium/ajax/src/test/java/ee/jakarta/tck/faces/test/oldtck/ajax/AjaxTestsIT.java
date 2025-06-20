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

package ee.jakarta.tck.faces.test.oldtck.ajax;

import static java.lang.System.getProperty;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlInput;
import org.htmlunit.html.HtmlPage;
import org.htmlunit.html.HtmlScript;
import org.htmlunit.html.HtmlSpan;
import org.htmlunit.html.HtmlSubmitInput;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;

public class AjaxTestsIT extends ITBase {

    @ArquillianResource
    private URL webUrl;
    private WebClient webClient;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return create(ZipImporter.class, getProperty("finalName") + ".war")
                .importFrom(new File("target/" + getProperty("finalName") + ".war"))
                .as(WebArchive.class);
    }

    @Before
    public void setUp() {
        webClient = new WebClient();
    }

    @After
    public void tearDown() {
        webClient.close();
    }


    @Test
    public void ajaxTagWrappingTest() throws Exception {
      HtmlPage page = webClient.getPage(webUrl + "/faces/tagwrapper/ajaxTagWrap.xhtml");

      // First we'll check the first page was output correctly
      HtmlSpan span1 = (HtmlSpan) page.getElementById("out1");
      assertNotNull(span1);
      assertEquals("0", span1.asNormalizedText());

      HtmlSpan span2 = (HtmlSpan) page.getElementById("checkedvalue");
      assertNotNull(span2);
      assertEquals("false", span2.asNormalizedText());

      HtmlSpan span3 = (HtmlSpan) page.getElementById("outtext");
      assertNotNull(span3);
      assertEquals("", span3.asNormalizedText());
  
      // Submit the ajax request
      HtmlInput button1 = (HtmlInput) page.getElementById("button1");
      assertNotNull(button1);
      page = button1.click();
  
      webClient.waitForBackgroundJavaScript(3000);

      // Check that the ajax request succeeds - eventually.
      span1 = (HtmlSpan) page.getElementById("out1");
      assertEquals("1",span1.asNormalizedText());
  
      // // Check on the text field
      HtmlInput intext = (HtmlInput) page.getElementById("intext");
      assertNotNull(intext);
      intext.focus();
      intext.type("test");
      intext.blur();
      webClient.waitForBackgroundJavaScript(3000);

      span3 = (HtmlSpan) page.getElementById("outtext");
      assertNotNull(span3);
      assertEquals("test", span3.asNormalizedText());
  
      // Check the checkbox
      HtmlInput checkbox = (HtmlInput) page.getElementById("checkbox");
      assertNotNull(checkbox);
      page = (HtmlPage) checkbox.setChecked(true);
      checkbox = (HtmlInput) page.getElementById("checkbox");
      assertTrue(checkbox.isChecked());

      // Check for "true" in outtext? Not originally tested (maybe due to HTMLUnit issues?). Will add to selenium run.
  
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
    urls.add(webUrl + "/faces/keyword/ajaxAllKeyword1.xhtml");
    urls.add(webUrl + "/faces/keyword/ajaxAllKeyword2.xhtml");
    urls.add(webUrl + "/faces/keyword/ajaxAllKeyword3.xhtml");

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
    urls.add(webUrl + "/faces/keyword/ajaxThisKeyword1.xhtml");
    urls.add(webUrl + "/faces/keyword/ajaxThisKeyword2.xhtml");
    urls.add(webUrl + "/faces/keyword/ajaxThisKeyword3.xhtml");

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
    urls.add(webUrl + "/faces/keyword/ajaxFormKeyword1.xhtml");
    urls.add(webUrl + "/faces/keyword/ajaxFormKeyword2.xhtml");
    urls.add(webUrl + "/faces/keyword/ajaxFormKeyword3.xhtml");

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
    urls.add(webUrl + "/faces/keyword/ajaxNoneKeyword1.xhtml");
    urls.add(webUrl + "/faces/keyword/ajaxNoneKeyword2.xhtml");
    urls.add(webUrl + "/faces/keyword/ajaxNoneKeyword3.xhtml");

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

    HtmlPage page = webClient.getPage( webUrl + "/faces/jsresource/pdlApproach.xhtml");
    HtmlScript script = (HtmlScript) page.getElementsByTagName("script").get(0);
    assertNotNull(script);

    // Test by Resource name.
    assertTrue(script.getSrcAttribute().contains("faces.js"));
    // Test by Resource Library name.
    assertTrue(script.getSrcAttribute().contains("jakarta.faces"));

  }// End ajaxPDLResourceTest


  // HELPER METHODS
  private void validateKeyword(List<String> urls, String buttonId,
      String spanId, String expectedValue) throws Exception {

      for (String url : urls) {
        HtmlPage page = webClient.getPage(url);
        HtmlSpan output = (HtmlSpan) page.getElementById(spanId);
        assertNotNull(output);

        // First we'll check the first page was output correctly
        assertEquals(expectedValue, output.asNormalizedText());

        // Submit the ajax request
        HtmlSubmitInput button = (HtmlSubmitInput) page.getElementById(buttonId);
        page = button.click();

        webClient.waitForBackgroundJavaScript(3000);

        // Check that the ajax request succeeds - if the page is rewritten,
        // this will be the same
        output = (HtmlSpan) page.getElementById(spanId);
        assertNotNull(output);
        assertEquals(expectedValue, output.asNormalizedText());
      }
  }
}
