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

package ee.jakarta.tck.faces.faces23.dynamic_components;

import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.html.HtmlPanelGroup;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AbortProcessingException;
import jakarta.faces.event.SystemEvent;

/**
 * Moves an existing child out of its current parent and into a sibling panel group. Unlike the other
 * dynamic components in this module it does not create a new component, so the relocation must be
 * recorded and replayed by the dynamic-component state saving on every postback.
 */
@FacesComponent(value = "ee.jakarta.tck.faces.faces23.dynamic_components.Issue2892Component")
public class Issue2892Component extends DynamicComponentBase {

    @Override
    public void processEvent(SystemEvent event) throws AbortProcessingException {
        if (FacesContext.getCurrentInstance().isPostback()) {
            return;
        }

        UIComponent movable = findComponent("movable");
        HtmlPanelGroup panel = (HtmlPanelGroup) findComponent("panel");
        getChildren().remove(movable);
        panel.getChildren().add(0, movable);
    }
}
