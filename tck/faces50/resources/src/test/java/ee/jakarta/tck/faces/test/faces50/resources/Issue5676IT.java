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
package ee.jakarta.tck.faces.test.faces50.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;

class Issue5676IT extends BaseITNG {

    @FindBy(id = "webjars")
    private WebElement webjars;

    @FindBy(id = "pftheme")
    private WebElement pftheme;

    /**
     * https://github.com/eclipse-ee4j/mojarra/issues/5676
     */
    @Test
    void test() {
        getPage("issue5676.xhtml");
        assertEquals(getContextPath() + "/jakarta.faces.resource/font-awesome/7.2.0/webfonts/fa-regular-400.woff2.xhtml?ln=webjars", webjars.getText());
        assertEquals(getContextPath() + "/jakarta.faces.resource/images/ui-bg_flat_75_ffffff_40x100.png.xhtml?ln=primefaces-casablanca", pftheme.getText());

        var css = getResponseBody("jakarta.faces.resource/issue5676.css.xhtml");
        assertTrue(css.contains("content: \"" + webjars.getText() + "\";"));
        assertTrue(css.contains("background: url(\"" + pftheme.getText() + "\");"));
    }

}
