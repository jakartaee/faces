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

import jakarta.faces.convert.DateTimeConverter;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

public class Issue4070IT extends BaseITNG {

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
        WebPage page = getPage("faces/Issue4070Using.xhtml");
        WebElement input = page.findElement(By.id(inputId));
        input.sendKeys(value);
        WebElement submit = page.findElement(By.id("submit"));
        submit.click();

        WebElement output = page.findElement(By.id(inputId + "Value"));
        assertEquals(expected, output.getText());
    }

    /**
     * @see DateTimeConverter
     * @see Temporal
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4074
     */
    @Test
    void inputOutputDiffer() throws Exception {
        WebPage page = getPage("faces/Issue4070InputOutputDiffer.xhtml");

        WebElement input = page.findElement(By.id("localDate"));
        input.sendKeys("30.09.2015");
        WebElement submit = page.findElement(By.id("submit"));
        submit.click();

        WebElement output = page.findElement(By.id("localDateValue"));
        assertEquals("30.09.15", output.getText());
    }

}
