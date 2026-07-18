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
package ee.jakarta.tck.faces.faces20.composite.retargeting;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.event.ActionListener;
import jakarta.faces.event.ValueChangeEvent;
import jakarta.faces.validator.Validator;
import jakarta.inject.Named;

/**
 * Backing bean shared by the composite-component retargeting fixtures. Every hook adds a
 * distinctive {@link FacesMessage} so the test can assert that the method expression, attached
 * validator/converter, or action listener was actually retargeted to the intended inner component.
 */
@Named
@RequestScoped
public class CompositeComponentsBean {

    public ActionListener getActionListener() {
        return new ActionListener() {
            @Override
            public void processAction(ActionEvent event) {
                FacesContext context = FacesContext.getCurrentInstance();
                String cid = ((UIComponent) event.getSource()).getClientId(context);
                context.addMessage(cid, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Action Invoked : " + cid, "Action Invoked : " + cid));
            }
        };
    }

    public Validator<Object> getValidator() {
        return new TestValidator();
    }

    public Converter<Object> getConverter() {
        return new TestConverter();
    }

    public String action() {
        FacesContext context = FacesContext.getCurrentInstance();
        UIComponent component = UIComponent.getCurrentComponent(context);
        String message = "Action invoked: " + component.getClientId(context);
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
        return "";
    }

    public String custom() {
        FacesContext context = FacesContext.getCurrentInstance();
        UIComponent component = UIComponent.getCurrentComponent(context);
        String message = "Custom action invoked: " + component.getClientId(context);
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
        return "";
    }

    public void actionListener(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        UIComponent component = UIComponent.getCurrentComponent(context);
        String message = "ActionListener invoked: " + component.getClientId(context);
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
    }

    public void validate(FacesContext context, UIComponent component, Object value) {
        String message = "validator invoked: " + component.getClientId(context);
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
    }

    public void valueChange(ValueChangeEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        UIComponent component = event.getComponent();
        String message = "ValueChange invoked: " + component.getClientId(context);
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
    }

    public static class TestValidator implements Validator<Object> {
        @Override
        public void validate(FacesContext context, UIComponent component, Object value) {
            String cid = component.getClientId(context);
            context.addMessage(cid, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Validator Invoked : " + cid, "Validator Invoked : " + cid));
        }
    }

    public static class TestConverter implements Converter<Object> {
        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) {
            return value;
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            String cid = component.getClientId(context);
            context.addMessage(cid, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Converter Invoked : " + cid, "Converter Invoked : " + cid));
            return String.valueOf(value);
        }
    }
}
