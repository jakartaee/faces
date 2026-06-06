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

package ee.jakarta.tck.faces.faces22.iteration;

import java.util.ArrayList;
import java.util.List;

import jakarta.el.ValueExpression;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.ValueHolder;
import jakarta.faces.component.visit.VisitCallback;
import jakarta.faces.component.visit.VisitContext;
import jakarta.faces.component.visit.VisitResult;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@RequestScoped
public class Issue2960Bean {

    private final List<String> list;
    private final StringBuilder iterations = new StringBuilder();

    public Issue2960Bean() {
        list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            list.add(String.valueOf(i));
        }
    }

    public String getIterations() {
        return iterations.toString();
    }

    public List<String> getList() {
        return list;
    }

    public void visitChildren() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().visitTree(VisitContext.createVisitContext(context), new VisitCallback() {
            @Override
            public VisitResult visit(VisitContext visitContext, UIComponent target) {
                if (target instanceof ValueHolder && "out".equals(target.getId())) {
                    ValueExpression expr = target.getValueExpression("value");
                    Object value = expr.getValue(context.getELContext());
                    iterations.append(value);
                    return VisitResult.REJECT;
                }
                return VisitResult.ACCEPT;
            }
        });
    }
}
