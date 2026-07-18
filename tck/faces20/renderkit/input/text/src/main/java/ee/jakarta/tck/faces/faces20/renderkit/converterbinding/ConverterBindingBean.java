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

package ee.jakarta.tck.faces.faces20.renderkit.converterbinding;

import java.util.Locale;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.NumberConverter;
import jakarta.inject.Named;

/**
 * Exposes converter instances for the {@code binding} attribute of {@code f:converter}: a
 * standard {@link NumberConverter} configured as currency, and a custom upper-casing converter.
 */
@Named
@RequestScoped
public class ConverterBindingBean {

    private String value;

    private final NumberConverter numberConverter;

    private final Converter<Object> converter = new ConverterBindingUppercaseConverter();

    public ConverterBindingBean() {
        numberConverter = new NumberConverter();
        numberConverter.setLocale(Locale.US);
        numberConverter.setType("currency");
    }

    public Double getDoubleProperty() {
        return 123.45;
    }

    public NumberConverter getNumberConverter() {
        return numberConverter;
    }

    public Converter<Object> getConverter() {
        return converter;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
