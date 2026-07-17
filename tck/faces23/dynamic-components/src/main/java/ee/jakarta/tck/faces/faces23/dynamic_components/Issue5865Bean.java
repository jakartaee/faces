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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.html.HtmlOutputText;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

/**
 * Adds a child to a panel group which sits inside a data table row.
 *
 * <p>
 * The command button invoking this also sits inside a row, so the row index is set by the time the action runs.
 * The panel group is a single component instance reused for every row, so the added child belongs to all of them.
 * </p>
 */
@Named
@SessionScoped
public class Issue5865Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String ADDED = "ADDED-INSIDE-ROW";

    private final List<String> rows = new ArrayList<>(List.of("row0", "row1", "row2"));

    public List<String> getRows() {
        return rows;
    }

    public void addInsideRow() {
        FacesContext context = FacesContext.getCurrentInstance();
        UIComponent group = context.getViewRoot().findComponent("form:table:group");

        HtmlOutputText output = new HtmlOutputText();
        output.setId("added" + group.getChildCount());
        output.setValue(ADDED);
        group.getChildren().add(output);
    }

    public void postback() {
        // Just a way to post back.
    }
}
