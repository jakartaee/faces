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

package ee.jakarta.tck.faces.faces22.default_validator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * A {@code @FacesValidator} annotated class without an explicit value gets a validator-id derived
 * from its simple class name with the first character lower-cased. So {@code AnnotatedValidatorNoValue}
 * is resolvable through {@code Application.createValidator("annotatedValidatorNoValue")} but not
 * through its exact simple class name, and it is not registered as a default validator.
 */
class FacesValidatorDefaultIdIT extends BaseITNG {

    /**
     * @see jakarta.faces.validator.FacesValidator
     * @see jakarta.faces.application.Application#createValidator(String)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3338
     */
    @Test
    void testDefaultValidatorId() {
        WebPage page = getPage("facesValidatorDefaultId.xhtml");
        assertEquals("true", page.findElement(By.id("result")).getText(),
                "the @FacesValidator default id must be derived from the simple class name, first char lower-cased");
    }
}
