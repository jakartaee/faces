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

package ee.jakarta.tck.faces.faces23.el;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class Issue4163Bean {

    @Inject
    private UIViewRoot view;

    @Inject
    private FacesContext context;

    public String getHello() {
        return "Hello";
    }

    public String getValueExpressionBase() {
        return view.findComponent(":output")
                .getValueExpression("value")
                .getValueReference(context.getELContext())
                .getBase()
                .getClass()
                .getName();
    }

    public String getValueExpressionProperty() {
        return view.findComponent(":output")
                .getValueExpression("value")
                .getValueReference(context.getELContext())
                .getProperty()
                .toString();
    }
}
