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

package ee.jakarta.tck.faces.faces22.component_events;

import java.util.Map;

import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.html.HtmlInputText;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AbortProcessingException;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.faces.event.ListenerFor;
import jakarta.faces.event.ListenersFor;
import jakarta.faces.event.PostValidateEvent;
import jakarta.faces.event.PreValidateEvent;

@FacesComponent(value = "ee.jakarta.tck.faces.faces22.component_events.Issue3323ListenersComponent")
@ListenersFor({
    @ListenerFor(systemEventClass = PreValidateEvent.class),
    @ListenerFor(systemEventClass = PostValidateEvent.class)
})
public class Issue3323ListenersComponent extends HtmlInputText {

    @Override
    public void processEvent(ComponentSystemEvent event) throws AbortProcessingException {
        Map<String, Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
        if (event instanceof PreValidateEvent) {
            requestMap.put("preValidateEvent", "preValidateEvent");
        } else if (event instanceof PostValidateEvent) {
            requestMap.put("postValidateEvent", "postValidateEvent");
        } else {
            super.processEvent(event);
        }
    }
}
