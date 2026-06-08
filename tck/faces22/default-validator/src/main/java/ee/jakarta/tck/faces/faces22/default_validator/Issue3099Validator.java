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

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

@FacesValidator(value = Issue3099Validator.VALIDATOR_ID, isDefault = true)
public class Issue3099Validator implements Validator<Object> {

    public static final String VALIDATOR_ID = "ee.jakarta.tck.faces.faces22.default_validator.Issue3099Validator";

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        throw new ValidatorException(new FacesMessage(component.getClientId(context) + ": Validation failed."));
    }
}
