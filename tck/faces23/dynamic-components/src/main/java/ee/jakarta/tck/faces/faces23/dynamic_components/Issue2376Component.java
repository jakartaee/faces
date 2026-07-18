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
import jakarta.faces.component.html.HtmlOutputText;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AbortProcessingException;
import jakarta.faces.event.SystemEvent;

/**
 * Inserts a child at index 1, i.e. in between two pre-existing static children, rather than
 * appending it. The insertion index must be honored on the initial render and replayed on postback.
 */
@FacesComponent(value = "ee.jakarta.tck.faces.faces23.dynamic_components.Issue2376Component")
public class Issue2376Component extends DynamicComponentBase {

    public static final String DYNAMIC_VALUE = "Dynamic Text";

    /**
     * Renders its children only. The marker output of {@link ComponentRenderer} would end up in
     * between the children whose relative order this test asserts.
     */
    public Issue2376Component() {
        setRendererType(null);
    }

    @Override
    public void processEvent(SystemEvent event) throws AbortProcessingException {
        if (FacesContext.getCurrentInstance().isPostback()) {
            return;
        }

        HtmlOutputText dynamic = new HtmlOutputText();
        dynamic.setValue(DYNAMIC_VALUE);
        getChildren().add(1, dynamic);
    }
}
