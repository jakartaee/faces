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

package ee.jakarta.tck.faces.faces23.facelets;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.RequiredValidator;
import jakarta.faces.validator.Validator;
import jakarta.inject.Named;

@Named
@RequestScoped
public class Issue3245Bean {

    private transient UIComponent parent;

    private String value;

    public void add() {
        FacesContext context = FacesContext.getCurrentInstance();
        Validator<?> dynamicValidator = context.getApplication().createValidator(RequiredValidator.VALIDATOR_ID);
        UIInput validatedInput = (UIInput) parent.findComponent("validatedInput");
        validatedInput.addValidator(dynamicValidator);
    }

    public UIComponent getParent() {
        return parent;
    }

    public void setParent(UIComponent parent) {
        this.parent = parent;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
