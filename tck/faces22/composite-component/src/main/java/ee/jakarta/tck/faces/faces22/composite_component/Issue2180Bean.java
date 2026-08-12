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

package ee.jakarta.tck.faces.faces22.composite_component;

import java.beans.BeanInfo;
import java.io.Serializable;
import java.util.Collection;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.visit.VisitContext;
import jakarta.faces.component.visit.VisitResult;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

/**
 * Counts, across the whole view, how many attribute names the composite components declare a default value for. The
 * count is a property of the view declaration and must therefore be identical on every postback.
 */
@Named
@SessionScoped
public class Issue2180Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    private int previousCount = -1;

    public String getStatus() {
        int observedCount = countAttributesWithDeclaredDefaultValues();

        try {
            return previousCount == -1 || observedCount == previousCount ? "SUCCESS" : "FAILED";
        }
        finally {
            previousCount = observedCount;
        }
    }

    private static int countAttributesWithDeclaredDefaultValues() {
        FacesContext context = FacesContext.getCurrentInstance();
        int[] count = { 0 };

        context.getViewRoot().visitTree(VisitContext.createVisitContext(context), (visitContext, target) -> {
            if (UIComponent.isCompositeComponent(target)) {
                BeanInfo beanInfo = (BeanInfo) target.getAttributes().get(UIComponent.BEANINFO_KEY);

                @SuppressWarnings("unchecked")
                Collection<String> names =
                    (Collection<String>) beanInfo.getBeanDescriptor().getValue(UIComponent.ATTRS_WITH_DECLARED_DEFAULT_VALUES);

                if (names != null) {
                    count[0] += names.size();
                }
            }

            return VisitResult.ACCEPT;
        });

        return count[0];
    }
}
