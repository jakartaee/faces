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

package ee.jakarta.tck.faces.faces23.facelets;

import static org.junit.jupiter.api.Assertions.assertFalse;

import jakarta.faces.event.PhaseListener;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue2852IT extends BaseITNG {

    /**
     * A phase listener registered via an {@code f:phaseListener} binding must result in exactly
     * one registered listener on the view root, and that must remain true across repeated
     * postbacks rather than accumulating duplicate registrations.
     *
     * @see PhaseListener
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2852
     */
    @Test
    void verifyOnePhaseListener() {
        WebPage page = getPage("issue2852.xhtml");

        for (int i = 0; i < 5; i++) {
            page.guardHttp(page.findElement(By.id("form:submit"))::click);
            assertFalse(page.containsText("ERROR"));
        }
    }
}
