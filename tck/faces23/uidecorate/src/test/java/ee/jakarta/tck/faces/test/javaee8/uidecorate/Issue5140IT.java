/*
 * Copyright (c) 2022, 2025 Contributors to Eclipse Foundation.
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

package ee.jakarta.tck.faces.test.javaee8.uidecorate;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Issue5140IT extends BaseITNG {

    /**
     * @see https://github.com/eclipse-ee4j/mojarra/issues/5140
     */
    @Test
    void test() throws Exception {
        WebPage page = getPage("issue5140.xhtml");
        assertThrows(NoSuchElementException.class, () -> page.findElement(By.id("Field")),
            "unexpected element may not exist");
        WebElement expectedElement = page.findElement(By.id("testInputIdField"));
        assertNotNull(expectedElement, "expected element exists");
        assertEquals("ui:insert content", expectedElement.getText(), "ui:insert content is present");
    }

}