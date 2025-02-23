/*
 * Copyright (c) Contributors to Eclipse Foundation.
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
package org.eclipse.ee4j.tck.faces.faces50.events;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Default;
import jakarta.faces.annotation.View;
import jakarta.faces.application.Application;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.event.PostConstructApplicationEvent;
import jakarta.faces.event.PostConstructViewMapEvent;
import jakarta.faces.event.PreDestroyApplicationEvent;
import jakarta.faces.event.PreDestroyViewMapEvent;
import jakarta.faces.event.PreRenderViewEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named
@ViewScoped
public class Spec1501Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static List<String> observedEvents = new ArrayList<>();

    @ApplicationScoped
    public static class Spec1501ApplicationScopedBean {
        public void observePostConstructApplicationEvent(@Observes PostConstructApplicationEvent event) {
            observedEvents.add("PostConstructApplicationEvent: " + (event.getSource() instanceof Application));
        }

        public void observePreDestroyApplicationEvent(@Observes PreDestroyApplicationEvent event) {
            observedEvents.add("PreDestroyApplicationEvent: " + (event.getSource() instanceof Application));
        }
    }

    public void observePostConstructViewMapEvent(@Observes @Default PostConstructViewMapEvent event) {
        observedEvents.add("PostConstructViewMapEvent: " + (event.getSource() instanceof UIViewRoot));
    }

    public void observePreRenderViewEvent(@Observes @Default PreRenderViewEvent event) {
        observedEvents.add("PreRenderViewEvent: " + (event.getSource() instanceof UIViewRoot));
    }

    public void observePreDestroyViewMapEvent(@Observes @Default PreDestroyViewMapEvent event) {
        observedEvents.add("PreDestroyViewMapEvent: " + (event.getSource() instanceof UIViewRoot));
    }

    public void observeMyPostConstructViewMapEvent(@Observes @View("/spec1501.xhtml") PostConstructViewMapEvent event) {
        observedEvents.add("MyPostConstructViewMapEvent: " + (event.getSource() instanceof UIViewRoot));
    }

    public void observeMyPreRenderViewEvent(@Observes @View("/spec1501.xhtml") PreRenderViewEvent event) {
        observedEvents.add("MyPreRenderViewEvent: " + (event.getSource() instanceof UIViewRoot));
    }

    public void observeMyPreDestroyViewMapEvent(@Observes @View("/spec1501.xhtml") PreDestroyViewMapEvent event) {
        observedEvents.add("MyPreDestroyViewMapEvent: " + (event.getSource() instanceof UIViewRoot));
    }

    public List<String> getObservedEvents() {
        return observedEvents;
    }
}
