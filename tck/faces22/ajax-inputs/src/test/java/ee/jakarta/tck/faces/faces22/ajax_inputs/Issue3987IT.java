/*
 * Copyright (c) 2026 Contributors to Eclipse Foundation.
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

package ee.jakarta.tck.faces.faces22.ajax_inputs;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.component.html.HtmlInputFile;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * Verifies that an ajax multipart form (enctype=multipart/form-data) containing an h:inputFile and a command
 * button with f:ajax execute=@all submits without a server exception and navigates to the next page.
 */
class Issue3987IT extends BaseITNG {

    /**
     * Submits the multipart ajax form with execute=@all and asserts that no server exception is reported back
     * into the text field (via the registered ajax onError handler) and that navigation to the next page occurs.
     *
     * @see HtmlInputFile
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3987
     */
    @Test
    void testFormWithInputFileAndAjaxButton() throws Exception {
        WebPage page = getPage("issue3987.xhtml");

        WebElement textField = page.findElement(By.id("test"));
        textField.sendKeys("CB");

        WebElement button = page.findElement(By.id("mybutton"));
        page.guardAjax(button::click);

        assertFalse(page.getSource().contains("Exception"), "Server should not throw an exception");
        assertTrue(page.containsText("This is the next page"), "Should navigate to the next page");
    }
}
