/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
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
package ee.jakarta.tck.faces.faces23.system_event;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue2723IT extends BaseITNG {

    /**
     * A preRenderView listener declared in the metadata facet must be invoked
     * exactly once per render, not registered multiple times.
     *
     * @see jakarta.faces.event.PreRenderViewEvent
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2723
     */
    @Test
    void preRenderViewListenerInvokedOnlyOnce() {
        WebPage page = getPage("issue2723.xhtml");
        assertEquals("1", page.findElement(By.id("count")).getText(), "preRenderView invocation count");
    }
}
