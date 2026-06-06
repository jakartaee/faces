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

package ee.jakarta.tck.faces.faces22.empty_as_null;

import java.io.Serializable;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.event.ValueChangeEvent;
import jakarta.inject.Named;

@Named
@SessionScoped
public class Issue2831Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Object integerPropertySet = "";
    private Integer integerProperty;
    private Object stringPropertySet = "";
    private String stringProperty;
    private boolean vceFired;
    private boolean vce2Fired;

    public Integer getIntegerProperty() {
        return integerProperty;
    }

    public void setIntegerProperty(Integer integerProperty) {
        integerPropertySet = integerProperty;
        this.integerProperty = integerProperty;
    }

    public String getStringProperty() {
        return stringProperty;
    }

    public void setStringProperty(String stringProperty) {
        stringPropertySet = stringProperty;
        this.stringProperty = stringProperty;
    }

    public boolean isVceFired() {
        boolean tmp = vceFired;
        vceFired = false;
        return tmp;
    }

    public boolean isVce2Fired() {
        boolean tmp = vce2Fired;
        vce2Fired = false;
        return tmp;
    }

    public boolean isStringNull() {
        boolean isNull = (stringPropertySet == null);
        stringPropertySet = "";
        return isNull;
    }

    public boolean isIntegerNull() {
        boolean isNull = (integerPropertySet == null);
        integerPropertySet = "";
        return isNull;
    }

    public void valueChange(ValueChangeEvent event) {
        vceFired = true;
    }

    public void valueChange2(ValueChangeEvent event) {
        vce2Fired = true;
    }
}
