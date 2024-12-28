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

import jakarta.faces.component.html.HtmlInputText;

import org.junit.jupiter.api.Test;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;

public class Spec1560IT extends ITBase {

  /**
   * @see HtmlInputText#getType()
     * @see https://github.com/jakartaee/faces/issues/1560
   */
  @Test
  void test() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "spec1560IT.xhtml");

        HtmlInput noType = (HtmlInput) page.getElementById("form:noType");
        assertEquals("text", noType.getTypeAttribute(), "Default type is 'text'");

        HtmlInput typeEmail = (HtmlInput) page.getElementById("form:typeEmail");
        assertEquals("email", typeEmail.getTypeAttribute(), "Type set via actual attribute is 'email'");

        HtmlInput passthroughTypeEmail = (HtmlInput) page.getElementById("form:passthroughTypeEmail");
        assertEquals("email", passthroughTypeEmail.getTypeAttribute(), "Type set via passthrough attribute is 'email'");

        HtmlInput typeTelAndPassthroughTypeEmail = (HtmlInput) page.getElementById("form:typeTelAndPassthroughTypeEmail");
        assertEquals("email", typeTelAndPassthroughTypeEmail.getTypeAttribute(), "Type overridden via passthrough attribute is 'email'");

        HtmlInput typeButton = (HtmlInput) page.getElementById("form:typeButton");
        assertEquals("button", typeButton.getTypeAttribute(), "Type set via actual attribute is 'button'");

        HtmlElement messageForTypeEmail = (HtmlElement) page.getElementById("form:messageForTypeEmail");
        HtmlElement messageForTypeButton = (HtmlElement) page.getElementById("form:messageForTypeButton");
        HtmlElement messages = (HtmlElement) page.getElementById("messages");

        String emailMessage = messageForTypeEmail.asNormalizedText();
        String buttonMessage = messageForTypeButton.asNormalizedText();
        String globalMessage = messages.asNormalizedText();

        if ("Development".equals(System.getProperty("webapp.projectStage"))) {
            assertEquals("", emailMessage, "There is no faces message for type 'email'");
            assertNotEquals("", buttonMessage, "There is a faces message for type 'button'");
            assertEquals(globalMessage, buttonMessage, "The message for type 'button' is the only message set");
        }
        else {
            assertEquals("", emailMessage, "There is no faces message for type 'email'");
            assertEquals("", buttonMessage, "There is no faces message for type 'button'");
            assertEquals("", globalMessage, "There is no faces message set at all");
        }
    }

}
