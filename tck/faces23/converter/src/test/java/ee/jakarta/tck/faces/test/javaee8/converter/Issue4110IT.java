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
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.Temporal;
import java.util.Locale;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlPage;
import org.htmlunit.html.HtmlSpan;
import org.htmlunit.html.HtmlSubmitInput;
import org.htmlunit.html.HtmlTextInput;

import jakarta.faces.convert.DateTimeConverter;

@RunWith(Arquillian.class)
public class Issue4110IT {

    private static final Locale DUTCH_LOCALE = Locale.forLanguageTag("nl-NL");
    private static final LocalDate LOCAL_DATE = LocalDate.of(2015, 5, 30);
    private static final LocalTime LOCAL_TIME = LocalTime.of(16, 52, 56);
    private static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.of(2015, 5, 30, 16, 14, 43);
    private static final DateTimeFormatter LOCAL_DATE_FORMATTER =
            DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(DUTCH_LOCALE);
    private static final DateTimeFormatter LOCAL_TIME_FORMATTER =
            DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).withLocale(DUTCH_LOCALE);
    private static final DateTimeFormatter LOCAL_DATE_TIME_FORMATTER =
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(DUTCH_LOCALE);

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
        webClient.addRequestHeader("Accept-Language", "en-US");
    }

    @After
    public void tearDown() {
        webClient.close();
    }

    /**
     * @see DateTimeConverter
     * @see Temporal
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4114
     */
    @Test
    public void testLocalDate() throws Exception {
        doTestJavaTimeTypes(LOCAL_DATE_FORMATTER.format(LOCAL_DATE), "localDate",
                LOCAL_DATE.toString());
    }

    @Test
    public void testLocalTime() throws Exception {
        doTestJavaTimeTypes(LOCAL_TIME_FORMATTER.format(LOCAL_TIME), "localTime",
                LOCAL_TIME.toString());
    }

    @Test
    public void testLocalDateTime() throws Exception {
        doTestJavaTimeTypes(LOCAL_DATE_TIME_FORMATTER.format(LOCAL_DATE_TIME), "localDateTime",
                LOCAL_DATE_TIME.toString());
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
