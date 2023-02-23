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
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlScript;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

@RunWith(Arquillian.class)
public class AjaxTestsIT {

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

    public void ajaxTagWrappingTest() throws Fault {
        StringBuilder messages = new StringBuilder(128);
        Formatter formatter = new Formatter(messages);
    
        HtmlPage page = getPage(CONTEXT_ROOT + "/faces/ajaxTagWrap.xhtml");
    
        // First we'll check the first page was output correctly
        this.validateSpanTag(page, "out1", "0");
        this.validateSpanTag(page, "checkedvalue", "false");
        this.validateSpanTag(page, "outtext", "");
    
        // Submit the ajax request
        HtmlInput button1 = (HtmlInput) getElementOfTypeIncludingId(page, "input",
            "button1");
    
        if (!validateExistence("button1", "input", button1, formatter)) {
          handleTestStatus(messages);
          return;
        }
    
        try {
          button1.click();
        } catch (IOException ex) {
          formatter.format("Unexpected Execption thrown while clicking '%s'.",
              button1.getId());
          ex.printStackTrace();
        }
    
        // Check that the ajax request succeeds - eventually.
        this.validateSpanTag(page, "out1", "1");
    
        // // Check on the text field
        HtmlInput intext = ((HtmlInput) getElementOfTypeIncludingId(page, "input",
            "intext"));
    
        if (!validateExistence("input", "input", intext, formatter)) {
          handleTestStatus(messages);
          return;
        }
        try {
          intext.focus();
          intext.type("test");
          intext.blur();
        } catch (IOException ex) {
          formatter.format("Unexpected Test failing when setting one or "
              + "more of the following attributes: focus, type, or blur");
          ex.printStackTrace();
        }
    
        this.validateSpanTag(page, "outtext", "test");
    
        // Check the checkbox
    
        HtmlInput checkbox = (HtmlInput) getElementOfTypeIncludingId(page, "input",
            "checkbox");
    
        if (!validateExistence("checkbox", "input", checkbox, formatter)) {
          handleTestStatus(messages);
          return;
        }
    
        checkbox.setChecked(true);
    
        if (!checkbox.isChecked()) {
          formatter.format(
              "Unexpected value for '%s'!" + NL + "Expected: '%s'" + NL
                  + "Received: '%s'" + NL,
              checkbox.getId(), "true", checkbox.isChecked());
        }
    
        handleTestStatus(messages);
      }// End ajaxAllKeywordTest
  
      https://github.com/jakartaee/faces/blob/3fae98234692ec16545a6d27cf36fabaeb883f9b/tck/old-tck/source/src/com/sun/ts/tests/jsf/spec/ajax/keyword/URLClient.java
    /**
     * @testName: ajaxAllKeywordTest
     * @assertion_ids: PENDING
     * @test_Strategy: Unsure the keyword 'all' works correctly with the f:ajax
     *                 tag as value to 'execute' and 'render' attributes.
     * 
     * @since 2.0
     */
    public void ajaxAllKeywordTest() throws Fault {
  
      List<HtmlPage> pages = new ArrayList<HtmlPage>();
      pages.add(getPage(CONTEXT_ROOT + "/faces/ajaxAllKeyword1.xhtml"));
      pages.add(getPage(CONTEXT_ROOT + "/faces/ajaxAllKeyword2.xhtml"));
      pages.add(getPage(CONTEXT_ROOT + "/faces/ajaxAllKeyword3.xhtml"));
  
      String buttonId = "allKeyword";
      String spanId = "out";
  
      this.validateKeyword(pages, buttonId, spanId, EXPECTED);
  
    }// End ajaxAllKeywordTest
  
    /**
     * @testName: ajaxThisKeywordTest
     * @assertion_ids: PENDING
     * @test_Strategy: Unsure the keyword 'this' works correctly with the f:ajax
     *                 tag as value to 'execute' and 'render' attributes.
     * 
     * @since 2.0
     */
    public void ajaxThisKeywordTest() throws Fault {
      List<HtmlPage> pages = new ArrayList<HtmlPage>();
      pages.add(getPage(CONTEXT_ROOT + "/faces/ajaxThisKeyword1.xhtml"));
      pages.add(getPage(CONTEXT_ROOT + "/faces/ajaxThisKeyword2.xhtml"));
      pages.add(getPage(CONTEXT_ROOT + "/faces/ajaxThisKeyword3.xhtml"));
  
      String buttonId = "thisKeyword";
      String spanId = "out";
  
      this.validateKeyword(pages, buttonId, spanId, EXPECTED);
    } // End ajaxThisKeywordTest
  
    /**
     * @testName: ajaxFormKeywordTest
     * @assertion_ids: PENDING
     * @test_Strategy: Unsure the keyword 'form' works correctly with the f:ajax
     *                 tag as value to 'execute' and 'render' attributes.
     * 
     * @since 2.0
     */
    public void ajaxFormKeywordTest() throws Fault {
      List<HtmlPage> pages = new ArrayList<HtmlPage>();
      pages.add(getPage(CONTEXT_ROOT + "/faces/ajaxFormKeyword1.xhtml"));
      pages.add(getPage(CONTEXT_ROOT + "/faces/ajaxFormKeyword2.xhtml"));
      pages.add(getPage(CONTEXT_ROOT + "/faces/ajaxFormKeyword3.xhtml"));
  
      String buttonId = "formKeyword";
      String spanId = "out";
  
      this.validateKeyword(pages, buttonId, spanId, EXPECTED);
    } // End ajaxThisKeywordTest
  
    /**
     * @testName: ajaxNoneKeywordTest
     * @assertion_ids: PENDING
     * @test_Strategy: Unsure the keyword 'none' works correctly with the f:ajax
     *                 tag as value to 'execute' and 'render' attributes.
     * 
     * @since 2.0
     */
    public void ajaxNoneKeywordTest() throws Fault {
      List<HtmlPage> pages = new ArrayList<HtmlPage>();
      pages.add(getPage(CONTEXT_ROOT + "/faces/ajaxNoneKeyword1.xhtml"));
      pages.add(getPage(CONTEXT_ROOT + "/faces/ajaxNoneKeyword2.xhtml"));
      pages.add(getPage(CONTEXT_ROOT + "/faces/ajaxNoneKeyword3.xhtml"));
  
      String buttonId = "noneKeyword";
      String spanId = "out";
  
      this.validateKeyword(pages, buttonId, spanId, EXPECTED);
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
    public void ajaxPDLResourceTest() throws Fault {
  
      this.validateScript(getPage(CONTEXT_ROOT + "/faces/pdlApproach.xhtml"));
  
    }// End ajaxPDLResourceTest
  
    private void validateScript(HtmlPage page) throws Fault {
      StringBuilder messages = new StringBuilder(128);
      Formatter formatter = new Formatter(messages);
      String script = "script";
  
      // Test by Resource name.
      HtmlScript resn = (HtmlScript) getElementOfTypeIncludingSrc(page, script,
          RES_NAME);
  
      if (resn == null) {
        formatter.format("Unexpected Test Result For %s Tag! %n"
            + "Expected Src Attribute to contain: %s %n", script, RES_NAME);
      }
  
      // Test by Resource Library name.
      HtmlScript resl = (HtmlScript) getElementOfTypeIncludingSrc(page, script,
          LIB_NAME);
  
      if (resl == null) {
        formatter.format("Unexpected Test Result For %s Tag! %n"
            + "Expected Src Attribute to contain: %s %n", script, LIB_NAME);
      }
  
      handleTestStatus(messages);
      formatter.close();
    }
  
    private void validateKeyword(List<HtmlPage> pages, String buttonId,
      String spanId, String expectedValue) throws Fault {
      StringBuilder messages = new StringBuilder(128);
      Formatter formatter = new Formatter(messages);
  
      String span = "span";
      for (HtmlPage page : pages) {
        HtmlSpan output = (HtmlSpan) getElementOfTypeIncludingId(page, span,
            spanId);
  
        if (!validateExistence(spanId, span, output, formatter)) {
          handleTestStatus(messages);
          return;
        }
  
        // First we'll check the first page was output correctly
        validateElementValue(output, expectedValue, formatter);
  
        // Submit the ajax request
        HtmlSubmitInput button = (HtmlSubmitInput) getElementOfTypeIncludingId(
            page, "input", buttonId);
        try {
          button.click();
        } catch (IOException ex) {
          formatter.format("Unexpected Execption thrown while clicking '%s'.",
              button.getId());
          ex.printStackTrace();
        }
  
        // Check that the ajax request succeeds - if the page is rewritten,
        // this will be the same
        validateElementValue(output, expectedValue, formatter);
  
        handleTestStatus(messages);
      }
    }
  
      /**
     * Test for a the give @String "expectedValue" to match the value of the
     * named @HtmlSpan "element "spanID".
     * 
     * @param page
     *          - @HtmlPage that contains @HtmlSpan element.
     * @param expectedValue
     *          - The expected result.
     * @param formatter
     *          - used to gather test result output.
     */
    private void validateSpanTag(HtmlPage page, String spanId,
        String expectedValue) throws Fault {
      StringBuilder messages = new StringBuilder(128);
      Formatter formatter = new Formatter(messages);
  
      HtmlSpan output = (HtmlSpan) getElementOfTypeIncludingId(page, SPAN,
          spanId);
  
      if (!validateExistence(spanId, SPAN, output, formatter)) {
        handleTestStatus(messages);
        return;
      }
      validateElementValue(output, expectedValue, formatter);
  
    }// End validateSpanTag
}
