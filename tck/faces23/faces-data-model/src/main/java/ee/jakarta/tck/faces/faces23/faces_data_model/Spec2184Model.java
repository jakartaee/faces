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

import jakarta.faces.model.DataModel;
import jakarta.faces.model.FacesDataModel;

/**
 * Wraps a {@link Spec2184Value}, one row per element of {@link Spec2184Value#getRows()}. Not a {@link FacesDataModel}.
 */
public class Spec2184Model extends DataModel<String> {

    private Spec2184Value value;
    private int rowIndex = -1;

    @Override
    public int getRowCount() {
        return value.getRows().size();
    }

    @Override
    public String getRowData() {
        return value.getRows().get(rowIndex);
    }

    @Override
    public int getRowIndex() {
        return rowIndex;
    }

    @Override
    public Object getWrappedData() {
        return value;
    }

    @Override
    public boolean isRowAvailable() {
        return rowIndex >= 0 && rowIndex < getRowCount();
    }

    @Override
    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    @Override
    public void setWrappedData(Object value) {
        this.value = (Spec2184Value) value;
    }

}
