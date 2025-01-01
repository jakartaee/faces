/*
 * Copyright (c) 2021 Contributors to the Eclipse Foundation.
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

package ee.jakarta.tck.faces.test.servlet50.inputtext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

public class Spec1560IT extends BaseITNG {

    /**
     * @see WebElement#getType()
     * @see https://github.com/jakartaee/faces/issues/1560
     */
    @Test
    void test() throws Exception {
        WebPage page = getPage("spec1560IT.xhtml");

        WebElement noType = page.findElement(By.id("form:noType"));
        assertEquals("text", noType.getDomAttribute("type"), "Default type is 'text'");

        WebElement typeEmail = page.findElement(By.id("form:typeEmail"));
        assertEquals("email", typeEmail.getDomAttribute("type"), "Type set via actual attribute is 'email'");

        WebElement passthroughTypeEmail = page.findElement(By.id("form:passthroughTypeEmail"));
        assertEquals("email", passthroughTypeEmail.getDomAttribute("type"), "Type set via passthrough attribute is 'email'");

        WebElement typeTelAndPassthroughTypeEmail = page.findElement(By.id("form:typeTelAndPassthroughTypeEmail"));
        assertEquals("email", typeTelAndPassthroughTypeEmail.getDomAttribute("type"), "Type overridden via passthrough attribute is 'email'");

        WebElement typeButton = page.findElement(By.id("form:typeButton"));
        assertEquals("button", typeButton.getDomAttribute("type"), "Type set via actual attribute is 'button'");

        WebElement messageForTypeEmail = page.findElement(By.id("form:messageForTypeEmail"));
        WebElement messageForTypeButton = page.findElement(By.id("form:messageForTypeButton"));
        WebElement messages = page.findElement(By.id("messages"));

        String emailMessage = messageForTypeEmail.getText();
        String buttonMessage = messageForTypeButton.getText();
        String globalMessage = messages.getText();

        if ("Development".equals(System.getProperty("webapp.projectStage"))) {
            assertEquals("", emailMessage, "There is no faces message for type 'email'");
            assertNotEquals("", buttonMessage, "There is a faces message for type 'button'");
            assertEquals(globalMessage, buttonMessage, "The message for type 'button' is the only message set");
        } else {
            assertEquals("", emailMessage, "There is no faces message for type 'email'");
            assertEquals("", buttonMessage, "There is no faces message for type 'button'");
            assertEquals("", globalMessage, "There is no faces message set at all");
        }
    }

}
