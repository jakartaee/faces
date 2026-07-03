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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

/**
 * Backs a page that iterates the same three logical rows through a c:forEach over each supported source kind - a List,
 * an array, a begin/end integer range and a Map - so a no-op postback can assert every kind re-renders unchanged.
 */
@Named
@ViewScoped
public class ForEachUnchangedPostbackBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private final List<String> list = List.of("list-A", "list-B", "list-C");
    private final String[] array = { "array-A", "array-B", "array-C" };
    private final Map<String, String> map = new LinkedHashMap<>();
    private int postbacks;

    public ForEachUnchangedPostbackBean() {
        map.put("k0", "map-A");
        map.put("k1", "map-B");
        map.put("k2", "map-C");
    }

    public List<String> getList() {
        return list;
    }

    public String[] getArray() {
        return array;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public int getPostbacks() {
        return postbacks;
    }

    /**
     * Records the postback but deliberately leaves every iterated source untouched.
     */
    public String submit() {
        postbacks++;
        return null;
    }
}
