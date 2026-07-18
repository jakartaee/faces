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

package ee.jakarta.tck.faces.faces23.faces_data_model;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class Spec479Bean {

    private final Collection<Spec479Item> users;

    public Spec479Bean() {
        users = new ItemCollection();
        for (int i = 0; i < 3; i++) {
            Spec479Item item = new Spec479Item();
            item.setFirstName("First" + i);
            item.setLastName("Last" + i);
            users.add(item);
        }
    }

    public Collection<Spec479Item> getUsers() {
        return users;
    }

    /**
     * A plain {@link Collection} that is deliberately not a {@link List}, to prove a UIData value
     * backed by an arbitrary Collection is supported.
     */
    private static final class ItemCollection extends AbstractCollection<Spec479Item> {

        private final List<Spec479Item> inner = new ArrayList<>();

        @Override
        public boolean add(Spec479Item item) {
            return inner.add(item);
        }

        @Override
        public Iterator<Spec479Item> iterator() {
            return inner.iterator();
        }

        @Override
        public int size() {
            return inner.size();
        }
    }
}
