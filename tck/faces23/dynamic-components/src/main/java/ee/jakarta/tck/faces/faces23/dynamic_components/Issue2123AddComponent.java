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
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.component.html.HtmlOutputText;
import jakarta.faces.component.html.HtmlPanelGrid;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AbortProcessingException;
import jakarta.faces.event.PostRestoreStateEvent;
import jakarta.faces.event.PreRenderViewEvent;
import jakarta.faces.event.SystemEvent;

/**
 * Pre-render adds a dynamic {@link HtmlPanelGrid} child; on postback nests an {@link HtmlOutputText}
 * grandchild inside it, exactly once.
 */
@FacesComponent(value = "ee.jakarta.tck.faces.faces23.dynamic_components.Issue2123AddComponent")
public class Issue2123AddComponent extends DynamicComponentBase {

    private static final String CHILD_ADDED = "CHILD_ADDED";

    public Issue2123AddComponent() {
        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot root = context.getViewRoot();
        root.subscribeToViewEvent(PostRestoreStateEvent.class, this);
    }

    @Override
    public void processEvent(SystemEvent event) throws AbortProcessingException {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            HtmlPanelGrid panel = new HtmlPanelGrid();
            panel.setId("PANEL");
            panel.setStyle("border: 1px dashed blue; padding: 5px; margin: 5px");
            getChildren().add(panel);
        } else {
            HtmlPanelGrid panel = (HtmlPanelGrid) getChildren().get(0);
            if (panel.getAttributes().get(CHILD_ADDED) == null) {
                HtmlOutputText output = new HtmlOutputText();
                output.setId("OUTPUT");
                output.setValue("NEW-OUTPUT");
                panel.getChildren().add(output);
                panel.getAttributes().put(CHILD_ADDED, CHILD_ADDED);
            }
        }
    }
}
