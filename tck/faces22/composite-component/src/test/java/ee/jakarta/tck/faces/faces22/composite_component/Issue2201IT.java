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

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue2201IT extends BaseITNG {

    private void typeInRegisterName(WebPage page, String value) {
        WebElement input = page.findElement(By.id("form:register:name"));
        input.clear();
        input.sendKeys(value);
    }

    private void submit(WebPage page) {
        page.guardHttp(() -> page.findElement(By.id("form:button")).click());
    }

    /**
     * @see jakarta.faces.view.facelets.CompositeFaceletHandler
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2201
     */
    @Test
    void convertNumber() throws Exception {
        WebPage page = getPage("issue2201-convertNumber.xhtml");
        typeInRegisterName(page, "Foo");
        submit(page);
        assertTrue(page.containsText("could not be understood as a percentage"), "f:convertNumber retargeted onto inner input");
    }

    /**
     * @see jakarta.faces.view.facelets.CompositeFaceletHandler
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2201
     */
    @Test
    void converter() throws Exception {
        WebPage page = getPage("issue2201-converter.xhtml");
        typeInRegisterName(page, "Foo");
        submit(page);
        assertTrue(page.containsText("must be a number consisting of one or more digits"), "f:converter retargeted onto inner input");
    }

    /**
     * @see jakarta.faces.view.facelets.CompositeFaceletHandler
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2201
     */
    @Test
    void convertDateTime() throws Exception {
        WebPage page = getPage("issue2201-convertDateTime.xhtml");
        typeInRegisterName(page, "Foo");
        submit(page);
        assertTrue(page.containsText("could not be understood as a date"), "f:convertDateTime retargeted onto inner input");
    }

    /**
     * @see jakarta.faces.view.facelets.CompositeFaceletHandler
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2201
     */
    @Test
    void validateLength() throws Exception {
        WebPage page = getPage("issue2201-validateLength.xhtml");
        typeInRegisterName(page, "FooFoo");
        submit(page);
        assertTrue(page.containsText("Length is greater than allowable maximum"), "f:validateLength retargeted onto inner input");
    }

    /**
     * @see jakarta.faces.view.facelets.CompositeFaceletHandler
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2201
     */
    @Test
    void actionListener() throws Exception {
        WebPage page = getPage("issue2201-actionListener.xhtml");
        page.guardHttp(() -> page.findElement(By.id("form:mybutton:name")).click());
        assertTrue(page.containsText("name was pressed"), "f:actionListener retargeted onto inner button");
    }

    /**
     * @see jakarta.faces.view.facelets.CompositeFaceletHandler
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2201
     */
    @Test
    void valueChangeListener() throws Exception {
        WebPage page = getPage("issue2201-valueChangeListener.xhtml");
        typeInRegisterName(page, "FooFoo");
        submit(page);
        assertTrue(page.containsText("name value was changed"), "f:valueChangeListener retargeted onto inner input");
    }

    /**
     * @see jakarta.faces.view.facelets.CompositeFaceletHandler
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2201
     */
    @Test
    void setPropertyActionListener() throws Exception {
        WebPage page = getPage("issue2201-setPropertyActionListener.xhtml");
        page.guardHttp(() -> page.findElement(By.id("form:mybutton:name")).click());
        assertTrue(page.containsText("foo"), "f:setPropertyActionListener retargeted onto inner button");
    }

    /**
     * @see jakarta.faces.view.facelets.CompositeFaceletHandler
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2201
     */
    @Test
    void validateDoubleRange() throws Exception {
        WebPage page = getPage("issue2201-validateDoubleRange.xhtml");
        typeInRegisterName(page, "123456");
        submit(page);
        assertTrue(page.containsText("Specified attribute is not between the expected values of 2 and 5"), "f:validateDoubleRange retargeted onto inner input");
    }

    /**
     * @see jakarta.faces.view.facelets.CompositeFaceletHandler
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2201
     */
    @Test
    void validateLongRange() throws Exception {
        WebPage page = getPage("issue2201-validateLongRange.xhtml");
        typeInRegisterName(page, "123456");
        submit(page);
        assertTrue(page.containsText("Specified attribute is not between the expected values of 2 and 5"), "f:validateLongRange retargeted onto inner input");
    }

    /**
     * @see jakarta.faces.view.facelets.CompositeFaceletHandler
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2201
     */
    @Test
    void validateRequired() throws Exception {
        WebPage page = getPage("issue2201-validateRequired.xhtml");
        WebElement input = page.findElement(By.id("form:register:name"));
        input.clear();
        submit(page);
        assertTrue(page.containsText("Value is required"), "f:validateRequired retargeted onto inner input");
    }

    /**
     * @see jakarta.faces.view.facelets.CompositeFaceletHandler
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2201
     */
    @Test
    void validator() throws Exception {
        WebPage page = getPage("issue2201-validator.xhtml");
        typeInRegisterName(page, "123456");
        submit(page);
        assertTrue(page.containsText("name was validated"), "f:validator retargeted onto inner input");
    }

    /**
     * @see jakarta.faces.view.facelets.CompositeFaceletHandler
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2201
     */
    @Test
    void validateRegex() throws Exception {
        WebPage page = getPage("issue2201-validateRegex.xhtml");
        typeInRegisterName(page, "$$$$$$$$$$$");
        submit(page);
        assertTrue(page.containsText("Regex Pattern not matched"), "f:validateRegex retargeted onto inner input");
    }
}
