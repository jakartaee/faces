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

package ee.jakarta.tck.faces.faces20.el_context_listener;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * An {@code ELContextListener} registered on the {@code Application} via
 * {@code Application.addELContextListener} must be notified when a new {@code ELContext} is created,
 * which happens once per request. The registrar records the notification and the page surfaces it.
 */
public class ELContextListenerNotifiedIT extends BaseITNG {

    /**
     * A registered ELContextListener is notified on ELContext creation during a request.
     *
     * @see jakarta.faces.application.Application#addELContextListener(jakarta.el.ELContextListener)
     * @see jakarta.el.ELContextListener
     */
    @Test
    void testELContextListenerNotified() {
        WebPage page = getPage("elContextListenerNotified.xhtml");
        assertEquals("PASSED", page.findElement(By.id("status")).getText(),
                "Registered ELContextListener must be notified on ELContext creation");
    }
}
