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

import jakarta.faces.context.Flash;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue3625IT extends BaseITNG {

    /**
     * A flash value set on a transient view must remain readable on the next transient view even
     * when that view invalidates the session, without throwing a NullPointerException.
     *
     * @see Flash
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3625
     */
    @Test
    void flashSurvivesSessionInvalidation() {
        getPage("issue3625-setvar.xhtml");
        WebPage page = getPage("issue3625-getvar.xhtml");
        assertEquals("hello", page.findElement(By.id("result")).getText());
    }
}
