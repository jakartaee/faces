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

package ee.jakarta.tck.faces.faces22.phase_listener;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.event.PhaseListener;

class Issue2375IT extends BaseITNG {

    /**
     * A dynamically added subtree with a transient root must be present after INVOKE_APPLICATION of the request that
     * created it, but absent after RESTORE_VIEW of the next request, since a transient subtree is not state-saved.
     *
     * @see UIComponent#setTransient(boolean)
     * @see PhaseListener
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2375
     */
    @Test
    void testTransientParent() throws Exception {
        WebPage page = getPage("transientParent2375.xhtml");

        page.guardHttp(() -> page.findElement(By.id("helloForm:button")).click());
        assertTrue(page.containsText("After INVOKE_APPLICATION 5 Transient Subtree Exists"));

        page.guardHttp(() -> page.findElement(By.id("helloForm:button")).click());
        assertTrue(page.containsText("After RESTORE_VIEW 1 Transient Subtree Does Not Exist"));
    }
}
