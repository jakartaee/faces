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
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

public class Issue4087IT extends BaseITNG {

    /**
     * @see DateTimeConverter
     * @see Temporal
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4091
     */
    @Test
    void javaTimeTypes() throws Exception {
        WebPage page = getPage("faces/issue4087.xhtml");
        WebElement input1 = page.findElement(By.id("localDateTime1"));
        input1.sendKeys("30 mei 2015 16:14:43");

        WebElement input2 = page.findElement(By.id("localDateTime2"));
        input2.sendKeys("30 mei 2015 16:14:43");

        WebElement input3 = page.findElement(By.id("localTime1"));
        input3.sendKeys("16:14:43");

        WebElement input4 = page.findElement(By.id("localTime2"));
        input4.sendKeys("16:14:43");

        WebElement submit = page.findElement(By.id("submit"));
        submit.click();

        WebElement time1Output = page.findElement(By.id("localDateTimeValue1"));
        assertTrue(time1Output.getText().contains("30 mei 2015 16:14"));

        WebElement time2Output = page.findElement(By.id("localDateTimeValue2"));
        assertTrue(time2Output.getText().contains("30 mei 2015 16:14"));

        WebElement time3Output = page.findElement(By.id("localTimeValue1"));
        assertTrue(time3Output.getText().contains("16:14:43"));

        WebElement time4Output = page.findElement(By.id("localTimeValue2"));
        assertTrue(time4Output.getText().contains("16:14"));
    }

}
