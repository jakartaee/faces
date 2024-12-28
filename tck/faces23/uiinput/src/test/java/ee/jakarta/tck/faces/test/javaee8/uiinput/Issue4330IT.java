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

import static java.lang.Boolean.parseBoolean;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.component.UISelectItem;
import jakarta.faces.component.html.HtmlSelectManyCheckbox;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class Issue4330IT extends BaseITNG {

    /**
     * @see HtmlSelectManyCheckbox
     * @see UISelectItem#isItemDisabled()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4330
     */
    @Test
    void issue4330() throws Exception {
        WebPage page;
        WebElement disabledRadio;
        WebElement enabledCheckbox;
        WebElement disabledCheckbox;
        WebElement hack;
        WebElement submit;

        page = getPage("issue4330.xhtml");
        assertTrue(page.findElement(By.id("form:result")).getText().isEmpty());

        disabledRadio = page.findElement(By.id("form:one:1"));
        enabledCheckbox = page.findElement(By.id("form:many:0"));
        disabledCheckbox = page.findElement(By.id("form:many:1"));
        hack = page.findElement(By.id("form:hack"));
        submit = page.findElement(By.id("form:submit"));

        assertTrue(parseBoolean(disabledRadio.getDomAttribute("disabled")));
        assertTrue(parseBoolean(disabledCheckbox.getDomAttribute("disabled")));

        hack.click();

        assertFalse(parseBoolean(disabledRadio.getDomAttribute("disabled")));
        assertFalse(parseBoolean(disabledCheckbox.getDomAttribute("disabled")));

        disabledRadio.click();
        enabledCheckbox.click();
        disabledCheckbox.click();

        submit.click();
        assertEquals("[enabled]", page.findElement(By.id("form:result")).getText()); // Thus not "disabled[enabled, disabled]"
    }

}