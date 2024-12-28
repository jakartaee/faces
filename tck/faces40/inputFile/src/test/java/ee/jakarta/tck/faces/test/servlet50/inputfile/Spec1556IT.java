/*
 * Copyright (c) 2021 Contributors to Eclipse Foundation.
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

package ee.jakarta.tck.faces.test.servlet50.inputfile;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

public class Spec1556IT extends BaseITNG {

    /**
     * @see WebElementFile#getAccept()
     * @see https://github.com/jakartaee/faces/issues/1556
     */
    @Test
    void renderingOfAcceptAttribute() throws Exception {
        WebPage page = getPage("spec1556IT.xhtml");

        WebElement inputFileWithoutAccept = page.findElement(By.id("form:inputFileWithoutAccept"));
        assertEquals("", inputFileWithoutAccept.getDomAttribute("accept"), "Unspecified 'accept' attribute on h:inputFile is NOT rendered");

        WebElement inputFileWithAccept = page.findElement(By.id("form:inputFileWithAccept"));
        assertEquals("image/*", inputFileWithAccept.getDomAttribute("accept"), "Specified 'accept' attribute on h:inputFile is rendered");

        // It's for Mojarra also explicitly tested on h:inputText because they share the same renderer.
        WebElement inputTextWithoutAccept = page.findElement(By.id("form:inputTextWithoutAccept"));
        assertEquals("", inputTextWithoutAccept.getDomAttribute("accept"), "Unspecified 'accept' attribute on h:inputText is NOT rendered");

        WebElement inputTextWithAccept = page.findElement(By.id("form:inputTextWithAccept"));
        assertEquals("", inputTextWithAccept.getDomAttribute("accept"), "Specified 'accept' attribute on h:inputText is NOT rendered");

        // NOTE: HtmlUnit doesn't support filtering files by accept attribute. So the upload part is not tested to keep it simple (it's nonetheless already
        // tested in Spec1555IT).
    }

}
