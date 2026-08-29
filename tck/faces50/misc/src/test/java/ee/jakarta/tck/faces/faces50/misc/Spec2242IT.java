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
package ee.jakarta.tck.faces.faces50.misc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;

class Spec2242IT extends BaseITNG {

    private static final String RESOURCE_URL = "/jakarta.faces.resource/spec2242.gif.xhtml?ln=spec2242&v=1";

    @FindBy(id = "image")
    private WebElement image;

    @FindBy(id = "el")
    private WebElement el;

    @FindBy(id = "elWithColon")
    private WebElement elWithColon;

    /**
     * Verifies that the query string of the name attribute is removed before the resource is resolved and appended to
     * the resource URL rendered by h:graphicImage.
     *
     * @see jakarta.faces.application.ResourceHandler#createResource(String, String)
     * @see https://github.com/jakartaee/faces/issues/2242
     */
    @Test
    void graphicImageNameCarriesQueryString() {
        getPage("spec2242.xhtml");
        String src = image.getAttribute("src");
        assertTrue(src.endsWith(getContextPath() + RESOURCE_URL), "Rendered src must carry the query string: " + src);
    }

    /**
     * Verifies that the query string of a resource expression is removed before the resource is resolved and appended
     * to the resource URL it evaluates to.
     *
     * @see jakarta.faces.application.ResourceHandler#createResource(String, String)
     * @see https://github.com/jakartaee/faces/issues/2242
     */
    @Test
    void resourceExpressionCarriesQueryString() {
        getPage("spec2242.xhtml");
        assertEquals(getContextPath() + RESOURCE_URL, el.getText());
    }

    /**
     * Verifies that the colon separating the library name of a resource expression is counted over the expression
     * without its query string, so that the query string may itself contain a colon.
     *
     * @see jakarta.faces.application.ResourceHandler#createResource(String, String)
     * @see https://github.com/jakartaee/faces/issues/2242
     */
    @Test
    void resourceExpressionCarriesQueryStringContainingColon() {
        getPage("spec2242.xhtml");
        assertEquals(getContextPath() + RESOURCE_URL + ":2", elWithColon.getText());
    }

}
