/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2023 Contributors to Eclipse Foundation.
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

package ee.jakarta.tck.faces.test.javaee8.uiinput;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

import jakarta.faces.application.Application;
import jakarta.faces.component.UISelectItems;
import jakarta.faces.component.UISelectMany;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class Spec1422IT extends BaseITNG {

    /**
     * @see UISelectMany
     * @see UISelectItems
     * @see Collection
     * @see Application#createConverter(Class)
     * @see https://github.com/jakartaee/faces/issues/1422
     */
    @Test
    void spec1422() throws Exception {
        WebPage page = getPage("spec1422.xhtml");
        assertTrue(page.findElement(By.id("form:result")).getText().isEmpty());

        WebElement item1 = page.findElement(By.id("form:items:0"));
        WebElement item2 = page.findElement(By.id("form:items:1"));
        WebElement item3 = page.findElement(By.id("form:items:2"));
        WebElement number1 = page.findElement(By.id("form:numbers:1"));
        WebElement number2 = page.findElement(By.id("form:numbers:2"));
        WebElement number3 = page.findElement(By.id("form:numbers:3"));
        WebElement number4 = page.findElement(By.id("form:numbers:4"));
        WebElement number5 = page.findElement(By.id("form:numbers:5"));
        WebElement number6 = page.findElement(By.id("form:numbers:6"));
        WebElement button = page.findElement(By.id("form:button"));
        item1.click();
        item2.click();
        item3.click();
        number1.click();
        number2.click();
        number3.click();
        number4.click();
        number5.click();
        number6.click();
        button.click();
        assertEquals("[ONE, TWO, THREE][null, 1, 2, 3, 4.5, 6.7, 8.9]", page.findElement(By.id("form:result")).getText());
    }

}
