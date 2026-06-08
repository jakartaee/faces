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

package ee.jakarta.tck.faces.faces22.phase_listener;

import jakarta.faces.context.FacesContext;

/**
 * The three globally registered lifecycle phase listeners in this module each belong to a distinct test view. They
 * must act only on their own view so they don't perturb the other tests' lifecycle. RESTORE_VIEW beforePhase runs
 * before the view root exists, so the requested view is derived from the request path instead.
 */
final class ViewIds {

    private ViewIds() {
    }

    static boolean isRequestedView(FacesContext context, String simpleName) {
        String path = context.getExternalContext().getRequestServletPath();
        String pathInfo = context.getExternalContext().getRequestPathInfo();
        String requested = pathInfo != null ? pathInfo : path;
        return requested != null && requested.endsWith("/" + simpleName);
    }
}
