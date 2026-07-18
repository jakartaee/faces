/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIViewRoot;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * An inline {@code h:outputScript target="head"} declared in a composite component implementation is
 * relocated to the head, but its EL must still resolve against the composite component that owns it:
 * {@code #{cc.attrs.*}} and {@code #{cc.clientId}} must yield the owning composite's values, not
 * those of whatever component the script ends up under.
 */
public class Issue3489IT extends BaseITNG {

    /**
     * A relocated inline script resolves {@code #{cc.attrs.value}} against its owning composite, in a
     * form that does not prepend its id.
     *
     * @see UIComponent#getCompositeComponentParent(UIComponent)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3489
     */
    @Test
    void testRelocatedScriptResolvesAttributeWithPrependIdFalse() {
        assertRelocatedValueScript(getPage("issue3489.xhtml"));
    }

    /**
     * The same holds in a form that does prepend its id, so the composite's client id differs.
     *
     * @see UIComponent#getCompositeComponentParent(UIComponent)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3489
     */
    @Test
    void testRelocatedScriptResolvesAttributeWithPrependIdTrue() {
        assertRelocatedValueScript(getPage("issue3489-prependid.xhtml"));
    }

    /**
     * With two sibling composites each carrying an inline script, relocating the first one's script to
     * the head must leave the second one's script resolving {@code #{cc.clientId}} against itself.
     *
     * @see UIViewRoot#addComponentResource(jakarta.faces.context.FacesContext, UIComponent, String)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3489
     */
    @Test
    void testSiblingCompositesResolveOwnClientId() {
        WebPage page = getPage("issue3489-clientid.xhtml");

        assertEquals("cmp1", page.executeScript("return window.issue3489Cmp1"),
                "the relocated script resolves cc.clientId against the first composite");
        assertEquals("cmp2", page.executeScript("return window.issue3489Cmp2"),
                "the non-relocated sibling script resolves cc.clientId against the second composite");

        String head = headHtml(page);
        assertTrue(head.contains("issue3489Cmp1"), "the first composite's script is relocated to the head");
        assertFalse(head.contains("issue3489Cmp2"), "the second composite's script has no target and stays in the body");
    }

    private void assertRelocatedValueScript(WebPage page) {
        assertEquals("ok", page.executeScript("return window.issue3489Value"),
                "the relocated script resolves cc.attrs.value against its owning composite");
        assertTrue(headHtml(page).contains("issue3489Value"), "the script is relocated to the head");
    }

    private static String headHtml(WebPage page) {
        return (String) page.executeScript("return document.head.innerHTML");
    }
}
