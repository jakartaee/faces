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

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.html.HtmlOutputText;
import jakarta.faces.component.html.HtmlPanelGroup;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Named;

/**
 * Applies every add/remove permutation to the bound panel group within a single action listener.
 * The final child set must reflect the net effect of the permutation, not the intermediate states.
 */
@Named
@RequestScoped
public class Issue2399Bean {

    private static final String ADDED_ID = "added";
    private static final String ADDED_VALUE = "I was dynamically added";
    private static final String REMOVE_ME_ID = "removeMe";

    private HtmlPanelGroup panelGroup;

    public void doAdd(ActionEvent event) {
        add();
    }

    public void doAddRemove(ActionEvent event) {
        remove(add());
    }

    public void doAddRemoveAdd(ActionEvent event) {
        remove(add());
        add();
    }

    public void doRemove(ActionEvent event) {
        remove(removeMe());
    }

    public void doRemoveAdd(ActionEvent event) {
        UIComponent removeMe = removeMe();
        remove(removeMe);
        panelGroup.getChildren().add(removeMe);
    }

    public void doRemoveAddRemove(ActionEvent event) {
        UIComponent removeMe = removeMe();
        remove(removeMe);
        panelGroup.getChildren().add(removeMe);
        remove(removeMe);
    }

    private UIComponent add() {
        HtmlOutputText added = new HtmlOutputText();
        added.setId(ADDED_ID);
        added.setValue(ADDED_VALUE);
        panelGroup.getChildren().add(added);
        return added;
    }

    private void remove(UIComponent component) {
        panelGroup.getChildren().remove(component);
    }

    private UIComponent removeMe() {
        return panelGroup.findComponent(REMOVE_ME_ID);
    }

    public HtmlPanelGroup getPanelGroup() {
        return panelGroup;
    }

    public void setPanelGroup(HtmlPanelGroup panelGroup) {
        this.panelGroup = panelGroup;
    }
}
