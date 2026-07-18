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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * End-to-end smoke test of the flash across a sequence of redirects. A value stored in
 * {@code flash} is not visible on the request that put it but is on the next one; a value in
 * {@code flash.now} is visible on the current request only; {@code flash.keep} promotes a value to
 * survive one more request; and a {@code FacesMessage} kept via {@code Flash.setKeepMessages}
 * survives exactly the immediately following redirect.
 */
class FlashRedirectIT extends BaseITNG {

    /**
     * @see jakarta.faces.context.Flash
     * @see jakarta.faces.context.Flash#setKeepMessages(boolean)
     */
    @Test
    void testFlash() {
        // A value put in the flash is null on the request that put it.
        WebPage page = getPage("flashRedirect.xhtml");
        assertEquals("", page.findElement(By.id("fooValueId")).getText(),
                "flash.foo must be null on the request that put it");

        // ...but visible on the next request (here a postback to the same view).
        page.guardHttp(page.findElement(By.id("form:reload"))::click);
        assertEquals("fooValue", page.findElement(By.id("fooValueId")).getText(),
                "flash.foo must be visible on the next request");

        // Store a value in the flash, add a kept message, then redirect to page 2.
        page = getPage("flashRedirect.xhtml");
        page.findElement(By.id("form:inputText")).sendKeys("addMessage");
        page.guardHttp(page.findElement(By.id("form:next"))::click);

        assertEquals("fooValue", page.findElement(By.id("flash2FooValueId")).getText(),
                "flash.foo must survive the redirect to page 2");
        assertEquals("barValue", page.findElement(By.id("flash2BarValueId")).getText(),
                "flash.now.bar must be readable on the request that put it");
        assertTrue(page.containsText(FlashRedirectBean.PERSISTENT_MESSAGE),
                "the kept message must survive the redirect to page 2");

        // Reloading page 2 must not re-show the message: it is kept for exactly one request.
        page.guardHttp(page.findElement(By.id("form:reload"))::click);
        assertFalse(page.containsText(FlashRedirectBean.PERSISTENT_MESSAGE),
                "the kept message must not survive a further request");

        // Going back and forward again must not resurrect the message.
        page.guardHttp(page.findElement(By.id("form:back"))::click);
        page.guardHttp(page.findElement(By.id("form:next"))::click);
        assertFalse(page.containsText(FlashRedirectBean.PERSISTENT_MESSAGE),
                "the message must not reappear");

        // Page 3 puts a value in flash.now and promotes it via flash.keep.
        page.guardHttp(page.findElement(By.id("form:next"))::click);
        assertEquals("banzai", page.findElement(By.id("flash3NowValueId")).getText(),
                "flash.now.buckaroo must be readable on the request that put it");

        // Page 4 sees the promoted value through flash (not flash.now).
        page.guardHttp(page.findElement(By.id("form:next"))::click);
        assertEquals("banzai", page.findElement(By.id("flash4BuckarooValueId")).getText(),
                "flash.keep must promote the value to the next request");
    }
}
