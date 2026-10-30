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

package ee.jakarta.tck.faces.faces50.view_scope;

import static jakarta.faces.component.UIViewRoot.VIEW_MAP_NAME;

import java.util.Map;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.spi.Bean;
import jakarta.enterprise.inject.spi.BeanManager;
import jakarta.faces.component.StateHolder;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class Spec2185Bean {

    @Inject
    private BeanManager beanManager;

    private Bean<?> getViewMapBean() {
        return beanManager.resolve(beanManager.getBeans(VIEW_MAP_NAME));
    }

    private Object getViewMapReference() {
        Bean<?> bean = getViewMapBean();
        return beanManager.getReference(bean, Map.class, beanManager.createCreationalContext(bean));
    }

    public boolean isDependent() {
        return getViewMapBean().getScope() == Dependent.class;
    }

    public boolean isMap() {
        return getViewMapBean().getTypes().contains(Map.class);
    }

    public boolean isStateHolder() {
        return getViewMapReference() instanceof StateHolder;
    }

    public boolean isNotTransient() {
        return !((StateHolder) getViewMapReference()).isTransient();
    }

    public boolean isNewMapPerReference() {
        return getViewMapReference() != getViewMapReference();
    }

}
