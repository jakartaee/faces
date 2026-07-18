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

package ee.jakarta.tck.faces.faces20.view_expired_exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * A {@code ViewExpiredException} raised by a plain HTTP postback must be dispatched to the
 * {@code error-page} which web.xml declares for it, rather than surfacing as a raw stack trace.
 * The postback deliberately is not an ajax one: Faces handles ajax errors itself by returning an
 * {@code error} partial response, which bypasses the container's error-page entirely.
 */
class ViewExpiredExceptionErrorPageIT extends BaseITNG {

    /**
     * The session is expired by shortening its max inactive interval, so the postback must be
     * preceded by more idle time than that interval; the container only detects the expiry upon the
     * next access. Waiting longer than strictly necessary cannot break this: idle time exceeding the
     * interval is exactly the condition being set up.
     */
    private static final long IDLE_TIME_EXCEEDING_SESSION_INTERVAL = 2000;

    /**
     * @see jakarta.faces.application.ViewExpiredException
     */
    @Test
    void viewExpiredExceptionRoutedToErrorPage() throws Exception {
        WebPage page = getPage("viewExpiredExceptionErrorPage.xhtml");

        // Client side state never expires, so there is nothing to route.
        if ("server".equals(page.findElement(By.id("stateSavingMethod")).getText())) {
            page.guardHttp(page.findElement(By.id("form:expireSession"))::click);
            Thread.sleep(IDLE_TIME_EXCEEDING_SESSION_INTERVAL);
            page.guardHttp(page.findElement(By.id("form:submit"))::click);

            assertEquals("Error page invoked", page.findElement(By.id("errorPage")).getText(), "error page invoked");
        }
    }
}
