/*
 * Copyright (c) 2021, 2022 Contributors to the Eclipse Foundation.
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
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

package ee.jakarta.tck.faces.test.javaee7.cdiinitdestroyevent.cdiinitdestroyevent;

import static java.lang.System.getProperty;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.net.URL;

import jakarta.faces.flow.FlowScoped;

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
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

// https://github.com/eclipse-ee4j/mojarra/issues/3001
// https://github.com/jakartaee/faces/issues/1734
@ExtendWith(ArquillianExtension.class)
public class Issue2997IT {

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
   * @see FlowScoped
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3001
   */
  @Test
  void globalReturn() throws Exception {
        HtmlPage page = webClient.getPage(webUrl);

        HtmlSubmitInput button = (HtmlSubmitInput) page.getElementById("flow-with-templates");
        page = button.click();

        String pageText = page.getBody().asNormalizedText();
        assertTrue(pageText.contains("Bottom From Template"));
        assertTrue(pageText.contains("issue2997Bean"));

        button = (HtmlSubmitInput) page.getElementById("issue2997Home");
        page = button.click();

        pageText = page.getBody().asNormalizedText();
        assertTrue(pageText.contains("Issue2997Home"));
        assertTrue(pageText.contains("flow-with-templates"));
        assertTrue(pageText.contains("issue2997Bean"));

        page = webClient.getPage(webUrl);

        button = (HtmlSubmitInput) page.getElementById("flow-with-templates");
        page = button.click();

        pageText = page.getBody().asNormalizedText();
        assertTrue(pageText.contains("Bottom From Template"));

        button = (HtmlSubmitInput) page.getElementById("issue2997UserList");
        page = button.click();

        pageText = page.getBody().asNormalizedText();
        assertTrue(pageText.contains("Issue2997UserList"));
        assertTrue(pageText.contains("flow-with-templates"));
        assertTrue(pageText.contains("issue2997Bean"));

        page = webClient.getPage(webUrl);

        button = (HtmlSubmitInput) page.getElementById("flow-with-templates");
        page = button.click();

        pageText = page.getBody().asNormalizedText();
        assertTrue(pageText.contains("Bottom From Template"));

        button = (HtmlSubmitInput) page.getElementById("issue2997PageInFacesConfig");
        page = button.click();

        pageText = page.getBody().asNormalizedText();
        assertTrue(pageText.contains("Issue2997PageInFacesConfig"));
        assertTrue(pageText.contains("flow-with-templates"));
        assertTrue(pageText.contains("issue2997Bean"));
    }
}
