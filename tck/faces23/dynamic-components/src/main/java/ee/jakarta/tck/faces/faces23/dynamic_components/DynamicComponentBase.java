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

package ee.jakarta.tck.faces.faces23.dynamic_components;

import jakarta.faces.component.UIComponentBase;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.PreRenderViewEvent;
import jakarta.faces.event.SystemEventListener;

/**
 * Shared base for the dynamic test components. Each subclass subscribes to
 * {@link PreRenderViewEvent} and mutates its own children when the view renders.
 */
public abstract class DynamicComponentBase extends UIComponentBase implements SystemEventListener {

    public static final String FAMILY = "ee.jakarta.tck.faces.faces23.dynamic_components";
    public static final String RENDERER_TYPE = "component";

    protected DynamicComponentBase() {
        setRendererType(RENDERER_TYPE);
        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot root = context.getViewRoot();
        root.subscribeToViewEvent(PreRenderViewEvent.class, this);
    }

    @Override
    public String getFamily() {
        return FAMILY;
    }

    @Override
    public boolean isListenerForSource(Object source) {
        return source instanceof UIViewRoot;
    }
}
