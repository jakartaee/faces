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

package ee.jakarta.tck.faces.faces20.disable_bean_validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * With the {@code jakarta.faces.validator.DISABLE_DEFAULT_BEAN_VALIDATOR} context-param set to
 * {@code true}, the default bean validator is not auto-attached to an input, so its validator list
 * does not contain the {@code BeanValidator}. An explicit {@code f:validateBean} still attaches it,
 * proving the context-param disables only the automatic default, not explicit opt-in.
 */
public class DisableBeanValidatorIT extends BaseITNG {

    private static final String BEAN_VALIDATOR = "jakarta.faces.validator.BeanValidator";

    /**
     * The default bean validator is absent from an input's validators when disabled by context-param.
     *
     * @see jakarta.faces.validator.BeanValidator
     * @see jakarta.faces.component.UIInput#getValidators()
     */
    @Test
    void testDefaultBeanValidatorDisabled() {
        WebPage page = getPage("disableBeanValidator.xhtml");
        assertFalse(page.findElement(By.id("form:validators")).getText().contains(BEAN_VALIDATOR),
                "Default bean validator must not be attached when disabled");
    }

    /**
     * An explicit f:validateBean still attaches the bean validator even when the default is disabled.
     *
     * @see jakarta.faces.validator.BeanValidator
     * @see jakarta.faces.component.UIInput#getValidators()
     */
    @Test
    void testExplicitBeanValidatorEnabled() {
        WebPage page = getPage("enableBeanValidator.xhtml");
        assertTrue(page.findElement(By.id("form:validators")).getText().contains(BEAN_VALIDATOR),
                "Explicit f:validateBean must attach the bean validator");
    }
}
