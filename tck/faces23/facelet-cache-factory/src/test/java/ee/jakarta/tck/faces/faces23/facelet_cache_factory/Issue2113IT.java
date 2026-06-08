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

package ee.jakarta.tck.faces.faces23.facelet_cache_factory;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.view.ViewDeclarationLanguageFactory;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

public class Issue2113IT extends BaseITNG {

    /**
     * Verifies that a custom {@link ViewDeclarationLanguageFactory} wrapping the default VDL
     * is consulted: its {@code buildView} is invoked on a normal request, and is skippable
     * via the {@code skipBuildView} request parameter.
     *
     * @see ViewDeclarationLanguageFactory
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2113
     */
    @Test
    void testReplaceVDL() throws Exception {
        WebPage page = getPage("issue2113.xhtml");
        assertTrue(page.containsText("buildView"));

        page.guardHttp(() -> page.findElement(By.id("button")).click());
        assertTrue(page.containsText("buildView"));

        page.guardHttp(() -> page.findElement(By.id("buttonSkipBuildView")).click());
        assertFalse(page.containsText("buildView"));
    }
}
