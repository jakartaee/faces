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

package ee.jakarta.tck.faces.faces23.passthrough;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.model.SelectItem;
import jakarta.inject.Named;

@Named
@RequestScoped
public class Issue3138Bean implements Serializable {

    private String name;
    private Map<String, String> map;

    private List<SelectItem> list;

    public List<SelectItem> getList() {
        list = new ArrayList<>();
        list.add(new SelectItem("aaaaa", "aaaaaaa"));
        list.add(new SelectItem("bbbbbb", "bbbbbb"));
        list.add(new SelectItem("cccccc", "cccccccc"));
        list.add(new SelectItem("ddddddd", "dddddddd"));
        return list;
    }

    public void setList(List<SelectItem> list) {
        this.list = list;
    }

    public Map<String, String> getMap() {
        map = new LinkedHashMap<>();
        map.put("aaaaaaa", "aaaaaaaaaa");
        map.put("bbbbbbb", "bbbbbbbbbb");
        map.put("ccccccc", "cccccccccc");
        map.put("ddddddd", "dddddddddd");
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
