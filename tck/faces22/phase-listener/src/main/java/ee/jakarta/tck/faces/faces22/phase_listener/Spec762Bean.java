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

import java.io.Serializable;
import java.util.Map;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@RequestScoped
public class Spec762Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    static final String BUILDER_KEY = "builder";

    void appendMessage(String message) {
        Map<String, Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
        StringBuilder builder = (StringBuilder) requestMap.get(BUILDER_KEY);
        if (builder == null) {
            builder = new StringBuilder();
            requestMap.put(BUILDER_KEY, builder);
        }
        builder.append(message);
    }

    public String getMessage() {
        Map<String, Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
        return requestMap.containsKey(BUILDER_KEY) ? requestMap.get(BUILDER_KEY).toString() : "no message";
    }
}
