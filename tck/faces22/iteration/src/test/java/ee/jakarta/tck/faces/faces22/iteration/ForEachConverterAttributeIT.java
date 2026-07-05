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

package ee.jakarta.tck.faces.faces22.iteration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * An f:convertNumber inside a c:forEach whose {@code pattern} is bound to a per-row value must format each row with its
 * own pattern - i.e. the tag converter is configured per cell, never shared across cells. Every row holds the same
 * value ({@code 1234}) with a different pattern, so the three renderings must differ. Also asserted after a postback, so
 * the per-cell converter configuration survives the restore/re-apply. Guards converter-instance reuse/caching from
 * collapsing per-row attributes.
 */
class ForEachConverterAttributeIT extends BaseITNG {

    /**
     * @see jakarta.faces.convert.NumberConverter#setPattern(String)
     */
    @Test
    void perRowConverterPatternIsApplied() {
        WebPage page = getPage("foreach-converter-attribute.xhtml");

        // Same value 1234, three patterns -> three distinct renderings, each from its own row's pattern.
        assertPatterns(page);

        // The per-cell converter configuration survives a postback (restore + re-apply of the unrolled body).
        page.guardHttp(() -> page.findElement(By.id("form:submit")).click());
        assertPatterns(page);
    }

    private void assertPatterns(WebPage page) {
        assertEquals("1,234", page.findElement(By.id("form:out_0")).getText(), "row 0 pattern #,##0");
        assertEquals("0001234", page.findElement(By.id("form:out_1")).getText(), "row 1 pattern 0000000");
        assertEquals("1234", page.findElement(By.id("form:out_2")).getText(), "row 2 pattern #");
    }
}
