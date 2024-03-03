/*
 * Copyright (c) 2024 Contributors to Eclipse Foundation.
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
package org.eclipse.ee4j.tck.faces.faces41.uuidConverter;

import static org.junit.Assert.assertEquals;

import jakarta.faces.convert.UUIDConverter;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

public class Spec1819IT extends BaseITNG {

    private static final String TEST_UUID = "cafebabe-0420-0069-dead-123456789012";

    /**
     * @see UUIDConverter
     * @see https://github.com/jakartaee/faces/issues/1819
     */
    @Test
    public void testValidImplicitUUID() {
        WebPage page = getPage("spec1819implicitConversion.xhtml");
        WebElement input = page.findElement(By.id("form:input"));
        input.sendKeys(TEST_UUID);
        WebElement submit = page.findElement(By.id("form:submit"));
        submit.click();
        WebElement messages = page.findElement(By.id("form:messages"));
        assertEquals("", messages.getText());
        WebElement output = page.findElement(By.id("form:output"));
        assertEquals(TEST_UUID, output.getText());
    }

    /**
     * @see UUIDConverter
     * @see https://github.com/jakartaee/faces/issues/1819
     */
    @Test
    public void testInvalidImplicitUUID() {
        WebPage page = getPage("spec1819implicitConversion.xhtml");
        WebElement input = page.findElement(By.id("form:input"));
        input.sendKeys("fubar");
        WebElement submit = page.findElement(By.id("form:submit"));
        submit.click();
        WebElement messages = page.findElement(By.id("form:messages"));
        assertEquals("form:input: 'fubar' must be a UUID.", messages.getText());
        WebElement output = page.findElement(By.id("form:output"));
        assertEquals("", output.getText());
    }

    /**
     * @see UUIDConverter
     * @see https://github.com/jakartaee/faces/issues/1819
     */
    @Test
    public void testEmptyImplicitUUID() {
        WebPage page = getPage("spec1819implicitConversion.xhtml");
        WebElement input = page.findElement(By.id("form:input"));
        input.sendKeys("");
        WebElement submit = page.findElement(By.id("form:submit"));
        submit.click();
        WebElement messages = page.findElement(By.id("form:messages"));
        assertEquals("", messages.getText());
        WebElement output = page.findElement(By.id("form:output"));
        assertEquals("", output.getText());
    }

    /**
     * @see UUIDConverter
     * @see https://github.com/jakartaee/faces/issues/1819
     */
    @Test
    public void testValidExplicitUUIDviaAttribute() {
        WebPage page = getPage("spec1819explicitConversionViaAttribute.xhtml");
        WebElement input = page.findElement(By.id("form:input"));
        input.sendKeys(TEST_UUID);
        WebElement submit = page.findElement(By.id("form:submit"));
        submit.click();
        WebElement messages = page.findElement(By.id("form:messages"));
        assertEquals("", messages.getText());
        WebElement output = page.findElement(By.id("form:output"));
        assertEquals(TEST_UUID, output.getText());
    }

    /**
     * @see UUIDConverter
     * @see https://github.com/jakartaee/faces/issues/1819
     */
    @Test
    public void testInvalidExplicitUUIDviaAttribute() {
        WebPage page = getPage("spec1819explicitConversionViaAttribute.xhtml");
        WebElement input = page.findElement(By.id("form:input"));
        input.sendKeys("fubar");
        WebElement submit = page.findElement(By.id("form:submit"));
        submit.click();
        WebElement messages = page.findElement(By.id("form:messages"));
        assertEquals("form:input: 'fubar' must be a UUID.", messages.getText());
        WebElement output = page.findElement(By.id("form:output"));
        assertEquals("", output.getText());
    }

    /**
     * @see UUIDConverter
     * @see https://github.com/jakartaee/faces/issues/1819
     */
    @Test
    public void testEmptyExplicitUUIDviaAttribute() {
        WebPage page = getPage("spec1819explicitConversionViaAttribute.xhtml");
        WebElement input = page.findElement(By.id("form:input"));
        input.sendKeys("");
        WebElement submit = page.findElement(By.id("form:submit"));
        submit.click();
        WebElement messages = page.findElement(By.id("form:messages"));
        assertEquals("", messages.getText());
        WebElement output = page.findElement(By.id("form:output"));
        assertEquals("", output.getText());
    }

    /**
     * @see UUIDConverter
     * @see https://github.com/jakartaee/faces/issues/1819
     */
    @Test
    public void testValidExplicitUUIDviaTag() {
        WebPage page = getPage("spec1819explicitConversionViaTag.xhtml");
        WebElement input = page.findElement(By.id("form:input"));
        input.sendKeys(TEST_UUID);
        WebElement submit = page.findElement(By.id("form:submit"));
        submit.click();
        WebElement messages = page.findElement(By.id("form:messages"));
        assertEquals("", messages.getText());
        WebElement output = page.findElement(By.id("form:output"));
        assertEquals(TEST_UUID, output.getText());
    }

    /**
     * @see UUIDConverter
     * @see https://github.com/jakartaee/faces/issues/1819
     */
    @Test
    public void testInvalidExplicitUUIDviaTag() {
        WebPage page = getPage("spec1819explicitConversionViaTag.xhtml");
        WebElement input = page.findElement(By.id("form:input"));
        input.sendKeys("fubar");
        WebElement submit = page.findElement(By.id("form:submit"));
        submit.click();
        WebElement messages = page.findElement(By.id("form:messages"));
        assertEquals("form:input: 'fubar' must be a UUID.", messages.getText());
        WebElement output = page.findElement(By.id("form:output"));
        assertEquals("", output.getText());
    }

    /**
     * @see UUIDConverter
     * @see https://github.com/jakartaee/faces/issues/1819
     */
    @Test
    public void testEmptyExplicitUUIDviaTag() {
        WebPage page = getPage("spec1819explicitConversionViaTag.xhtml");
        WebElement input = page.findElement(By.id("form:input"));
        input.sendKeys("");
        WebElement submit = page.findElement(By.id("form:submit"));
        submit.click();
        WebElement messages = page.findElement(By.id("form:messages"));
        assertEquals("", messages.getText());
        WebElement output = page.findElement(By.id("form:output"));
        assertEquals("", output.getText());
    }
}
