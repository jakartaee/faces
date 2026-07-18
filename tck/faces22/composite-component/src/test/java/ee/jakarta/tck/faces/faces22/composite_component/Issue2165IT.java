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

package ee.jakarta.tck.faces.faces22.composite_component;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue2165IT extends BaseITNG {

    /**
     * When a composite's implementation is a ui:decorate on an external template, #{cc} inside that
     * template must still resolve to the enclosing composite, so its attributes evaluate rather than
     * yielding the parent composite's or an empty value.
     *
     * @see jakarta.faces.component.UIComponent#getCompositeComponentParent(jakarta.faces.component.UIComponent)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2165
     */
    @Test
    void ccAttrsResolveInsideUiDecorateOnExternalTemplate() {
        WebPage page = getPage("issue2165.xhtml");
        assertEquals("Do you see me?", page.findElement(By.id("outer:inner:result")).getText());
    }
}
