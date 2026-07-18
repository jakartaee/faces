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

package ee.jakarta.tck.faces.faces20.el_context_listener;

import java.util.concurrent.atomic.AtomicBoolean;

import jakarta.el.ELContextEvent;
import jakarta.el.ELContextListener;
import jakarta.faces.application.Application;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AbortProcessingException;
import jakarta.faces.event.SystemEvent;
import jakarta.faces.event.SystemEventListener;

/**
 * Registers itself as an {@link ELContextListener} on the {@link Application} at startup (on
 * {@code PostConstructApplicationEvent}), then records whether it is notified when a new
 * {@code ELContext} is created for a subsequent request. This exercises the public contract of
 * {@link Application#addELContextListener(ELContextListener)} without touching implementation
 * internals.
 */
public class ELContextListenerNotifiedRegistrar implements SystemEventListener, ELContextListener {

    private static final AtomicBoolean NOTIFIED = new AtomicBoolean(false);

    public static boolean isNotified() {
        return NOTIFIED.get();
    }

    @Override
    public void processEvent(SystemEvent event) throws AbortProcessingException {
        FacesContext.getCurrentInstance().getApplication().addELContextListener(this);
    }

    @Override
    public boolean isListenerForSource(Object source) {
        return source instanceof Application;
    }

    @Override
    public void contextCreated(ELContextEvent event) {
        NOTIFIED.set(true);
    }
}
