/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
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

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * A validator method declared on the outer composite component fires for an EditableValueHolder nested two
 * composite-component levels deep, producing the expected validation message.
 */
class Issue3089IT extends BaseITNG {

    /**
     * The validator method passed to the outermost composite component is resolved against the deeply nested
     * h:inputText and throws its ValidatorException, so the resulting message is rendered on submit.
     *
     * @see jakarta.faces.validator.ValidatorException
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3089
     */
    @Test
    void testInputTextValidator1() {
        WebPage page = getPage("issue3089.xhtml");
        page.findElement(By.id("form:level1:level2:inputText")).sendKeys("test");
        page.guardHttp(() -> page.findElement(By.id("form:submit")).click());
        assertTrue(page.getSource().contains("Oops"), "Validation message should be rendered");
    }
}
