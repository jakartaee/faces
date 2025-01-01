/*
 * Copyright (c) 2021 Contributors to Eclipse Foundation.
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

package ee.jakarta.tck.faces.test.servlet50.selectmanycheckbox;

import static java.lang.System.getProperty;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.net.URL;

import jakarta.faces.component.html.HtmlSelectManyCheckbox;
import jakarta.faces.component.html.HtmlSelectOneRadio;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

@ExtendWith(ArquillianExtension.class)
public class Spec1574IT {

    @ArquillianResource
    private URL webUrl;
    private WebClient webClient;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return create(ZipImporter.class, getProperty("finalName") + ".war")
                .importFrom(new File("target/" + getProperty("finalName") + ".war"))
                .as(WebArchive.class);
    }

  @BeforeEach
  void setUp() {
        webClient = new WebClient();
    }

  @AfterEach
  void tearDown() {
        webClient.close();
    }

  /**
   * @see HtmlSelectManyCheckbox
     * @see https://github.com/jakartaee/faces/issues/1574
   */
  @Test
  void selectManyCheckboxDefaultMarkup() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "spec1574IT.xhtml");
        HtmlElement selectManyCheckbox = page.getHtmlElementById("form:input");
        assertValidMarkup(selectManyCheckbox, true, false);
    }

  /**
   * @see HtmlSelectManyCheckbox
     * @see https://github.com/jakartaee/faces/issues/1574
   */
  @Test
  void selectManyCheckboxLineDirectionMarkup() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "spec1574IT.xhtml?layout=lineDirection");
        HtmlElement selectManyCheckbox = page.getHtmlElementById("form:input");
        assertValidMarkup(selectManyCheckbox, true, false);
    }

  /**
   * @see HtmlSelectManyCheckbox
     * @see https://github.com/jakartaee/faces/issues/1574
   */
  @Test
  void selectManyCheckboxPageDirectionMarkup() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "spec1574IT.xhtml?layout=pageDirection");
        HtmlElement selectManyCheckbox = page.getHtmlElementById("form:input");
        assertValidMarkup(selectManyCheckbox, true, true);
    }

  /**
   * @see HtmlSelectManyCheckbox
     * @see https://github.com/jakartaee/faces/issues/1574
   */
  @Test
  void selectManyCheckboxListMarkup() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "spec1574IT.xhtml?layout=list");
        HtmlElement selectManyCheckbox = page.getHtmlElementById("form:input");
        assertValidMarkup(selectManyCheckbox, false, true);
    }

  /**
   * @see HtmlSelectOneRadio
     * @see https://github.com/jakartaee/faces/issues/1574
   */
  @Test
  void selectOneRadioDefaultMarkup() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "spec1574IT.xhtml?radio=true");
        HtmlElement selectOneRadio = page.getHtmlElementById("form:input");
        assertValidMarkup(selectOneRadio, true, false);
    }

  /**
   * @see HtmlSelectOneRadio
     * @see https://github.com/jakartaee/faces/issues/1574
   */
  @Test
  void selectOneRadioLineDirectionMarkup() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "spec1574IT.xhtml?radio=true&layout=lineDirection");
        HtmlElement selectOneRadio = page.getHtmlElementById("form:input");
        assertValidMarkup(selectOneRadio, true, false);
    }

  /**
   * @see HtmlSelectOneRadio
     * @see https://github.com/jakartaee/faces/issues/1574
   */
  @Test
  void selectOneRadioPageDirectionMarkup() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "spec1574IT.xhtml?radio=true&layout=pageDirection");
        HtmlElement selectOneRadio = page.getHtmlElementById("form:input");
        assertValidMarkup(selectOneRadio, true, true);
    }

  /**
   * @see HtmlSelectOneRadio
     * @see https://github.com/jakartaee/faces/issues/1574
   */
  @Test
  void selectOneRadioListMarkup() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "spec1574IT.xhtml?radio=true&layout=list");
        HtmlElement selectOneRadio = page.getHtmlElementById("form:input");
        assertValidMarkup(selectOneRadio, false, true);
    }

    private static void assertValidMarkup(HtmlElement element, boolean table, boolean vertical) {
        int inputFields = 0;

        if (table) {
            assertEquals("table", element.getNodeName(), "element is table");
            assertEquals(1, element.getChildElementCount(), "table has 1 child");
            assertEquals("tbody", element.getFirstElementChild().getNodeName(), "table child is tbody");
            int tbodyChildCount = vertical ? 4 : 1;
            assertEquals(tbodyChildCount, element.getFirstElementChild().getChildElementCount(), "tbody has " + tbodyChildCount + " rows");
            int i = 1;

            for (DomElement row : element.getFirstElementChild().getChildElements()) {
                assertEquals("tr", row.getNodeName(), "row is tr");
                int trChildCount = vertical ? 1 : 4;
                assertEquals(trChildCount, row.getChildElementCount(), "tr has " + trChildCount + " cells");

                for (DomElement cell : row.getChildElements()) {
                    assertEquals("td", cell.getNodeName(), "cell is td");

                    if (i % 2 == 0) {
                        assertEquals(1, cell.getChildElementCount(), "cell has 1 child");
                        assertEquals("table", cell.getFirstElementChild().getNodeName(), "cell child is table");
                        assertEquals(1, cell.getFirstElementChild().getChildElementCount(), "child table has 1 child");
                        assertEquals("tbody", cell.getFirstElementChild().getFirstElementChild().getNodeName(), "group is tbody");
                        int groupChildCount = vertical ? 3 : 1;
                        assertEquals(groupChildCount, cell.getFirstElementChild().getFirstElementChild().getChildElementCount(), "group has " + groupChildCount + " rows");

                        for (DomElement group : cell.getFirstElementChild().getFirstElementChild().getChildElements()) {
                            assertEquals("tr", group.getNodeName(), "group is tr");

                            for (DomElement item : group.getChildElements()) {
                                assertEquals("td", item.getNodeName(), "item is td");
                                assertEquals(2, item.getChildElementCount(), "td has 2 children");
                                assertEquals("input", item.getFirstElementChild().getNodeName(), "first child is input");
                                assertEquals("label", item.getLastElementChild().getNodeName(), "last child is label");
                                inputFields++;
                            }
                        }
                    }
                    else {
                        assertEquals(0, cell.getChildElementCount(), "cell has no children");
                    }
                    
                    i++;
                }
            }
        }
        else {
            assertEquals("ul", element.getNodeName(), "element is ul");
            assertEquals(2, element.getChildElementCount(), "ul has 2 children");

            for (DomElement group : element.getChildElements()) {
                assertEquals("li", group.getNodeName(), "group is li");
                assertEquals(1, group.getChildElementCount(), "li has 1 child");
                assertEquals("ul", group.getFirstElementChild().getNodeName(), "child is ul");

                for (DomElement item : group.getFirstElementChild().getChildElements()) {
                    assertEquals("li", item.getNodeName(), "item is li");
                    assertEquals(2, item.getChildElementCount(), "li has 2 children");
                    assertEquals("input", item.getFirstElementChild().getNodeName(), "first child is input");
                    assertEquals("label", item.getLastElementChild().getNodeName(), "last child is label");
                    inputFields++;
                }
            }
        }
        
        assertEquals(6, inputFields, "there were 6 input fields");
    }
}
