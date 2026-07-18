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

import java.util.Set;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.FacesException;
import jakarta.faces.application.Application;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.Validator;
import jakarta.inject.Named;

@Named
@RequestScoped
public class FacesValidatorDefaultIdBean {

    private static final String DERIVED_ID = "annotatedValidatorNoValue";
    private static final String CLASS_SIMPLE_NAME = "AnnotatedValidatorNoValue";

    public String getTestResult() {
        Application application = FacesContext.getCurrentInstance().getApplication();

        Validator<?> validator = application.createValidator(DERIVED_ID);
        if (!(validator instanceof AnnotatedValidatorNoValue)) {
            return Boolean.FALSE.toString();
        }
        if (!CLASS_SIMPLE_NAME.equals(((AnnotatedValidatorNoValue) validator).getWelcomeMessage())) {
            return Boolean.FALSE.toString();
        }

        Set<String> defaultValidatorIds = application.getDefaultValidatorInfo().keySet();
        if (defaultValidatorIds.contains(CLASS_SIMPLE_NAME)) {
            return Boolean.FALSE.toString();
        }

        try {
            application.createValidator(CLASS_SIMPLE_NAME);
            return Boolean.FALSE.toString();
        } catch (FacesException expected) {
            return Boolean.TRUE.toString();
        }
    }
}
