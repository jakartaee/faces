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
package ee.jakarta.tck.faces.faces23.faces_data_model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

/**
 * Backs the nested-iteration state scenarios: an outer iteration over rows, each rendering an inner
 * iteration over that row's cells, with one input per cell.
 *
 * <p>The per-cell input value is the only thing distinguishing the cells, so a cell rendering
 * another cell's value means the iteration components confused their per-row state.
 */
@Named
@SessionScoped
public class NestedIterationBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final int OUTER_SIZE = 2;
    private static final int INNER_SIZE = 3;

    private List<List<NestedIterationCell>> matrix;

    @PostConstruct
    public void init() {
        matrix = new ArrayList<>(OUTER_SIZE);
        for (int outer = 0; outer < OUTER_SIZE; outer++) {
            List<NestedIterationCell> row = new ArrayList<>(INNER_SIZE);
            for (int inner = 0; inner < INNER_SIZE; inner++) {
                row.add(new NestedIterationCell());
            }
            matrix.add(row);
        }
    }

    public List<List<NestedIterationCell>> getMatrix() {
        return matrix;
    }
}
