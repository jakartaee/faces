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
import java.util.List;

import jakarta.faces.component.UIData;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.context.FacesContext;

/**
 * A single inner-table cell. Because the inner model is shared across the outer rows, its value is
 * stored in the shared bean's flat value list, keyed on the combined (outer, inner) row index.
 */
public class NestedDatatablesInputBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private final NestedDatatablesBean owner;
    private String stringProperty;

    public NestedDatatablesInputBean(NestedDatatablesBean owner, String stringProperty) {
        this.owner = owner;
        this.stringProperty = stringProperty;
    }

    public String getStringProperty() {
        if (stringProperty != null) {
            return stringProperty;
        }
        List<Object> inputValues = owner.getInputValues();
        return inputValues.isEmpty() ? null : (String) inputValues.get(getFlatIndex());
    }

    public void setStringProperty(String newStringProperty) {
        List<Object> inputValues = owner.getInputValues();
        if (inputValues.isEmpty()) {
            for (int i = 0, size = getFlatSize(); i < size; i++) {
                inputValues.add(new Object());
            }
        }
        inputValues.set(getFlatIndex(), newStringProperty);
        stringProperty = null;
    }

    private int getFlatIndex() {
        UIData outerData = getOuterData();
        UIData innerData = getInnerData();
        return innerData.getRowCount() * outerData.getRowIndex() + innerData.getRowIndex();
    }

    private int getFlatSize() {
        return getOuterData().getRowCount() * getInnerData().getRowCount();
    }

    private UIData getOuterData() {
        return (UIData) getViewRoot().findComponent(owner.getOuterDataName());
    }

    private UIData getInnerData() {
        return (UIData) getViewRoot().findComponent(owner.getInnerDataName());
    }

    private UIViewRoot getViewRoot() {
        return FacesContext.getCurrentInstance().getViewRoot();
    }
}
