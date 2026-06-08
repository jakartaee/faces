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

package ee.jakarta.tck.faces.faces22.iteration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * Submitting a ui:repeat row's commandButton whose actionListener removes that row from the backing list removes
 * that row's rendered input component from the re-rendered page.
 */
class Issue2942IT extends BaseITNG {

    /**
     * The single repeated row renders its input; clicking its remove button drops the row from the backing list so
     * the re-rendered page no longer contains that row's input.
     *
     * @see jakarta.faces.component.UIData
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2942
     */
    @Test
    void testRowRemoved() {
        WebPage page = getPage("issue2942.xhtml");
        assertTrue(page.containsSource("id=\"f:r:0:i\""), "row input is rendered before removal");
        page.guardHttp(() -> page.findElement(By.id("f:r:0:b")).click());
        assertFalse(page.containsSource("id=\"f:r:0:i\""), "row input is gone after removal");
    }
}
