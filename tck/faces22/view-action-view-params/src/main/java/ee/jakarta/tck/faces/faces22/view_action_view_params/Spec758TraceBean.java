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

package ee.jakarta.tck.faces.faces22.view_action_view_params;

import java.io.Serializable;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

/**
 * Records which action listeners a view action broadcast to, and in which order. Session scoped
 * because a view action which navigates on a GET does so by redirecting, so the trace has to survive
 * into the request which renders the result page.
 */
@Named
@SessionScoped
public class Spec758TraceBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private final StringBuilder trace = new StringBuilder();

    public void append(String listenerId, String componentId) {
        if (trace.length() > 0) {
            trace.append(' ');
        }

        trace.append(listenerId).append(' ').append(componentId);
    }

    public String getTrace() {
        return trace.toString();
    }
}
