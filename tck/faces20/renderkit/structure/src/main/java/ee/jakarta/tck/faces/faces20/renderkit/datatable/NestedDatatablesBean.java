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

package ee.jakarta.tck.faces.faces20.renderkit.datatable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.model.ListDataModel;
import jakarta.inject.Named;

/**
 * Drives two nested {@code h:dataTable}s that share a single inner model. The outer table iterates
 * two rows and every outer row renders the same inner {@link ListDataModel} of three rows, so a
 * per-cell input value must be keyed on the combined (outer, inner) row index rather than on the
 * inner row alone.
 */
@Named
@SessionScoped
public class NestedDatatablesBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final int INNER_SIZE = 3;
    private static final String OUTER_DATA_NAME = "form:outerData";
    private static final String INNER_DATA_NAME = "form:outerData:innerData";
    private static final String INNER_LABEL = "inner";

    private final List<Object> inputValues = new ArrayList<>();
    private transient ListDataModel<NestedDatatablesInputBean> listDataModel;

    public List<String> getOuter() {
        return List.of("outer 0", "outer 1");
    }

    public ListDataModel<NestedDatatablesInputBean> getListDataModel() {
        if (listDataModel == null) {
            List<NestedDatatablesInputBean> beans = new ArrayList<>(INNER_SIZE);
            for (int i = 0; i < INNER_SIZE; i++) {
                beans.add(new NestedDatatablesInputBean(this, INNER_LABEL + " " + i));
            }
            listDataModel = new ListDataModel<>(beans);
        }
        return listDataModel;
    }

    public String getOuterDataName() {
        return OUTER_DATA_NAME;
    }

    public String getInnerDataName() {
        return INNER_DATA_NAME;
    }

    List<Object> getInputValues() {
        return inputValues;
    }
}
