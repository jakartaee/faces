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
package ee.jakarta.tck.faces.test.faces50.facelets;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;

class Issue5543IT extends BaseITNG {

    @FindBy(id = "form:input")
    private WebElement input;

    @FindBy(id = "form:nonAjaxSubmit")
    private WebElement nonAjaxSubmit;

    @FindBy(id = "form:ajaxSubmit")
    private WebElement ajaxSubmit;

    @FindBy(id = "form:output")
    private WebElement output;

    /**
     * https://github.com/eclipse-ee4j/mojarra/issues/5543
     */
    @Test
    void testDefaultResponseEncodingNonAjax() {
        var page = getPage("issue5543.xhtml");
        assertEquals("ä", input.getDomProperty("value"));
        assertEquals("ä", output.getText());
        page.guardHttp(nonAjaxSubmit::click);
        assertEquals("ä", input.getDomProperty("value"));
        assertEquals("ä", output.getText());
    }

    /**
     * https://github.com/eclipse-ee4j/mojarra/issues/5543
     */
    @Test
    void testDefaultResponseEncodingAjax() {
        var page = getPage("issue5543.xhtml");
        assertEquals("ä", input.getDomProperty("value"));
        assertEquals("ä", output.getText());
        page.guardAjax(ajaxSubmit::click);
        assertEquals("ä", input.getDomProperty("value"));
        assertEquals("ä", output.getText());
    }
}
