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

package ee.jakarta.tck.faces.test.servlet50.namespaces;

import static java.lang.System.getProperty;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.net.URL;

import jakarta.faces.application.Application;
import jakarta.faces.view.facelets.Facelet;

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
public class Spec1553IT {

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
   * @see Facelet
     * @see Application#createComponent(String)
     * @see https://github.com/jakartaee/faces/issues/1553
   */
  @Test
  void test() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "spec1553IT.xhtml");

        assertEquals("Spec1553IT", page.getTitleText(), "jakarta.faces.html h:head works");

        assertEquals("value", getValue(page.getElementById("ui_sun")), "http://java.sun.com/jsf/facelets works");
        assertEquals("value", getValue(page.getElementById("f_sun")), "http://java.sun.com/jsf/core works");
        assertEquals("value", getValue(page.getElementById("h_sun")), "http://java.sun.com/jsf/html works");
        assertEquals("value", getValue(page.getElementById("cc_sun")), "http://java.sun.com/jsf/composite works");
        assertEquals("value", getValue(page.getElementById("c_sun")), "http://java.sun.com/jsp/jstl/core works");
        assertEquals("value", getValue(page.getElementById("fn_sun")), "http://java.sun.com/jsp/jstl/functions works");

        assertEquals("id_jcp", page.getElementById("jsf_jcp").getChildElements().iterator().next().getAttribute("id"), "http://xmlns.jcp.org/jsf works");
        assertEquals("value", getValue(page.getElementById("ui_jcp")), "http://xmlns.jcp.org/jsf/facelets works");
        assertEquals("value", getValue(page.getElementById("f_jcp")), "http://xmlns.jcp.org/jsf/core works");
        assertEquals("value", getValue(page.getElementById("h_jcp")), "http://xmlns.jcp.org/jsf/html works");
        assertEquals("email", page.getElementById("p_jcp").getChildElements().iterator().next().getAttribute("type"), "http://xmlns.jcp.org/jsf/passthrough works");
        assertEquals("value", getValue(page.getElementById("cc_jcp")), "http://xmlns.jcp.org/jsf/composite works");
        assertEquals("value", getValue(page.getElementById("comp_jcp")), "http://xmlns.jcp.org/jsf/component works");
        assertEquals("value", getValue(page.getElementById("c_jcp")), "http://xmlns.jcp.org/jsp/jstl/core works");
        assertEquals("value", getValue(page.getElementById("fn_jcp")), "http://xmlns.jcp.org/jsp/jstl/functions works");

        assertEquals("id_jakarta", page.getElementById("faces_jakarta").getChildElements().iterator().next().getAttribute("id"), "jakarta.faces works");
        assertEquals("value", getValue(page.getElementById("ui_jakarta")), "jakarta.faces.facelets works");
        assertEquals("value", getValue(page.getElementById("f_jakarta")), "jakarta.faces.core works");
        assertEquals("value", getValue(page.getElementById("h_jakarta")), "jakarta.faces.html works");
        assertEquals("email", page.getElementById("p_jakarta").getChildElements().iterator().next().getAttribute("type"), "jakarta.faces.passthrough works");
        assertEquals("value", getValue(page.getElementById("cc_jakarta")), "jakarta.faces.composite works");
        assertEquals("value", getValue(page.getElementById("comp_jakarta")), "jakarta.faces.component works");
        assertEquals("value", getValue(page.getElementById("c_jakarta")), "jakarta.tags.core works");
        assertEquals("value", getValue(page.getElementById("fn_jakarta")), "jakarta.tags.functions works");

    }

    private static String getValue(DomElement element) {
        assertEquals(0, element.getChildElementCount(), "This element has no children");
        return element.asNormalizedText();
    }
}
