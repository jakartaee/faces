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
package ee.jakarta.tck.faces.faces50.facelets;

import static ee.jakarta.tck.faces.faces50.facelets.Issue5885Bean.MODEL_VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;

class Issue5885IT extends BaseITNG {

    @FindBy(id = "singleChildFacet:input")
    private WebElement singleChildFacetInput;

    @FindBy(id = "singleChildFacet:message")
    private WebElement singleChildFacetMessage;

    @FindBy(id = "singleChildFacet:submit")
    private WebElement singleChildFacetSubmit;

    @FindBy(id = "multiChildFacet:input")
    private WebElement multiChildFacetInput;

    @FindBy(id = "multiChildFacet:message")
    private WebElement multiChildFacetMessage;

    @FindBy(id = "multiChildFacet:submit")
    private WebElement multiChildFacetSubmit;

    /**
     * An input which is the sole child of a facet must, after a failed validation on an ajax postback, re-render the submitted value instead of the model
     * value.
     *
     * @see https://github.com/eclipse-ee4j/mojarra/issues/5885
     */
    @Test
    void testSingleChildFacet() {
        var page = getPage("issue5885.xhtml");
        assertEquals(MODEL_VALUE, singleChildFacetInput.getDomProperty("value"));

        singleChildFacetInput.clear();
        page.guardAjax(singleChildFacetSubmit::click);

        assertFalse(singleChildFacetMessage.getText().isBlank());
        assertEquals("", singleChildFacetInput.getDomProperty("value"));
    }

    /**
     * An input in a facet with more than one child, which Facelets wraps in an implicit panel, must behave the same as one in a single child facet: after a
     * failed validation on an ajax postback it must re-render the submitted value instead of the model value, also when the view holds a programmatically added
     * component and the Facelets refresh is therefore re-applied during Render Response.
     *
     * @see https://github.com/eclipse-ee4j/mojarra/issues/5885
     */
    @Test
    void testMultiChildFacet() {
        var page = getPage("issue5885.xhtml");
        assertEquals(MODEL_VALUE, multiChildFacetInput.getDomProperty("value"));

        multiChildFacetInput.clear();
        page.guardAjax(multiChildFacetSubmit::click);

        assertFalse(multiChildFacetMessage.getText().isBlank());
        assertEquals("", multiChildFacetInput.getDomProperty("value"));
    }

}
