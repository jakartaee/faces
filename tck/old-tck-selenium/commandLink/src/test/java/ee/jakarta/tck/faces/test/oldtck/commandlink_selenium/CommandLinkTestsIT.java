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

package ee.jakarta.tck.faces.test.oldtck.commandlink_selenium;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class CommandLinkTestsIT extends BaseITNG {

  /**
   * @testName: clinkRenderEncodeTest
   * 
   * @assertion_ids: PENDING
   * 
   * @test_Strategy: Ensure proper CommandLink rendering: - case 1: Anchor has
   *                 href value of '#' Anchor display value is "Click Me1"
   *                 Anchor onclick attribute value has a non-zero length
   * 
   *                 - case 2: Anchor has href value of '#' Anchor display value
   *                 is "Click Me2" The styleClass tag attribute is rendered as
   *                 the class attribute on the rendered anchor Anchor onclick
   *                 attribute value has a non-zero length
   * 
   *                 - case 3: Anchor has href value of '#' Anchor has display
   *                 value of "Click Me3" which is specified as a nested
   *                 HtmlOutput tag. Anchor onclick attribute value has a
   *                 non-zero length
   * 
   *                 - case 4: REMOVED CODE DUE TO BUG ID:6460959
   * 
   *                 - case 5: CommandLink has the disabled attribute set to
   *                 true. Ensure that: A span element is rendered instead of an
   *                 anchor The span has no onclick content The display value of
   *                 the span is 'Disabled Link' The styleClass tag attribute is
   *                 rendered as the class attribute on the rendered span
   *                 element
   * 
   *                 - case 6: CommandLink has the disabled attribute set to
   *                 true with a nested output component as the link's textual
   *                 value.
   * 
   *                 - case 7: CommandLink is tied to a backend bean via the
   *                 "binding" attribute. Test to make sure that the "title",
   *                 "shape" & "styleclass" are set and rendered correctly.
   * 
   * @since 1.2
   */
  @Test
  void clinkRenderEncodeTest() throws Exception {

    WebPage page = getPage("faces/encodetest_facelet.xhtml");

    WebElement link1 = page.findElement(By.id("form:link1"));
    assertEquals("#",link1.getDomAttribute​("href"));
    assertEquals("Click Me1",link1.getText());
    assertFalse(link1.getDomAttribute​("onclick").length() < 0);

    WebElement link2 = page.findElement(By.id("form:link2"));
    assertEquals("#",link1.getDomAttribute​("href"));
    assertEquals("Click Me2",link2.getText());
    assertEquals("sansserif", link2.getDomAttribute​("class"));
    assertFalse(link2.getDomAttribute​("onclick").length() < 0);

    WebElement link3 = page.findElement(By.id("form:link3"));
    assertEquals("#",link3.getDomAttribute​("href"));
    assertEquals("Click Me3",link3.getText());
    assertFalse(link3.getDomAttribute​("onclick").length() < 0);

    WebElement link5 = page.findElement(By.id("form:link5"));
    assertEquals("sansserif", link5.getDomAttribute​("class"));
    assertEquals("Disabled Link",link5.getText());
    assertNull(link5.getDomAttribute​("onclick")); 

    WebElement span2 = page.findElement(By.id("form:link6")); 
    assertEquals("Disabled Link(Nested)",span2.getText());

    WebElement span7 = page.findElement(By.id("form:link7"));
    assertEquals("sansserif",span7.getDomAttribute​("class"));
    assertEquals("rectangle",span7.getDomAttribute​("shape"));
    assertEquals("gone",span7.getDomAttribute​("title"));
  } // END clinkRenderEncodeTest

  /**
   * @testName: clinkRenderDecodeTest
   * 
   * @assertion_ids: PENDING
   * 
   * @test_Strategy:
   * 
   * @since 1.2
   */
  @Test
  void clinkRenderDecodeTest() throws Exception {
    WebPage page = getPage("faces/decodetest_facelet.xhtml");

    WebElement result = page.findElement(By.id("result"));
    assertEquals("", result.getText());

    WebElement link1 = page.findElement(By.id("form:link1"));
    link1.click();

    result = page.findElement(By.id("result"));
    assertEquals("PASSED", result.getText());

  } // END clinkRenderDecodeTest

  /**
   * @testName: clinkRenderPassthroughTest
   * 
   * @assertion_ids: PENDING
   * 
   * @test_Strategy: Ensure those attributes marked as passthrough are indeed
   *                 visible in the rendered markup as specified in the JSP
   *                 page.
   * 
   * @since 1.2
   */
  @Test
  void clinkRenderPassthroughTest() throws Exception {

    TreeMap<String, String> control = new TreeMap<String, String>();
    control.put("accesskey", "U");
    control.put("charset", "ISO-8859-1");
    control.put("coords", "31,45");
    control.put("dir", "LTR");
    control.put("hreflang", "en");
    control.put("lang", "en");
    control.put("onblur", "js1");
    control.put("ondblclick", "js3");
    control.put("onfocus", "js4");
    control.put("onkeydown", "js5");
    control.put("onkeypress", "js6");
    control.put("onkeyup", "js7");
    control.put("onmousedown", "js8");
    control.put("onmousemove", "js9");
    control.put("onmouseout", "js10");
    control.put("onmouseover", "js11");
    control.put("onmouseup", "js12");
    control.put("rel", "somevalue");
    control.put("rev", "revsomevalue");
    control.put("shape", "rect");
    control.put("style", "Color: red;");
    control.put("tabindex", "0");
    control.put("title", "sample");
    control.put("type", "type");

    TreeMap<String, String> controlSpan = new TreeMap<String, String>();
    controlSpan.put("accesskey", "U");
    controlSpan.put("dir", "LTR");
    controlSpan.put("lang", "en");
    controlSpan.put("onblur", "js1");
    controlSpan.put("ondblclick", "js3");
    controlSpan.put("onfocus", "js4");
    controlSpan.put("onkeydown", "js5");
    controlSpan.put("onkeypress", "js6");
    controlSpan.put("onkeyup", "js7");
    controlSpan.put("onmousedown", "js8");
    controlSpan.put("onmousemove", "js9");
    controlSpan.put("onmouseout", "js10");
    controlSpan.put("onmouseover", "js11");
    controlSpan.put("onmouseup", "js12");
    controlSpan.put("style", "Color: red;");
    controlSpan.put("tabindex", "0");
    controlSpan.put("title", "sample");

    List<String> urls = new ArrayList<String>();
    urls.add("faces/passthroughtest.xhtml");
    urls.add("faces/passthroughtest_facelet.xhtml");

    for (String url : urls) {
      WebPage page = getPage(url);
      // Facelet Specific PassThrough options
      if (page.getTitle().contains("facelet")) {
        control.put("foo", "bar");
        control.put("singleatt", "singleAtt");
        control.put("manyattone", "manyOne");
        control.put("manyatttwo", "manyTwo");
        control.put("manyattthree", "manyThree");
        controlSpan.put("foo", "bar");
        controlSpan.put("singleatt", "singleAtt");
        controlSpan.put("manyattone", "manyOne");
        controlSpan.put("manyatttwo", "manyTwo");
        controlSpan.put("manyattthree", "manyThree");
      }

      WebElement anchor = page.findElement(By.id("form:link1"));

      for (Map.Entry<String, String> entry : control.entrySet()) {
        assertEquals(anchor.getDomAttribute​(entry.getKey()), entry.getValue());
      }


      WebElement span = page.findElement(By.id("form:link2"));
      for (Map.Entry<String, String> entry : controlSpan.entrySet()) {
        assertEquals(span.getDomAttribute​(entry.getKey()), entry.getValue());
      }

    }

  } // END clinkRenderPassthroughTest

}
