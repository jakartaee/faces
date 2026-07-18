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
package ee.jakarta.tck.faces.faces22.flash_factory;

import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.context.FlashFactory;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Spec1071IT extends BaseITNG {

    /**
     * A custom {@link FlashFactory} registered via {@code faces-config.xml} must be used, so the
     * runtime hands out the wrapping custom {@link jakarta.faces.context.Flash} implementation.
     *
     * @see FlashFactory#getFlash
     * @see https://github.com/jakartaee/faces/issues/1071
     */
    @Test
    void customFlashFactoryIsUsed() {
        WebPage page = getPage("spec1071.xhtml");
        String flashClassName = page.findElement(By.id("form:flashClassName")).getText();
        assertTrue(flashClassName.endsWith("Spec1071CustomFlash"),
                "Expected custom flash class, but was: " + flashClassName);
    }
}
