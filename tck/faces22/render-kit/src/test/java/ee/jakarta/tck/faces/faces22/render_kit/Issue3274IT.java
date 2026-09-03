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

package ee.jakarta.tck.faces.faces22.render_kit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.faces.component.html.HtmlPanelGroup;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue3274IT extends BaseITNG {

    private static final String STYLE = "width: 100px; height: 60px";

    /**
     * The Group renderer writes styleClass out as the class attribute and style as the style attribute, each exactly once, also when both are given on a block
     * layout.
     *
     * @see HtmlPanelGroup#getStyle()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3274
     */
    @Test
    void styleAndStyleClassAreEachRenderedOnce() {
        WebPage page = getPage("issue3274.xhtml");

        assertEquals(STYLE, page.findElement(By.id("group")).getDomAttribute("style"), "style attribute");
        assertEquals("foo", page.findElement(By.id("group")).getDomAttribute("class"), "class attribute");
        assertEquals(1, countOccurrences(page.getSource(), "style="), "occurrences of style=");
    }

    private static int countOccurrences(String source, String token) {
        int count = 0;

        for (int index = source.indexOf(token); index != -1; index = source.indexOf(token, index + token.length())) {
            count++;
        }

        return count;
    }

}
