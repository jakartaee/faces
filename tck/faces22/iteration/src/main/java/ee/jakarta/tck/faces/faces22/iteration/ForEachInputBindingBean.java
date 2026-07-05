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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

/**
 * Backs a page that binds an h:inputText to a per-item property inside a c:forEach, for each source kind whose loop var
 * addresses a mutable element: a List and an insertion-ordered Set of mutable rows, and a Map. Submitting a distinct
 * value into every input must update the matching model element, which requires resolving the loop var during Update
 * Model Values (not just on render) - exercising IndexedValueExpression (List), IteratedValueExpression (Set) and
 * MappedValueExpression (Map). The summaries read the model back so a test can assert each value reached its element.
 */
@Named
@ViewScoped
public class ForEachInputBindingBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /** A mutable element; identity-based (no equals/hashCode) so mutating {@code value} is safe while held in a Set. */
    public static class Row implements Serializable {

        private static final long serialVersionUID = 1L;

        private String value;

        public Row(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    private final List<Row> list = new ArrayList<>(List.of(new Row("L0"), new Row("L1"), new Row("L2")));
    private final Set<Row> set = new LinkedHashSet<>(List.of(new Row("S0"), new Row("S1"), new Row("S2")));
    private final Map<String, Row> map = new LinkedHashMap<>();

    public ForEachInputBindingBean() {
        map.put("k0", new Row("M0"));
        map.put("k1", new Row("M1"));
        map.put("k2", new Row("M2"));
    }

    public List<Row> getList() {
        return list;
    }

    public Set<Row> getSet() {
        return set;
    }

    public Map<String, Row> getMap() {
        return map;
    }

    public String getListSummary() {
        return "L:" + list.stream().map(Row::getValue).collect(Collectors.joining(","));
    }

    public String getSetSummary() {
        return "S:" + set.stream().map(Row::getValue).collect(Collectors.joining(","));
    }

    public String getMapSummary() {
        return "M:" + map.values().stream().map(Row::getValue).collect(Collectors.joining(","));
    }

    public String submit() {
        return null;
    }
}
