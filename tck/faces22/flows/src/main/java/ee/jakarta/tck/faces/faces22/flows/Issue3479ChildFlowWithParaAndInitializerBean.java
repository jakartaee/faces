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
package ee.jakarta.tck.faces.faces22.flows;

import java.io.Serializable;

import jakarta.el.ELContext;
import jakarta.el.ExpressionFactory;
import jakarta.el.ValueExpression;
import jakarta.faces.context.FacesContext;
import jakarta.faces.flow.FlowScoped;
import jakarta.inject.Named;

@Named
@FlowScoped(value = "issue3479-child-flow-with-parameters-and-initializer")
public class Issue3479ChildFlowWithParaAndInitializerBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String strToDisplayOfPara;

    public String getStrToDisplayOfPara() {
        return strToDisplayOfPara;
    }

    public void initializerCalled() {
        this.strToDisplayOfPara = evalAsString("#{flowScope.Para1}");
    }

    private static String evalAsString(String expression) {
        FacesContext context = FacesContext.getCurrentInstance();
        ExpressionFactory expressionFactory = context.getApplication().getExpressionFactory();
        ELContext elContext = context.getELContext();
        ValueExpression valueExpression = expressionFactory.createValueExpression(elContext, expression, String.class);
        return (String) valueExpression.getValue(elContext);
    }
}
