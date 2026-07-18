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

package ee.jakarta.tck.faces.faces20.view_expired_exception;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@RequestScoped
public class ViewExpiredExceptionErrorPageBean {

    private static final int EXPIRE_SESSION_SECONDS = 1;

    /**
     * The test can only expire server side state, so it must know which state saving method is
     * currently configured; client side state never expires.
     */
    public String getStateSavingMethod() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().getStateManager().isSavingStateInClient(context) ? "client" : "server";
    }

    public void expireSession() {
        FacesContext.getCurrentInstance().getExternalContext().setSessionMaxInactiveInterval(EXPIRE_SESSION_SECONDS);
    }
}
