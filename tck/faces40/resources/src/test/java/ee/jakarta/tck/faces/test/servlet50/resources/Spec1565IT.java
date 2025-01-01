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

package ee.jakarta.tck.faces.test.servlet50.resources;

import static java.lang.System.getProperty;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.net.URL;

import jakarta.faces.component.UIOutput;

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
import com.gargoylesoftware.htmlunit.html.HtmlPage;

@ExtendWith(ArquillianExtension.class)
public class Spec1565IT {

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
   * @see UIOutput
     * @see com.sun.faces.renderkit.html_basic.ScriptStyleBaseRenderer
     * @see https://github.com/jakartaee/faces/issues/1565
   */
  @Test
  void html5() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "spec1565IT-HTML5.xhtml");

        for (DomElement element : page.getElementsByTagName("script")) {
            assertEquals("", element.getAttribute("type"), "Script element has no type attribute");
        }

        for (DomElement element : page.getElementsByTagName("link")) {
            assertEquals("", element.getAttribute("type"), "Link element has no type attribute");
        }

        for (DomElement element : page.getElementsByTagName("style")) {
            assertEquals("", element.getAttribute("type"), "Style element has no type attribute");
        }
    }

  /**
   * @see UIOutput
     * @see com.sun.faces.renderkit.html_basic.ScriptStyleBaseRenderer
     * @see https://github.com/jakartaee/faces/issues/1565
   */
  @Test
  void html4() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "spec1565IT-HTML4.xhtml");

        for (DomElement element : page.getElementsByTagName("script")) {
            assertEquals("text/javascript", element.getAttribute("type"), "Script element has a type='text/javascript' attribute");
        }

        for (DomElement element : page.getElementsByTagName("link")) {
            assertEquals("text/css", element.getAttribute("type"), "Link element has a type='text/css' attribute");
        }

        for (DomElement element : page.getElementsByTagName("style")) {
            assertEquals("text/css", element.getAttribute("type"), "Style element has a type='text/css' attribute");
        }
    }

}
