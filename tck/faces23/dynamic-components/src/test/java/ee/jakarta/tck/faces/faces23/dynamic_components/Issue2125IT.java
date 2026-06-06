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

package ee.jakarta.tck.faces.faces23.dynamic_components;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * Each postback programmatically appends another HtmlOutputText child to a panel group, so the
 * rendered text accumulates one "OUTPUT" per AddComponent click.
 */
public class Issue2125IT extends BaseITNG {

    private static final String OUTPUT = "OUTPUT";

    private static int outputChildCount(WebPage page) {
        // HtmlOutputText with a plain value renders as a bare text node (no wrapping element),
        // so count "OUTPUT" occurrences in the panel group's text rather than matching elements.
        String text = page.findElement(By.id("form:group")).getText();
        int count = 0;
        for (int i = text.indexOf(OUTPUT); i >= 0; i = text.indexOf(OUTPUT, i + OUTPUT.length())) {
            count++;
        }
        return count;
    }

    /**
     * @see jakarta.faces.component.UIComponent#getChildren()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2125
     */
    @Test
    void testAddComponent() throws Exception {
        WebPage page = getPage("issue2125.xhtml");
        assertEquals(0, outputChildCount(page));

        WebElement button = page.findElement(By.id("form:add"));
        page.guardHttp(button::click);
        assertEquals(1, outputChildCount(page));

        button = page.findElement(By.id("form:add"));
        page.guardHttp(button::click);
        assertEquals(2, outputChildCount(page));
    }
}
