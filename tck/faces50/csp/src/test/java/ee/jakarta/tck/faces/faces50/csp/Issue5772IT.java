/*
 * Copyright (c) Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the Apache License, Version 2.0
 * which is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GPL-2.0 with Classpath-exception-2.0 which
 * is available at https://openjdk.java.net/legal/gplv2+ce.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0 or Apache-2.0
 */
package ee.jakarta.tck.faces.faces50.csp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;

class Issue5772IT extends BaseITNG {

    @FindBy(id = "form:name")
    private WebElement name;

    @FindBy(id = "form:echo")
    private WebElement echo;

    /**
     * An h:inputText that combines a pass through attribute with an f:ajax behavior must render correctly and its
     * ajax behavior must keep working: the pass through attribute is rendered verbatim and the change event triggers
     * an ajax request that executes and re-renders the form, so the submitted value round-trips back to the view.
     *
     * @see https://github.com/eclipse-ee4j/mojarra/issues/5772
     */
    @Test
    public void testPassThroughAttributeWithAjax() {
        var page = getPage("issue5772.xhtml");

        // Pass through attribute is rendered verbatim onto the input element.
        assertEquals("Name", name.getAttribute("placeholder"));

        // The f:ajax change behavior executes and re-renders the form, so the value round-trips.
        page.guardAjax(() -> {
            name.sendKeys("John");
            name.sendKeys(Keys.TAB);
        });
        assertEquals("John", echo.getText());
    }
}
