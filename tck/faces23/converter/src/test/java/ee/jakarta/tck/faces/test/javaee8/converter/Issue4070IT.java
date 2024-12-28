/*
 * Copyright (c) 2024 Contributors to Eclipse Foundation.
 * Copyright (c) 1997, 2021 Oracle and/or its affiliates. All rights reserved.
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

package ee.jakarta.tck.faces.test.javaee8.converter;

import static java.lang.System.getProperty;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.net.URL;
import java.time.temporal.Temporal;

import jakarta.faces.convert.DateTimeConverter;

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
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;


@ExtendWith(ArquillianExtension.class)
public class Issue4070IT {

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
        webClient.getOptions().setTimeout(900_000);
        webClient.addRequestHeader("Accept-Language", "en-US");
    }

  @AfterEach
  void tearDown() {
        webClient.close();
    }

  /**
   * @see DateTimeConverter
     * @see Temporal
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4074
   */
  @Test
  void localDateTime() throws Exception {
        doTestJavaTimeTypes("May 30, 2015, 4:14:43 PM", "localDateTime", "2015-05-30T16:14:43");
    }

  @Test
  void localDate() throws Exception {
        doTestJavaTimeTypes("May 30, 2015", "localDate", "2015-05-30");
    }

  @Test
  void localTime() throws Exception {
        doTestJavaTimeTypes("4:52:56 PM", "localTime", "16:52:56");
    }

  @Test
  void offsetTime() throws Exception {
        doTestJavaTimeTypes("17:07:19.358-04:00", "offsetTime", "17:07:19.358-04:00");
    }

  @Test
  void offsetDateTime() throws Exception {
        doTestJavaTimeTypes("2015-09-30T17:24:36.529-04:00", "offsetDateTime", "2015-09-30T17:24:36.529-04:00");
    }

  @Test
  void zonedDateTime() throws Exception {
        doTestJavaTimeTypes("2015-09-30T17:31:42.09-04:00[America/New_York]", "zonedDateTime", "2015-09-30T17:31:42.090-04:00[America/New_York]");
    }

    private void doTestJavaTimeTypes(String value, String inputId, String expected) throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "faces/Issue4070Using.xhtml");

        try {
            HtmlTextInput input = page.getHtmlElementById(inputId);
            input.setValueAttribute(value);
            HtmlSubmitInput submit = page.getHtmlElementById("submit");
            page = submit.click();

            HtmlSpan output = page.getHtmlElementById(inputId + "Value");
            assertEquals(expected, output.getTextContent());
        } catch (AssertionError e) {
            System.out.println(page.getHtmlElementById("messages").asXml());

            throw e;
        }
    }

  /**
   * @see DateTimeConverter
     * @see Temporal
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4074
   */
  @Test
  void inputOutputDiffer() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "faces/Issue4070InputOutputDiffer.xhtml");

        HtmlTextInput input = page.getHtmlElementById("localDate");
        input.setValueAttribute("30.09.2015");
        HtmlSubmitInput submit = page.getHtmlElementById("submit");
        page = submit.click();

        HtmlSpan output = page.getHtmlElementById("localDateValue");
        assertEquals("30.09.15", output.getTextContent());
    }

}
