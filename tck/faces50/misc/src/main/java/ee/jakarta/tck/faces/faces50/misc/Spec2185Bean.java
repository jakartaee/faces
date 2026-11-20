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

package ee.jakarta.tck.faces.faces50.misc;

import java.lang.reflect.Modifier;
import java.util.Map;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.ViewHandler;
import jakarta.faces.component.StateHolder;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class Spec2185Bean {

    @Inject
    private FacesContext context;

    private Map<String, Object> createViewMap() {
        return context.getApplication().getViewHandler().createViewMap(context);
    }

    public boolean isStateHolder() {
        return createViewMap() instanceof StateHolder;
    }

    public boolean isNotTransient() {
        return !((StateHolder) createViewMap()).isTransient();
    }

    public boolean isNewMapPerCall() {
        return createViewMap() != createViewMap();
    }

    public boolean isPublicClassWithPublicNoArgConstructor() {
        Class<?> type = createViewMap().getClass();

        try {
            type.getConstructor();
            return Modifier.isPublic(type.getModifiers());
        }
        catch (NoSuchMethodException e) {
            return false;
        }
    }

    /**
     * @return the class name of the view map of the current view
     */
    public String getViewMapClassName() {
        return context.getViewRoot().getViewMap().getClass().getName();
    }

    /**
     * @return whether the view map of the current view is the one which {@link ViewHandler#createViewMap(FacesContext)} created
     */
    public boolean isViewMapFromViewHandler() {
        Map<String, Object> viewMap = context.getViewRoot().getViewMap();
        return viewMap == context.getAttributes().get(Spec2185ViewHandler.CREATED_VIEW_MAP);
    }

}
