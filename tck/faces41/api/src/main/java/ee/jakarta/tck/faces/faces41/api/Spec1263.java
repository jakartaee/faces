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

package ee.jakarta.tck.faces.faces41.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

/**
 * Backs the row state preserved scenarios: an outer iteration over rows, each rendering an inner iteration over that row's cells, with one input and one mark
 * button per cell.
 *
 * <p>
 * The per-cell input value is the only thing distinguishing the cells, so a cell rendering another cell's value means the iterating components confused their
 * per-row state.
 */
@Named
@ViewScoped
public class Spec1263 implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final int OUTER_SIZE = 2;
    private static final int INNER_SIZE = 3;

    private List<List<Cell>> matrix;

    @PostConstruct
    public void init() {
        matrix = new ArrayList<>(OUTER_SIZE);
        for (int outer = 0; outer < OUTER_SIZE; outer++) {
            List<Cell> row = new ArrayList<>(INNER_SIZE);
            for (int inner = 0; inner < INNER_SIZE; inner++) {
                row.add(new Cell());
            }
            matrix.add(row);
        }
    }

    public List<List<Cell>> getMatrix() {
        return matrix;
    }

    /**
     * Sets a style class on the input of the cell this action was invoked from. The style class is a property of the single child instance shared by every
     * cell, so it belongs to the invoking cell only when the iterating component keeps the full component state of its children per row.
     *
     * @param event the action event of the cell's mark button.
     */
    public void mark(ActionEvent event) {
        event.getComponent().findComponent("input").getAttributes().put("styleClass", "marked");
    }

    public static class Cell implements Serializable {

        private static final long serialVersionUID = 1L;

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }

}
