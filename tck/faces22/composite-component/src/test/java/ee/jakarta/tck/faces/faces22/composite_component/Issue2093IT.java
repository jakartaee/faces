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

/**
 * When a composite component subscribes to {@code PreRenderComponentEvent} and adds children in the
 * listener, its {@code cc.attrs.item} attribute must be resolvable (non-null) during that event -
 * both in the outer composite component and in a nested composite component it forwards the
 * attribute to.
 */
public class Issue2093IT extends BaseITNG {

    private static final String ITEM_NOT_NULL = "Item Attribute is null: false";

    /**
     * The composite attribute resolves to a non-null value during PreRenderComponentEvent, in both
     * the outer and the nested composite component.
     *
     * @see jakarta.faces.event.PreRenderComponentEvent
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2093
     */
    @Test
    void testPreRenderView() {
        WebPage page = getPage("issue2093.xhtml");
        assertEquals(ITEM_NOT_NULL, page.findElement(By.cssSelector("span[id$='check1']")).getText(),
                "Outer composite component attribute must be non-null");
        assertEquals(ITEM_NOT_NULL, page.findElement(By.cssSelector("span[id$='check2']")).getText(),
                "Nested composite component attribute must be non-null");
    }
}
