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

 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package ee.jakarta.tck.faces.faces20.system_event_listener_startup;

import jakarta.faces.component.UIViewRoot;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AbortProcessingException;
import jakarta.faces.event.SystemEvent;
import jakarta.faces.event.SystemEventListener;

/**
 * Reaches for the view map of the current view root from its own constructor. The runtime instantiates a listener declared in faces-config while the
 * application is still starting up, at which point there is no request and hence no view root, so this must degrade rather than fail the deployment.
 */
public class Issue2648Listener implements SystemEventListener {

    static final String CONSTRUCTED = "issue2648.constructed";

    public Issue2648Listener() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context == null) {
            return;
        }

        UIViewRoot viewRoot = context.getViewRoot();

        if (viewRoot != null) {
            viewRoot.getViewMap();
        }

        context.getExternalContext().getApplicationMap().put(CONSTRUCTED, "yes");
    }

    @Override
    public void processEvent(SystemEvent event) throws AbortProcessingException {
        // NOOP, constructing this listener at startup is what is under test.
    }

    @Override
    public boolean isListenerForSource(Object source) {
        return source instanceof UIViewRoot;
    }

}
