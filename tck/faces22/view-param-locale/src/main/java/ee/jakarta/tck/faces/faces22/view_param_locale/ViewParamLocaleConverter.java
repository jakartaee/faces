/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
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
package ee.jakarta.tck.faces.faces22.view_param_locale;

import java.util.ResourceBundle;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;

/**
 * Always fails conversion, carrying a message resolved from the {@code messages} resource bundle so
 * that the language of the rendered {@link FacesMessage} reflects the active view locale.
 */
public class ViewParamLocaleConverter implements Converter<Object> {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        ResourceBundle bundle = context.getApplication()
                .evaluateExpressionGet(context, "#{messages}", ResourceBundle.class);
        String message = bundle.getString("viewParamLocaleMessage");
        throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        throw new UnsupportedOperationException();
    }
}
