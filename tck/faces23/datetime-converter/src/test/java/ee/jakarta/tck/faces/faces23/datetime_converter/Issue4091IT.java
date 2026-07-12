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

package ee.jakarta.tck.faces.faces23.datetime_converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.Temporal;
import java.util.Locale;

import jakarta.faces.convert.DateTimeConverter;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

public class Issue4091IT extends BaseITNG {

    private static final Locale DUTCH_LOCALE = Locale.forLanguageTag("nl-NL");
    private static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.of(2015, 5, 30, 16, 14, 43);
    private static final LocalTime LOCAL_TIME = LocalTime.of(16, 14, 43);
    private static final DateTimeFormatter MEDIUM_DATE_TIME_FORMATTER =
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(DUTCH_LOCALE);
    private static final DateTimeFormatter MEDIUM_SHORT_DATE_TIME_FORMATTER =
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT).withLocale(DUTCH_LOCALE);
    private static final DateTimeFormatter MEDIUM_TIME_FORMATTER =
            DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).withLocale(DUTCH_LOCALE);
    private static final DateTimeFormatter SHORT_TIME_FORMATTER =
            DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(DUTCH_LOCALE);

    /**
     * @see DateTimeConverter
     * @see Temporal
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4091
     */
    @Test
    void javaTimeTypes() throws Exception {
        String localDateTime = MEDIUM_DATE_TIME_FORMATTER.format(LOCAL_DATE_TIME);
        String localTime = MEDIUM_TIME_FORMATTER.format(LOCAL_TIME);

        WebPage page = getPage("issue4091.xhtml");
        WebElement input1 = page.findElement(By.id("localDateTime1"));
        input1.sendKeys(localDateTime);

        WebElement input2 = page.findElement(By.id("localDateTime2"));
        input2.sendKeys(localDateTime);

        WebElement input3 = page.findElement(By.id("localTime1"));
        input3.sendKeys(localTime);

        WebElement input4 = page.findElement(By.id("localTime2"));
        input4.sendKeys(localTime);

        WebElement submit = page.findElement(By.id("submit"));
        submit.click();

        WebElement time1Output = page.findElement(By.id("localDateTimeValue1"));
        assertEquals(MEDIUM_SHORT_DATE_TIME_FORMATTER.format(LOCAL_DATE_TIME), time1Output.getText());

        WebElement time2Output = page.findElement(By.id("localDateTimeValue2"));
        assertEquals(MEDIUM_SHORT_DATE_TIME_FORMATTER.format(LOCAL_DATE_TIME), time2Output.getText());

        WebElement time3Output = page.findElement(By.id("localTimeValue1"));
        assertEquals(MEDIUM_TIME_FORMATTER.format(LOCAL_TIME), time3Output.getText());

        WebElement time4Output = page.findElement(By.id("localTimeValue2"));
        assertEquals(SHORT_TIME_FORMATTER.format(LOCAL_TIME), time4Output.getText());
    }

}
