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

package ee.jakarta.tck.faces.faces23.passthrough;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * Plain HTML elements that are not otherwise mapped to a Faces component (for example {@code base},
 * {@code command} and {@code meta}) still become passthrough components when decorated with a
 * {@code jsf:id}, keeping their remaining attributes rendered verbatim.
 */
class Spec1075IT extends BaseITNG {

    /**
     * A decorated {@code base} element renders its id and its {@code href}/{@code target} attributes.
     *
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2611
     * @see jakarta.faces.component.UIComponent#getPassThroughAttributes()
     */
    @Test
    void base() {
        WebPage page = getPage("spec1075base.xhtml");
        WebElement base = page.findElement(By.id("base1"));
        assertEquals("base", base.getTagName(), "tag name");
        assertEquals("http://foobar.com", base.getDomAttribute("href"), "href");
        assertEquals("_blank", base.getDomAttribute("target"), "target");
    }

    /**
     * A decorated {@code command} element (a name with no dedicated Faces component) renders its id
     * and its {@code onclick} attribute.
     *
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2611
     * @see jakarta.faces.component.UIComponent#getPassThroughAttributes()
     */
    @Test
    void command() {
        WebPage page = getPage("spec1075command.xhtml");
        WebElement command = page.findElement(By.id("form:command1"));
        assertEquals("command", command.getTagName(), "tag name");
        assertTrue(page.isAttributeWired(command, "onclick"), "command onclick wired");
    }

    /**
     * A decorated {@code meta} element renders its id and its {@code charset} attribute.
     *
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2611
     * @see jakarta.faces.component.UIComponent#getPassThroughAttributes()
     */
    @Test
    void meta() {
        WebPage page = getPage("spec1075meta.xhtml");
        WebElement meta = page.findElement(By.id("meta1"));
        assertEquals("meta", meta.getTagName(), "tag name");
        assertEquals("UTF-8", meta.getDomAttribute("charset"), "charset");
    }
}
