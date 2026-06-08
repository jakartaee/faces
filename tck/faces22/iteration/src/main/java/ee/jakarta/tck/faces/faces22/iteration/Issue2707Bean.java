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

package ee.jakarta.tck.faces.faces22.iteration;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.ValueHolder;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.NumberConverter;
import jakarta.inject.Named;

@Named
@SessionScoped
public class Issue2707Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    private int counter = 0;
    private List<Issue2707Item> items = new LinkedList<>();

    public List<Issue2707Item> getItems() {
        return items;
    }

    public void add() {
        items.add(new Issue2707Item(++counter));
    }

    public String getString() {
        StringBuilder sb = new StringBuilder();

        for (Issue2707Item item : items) {
            sb.append("[");

            String value = "null";
            if (item.getValue() != null) {
                value = item.getValue().toString();
            }

            sb.append(value);
            sb.append("] ");
        }

        return sb.toString();
    }

    private UIComponent findItemValue(UIComponent root) {
        if ("itemValue".equals(root.getId())) {
            return root;
        }

        for (UIComponent child : root.getChildren()) {
            UIComponent ret = findItemValue(child);
            if (ret != null) {
                return ret;
            }
        }

        return null;
    }

    public void addConverters() {
        FacesContext ctx = FacesContext.getCurrentInstance();

        if (!ctx.isPostback()) {
            UIComponent component = findItemValue(ctx.getViewRoot());
            if (component instanceof ValueHolder) {
                ValueHolder parentValueHolder = (ValueHolder) component;
                Converter parentConverter = parentValueHolder.getConverter();
                if (parentConverter == null) {
                    NumberConverter numberConverter = new NumberConverter();
                    numberConverter.setMaxFractionDigits(2);
                    numberConverter.setPattern("##.00");
                    parentValueHolder.setConverter(numberConverter);
                }
            }
        }
    }
}
