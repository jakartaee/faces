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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.faces.component.UIComponent;
import jakarta.faces.component.html.HtmlInputText;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named
@ViewScoped
public class Issue6128Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    private final List<String> tableRows = new ArrayList<>(List.of("0", "1"));
    private final List<String> repeatRows = new ArrayList<>(List.of("0", "1"));

    /**
     * Adds an input with only an id, so that its initial state has nothing in its state helper.
     */
    public void addInput(ComponentSystemEvent event) {
        UIComponent cell = event.getComponent();

        if (cell.getChildCount() == 0) {
            HtmlInputText input = new HtmlInputText();
            input.setId("in");
            cell.getChildren().add(input);
        }
    }

    public void addRow() {
        tableRows.add(String.valueOf(tableRows.size()));
        repeatRows.add(String.valueOf(repeatRows.size()));
    }

    public List<String> getTableRows() {
        return tableRows;
    }

    public List<String> getRepeatRows() {
        return repeatRows;
    }

}
