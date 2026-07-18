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
import jakarta.el.ELException;
import jakarta.el.ValueExpression;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

/**
 * Exercises {@link ValueExpression#setValue} against a bean stored in the session map, including
 * setting a property to {@code null} and setting a read-only property (which must throw).
 */
@Named
@RequestScoped
public class Issue2834Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String KEY = "issue2834Holder";

    private Issue2834Holder prepareHolder() {
        FacesContext context = FacesContext.getCurrentInstance();
        Issue2834Holder holder = new Issue2834Holder();
        context.getExternalContext().getSessionMap().put(KEY, holder);
        return holder;
    }

    private ValueExpression valueExpression(String expression) {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().getExpressionFactory()
                .createValueExpression(context.getELContext(), expression, Object.class);
    }

    private ELContext elContext() {
        return FacesContext.getCurrentInstance().getELContext();
    }

    public String getTest5() {
        Issue2834Holder holder = prepareHolder();
        valueExpression("#{sessionScope." + KEY + ".one}").setValue(elContext(), null);
        return holder.getOne() == null ? "SUCCESS" : "FAILED";
    }

    public String getTest6() {
        Issue2834Holder holder = prepareHolder();
        Issue2834Inner inner = new Issue2834Inner();
        valueExpression("#{sessionScope." + KEY + ".inner}").setValue(elContext(), inner);
        return holder.getInner() != null ? "SUCCESS" : "FAILED";
    }

    public String getTest7() {
        Issue2834Holder holder = prepareHolder();
        Issue2834Inner inner = new Issue2834Inner();
        valueExpression("#{sessionScope." + KEY + ".inner}").setValue(elContext(), inner);
        valueExpression("#{sessionScope." + KEY + ".inner}").setValue(elContext(), null);
        return holder.getInner() == null ? "SUCCESS" : "FAILED";
    }

    public String getTest8() {
        Issue2834Holder holder = prepareHolder();
        Issue2834Inner inner = new Issue2834Inner();
        valueExpression("#{sessionScope." + KEY + ".inner}").setValue(elContext(), inner);
        valueExpression("#{sessionScope." + KEY + ".inner}").setValue(elContext(), inner);

        boolean exceptionThrown = false;
        try {
            valueExpression("#{sessionScope." + KEY + ".inner.test4}").setValue(elContext(), null);
        } catch (ELException expected) {
            exceptionThrown = true;
        }
        return exceptionThrown ? "SUCCESS" : "FAILED";
    }
}
