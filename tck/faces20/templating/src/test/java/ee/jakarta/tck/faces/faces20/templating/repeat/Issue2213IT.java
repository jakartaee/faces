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
package ee.jakarta.tck.faces.faces20.templating.repeat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * The {@code offset} attribute of {@code ui:repeat} must behave identically whether it is supplied as
 * a literal or as a ValueExpression.
 */
class Issue2213IT extends BaseITNG {

    /**
     * A deferred-value offset must skip the same leading rows as the equivalent literal offset. The
     * bean offset is 3, so of Red/Green/Blue/Violet/Pink only rows 3 and 4 may render.
     *
     * @see jakarta.faces.component.UIData#setFirst(int)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2213
     */
    @Test
    void repeatOffsetAsValueExpression() {
        WebPage page = getPage("repeat/issue2213.xhtml");

        assertTrue(page.findElements(By.id("deferred:2:label")).isEmpty(),
                "Row 2 is before the offset and must not render");
        assertEquals("Color: Violet", page.findElement(By.id("deferred:3:label")).getText());
        assertEquals("Color: Pink", page.findElement(By.id("deferred:4:label")).getText());
    }

    /**
     * The deferred-value offset must yield exactly the same slice as the literal offset on the same
     * list, i.e. both must take the same code path.
     *
     * @see jakarta.faces.component.UIData#setFirst(int)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2213
     */
    @Test
    void repeatOffsetAsValueExpressionMatchesLiteralOffset() {
        WebPage page = getPage("repeat/issue2213.xhtml");

        for (int row = 3; row <= 4; row++) {
            assertEquals(page.findElement(By.id("literal:" + row + ":label")).getText(),
                    page.findElement(By.id("deferred:" + row + ":label")).getText(),
                    "Row " + row + " must be identical for a literal and a deferred offset");
        }
    }
}
