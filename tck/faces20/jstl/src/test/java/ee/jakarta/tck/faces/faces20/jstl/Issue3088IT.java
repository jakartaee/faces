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
package ee.jakarta.tck.faces.faces20.jstl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * The begin/end/step index arithmetic of {@code c:forEach} must apply to a non-indexed
 * {@link java.util.Set} exactly as it does to an indexed {@link java.util.List}.
 */
class Issue3088IT extends BaseITNG {

    /**
     * {@code begin="1"} over a Set of foo/bar/baz/qux/quux must skip only the first element.
     *
     * @see jakarta.faces.view.facelets.FaceletContext
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3088
     */
    @Test
    void forEachBeginSkipsLeadingSetElement() {
        WebPage page = getPage("foreachtag/issue3088.xhtml");

        assertTrue(page.findElements(By.id("begin_foo")).isEmpty(), "foo is before begin and must be skipped");
        assertEquals("bar", page.findElement(By.id("begin_bar")).getText());
        assertEquals("baz", page.findElement(By.id("begin_baz")).getText());
        assertEquals("qux", page.findElement(By.id("begin_qux")).getText());
        assertEquals("quux", page.findElement(By.id("begin_quux")).getText());
    }

    /**
     * {@code begin="1" end="3" step="2"} over the same Set must yield exactly the elements at index 1
     * and 3, i.e. bar and qux.
     *
     * @see jakarta.faces.view.facelets.FaceletContext
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3088
     */
    @Test
    void forEachBeginEndStepSlicesSet() {
        WebPage page = getPage("foreachtag/issue3088.xhtml");

        assertEquals("bar", page.findElement(By.id("slice_bar")).getText());
        assertEquals("qux", page.findElement(By.id("slice_qux")).getText());

        for (String skipped : new String[] { "foo", "baz", "quux" }) {
            assertTrue(page.findElements(By.id("slice_" + skipped)).isEmpty(),
                    skipped + " is outside the begin/end/step slice and must not render");
        }
    }
}
