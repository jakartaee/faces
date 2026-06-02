/*
 * Copyright (c) Contributors to Eclipse Foundation.
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

package ee.jakarta.tck.faces.faces23.facelets;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.faces.component.html.HtmlDataTable;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named
@ViewScoped
public class Issue4128Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    // The table is bound to this property: the component instance is reused across postbacks, and the
    // Facelets refresh must locate-and-reuse it (not recreate its subtree) -- the condition the test exercises.
    private HtmlDataTable dataTable;

    private List<String> rows = new ArrayList<>(List.of("row"));

    // Counts action-listener invocations: the regression recreated the bound button and fired this twice per click.
    private int actionCount;

    public void addRow() {
        actionCount++;
        rows.add("row" + actionCount);
        dataTable.setRows(dataTable.getRows() + 1);
    }

    public HtmlDataTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(HtmlDataTable dataTable) {
        this.dataTable = dataTable;
    }

    public List<String> getRows() {
        return rows;
    }

    public int getActionCount() {
        return actionCount;
    }
}
