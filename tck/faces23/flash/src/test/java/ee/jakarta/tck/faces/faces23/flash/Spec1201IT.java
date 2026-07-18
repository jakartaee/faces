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

import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * The cookie by which the flash bridges requests must carry the {@code HttpOnly} flag: the flash is
 * server-side state and its correlation cookie is never meant to be read by script.
 * <p>
 * The cookie's name is implementation-specific, so it is located by loading an otherwise identical
 * page that does not touch the flash first and taking the cookie that only the flash-writing page
 * adds.
 */
class Spec1201IT extends BaseITNG {

    /**
     * Writing to the flash sets a cookie, that cookie is HttpOnly, and it does bridge the flash value
     * onto the next request.
     *
     * @see jakarta.faces.context.Flash
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2915
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2130
     */
    @Test
    void testFlashCookieIsHttpOnly() {
        getPage("spec1201-baseline.xhtml");
        Set<String> containerCookies = cookieNames();

        WebPage page = getPage("spec1201.xhtml");
        Set<Cookie> flashCookies = getWebDriver().manage().getCookies().stream()
                .filter(cookie -> !containerCookies.contains(cookie.getName()))
                .collect(toSet());

        assertFalse(flashCookies.isEmpty(), "Writing to the flash must set a cookie");
        flashCookies.forEach(cookie -> assertTrue(cookie.isHttpOnly(),
                "Flash cookie '" + cookie.getName() + "' must carry HttpOnly"));

        page.guardHttp(page.findElement(By.id("form:next"))::click);
        assertEquals("foo = bar", page.findElement(By.id("result")).getText(),
                "The located cookie must indeed be the one bridging the flash value");
    }

    private Set<String> cookieNames() {
        return getWebDriver().manage().getCookies().stream().map(Cookie::getName).collect(toSet());
    }
}
