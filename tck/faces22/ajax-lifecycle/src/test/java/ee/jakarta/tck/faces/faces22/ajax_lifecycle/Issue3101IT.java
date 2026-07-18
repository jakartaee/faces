/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
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

package ee.jakarta.tck.faces.faces22.ajax_lifecycle;

import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.component.behavior.AjaxBehavior;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue3101IT extends BaseITNG {

    /**
     * The session is expired by shortening its max inactive interval, so the postback must be
     * preceded by more idle time than that interval; the container only detects the expiry upon the
     * next access. Without this wait the test passes only as long as the intervening ajax round trip
     * happens to outlast the interval.
     */
    private static final long IDLE_TIME_EXCEEDING_SESSION_INTERVAL = 2000;

  /**
   * @see AjaxBehavior
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3101
   */
  @Test
  void viewExpiredExceptionRenderedAfterServerStateExpiry() throws Exception {
        WebPage page = getPage("issue3101.xhtml");

        if (page.containsText("State Saving Method: server")) {
            WebElement expireButton = page.findElement(By.id("form:expireSessionSoon"));
            page.guardAjax(expireButton::click);

            Thread.sleep(IDLE_TIME_EXCEEDING_SESSION_INTERVAL);

            WebElement submitButton = page.findElement(By.id("form:submit"));
            page.guardAjax(submitButton::click);

            assertTrue(page.containsText("jakarta.faces.application.ViewExpiredException"));
        }
    }
}
