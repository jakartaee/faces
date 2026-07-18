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

package ee.jakarta.tck.faces.faces20.viewhandler;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.ViewHandler;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

/**
 * Reports the character encoding state of the current request. The state is snapshotted before the
 * {@code makeSession}/{@code invalidateSession} request parameters are honoured, so that a request which creates the
 * session still reports the state as it was on arrival.
 */
@Named
@RequestScoped
public class ViewHandlerCharEncBean {

    private static final String MAKE_SESSION_PARAM = "makeSession";

    private static final String INVALIDATE_SESSION_PARAM = "invalidateSession";

    private String requestCharacterEncoding;

    private String responseCharacterEncoding;

    private boolean hasSession;

    private String sessionCharacterEncoding;

    @PostConstruct
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        requestCharacterEncoding = externalContext.getRequestCharacterEncoding();
        responseCharacterEncoding = externalContext.getResponseCharacterEncoding();
        hasSession = externalContext.getSession(false) != null;
        sessionCharacterEncoding = hasSession
                ? (String) externalContext.getSessionMap().get(ViewHandler.CHARACTER_ENCODING_KEY)
                : null;

        if (externalContext.getRequestParameterMap().containsKey(MAKE_SESSION_PARAM)) {
            externalContext.getSession(true);
        }

        if (externalContext.getRequestParameterMap().containsKey(INVALIDATE_SESSION_PARAM)) {
            externalContext.invalidateSession();
        }
    }

    public String getRequestCharacterEncoding() {
        return requestCharacterEncoding;
    }

    public String getResponseCharacterEncoding() {
        return responseCharacterEncoding;
    }

    public boolean isHasSession() {
        return hasSession;
    }

    public String getSessionCharacterEncoding() {
        return sessionCharacterEncoding;
    }
}
