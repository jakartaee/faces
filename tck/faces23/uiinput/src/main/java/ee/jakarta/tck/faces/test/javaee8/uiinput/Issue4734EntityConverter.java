/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2020 Contributors to the Eclipse Foundation.
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
package ee.jakarta.tck.faces.test.javaee8.uiinput;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(forClass=Issue4734Entity.class)
public class Issue4734EntityConverter implements Converter<Issue4734Entity> {

    @Override
    public String getAsString(FacesContext context, UIComponent component, Issue4734Entity modelValue) {
        Long id = modelValue == null ? null : modelValue.getId();
        return id == null ? "" : id.toString();
    }

    @Override
    public Issue4734Entity getAsObject(FacesContext context, UIComponent component, String submittedValue) {
        Long id = submittedValue == null || submittedValue.isEmpty() ? null : Long.valueOf(submittedValue);
        return id == null ? null : new Issue4734Entity(id);
    }

}