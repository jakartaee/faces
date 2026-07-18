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

package ee.jakarta.tck.faces.faces22.phase_listener_default;

import java.util.Iterator;
import java.util.Map;

import jakarta.faces.FacesException;
import jakarta.faces.context.ExceptionHandler;
import jakarta.faces.context.ExceptionHandlerWrapper;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ExceptionQueuedEvent;
import jakarta.faces.event.ExceptionQueuedEventContext;
import jakarta.servlet.http.HttpServletResponse;

public class Spec1216ExceptionHandler extends ExceptionHandlerWrapper {

    static final String KEY = "exceptionHandlerMessage";

    static final String HEADER = "X-Faces-Config-PhaseListener";

    public Spec1216ExceptionHandler(ExceptionHandler parent) {
        super(parent);
    }

    @Override
    public void handle() throws FacesException {
        for (Iterator<ExceptionQueuedEvent> it = getUnhandledExceptionQueuedEvents().iterator(); it.hasNext();) {
            ExceptionQueuedEvent event = it.next();
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
            appendMessage(context.getContext(), context.getException());
            it.remove();
        }

        getWrapped().handle();
    }

    private void appendMessage(FacesContext context, Throwable t) {
        Map<Object, Object> attrs = context.getAttributes();
        String cur = attrs.containsKey(KEY) ? (String) attrs.get(KEY) : "";
        attrs.put(KEY, cur + " " + t.getMessage());
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        response.addHeader(HEADER, t.getMessage());
    }
}
