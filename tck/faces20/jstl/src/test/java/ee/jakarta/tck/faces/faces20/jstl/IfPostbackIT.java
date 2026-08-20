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
 * The subtree a {@code c:if} builds follows its test on every postback: it stays in place with its state intact for
 * as long as the test holds, and appears and disappears as the test changes.
 */
class IfPostbackIT extends BaseITNG {

    /**
     * A postback that leaves the test true must leave the subtree it built in place, with each component keeping its
     * client id and the value submitted to it.
     *
     * @see jakarta.faces.component.UIComponent#getClientId()
     */
    @Test
    void unchangedTestRetainsSubtreeAndItsState() {
        WebPage page = shown();
        int postbacks = postbacks(page);

        page.findElement(By.id("form:input")).sendKeys("typed");
        page.guardHttp(page.findElement(By.id("form:submit"))::click);

        assertEquals(postbacks + 1, postbacks(page), "the postback executed");
        assertEquals("conditional", page.findElement(By.id("form:conditional")).getText(), "the subtree survived");
        assertEquals("typed", page.findElement(By.id("form:input")).getAttribute("value"), "the submitted value survived");
    }

    /**
     * A postback whose action makes the test false must remove the subtree, and one that makes it true again must
     * build it back exactly once.
     */
    @Test
    void changedTestRebuildsSubtree() {
        WebPage page = shown();

        page.guardHttp(page.findElement(By.id("form:hide"))::click);
        assertTrue(page.findElements(By.id("form:conditional")).isEmpty(), "the subtree is gone");
        assertTrue(page.findElements(By.id("form:input")).isEmpty(), "including its input");

        page.guardHttp(page.findElement(By.id("form:show"))::click);
        assertEquals("conditional", page.findElement(By.id("form:conditional")).getText(), "the subtree is back");
        assertEquals(1, page.findElements(By.id("form:input")).size(), "including its input, exactly once");
    }

    /**
     * The page under a true test, reached by an action rather than by the initial value, so the outcome does not
     * depend on what an earlier test left in the session.
     */
    private WebPage shown() {
        WebPage page = getPage("iftag/if-postback.xhtml");
        page.guardHttp(page.findElement(By.id("form:show"))::click);
        assertEquals("conditional", page.findElement(By.id("form:conditional")).getText(), "the subtree is built");
        return page;
    }

    private static int postbacks(WebPage page) {
        return Integer.parseInt(page.findElement(By.id("form:count")).getText().replace("postbacks=", ""));
    }
}
