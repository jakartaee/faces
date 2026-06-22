/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
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

package ee.jakarta.tck.faces.faces23.search_expression;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

public class Issue5809IT extends BaseITNG {

    /**
     * Resolving an f:ajax render expression such as {@code @root:@id(...)} must not
     * traverse unrendered components. An unrendered subtree has no client-side
     * markup and can never be a useful ajax target, so the resolution visit must
     * skip it - mirroring the partial render visit, which already does so.
     *
     * @see https://github.com/eclipse-ee4j/mojarra/issues/5809
     */
    @Test
    void test() throws Exception {
        WebPage page = getPage("issue5809.xhtml");

        // The rendered target must still resolve to its client id.
        WebElement button = page.findElement(By.id("form:button"));
        assertTrue(page.getBehaviorScript(button).contains("form:target"));

        // The unrendered h:dataTable must not have been traversed, so its model
        // getter must not have been invoked during the render expression resolution.
        WebElement listTouched = page.findElement(By.id("form:listTouched"));
        assertEquals("false", listTouched.getText());
    }

    /**
     * Resolving a render target located past an unrendered subtree must skip that
     * subtree, and doing so must not break revealing a component only after the ajax
     * call - the "render after ajax" case raised against this change.
     * <p>
     * The component that becomes visible after the action is wrapped in an
     * always-rendered {@code wrapper}: that wrapper supplies the client-side markup
     * the partial response replaces, which is what makes render-after-ajax work at
     * all (a partial update for an id with no element throws client-side). The
     * wrapper is rendered, so {@code SKIP_UNRENDERED} never skips it; the unrendered
     * table that precedes it is skipped rather than row-iterated.
     *
     * @see https://github.com/eclipse-ee4j/mojarra/issues/5809
     */
    @Test
    void testRenderAfterAjaxDoesNotTraverseUnrenderedSubtree() throws Exception {
        WebPage page = getPage("issue5809conditional.xhtml");

        // Resolving @id(wrapper) must skip the unrendered table that precedes it
        // instead of iterating its rows and invoking the model getter.
        WebElement button = page.findElement(By.id("form:button"));
        assertTrue(page.getBehaviorScript(button).contains("form:wrapper"));
        assertEquals("false", page.findElement(By.id("form:listTouched")).getText());

        // The revealed content is not rendered yet.
        assertFalse(page.containsText("updated"));

        // After the ajax call the component that becomes rendered shows up through its
        // wrapper - skipping the unrendered subtree does not break render-after-ajax.
        page.guardAjax(() -> page.findElement(By.id("form:button")).click());
        assertTrue(page.containsText("updated"));
    }
}
