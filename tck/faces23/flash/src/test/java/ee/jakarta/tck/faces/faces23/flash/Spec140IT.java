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

package ee.jakarta.tck.faces.faces23.flash;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * A value put in {@code flash.now} is visible for the remainder of the current request only: it is
 * readable through {@code flash.now} but not through {@code flash} (which exposes the previous
 * request's values), and it does not survive a redirect. Promoting it with {@code flash.keep} makes
 * it survive exactly that one redirect.
 */
class Spec140IT extends BaseITNG {

    private static final String EXPECTED_VALUE = "banzai";

    /**
     * A value put in {@code flash.now} renders through {@code flash.now} on the same request, does not
     * leak into the next request's map, and is gone after the redirect.
     *
     * @see jakarta.faces.context.Flash#putNow(String, Object)
     * @see https://github.com/jakartaee/faces/issues/140
     */
    @Test
    void testFlashNowIsRequestScopedOnly() {
        WebPage page = getPage("spec140.xhtml");
        assertEquals(EXPECTED_VALUE, page.findElement(By.id("nowValue")).getText(),
                "flash.now value must be readable on the request that put it");
        assertEquals("", page.findElement(By.id("nextValue")).getText(),
                "flash.now value must not populate the next request's flash map");

        page.guardHttp(page.findElement(By.id("form:next"))::click);
        assertEquals("", page.findElement(By.id("result")).getText(),
                "flash.now value must not survive the redirect");
    }

    /**
     * A {@code flash.now} value promoted with {@code flash.keep} does survive the redirect.
     *
     * @see jakarta.faces.context.Flash#keep(String)
     * @see https://github.com/jakartaee/faces/issues/140
     */
    @Test
    void testFlashNowPromotedByKeep() {
        WebPage page = getPage("spec140-keep.xhtml");
        assertEquals(EXPECTED_VALUE, page.findElement(By.id("nowValue")).getText(),
                "flash.now value must be readable on the request that put it");

        page.guardHttp(page.findElement(By.id("form:next"))::click);
        assertEquals(EXPECTED_VALUE, page.findElement(By.id("result")).getText(),
                "flash.keep must promote the flash.now value to the next request");
    }
}
