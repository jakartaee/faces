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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.Temporal;
import java.util.Locale;

import jakarta.faces.convert.DateTimeConverter;

import org.junit.jupiter.api.Test;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;

public class Issue4110IT extends ITBase {

  /**
   * @see DateTimeConverter
     * @see Temporal
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4114
   */
  @Test
  void localDate() throws Exception {
        doTestJavaTimeTypes("30 mei 2015", "localDate", "2015-05-30");
    }

  @Test
  void localTime() throws Exception {
        doTestJavaTimeTypes("16:52:56", "localTime", "16:52:56");
    }

  @Test
  void localDateTime() throws Exception {
        doTestJavaTimeTypes("30 mei 2015 16:14:43", "localDateTime", "2015-05-30T16:14:43");
    }

    private void doTestJavaTimeTypes(String value, String type, String expected) throws Exception {
        Locale.setDefault(Locale.US);
        HtmlPage page = webClient.getPage(webUrl + "faces/issue4110.xhtml");

        try {
            HtmlTextInput input = page.getHtmlElementById("form:" + type + "Input");
            input.setValueAttribute(value);
            HtmlSubmitInput submit = page.getHtmlElementById("form:submit");
            page = submit.click();

            HtmlSpan output = page.getHtmlElementById("form:" + type + "Output");
            assertEquals(expected, output.getTextContent());
        } catch (AssertionError e) {
            if (page != null) {
                System.out.println(page.asXml());
            }
            throw e;
        }
    }

}
