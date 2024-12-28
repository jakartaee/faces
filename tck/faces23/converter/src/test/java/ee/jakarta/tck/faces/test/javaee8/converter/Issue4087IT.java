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

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.temporal.Temporal;

import jakarta.faces.convert.DateTimeConverter;

import org.junit.jupiter.api.Test;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;


public class Issue4087IT extends ITBase {

  /**
   * @see DateTimeConverter
     * @see Temporal
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4091
   */
  @Test
  void javaTimeTypes() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "faces/issue4087.xhtml");
        HtmlPage page1 = null;

        try {

            HtmlTextInput input1 = (HtmlTextInput)page.getHtmlElementById("localDateTime1");
            input1.setValueAttribute("30 mei 2015 16:14:43");

            HtmlTextInput input2 = (HtmlTextInput)page.getHtmlElementById("localDateTime2");
            input2.setValueAttribute("30 mei 2015 16:14:43");

            HtmlTextInput input3 = (HtmlTextInput)page.getHtmlElementById("localTime1");
            input3.setValueAttribute("16:14:43");

            HtmlTextInput input4 = (HtmlTextInput)page.getHtmlElementById("localTime2");
            input4.setValueAttribute("16:14:43");

            HtmlSubmitInput submit = (HtmlSubmitInput)page.getHtmlElementById("submit");
            page1 = submit.click();

            HtmlSpan time1Output = (HtmlSpan)page1.getHtmlElementById("localDateTimeValue1");
            assertTrue(time1Output.getTextContent().contains("30 mei 2015 16:14"));

            HtmlSpan time2Output = (HtmlSpan)page1.getHtmlElementById("localDateTimeValue2");
            assertTrue(time2Output.getTextContent().contains("30 mei 2015 16:14"));

            HtmlSpan time3Output = (HtmlSpan)page1.getHtmlElementById("localTimeValue1");
            assertTrue(time3Output.getTextContent().contains("16:14:43"));

            HtmlSpan time4Output = (HtmlSpan)page1.getHtmlElementById("localTimeValue2");
            assertTrue(time4Output.getTextContent().contains("16:14"));
        } catch (AssertionError w) {
            System.out.println(page.asXml());
            if (page1 != null) {
                System.out.println(page1.asXml());
            }
            throw w;
        }
    }

}
