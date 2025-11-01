/*
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

package ee.jakarta.tck.faces.test.servlet30.ajax_selenium;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import jakarta.faces.component.behavior.AjaxBehavior;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class Issue2439IT extends BaseITNG {

    /**
     * @see AjaxBehavior#isDisabled()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2443
     */
    @Test
    void disabledBehaviors() throws Exception {
        WebPage page = getPage("disabledBehaviors.xhtml");

        WebElement output = page.findElement(By.id("output"));
        WebElement input1 = page.findElement(By.id("form1:input1"));

        try {
            page.guardAjax(() -> input1.sendKeys("1", Keys.TAB));
            fail("There should be no ajax behavior");
        }
        catch (TimeoutException e) {
            assertEquals("", output.getText());
        }

        WebElement input2 = page.findElement(By.id("form1:input2"));
        page.guardAjax(() -> input2.sendKeys("2", Keys.TAB));
        assertEquals("form1:input2", output.getText());

        WebElement input3 = page.findElement(By.id("form1:input3"));
        input3.sendKeys("3", Keys.TAB);
        assertEquals("form1:input2 Hello, form1:input3", output.getText());

        WebElement input4 = page.findElement(By.id("form1:input4"));
        page.guardAjax(() -> input4.sendKeys("4", Keys.TAB));
        assertEquals("form1:input2 Hello, form1:input3 form1:input4 form1:input4", output.getText());

        WebElement input5 = page.findElement(By.id("form1:input5"));
        page.guardAjax(() -> input5.sendKeys("5", Keys.TAB));
        assertEquals("form1:input2 Hello, form1:input3 form1:input4 form1:input4 Hello, form1:input5 form1:input5", output.getText());
    }
}
