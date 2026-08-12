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

package ee.jakarta.tck.faces.faces23.ajax;

import java.util.TreeSet;
import java.util.stream.Collectors;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

/**
 * Echoes the names of the request parameters of the current request, so that a test can assert exactly which form
 * controls {@code faces.getViewState} collected and encoded.
 */
@Named
@RequestScoped
public class Issue2617Bean {

    public String getParameterNames() {
        return new TreeSet<>(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().keySet())
                .stream().collect(Collectors.joining(", "));
    }
}
