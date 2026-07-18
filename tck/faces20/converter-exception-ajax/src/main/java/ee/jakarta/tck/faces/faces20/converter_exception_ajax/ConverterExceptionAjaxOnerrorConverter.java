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

package ee.jakarta.tck.faces.faces20.converter_exception_ajax;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

/**
 * A converter that throws an unchecked {@link NullPointerException} (not a
 * {@link jakarta.faces.convert.ConverterException}) while decoding, to exercise how the runtime
 * surfaces an unexpected exception raised inside conversion. Encoding must not throw: it runs on
 * every render, including the one which produces the form that the test submits.
 */
@FacesConverter("throwNPE")
public class ConverterExceptionAjaxOnerrorConverter implements Converter<Object> {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        throw new NullPointerException("thrown from getAsObject");
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value == null ? "" : value.toString();
    }
}
