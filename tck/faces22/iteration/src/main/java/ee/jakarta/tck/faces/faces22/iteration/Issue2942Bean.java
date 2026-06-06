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
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class Issue2942Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    private final List<Integer> ints = new ArrayList<>();
    private final ValueHolderList vl = new ValueHolderList();

    public Issue2942Bean() {
        ints.add(1);
    }

    public ValueHolderList getList() {
        return vl;
    }

    public void removeInt(int index) {
        ints.remove(index);
    }

    public class ValueHolder {

        private final int index;

        public ValueHolder(int index) {
            this.index = index;
        }

        public int getValue() {
            return ints.get(index);
        }

        public void setValue(int value) {
            ints.set(index, value);
        }
    }

    public class ValueHolderList extends AbstractList<ValueHolder> {

        @Override
        public ValueHolder get(int index) {
            return new ValueHolder(index);
        }

        @Override
        public int size() {
            return ints.size();
        }
    }
}
