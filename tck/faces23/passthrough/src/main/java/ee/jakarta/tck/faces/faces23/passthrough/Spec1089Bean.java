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

package ee.jakarta.tck.faces.faces23.passthrough;

import java.util.LinkedHashMap;
import java.util.Map;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@RequestScoped
public class Spec1089Bean {

    /**
     * Backs {@code f:passThroughAttributes} on the passthrough page: one EL-derived
     * (the current view id) and one literal pass-through attribute.
     */
    public Map<String, Object> getPassThroughAttrs() {
        Map<String, Object> attrs = new LinkedHashMap<>();
        attrs.put("elname", FacesContext.getCurrentInstance().getViewRoot().getViewId());
        attrs.put("literalname", "literalValue");
        return attrs;
    }

}
