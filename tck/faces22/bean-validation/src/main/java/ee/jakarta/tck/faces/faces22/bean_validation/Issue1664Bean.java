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

package ee.jakarta.tck.faces.faces22.bean_validation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.model.SelectItem;
import jakarta.inject.Named;

@Named
@RequestScoped
public class Issue1664Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Issue1664SimpleEnum simpleValue = Issue1664SimpleEnum.VALUE1;
    private Issue1664ComplexEnum complexValue = Issue1664ComplexEnum.VALUE2;

    public Issue1664SimpleEnum getSimpleValue() {
        return simpleValue;
    }

    public void setSimpleValue(Issue1664SimpleEnum simpleValue) {
        this.simpleValue = simpleValue;
    }

    public Issue1664ComplexEnum getComplexValue() {
        return complexValue;
    }

    public void setComplexValue(Issue1664ComplexEnum complexValue) {
        this.complexValue = complexValue;
    }

    public List<SelectItem> getSimpleValues() {
        List<SelectItem> result = new ArrayList<>();
        for (Issue1664SimpleEnum value : Issue1664SimpleEnum.values()) {
            result.add(new SelectItem(value, value.toString()));
        }
        return result;
    }

    public List<SelectItem> getComplexValues() {
        List<SelectItem> result = new ArrayList<>();
        for (Issue1664ComplexEnum value : Issue1664ComplexEnum.values()) {
            result.add(new SelectItem(value, value.toString()));
        }
        return result;
    }
}
