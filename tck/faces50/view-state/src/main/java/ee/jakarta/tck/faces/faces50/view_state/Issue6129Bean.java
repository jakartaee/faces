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
import java.util.List;

import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named
@ViewScoped
public class Issue6129Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String ROW_1 = "one";
    public static final String CHANGED = "changed";

    private final List<Row> tableRows = List.of(new Row("zero"), new Row(ROW_1));
    private final List<Row> repeatRows = List.of(new Row("zero"), new Row(ROW_1));
    private String required;

    public void change(AjaxBehaviorEvent event) {
        tableRows.forEach(row -> row.setName(CHANGED));
        repeatRows.forEach(row -> row.setName(CHANGED));
    }

    public List<Row> getTableRows() {
        return tableRows;
    }

    public List<Row> getRepeatRows() {
        return repeatRows;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public static class Row implements Serializable {

        private static final long serialVersionUID = 1L;

        private String name;

        public Row(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

}
