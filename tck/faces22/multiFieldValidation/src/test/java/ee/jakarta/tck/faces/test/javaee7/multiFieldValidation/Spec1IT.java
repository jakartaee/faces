/*
 * Copyright (c) 2021 Contributors to the Eclipse Foundation.
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
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

package ee.jakarta.tck.faces.test.javaee7.multiFieldValidation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

public class Spec1IT extends BaseITNG {

    /**
     * @see com.sun.faces.ext.component.UIValidateWholeBean
     * @see https://github.com/jakartaee/faces/issues/1
     */
    @Test
    void simpleInvalidField() throws Exception {
        WebPage page = getPage("");

        WebElement password1 = page.findElement(By.id("password1"));
        password1.sendKeys("foofoofoo");

        WebElement password2 = page.findElement(By.id("password2"));
        password2.sendKeys("bar");

        WebElement button = page.findElement(By.id("submit"));

        button.click();

        String pageText = page.getPageSource();
        assertFalse(pageText.contains("[foofoofoo]"));
        assertTrue(pageText.contains("[bar]"));

        assertFalse(pageText.contains("Password fields must match"));

        WebElement password1Value = page.findElement(By.id("password1Value"));
        assertTrue(password1Value.getText().isEmpty());

        WebElement password2Value = page.findElement(By.id("password2Value"));
        assertTrue(password2Value.getText().isEmpty());
    }

    /**
     * @see com.sun.faces.ext.component.UIValidateWholeBean
     * @see https://github.com/jakartaee/faces/issues/1
     */
    @Test
    void simpleInvalidFields() throws Exception {
        WebPage page = getPage("");

        WebElement password1 = page.findElement(By.id("password1"));
        password1.sendKeys("foo");

        WebElement password2 = page.findElement(By.id("password2"));
        password2.sendKeys("bar");

        WebElement button = page.findElement(By.id("submit"));

        button.click();

        String pageText = page.getPageSource();
        assertTrue(pageText.contains("[foo]"));
        assertTrue(pageText.contains("[bar]"));

        assertFalse(pageText.contains("Password fields must match"));

        WebElement password1Value = page.findElement(By.id("password1Value"));
        assertTrue(password1Value.getText().isEmpty());

        WebElement password2Value = page.findElement(By.id("password2Value"));
        assertTrue(password2Value.getText().isEmpty());
    }

    /**
     * @see com.sun.faces.ext.component.UIValidateWholeBean
     * @see https://github.com/jakartaee/faces/issues/1
     */
    @Test
    void simpleValidFieldsInvalidBean() throws Exception {
        WebPage page = getPage("");

        WebElement password1 = page.findElement(By.id("password1"));
        password1.sendKeys("foofoofoo");

        WebElement password2 = page.findElement(By.id("password2"));
        password2.sendKeys("barbarbar");

        WebElement button = page.findElement(By.id("submit"));

        button.click();

        String pageText = page.getPageSource();
        assertFalse(pageText.contains("[foofoofoo]"));
        assertFalse(pageText.contains("[barbarbar]"));

        assertTrue(pageText.contains("Password fields must match"));

        WebElement password1Value = page.findElement(By.id("password1Value"));
        assertTrue(password1Value.getText().isEmpty());

        WebElement password2Value = page.findElement(By.id("password2Value"));
        assertTrue(password2Value.getText().isEmpty());

    }

    /**
     * @see com.sun.faces.ext.component.UIValidateWholeBean
     * @see https://github.com/jakartaee/faces/issues/1
     */
    @Test
    void simpleValidFieldsValidBean() throws Exception {
        WebPage page = getPage("");

        WebElement password1 = page.findElement(By.id("password1"));
        password1.sendKeys("foofoofoo");

        WebElement password2 = page.findElement(By.id("password2"));
        password2.sendKeys("foofoofoo");

        WebElement button = page.findElement(By.id("submit"));

        button.click();

        String pageText = page.getPageSource();
        assertFalse(pageText.contains("[foofoofoo]"));
        assertFalse(pageText.contains("[barbarbar]"));

        assertFalse(pageText.contains("Password fields must match"));

        WebElement password1Value = page.findElement(By.id("password1Value"));
        assertTrue(password1Value.getText().contains("foofoofoo"));

        WebElement password2Value = page.findElement(By.id("password2Value"));
        assertTrue(password2Value.getText().contains("foofoofoo"));

    }

    /**
     * @see com.sun.faces.ext.component.UIValidateWholeBean
     * @see https://github.com/jakartaee/faces/issues/1
     */
    @Test
    void failingPreconditionsNotAfterAllInputComponents() throws Exception {
        // In this test f:validateWholeBean is misplaced (does not appear after
        // all input components), which should result in an exception
        WebPage page = getPage("faces/failingDevTimePreconditions.xhtml");
        assertEquals(500, page.getResponseStatus(), "Exception should have been thrown resulting in a 500 http status code");
    }

}
