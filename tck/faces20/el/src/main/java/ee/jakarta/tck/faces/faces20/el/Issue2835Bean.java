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

package ee.jakarta.tck.faces.faces20.el;

import java.io.Serializable;

import jakarta.el.ELContext;
import jakarta.el.PropertyNotWritableException;
import jakarta.el.ValueExpression;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

/**
 * Exercises {@link ValueExpression#setValue} against a session scoped target, for a plain property, a nested bean
 * property and a read only property. Each getter reports SUCCESS or FAILED so the outcome is observable in the view.
 */
@Named
@RequestScoped
public class Issue2835Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String TARGET = "issue2835Target";
    private static final String SUCCESS = "SUCCESS";
    private static final String FAILED = "FAILED";

    public static class Target implements Serializable {

        private static final long serialVersionUID = 1L;

        private String one;
        private Inner inner;

        public String getOne() {
            return one;
        }

        public void setOne(String one) {
            this.one = one;
        }

        public Inner getInner() {
            return inner;
        }

        public void setInner(Inner inner) {
            this.inner = inner;
        }

        public String getReadOnly() {
            return "read only";
        }
    }

    public static class Inner implements Serializable {

        private static final long serialVersionUID = 1L;
    }

    /**
     * Setting null through a value expression nulls a plain property.
     */
    public String getSetNullOnProperty() {
        Target target = newTarget();
        target.setOne("initial");

        setValue("#{sessionScope." + TARGET + ".one}", null);

        return target.getOne() == null ? SUCCESS : FAILED;
    }

    /**
     * Setting a value through a value expression reaches a nested bean property.
     */
    public String getSetValueOnNestedProperty() {
        Target target = newTarget();

        setValue("#{sessionScope." + TARGET + ".inner}", new Inner());

        return target.getInner() != null ? SUCCESS : FAILED;
    }

    /**
     * Setting null through a value expression nulls a previously set nested bean property.
     */
    public String getSetNullOnNestedProperty() {
        Target target = newTarget();

        setValue("#{sessionScope." + TARGET + ".inner}", new Inner());
        setValue("#{sessionScope." + TARGET + ".inner}", null);

        return target.getInner() == null ? SUCCESS : FAILED;
    }

    /**
     * Setting a read only property through a value expression raises PropertyNotWritableException.
     */
    public String getSetValueOnReadOnlyProperty() {
        newTarget();

        try {
            setValue("#{sessionScope." + TARGET + ".readOnly}", "other");
            return FAILED;
        }
        catch (PropertyNotWritableException e) {
            return SUCCESS;
        }
    }

    private static Target newTarget() {
        Target target = new Target();
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(TARGET, target);
        return target;
    }

    private static void setValue(String expression, Object value) {
        FacesContext context = FacesContext.getCurrentInstance();
        ELContext elContext = context.getELContext();
        ValueExpression valueExpression = context.getApplication().getExpressionFactory()
                .createValueExpression(elContext, expression, Object.class);

        valueExpression.setValue(elContext, value);
    }
}
