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
import java.util.Set;
import java.util.TreeSet;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named
@SessionScoped
public class Issue2631Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean toggle;
    private final Set<String> set = new TreeSet<>();
    private final Set<String> set1;
    private final Set<String> set2;
    private String setToShow;

    public Issue2631Bean() {
        toggle = true;
        setToShow = "SET1 - INIT";
        set1 = populateSet("-SET1");
        set2 = populateSet("-SET2");
        set.addAll(set1);
    }

    public Set<String> getSet() {
        return set;
    }

    public String getSetToShow() {
        return setToShow;
    }

    public String toggle() {
        toggle = !toggle;

        if (toggle) {
            setToShow = "SET1";
            set.clear();
            set.addAll(set1);
        } else {
            setToShow = "SET2";
            set.clear();
            set.addAll(set2);
        }

        return null;
    }

    private TreeSet<String> populateSet(String suffix) {
        TreeSet<String> newSet = new TreeSet<>();

        for (int j = 0; j < 3; j++) {
            newSet.add(j + suffix);
        }

        return newSet;
    }
}
