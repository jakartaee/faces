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

package ee.jakarta.tck.faces.faces22.ajax_lifecycle;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

/**
 * Throws an exception which is deliberately <em>not</em> a {@link jakarta.faces.convert.ConverterException}, so that it
 * must not be absorbed into a {@link jakarta.faces.application.FacesMessage} but instead reach the
 * {@link jakarta.faces.context.ExceptionHandler}.
 */
@FacesConverter(ConverterExceptionAjaxOnerrorConverter.CONVERTER_ID)
public class ConverterExceptionAjaxOnerrorConverter implements Converter<String> {

    public static final String CONVERTER_ID = "converterExceptionAjaxOnerror";

    @Override
    public String getAsObject(FacesContext context, UIComponent component, String value) {
        throw new IllegalStateException("Not a ConverterException");
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, String value) {
        return value == null ? "" : value;
    }
}
