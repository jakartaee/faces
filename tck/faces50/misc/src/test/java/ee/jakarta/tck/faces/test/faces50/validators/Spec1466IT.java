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
package ee.jakarta.tck.faces.test.faces50.validators;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;

class Spec1466IT extends BaseITNG {

    @FindBy(id = "Spec1466Constants1")
    private WebElement spec1466Constants1;

    @FindBy(id = "Spec1466Constants2a")
    private WebElement spec1466Constants2a;

    @FindBy(id = "Spec1466Constants2b")
    private WebElement spec1466Constants2b;

    /**
     * https://github.com/jakartaee/faces/issues/1466
     */
    @Test
    void testBeanValidationMessages() {
        getPage("spec1466.xhtml");
        assertAll(
            () -> assertEquals("{ONE1=ONE1, TWO1=TWO1, THREE1=THREE1}", spec1466Constants1.getText()),
            () -> assertEquals("{ONE2=ONE2, TWO2=TWO2, THREE2=THREE2}", spec1466Constants2a.getText()),
            () -> assertEquals("{ONE2=ONE2, TWO2=TWO2, THREE2=THREE2}", spec1466Constants2b.getText())
        );
    }
}
