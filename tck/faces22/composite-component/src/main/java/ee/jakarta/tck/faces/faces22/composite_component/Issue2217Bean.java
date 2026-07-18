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

package ee.jakarta.tck.faces.faces22.composite_component;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

/**
 * Supplies the cc:clientBehavior name, event and targets, both as literals and through EL, and
 * receives the client id the behavior script recorded into the marker input.
 */
@Named
@RequestScoped
public class Issue2217Bean {

    private String marker;

    public String getName() {
        return "ok";
    }

    public String getEvent() {
        return "action";
    }

    public String getTargets() {
        return "cancel sub:command";
    }

    public String getTargetsEL() {
        return "cancelEL sub:commandEL";
    }

    public String doAction() {
        return null;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }
}
