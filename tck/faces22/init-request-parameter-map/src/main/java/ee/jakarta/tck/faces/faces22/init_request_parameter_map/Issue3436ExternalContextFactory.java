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

package ee.jakarta.tck.faces.faces22.init_request_parameter_map;

import jakarta.faces.FacesException;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.ExternalContextFactory;

/**
 * Reads the request parameter map while creating the external context, which is before the faces context it belongs to
 * has been established.
 */
public class Issue3436ExternalContextFactory extends ExternalContextFactory {

    private final ExternalContextFactory wrapped;

    public Issue3436ExternalContextFactory(ExternalContextFactory wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public ExternalContext getExternalContext(Object context, Object request, Object response) throws FacesException {
        ExternalContext externalContext = new Issue3436ExternalContext(wrapped.getExternalContext(context, request, response));
        externalContext.getRequestParameterMap().get("issue3436");
        return externalContext;
    }

    @Override
    public ExternalContextFactory getWrapped() {
        return wrapped;
    }
}
