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
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * A behavior event attribute must have its script invoked on the corresponding DOM event. Whether the runtime renders it inline or arranges for it at runtime
 * is left to the implementation, so these assert the event itself rather than the markup. The <code>error</code> of a resource element is the case which admits
 * no runtime mechanism taking effect after the element has been parsed, as the fetch starts as the element is parsed and the event can be dispatched before any
 * script following it runs.
 *
 * @see <a href="https://github.com/jakartaee/faces/issues/1507">faces#1507</a>
 */
class Spec1507IT extends BaseITNG {

    @FindBy(id = "form:add")
    private WebElement addButton;

    /**
     * The <code>onerror</code> of a script resource which fails to load runs, and runs with the script element as <code>this</code>.
     */
    @Test
    public void testScriptResourceOnError() {
        var page = getPage("spec1507.xhtml");
        assertEquals("SCRIPT", awaitHandler(page, "scriptError"));
    }

    /**
     * The <code>onerror</code> of a stylesheet resource which fails to load runs, and runs with the link element as <code>this</code>.
     */
    @Test
    public void testStylesheetResourceOnError() {
        var page = getPage("spec1507.xhtml");
        assertEquals("LINK", awaitHandler(page, "stylesheetError"));
    }

    /**
     * The <code>onerror</code> of an image which arrives through an ajax response runs, even though the response carries a script with a <code>src</code> ahead
     * of that image. The image is live as soon as the response markup is inserted, so a runtime which wires the handler behind that script never observes the
     * event.
     */
    @Test
    public void testImageResourceOnErrorAfterAjax() {
        var page = getPage("spec1507ajax.xhtml");
        page.guardAjax(addButton::click);
        assertEquals("IMG", awaitHandler(page, "imageError"));
    }

    /**
     * Returns the value the handler recorded in the given window property, waiting for the resource fetch to fail first, as the load of the document does not
     * await it.
     */
    private static String awaitHandler(WebPage page, String property) {
        String script = "return window." + property + ";";

        try {
            page.waitForCondition($ -> page.executeScript(script) != null);
        }
        catch (TimeoutException e) {
            fail("Handler did not set window." + property);
        }

        return (String) page.executeScript(script);
    }

}
