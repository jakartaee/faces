/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
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
package ee.jakarta.tck.faces.faces50.view_state;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.component.UIData;
import jakarta.faces.component.UIOutput;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@RequestScoped
public class Issue6125Bean {

    public void setValues() {
        UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
        ((UIData) viewRoot.findComponent("form:table")).setValue(new ArrayList<>(List.of("b", "a", "b")));

        Map<String, String> map = new LinkedHashMap<>();
        map.put("z", "1");
        map.put("a", "2");
        map.put("m", "3");
        ((UIOutput) viewRoot.findComponent("form:map")).setValue(map);
    }

}
