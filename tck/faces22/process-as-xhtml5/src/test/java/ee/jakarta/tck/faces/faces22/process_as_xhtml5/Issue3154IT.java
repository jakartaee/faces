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

package ee.jakarta.tck.faces.faces22.process_as_xhtml5;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;

/**
 * When a Facelet is processed as HTML5, request/view-param values echoed into the page must be XSS-escaped
 * across h:outputText, h:inputHidden, raw pass-through markup attributes and inline script blocks.
 */
class Issue3154IT extends BaseITNG {

    /**
     * Verifies that param values written into a page processed as HTML5 are properly escaped: h:outputText
     * escapes element body, h:inputHidden and raw pass-through input render the value as an attribute, an
     * h:outputText nested inside a script block escapes its content, and a value containing markup
     * (&lt;script&gt;...) is HTML-entity-escaped rather than emitted as live markup.
     *
     * @see jakarta.faces.component.html.HtmlOutputText
     * @see jakarta.faces.component.html.HtmlInputHidden
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3154
     */
    @Test
    void testEscaping() {
        String pageMarkup = getResponseBody("issue3154.xhtml"
                + "?id4=%3Cscript%3Ealert%28%27hi%27%29;%3C/script%3E"
                + "&id=myId"
                + "&id2=%22myId2%22"
                + "&id3=myId3");

        // Case 1: h:outputText escapes the element body.
        assertTrue(pageMarkup.contains("<span id=\"id\">myId</span>"), pageMarkup);

        // Case 2: h:inputHidden renders the value as an attribute.
        assertTrue(pageMarkup.contains("<input id=\"id\" type=\"hidden\" name=\"id\" value=\"myId\" />"), pageMarkup);

        // Case 3: raw pass-through input renders the value as authored.
        assertTrue(pageMarkup.contains("<input type=\"hidden\" name=\"id\" id=\"id\" value=\"myId\" />"), pageMarkup);

        // Plain EL text echoed before the script block.
        assertTrue(pageMarkup.contains("beforeScriptBlock: myId"), pageMarkup);

        // Case 4: EL substituted verbatim into the script block.
        assertTrue(pageMarkup.contains("var paramId = \"myId2\";"), pageMarkup);

        // Case 5: h:outputText nested in a script block escapes its content.
        assertTrue(pageMarkup.contains("var paramIdd = \"myId3\";"), pageMarkup);

        // Case 6: a value containing markup is HTML-entity-escaped, not emitted as live markup.
        assertTrue(pageMarkup.contains("&lt;script&gt;alert('hi');&lt;/script&gt;"), pageMarkup);
    }
}
