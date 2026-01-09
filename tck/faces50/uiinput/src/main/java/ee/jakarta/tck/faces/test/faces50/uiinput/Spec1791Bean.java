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
package ee.jakarta.tck.faces.test.faces50.uiinput;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.model.SelectItem;
import jakarta.inject.Named;

@Named
@RequestScoped
public class Spec1791Bean {

    private static final SelectItem[] selectItems = {
        new SelectItem("value1", "label1"),
        new SelectItem("value2", "label2"),
        new SelectItem("value3", "label3")
    };

    public SelectItem[] getSelectItemArray() {
        return selectItems;
    }

    public List<SelectItem> getSelectItemList() {
        return Arrays.asList(selectItems);
    }

    public Map<String, Object> getSelectItemMap() {
        return Arrays.stream(selectItems).collect(Collectors.toMap(SelectItem::getLabel, SelectItem::getValue));
    }
}
