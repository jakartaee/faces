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
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named
@ViewScoped
public class Issue2896Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<List<Issue2896Item>> items;

    @PostConstruct
    public void init() {
        items = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            List<Issue2896Item> list = new ArrayList<>();
            list.add(new Issue2896Item("list" + i + "item1"));
            list.add(new Issue2896Item("list" + i + "item2"));
            list.add(new Issue2896Item("list" + i + "item3"));
            items.add(list);
        }
    }

    public void add(List<Issue2896Item> list) {
        list.add(new Issue2896Item("new" + list.size()));
    }

    public List<List<Issue2896Item>> getItems() {
        return items;
    }
}
