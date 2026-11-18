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
package ee.jakarta.tck.faces.faces50.facelets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * An HTML element with an attribute in the <code>jakarta.faces</code> namespace whose local name is in the localName and selector attribute table of the
 * default <code>TagDecorator</code>, but which has none of the selector attributes of that local name, is backed by <code>faces:element</code>. So it gets a
 * client id and honors <code>rendered</code>. The <code>faces:rendered</code> attribute itself does not appear in the output.
 *
 * @see <a href="https://github.com/eclipse-ee4j/mojarra/issues/6181">issue 6181</a>
 */
class Issue6181IT extends BaseITNG {

    private WebPage page;

    /**
     * An <code>a</code> without <code>action</code>, <code>actionListener</code>, <code>value</code> or <code>outcome</code> renders as a component. With
     * <code>faces:rendered</code> as its only <code>faces:</code> attribute, it renders without that attribute when true, and is absent when false.
     */
    @Test
    void testAnchorWithoutSelectorAttributeIsElement() {
        page = getPage("issue6181-elements.xhtml");

        assertEquals("a", page.findElement(By.id("form:link")).getTagName());
        assertNull(page.findElement(By.cssSelector("[data-case=shownLink]")).getAttribute("faces:rendered"), "faces:rendered");
        assertTrue(page.findElements(By.cssSelector("[data-case=hiddenLink]")).isEmpty());
    }

    /**
     * An <code>input</code> without <code>type</code> renders as a component. With <code>faces:rendered</code> as its only <code>faces:</code> attribute, it
     * renders without that attribute when true, and is absent when false.
     */
    @Test
    void testInputWithoutTypeIsElement() {
        page = getPage("issue6181-elements.xhtml");

        assertEquals("input", page.findElement(By.id("form:input")).getTagName());
        assertNull(page.findElement(By.cssSelector("[data-case=shownInput]")).getAttribute("faces:rendered"), "faces:rendered");
        assertTrue(page.findElements(By.cssSelector("[data-case=hiddenInput]")).isEmpty());
    }

}
