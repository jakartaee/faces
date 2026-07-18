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
package ee.jakarta.tck.faces.faces20.el;

import java.io.Serializable;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Named;

/**
 * Backs converters and validators bound through the {@code binding} attribute, re-resolved on every
 * request. Decoding flips the converter and validating flips the validator, each independently, so
 * the instance returned by either getter alternates across successive postbacks. Encoding must not
 * flip anything: it runs on every render and would otherwise desynchronise the two.
 */
@Named
@SessionScoped
public class LateBindingsBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean switchConverter;
    private boolean switchValidator;

    private final Validator<Object> v1 = new CustomValidator1();
    private final Validator<Object> v2 = new CustomValidator2();
    private Validator<Object> vret = v1;
    private Validator<Object> vnext = v2;

    public Validator<Object> getValidator() {
        if (switchValidator) {
            Validator<Object> tmp = vret;
            vret = vnext;
            vnext = tmp;
            switchValidator = false;
        }
        return vret;
    }

    private final Converter<Object> c1 = new CustomConverter1();
    private final Converter<Object> c2 = new CustomConverter2();
    private Converter<Object> cret = c1;
    private Converter<Object> cnext = c2;

    public Converter<Object> getConverter() {
        if (switchConverter) {
            Converter<Object> tmp = cret;
            cret = cnext;
            cnext = tmp;
            switchConverter = false;
        }
        return cret;
    }

    private String value1 = "hello";
    private String value2 = "hello2";
    private String value3 = "hello3";

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getValue3() {
        return value3;
    }

    public void setValue3(String value3) {
        this.value3 = value3;
    }

    private Validator<Object> val;

    public void setValidator2(Validator<Object> val) {
        if (!(val instanceof LBValidator)) {
            throw new IllegalArgumentException("Expected LBValidator, received: " + val.getClass().getName());
        }
        this.val = val;
    }

    public Validator<Object> getValidator2() {
        return val;
    }

    private Converter<Object> con;

    public void setConverter2(Converter<Object> con) {
        if (!(con instanceof LBConverter)) {
            throw new IllegalArgumentException("Expected LBConverter, received: " + con.getClass().getName());
        }
        this.con = con;
    }

    public Converter<Object> getConverter2() {
        return con;
    }

    private class CustomValidator1 implements Validator<Object> {
        @Override
        public void validate(FacesContext context, UIComponent component, Object value) {
            switchValidator = true;
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "CustomValidator1 invoked", "CustomValidator1 invoked"));
        }
    }

    private class CustomValidator2 implements Validator<Object> {
        @Override
        public void validate(FacesContext context, UIComponent component, Object value) {
            switchValidator = true;
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "CustomValidator2 invoked", "CustomValidator2 invoked"));
        }
    }

    private class CustomConverter1 implements Converter<Object> {
        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) {
            switchConverter = true;
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "CustomConverter1 invoked", "CustomConverter1 invoked"));
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            return value == null ? "" : value.toString();
        }
    }

    private class CustomConverter2 implements Converter<Object> {
        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) {
            switchConverter = true;
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "CustomConverter2 invoked", "CustomConverter2 invoked"));
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            return value == null ? "" : value.toString();
        }
    }
}
