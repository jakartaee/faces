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

package ee.jakarta.tck.faces.test.servlet50.selectitemgroup;

import static java.lang.System.getProperty;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.net.URL;

import jakarta.faces.component.UISelectItemGroup;

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
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;

@ExtendWith(ArquillianExtension.class)
public class Spec1563IT {

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
   * @see UISelectItemGroup
     * @see https://github.com/jakartaee/faces/issues/1563
   */
  @Test
  void test() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "spec1563IT.xhtml");
        HtmlSelect select = page.getHtmlElementById("form:input");

        assertValidMarkup(select);

        select.setSelectedAttribute(select.getOptionByValue("5"), true);

        assertEquals("", page.getHtmlElementById("form:messages").asNormalizedText(), "messages is empty before submit");
        assertEquals("", page.getHtmlElementById("form:output").asNormalizedText(), "output is empty before submit");

        page = page.getHtmlElementById("form:submit").click();

        assertValidMarkup(select);
        assertEquals("", page.getHtmlElementById("form:messages").asNormalizedText(), "messages is still empty after submit");
        assertEquals("5", page.getHtmlElementById("form:output").asNormalizedText(), "output is '5' after submit");

        select = page.getHtmlElementById("form:input");
        select.setSelectedAttribute(select.getOptionByValue("2"), true);
        page = page.getHtmlElementById("form:submit").click();

        assertValidMarkup(select);
        assertEquals("", page.getHtmlElementById("form:messages").asNormalizedText(), "messages is still empty after submit");
        assertEquals("2", page.getHtmlElementById("form:output").asNormalizedText(), "output is '2' after submit");
    }

    private static void assertValidMarkup(HtmlSelect select) {
        assertEquals(2, select.getChildElementCount(), "select has 2 children");

        for (DomElement child : select.getChildElements()) {
            assertEquals("optgroup", child.getNodeName(), "child element is an optgroup");
            assertEquals(3, child.getChildElementCount(), "child has in turn 3 grandchildren");

            for (DomElement grandchild : child.getChildElements()) {
                assertEquals("option", grandchild.getNodeName(), "grandchild  element is an option");
            }
        }

        assertEquals(6, select.getOptions().size(), "select element has 6 options");

        HtmlOption option2 = select.getOptionByValue("2");
        assertEquals("Cat", option2.getText(), "2nd option is 'Cat'");

        HtmlOption option5 = select.getOptionByValue("5");
        assertEquals("Audi", option5.getText(), "5th option is 'Audi'");
    }
}
