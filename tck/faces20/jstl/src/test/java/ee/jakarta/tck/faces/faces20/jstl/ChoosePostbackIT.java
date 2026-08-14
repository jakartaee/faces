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
 * The branch a {@code c:choose} builds follows its tests on every postback: it stays in place with its state intact
 * for as long as the same {@code c:when} matches, and is replaced by the branch that matches once one does not,
 * including the {@code c:otherwise} when none matches.
 */
class ChoosePostbackIT extends BaseITNG {

    /**
     * A postback that leaves the matching {@code c:when} matching must leave the branch it built in place, with each
     * component keeping its client id and the value submitted to it.
     *
     * @see jakarta.faces.component.UIComponent#getClientId()
     */
    @Test
    void unchangedTestRetainsBranchAndItsState() {
        WebPage page = alpha();
        int postbacks = postbacks(page);

        page.findElement(By.id("form:alphaInput")).sendKeys("typed");
        page.guardHttp(page.findElement(By.id("form:submit"))::click);

        assertEquals(postbacks + 1, postbacks(page), "the postback executed");
        assertEquals("alpha", page.findElement(By.id("form:alpha")).getText(), "the branch survived");
        assertEquals("typed", page.findElement(By.id("form:alphaInput")).getAttribute("value"), "the submitted value survived");
    }

    /**
     * A postback whose action makes another {@code c:when} match must build that branch instead, and one whose
     * action leaves no {@code c:when} matching must build the {@code c:otherwise}.
     */
    @Test
    void changedTestSwitchesBranch() {
        WebPage page = alpha();

        page.guardHttp(page.findElement(By.id("form:bravoButton"))::click);
        assertEquals("bravo", page.findElement(By.id("form:bravo")).getText(), "the second when matches");
        assertTrue(page.findElements(By.id("form:alpha")).isEmpty(), "the first branch is gone");

        page.guardHttp(page.findElement(By.id("form:zuluButton"))::click);
        assertEquals("otherwise", page.findElement(By.id("form:otherwise")).getText(), "no when matches");
        assertTrue(page.findElements(By.id("form:bravo")).isEmpty(), "the second branch is gone");

        page.guardHttp(page.findElement(By.id("form:alphaButton"))::click);
        assertEquals("alpha", page.findElement(By.id("form:alpha")).getText(), "the first when matches again");
        assertEquals(1, page.findElements(By.id("form:alphaInput")).size(), "its input is back, exactly once");
    }

    /**
     * The page under the first matching {@code c:when}, reached by an action rather than by the initial value, so the
     * outcome does not depend on what an earlier test left in the session.
     */
    private WebPage alpha() {
        WebPage page = getPage("cwo/choose-postback.xhtml");
        page.guardHttp(page.findElement(By.id("form:alphaButton"))::click);
        assertEquals("alpha", page.findElement(By.id("form:alpha")).getText(), "the first branch is built");
        return page;
    }

    private static int postbacks(WebPage page) {
        return Integer.parseInt(page.findElement(By.id("form:count")).getText().replace("postbacks=", ""));
    }
}
