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

import static java.util.stream.Collectors.joining;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.faces.component.UIInput;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.validator.LengthValidator;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named
@ViewScoped
public class Issue6133Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final int MINIMUM_LENGTH = 3;

    private final List<Row> tableRows = new ArrayList<>(List.of(new Row("zero"), new Row("one")));
    private final List<Row> repeatRows = new ArrayList<>(List.of(new Row("zero"), new Row("one")));

    /**
     * Adds a validator to the input next to the button, in the row of the button only.
     */
    public void restrict(ActionEvent event) {
        LengthValidator validator = new LengthValidator();
        validator.setMinimum(MINIMUM_LENGTH);
        ((UIInput) event.getComponent().getParent().findComponent("in")).addValidator(validator);
    }

    public void addRow() {
        tableRows.add(new Row("new"));
        repeatRows.add(new Row("new"));
    }

    public List<Row> getTableRows() {
        return tableRows;
    }

    public List<Row> getRepeatRows() {
        return repeatRows;
    }

    public String getTableNames() {
        return tableRows.stream().map(Row::getName).collect(joining(","));
    }

    public String getRepeatNames() {
        return repeatRows.stream().map(Row::getName).collect(joining(","));
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
