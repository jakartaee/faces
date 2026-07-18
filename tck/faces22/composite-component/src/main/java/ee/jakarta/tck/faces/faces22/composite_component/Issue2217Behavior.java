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

import jakarta.faces.component.behavior.ClientBehaviorBase;
import jakarta.faces.component.behavior.ClientBehaviorContext;
import jakarta.faces.component.behavior.FacesBehavior;

/**
 * Records the client id of the component it was ultimately retargeted onto into the marker input,
 * so that the retargeting is observable server side after the enclosing form is submitted.
 */
@FacesBehavior(Issue2217Behavior.BEHAVIOR_ID)
public class Issue2217Behavior extends ClientBehaviorBase {

    public static final String BEHAVIOR_ID = "issue2217Behavior";

    private static final String EVENT_NAME = "action";
    private static final String MARKER_CLIENT_ID = "form:marker";

    @Override
    public String getScript(ClientBehaviorContext clientBehaviorContext) {
        if (!EVENT_NAME.equals(clientBehaviorContext.getEventName())) {
            return null;
        }

        String clientId = clientBehaviorContext.getComponent().getClientId(clientBehaviorContext.getFacesContext());
        return "document.getElementById('" + MARKER_CLIENT_ID + "').value='" + clientId + "';";
    }
}
