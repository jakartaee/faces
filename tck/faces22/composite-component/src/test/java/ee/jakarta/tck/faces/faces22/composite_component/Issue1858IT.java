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

package ee.jakarta.tck.faces.faces22.composite_component;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.component.html.HtmlOutcomeTargetLink;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue1858IT extends BaseITNG {

    private static final String MARKER = "markOneClick()";

    /**
     * An onclick supplied as a composite component attribute and forwarded to a nested h:link is wired to the anchor, and its handler appears in the response
     * exactly once. Whether the handler is rendered as an inline attribute or wired from a script block is left to the implementation by jakartaee/faces#2167.
     *
     * @see HtmlOutcomeTargetLink#getOnclick()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/1858
     */
    @Test
    void onclickForwardedThroughCompositeIsRenderedOnce() {
        WebPage page = getPage("issue1858.xhtml");

        assertTrue(
            page.isAttributeWired(page.findElement(By.id("composite:link")), "onclick"),
            "The forwarded onclick must be wired to the anchor."
        );
        assertEquals(
            1, countOccurrences(page.getSource(), MARKER),
            "The forwarded onclick handler must appear exactly once."
        );
    }

    private static int countOccurrences(String source, String token) {
        int count = 0;

        for (int index = source.indexOf(token); index != -1; index = source.indexOf(token, index + token.length())) {
            count++;
        }

        return count;
    }

}
