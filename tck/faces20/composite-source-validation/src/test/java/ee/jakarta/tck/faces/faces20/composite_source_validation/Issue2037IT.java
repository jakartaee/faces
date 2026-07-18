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

package ee.jakarta.tck.faces.faces20.composite_source_validation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * A composite component wired through a facelet-taglib {@code resource-id} (rather than the default
 * {@code resources/<library>/<tag>.xhtml} convention) is resolved and rendered like any other
 * composite component.
 */
public class Issue2037IT extends BaseITNG {

    /**
     * The taglib-mapped composite component renders its implementation content.
     *
     * @see jakarta.faces.view.facelets.FaceletContext
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2037
     */
    @Test
    void testSourceValidationFails() {
        WebPage page = getPage("issue2037.xhtml");
        assertEquals("Test", page.findElement(By.id("myLayout")).getText());
    }
}
