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
import java.util.List;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

/**
 * A ui:repeat over items of two different kinds, each rendered by a mutually exclusive h:panelGroup, so that only one
 * of the two inputs of a row is ever rendered.
 */
@Named
@SessionScoped
public class Issue3219Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    public static class Item implements Serializable {

        private static final long serialVersionUID = 1L;

        private final String kind;
        private String a;
        private String b;

        public Item(String kind, String a, String b) {
            this.kind = kind;
            this.a = a;
            this.b = b;
        }

        public String getKind() {
            return kind;
        }

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }
    }

    private final List<Item> items = List.of(
        new Item("A", "a0", null),
        new Item("B", null, "b1"),
        new Item("A", "a2", null),
        new Item("B", null, "b3"));

    public List<Item> getItems() {
        return items;
    }
}
