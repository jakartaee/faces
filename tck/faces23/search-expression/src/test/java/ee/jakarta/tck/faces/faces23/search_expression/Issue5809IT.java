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
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * Characterization tests for the agreed, by-design behavior of search expressions
 * used as f:ajax render targets. They lock in the conclusion of
 * <a href="https://github.com/eclipse-ee4j/mojarra/issues/5809">mojarra #5809</a>:
 * resolution scans the whole tree and deliberately does not skip unrendered
 * subtrees, so currently-unrendered targets stay resolvable and the coupling
 * between an ajax source and its targets is not tied to their render state.
 * These guard against traversal/perf refactors silently changing it.
 */
public class Issue5809IT extends BaseITNG {

    /**
     * UC1: a target that is currently {@code rendered="false"} must still be
     * resolved into the source's ajax request, so toggling its visibility later
     * does not require re-rendering the source.
     */
    @Test
    void testUnrenderedTargetStaysResolvable() throws Exception {
        WebPage page = getPage("issue5809uc1.xhtml");

        WebElement button = page.findElement(By.id("form:button"));
        assertTrue(page.getBehaviorScript(button).contains("form:target"),
                "unrendered target must still resolve");
    }

    /**
     * UC2: in a render list mixing a rendered and an unrendered target, both must
     * resolve - the unrendered one must not abort resolution of the rest.
     */
    @Test
    void testMixedRenderedAndUnrenderedTargets() throws Exception {
        WebPage page = getPage("issue5809uc2.xhtml");

        WebElement button = page.findElement(By.id("form:button"));
        String script = page.getBehaviorScript(button);
        assertTrue(script.contains("form:shown"), "rendered target must resolve");
        assertTrue(script.contains("form:hidden"), "unrendered target must resolve");
    }

    /**
     * UC3: {@code @root:@id(...)} resolving a target located past an unrendered
     * subtree must traverse that subtree (by design, no SKIP_UNRENDERED), reaching
     * the model getter of the unrendered table along the way. Pins the exact #5809
     * behavior so it cannot be silently reverted.
     */
    @Test
    void testResolutionTraversesUnrenderedSubtree() throws Exception {
        WebPage page = getPage("issue5809uc3.xhtml");

        WebElement button = page.findElement(By.id("form:button"));
        assertTrue(page.getBehaviorScript(button).contains("form:pnlShown1"),
                "target past the unrendered subtree must resolve");

        String listTouched = page.findElement(By.id("form:listTouched")).getText();
        assertEquals("true", listTouched, "unrendered subtree must be traversed during resolution");
    }

    /**
     * UC4: a target after a rendered, row-stamped {@code h:dataTable} must resolve,
     * i.e. resolution visits through the table's rows and continues to the later
     * sibling. Guards the indexed-traversal path.
     */
    @Test
    void testResolutionTraversesRenderedDataTable() throws Exception {
        WebPage page = getPage("issue5809uc4.xhtml");

        WebElement button = page.findElement(By.id("form:button"));
        assertTrue(page.getBehaviorScript(button).contains("form:after"),
                "target after the table must resolve");

        String firstCell = page.findElement(By.id("form:table:0:cell")).getText();
        assertEquals("one", firstCell, "table rows must have rendered");
    }

    /**
     * UC5: an f:ajax whose source is inside an iterating component must resolve per
     * row - the row-stamped source must not break resolving the shared target.
     */
    @Test
    void testResolutionFromInsideIteratingComponent() throws Exception {
        WebPage page = getPage("issue5809uc5.xhtml");

        WebElement row0 = page.findElement(By.id("form:table:0:in"));
        WebElement row1 = page.findElement(By.id("form:table:1:in"));
        assertTrue(page.getBehaviorScript(row0).contains("form:status"),
                "row 0 must resolve the shared target");
        assertTrue(page.getBehaviorScript(row1).contains("form:status"),
                "row 1 must resolve the shared target");
    }

    /**
     * UC6: the common relative keywords resolve. {@code @form} is passthrough
     * (emitted as-is); {@code @parent} and {@code @previous} resolve server-side to
     * concrete client ids. Complements Spec1238IT, which covers {@code @this @next}.
     */
    @Test
    void testRelativeKeywords() throws Exception {
        WebPage page = getPage("issue5809uc6.xhtml");

        WebElement inForm = page.findElement(By.id("form:inForm"));
        assertTrue(page.getBehaviorScript(inForm).contains("@form"),
                "@form must stay passthrough");

        WebElement inParent = page.findElement(By.id("form:inParent"));
        assertTrue(page.getBehaviorScript(inParent).contains("form:wrap"),
                "@parent must resolve to the enclosing panelGroup");

        WebElement inPrevB = page.findElement(By.id("form:inPrevB"));
        assertTrue(page.getBehaviorScript(inPrevB).contains("form:inPrevA"),
                "@previous must resolve to the preceding sibling");
    }
}
