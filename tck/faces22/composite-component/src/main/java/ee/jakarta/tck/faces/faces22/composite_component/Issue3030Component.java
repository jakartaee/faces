/*
 * Copyright (c) 2017, 2018 Oracle and/or its affiliates. All rights reserved.
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

package ee.jakarta.tck.faces.faces22.composite_component;

import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.UINamingContainer;

@FacesComponent("ee.jakarta.tck.faces.faces22.composite_component.Issue3030Component")
public class Issue3030Component extends UINamingContainer {

    protected enum PropertyKeys {

        mandatory
    }

    public Boolean getMandatory() {
        return (Boolean) getStateHelper().eval(PropertyKeys.mandatory);
    }

    public void setMandatory(Boolean mandatory) {
        getStateHelper().put(PropertyKeys.mandatory, mandatory);
    }
}
