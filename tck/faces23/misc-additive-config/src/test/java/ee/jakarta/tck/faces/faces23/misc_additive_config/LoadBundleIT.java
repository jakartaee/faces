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

package ee.jakarta.tck.faces.faces23.misc_additive_config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * An f:loadBundle whose result depends on per-request state must be re-resolved on a postback that changes
 * that state, even when the view structure is otherwise static. This exercises both the view-locale and the
 * basename inputs of f:loadBundle across a full postback.
 *
 * @see jakarta.faces.view.facelets.FaceletContext
 */
class LoadBundleIT extends BaseITNG {

    /**
     * The bundle is loaded under the active view locale; switching f:view locale via a postback must re-resolve
     * it to the new locale's variant.
     */
    @Test
    void testLocaleSwitchReResolvesBundle() {
        WebPage page = getPage("LoadBundleLocale.xhtml");
        assertEquals("Hello", greeting(page), "default locale en resolves greeting to Messages_en");

        page.guardHttp(page.findElement(By.id("form:fr"))::click);
        assertEquals("Bonjour", greeting(page), "locale fr resolves greeting to Messages_fr");

        page.guardHttp(page.findElement(By.id("form:au"))::click);
        assertEquals("Gday", greeting(page), "locale au resolves greeting to Messages_au");

        page.guardHttp(page.findElement(By.id("form:en"))::click);
        assertEquals("Hello", greeting(page), "locale en resolves greeting to Messages_en after postback");
    }

    /**
     * Switching a non-literal f:loadBundle basename via a postback must re-resolve it to the new bundle.
     */
    @Test
    void testBasenameSwitchReResolvesBundle() {
        WebPage page = getPage("LoadBundleBasename.xhtml");
        assertEquals("Alpha", greeting(page), "default basename GreetingA");

        page.guardHttp(page.findElement(By.id("form:b"))::click);
        assertEquals("Bravo", greeting(page), "basename GreetingB after postback");

        page.guardHttp(page.findElement(By.id("form:a"))::click);
        assertEquals("Alpha", greeting(page), "basename GreetingA after postback");
    }

    private static String greeting(WebPage page) {
        return page.findElement(By.id("greeting")).getText();
    }
}
