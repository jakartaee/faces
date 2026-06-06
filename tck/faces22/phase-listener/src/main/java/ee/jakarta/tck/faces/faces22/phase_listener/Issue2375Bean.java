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

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.html.HtmlOutputText;
import jakarta.faces.component.html.HtmlPanelGroup;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Named;

@Named
@RequestScoped
public class Issue2375Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    public void transientRoot(ActionEvent event) {
        UIComponent button = event.getComponent();
        UIComponent addto = button.findComponent("addto");

        HtmlPanelGroup transientRoot = new HtmlPanelGroup();
        transientRoot.setTransient(true);
        HtmlOutputText text = new HtmlOutputText();
        text.setValue("transient parent");
        transientRoot.getChildren().add(text);
        addto.getChildren().add(transientRoot);
    }
}
