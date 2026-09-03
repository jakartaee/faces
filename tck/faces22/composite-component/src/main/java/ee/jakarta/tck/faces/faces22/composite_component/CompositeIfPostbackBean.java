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

package ee.jakarta.tck.faces.faces22.composite_component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

/**
 * Backs the composite component of {@code composite-if-postback.xhtml}, whose {@code c:if} tests the list this passes as a composite component attribute.
 * Session scoped so the list an action filled outlives the postback that filled it.
 */
@Named
@SessionScoped
public class CompositeIfPostbackBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<String> items = new ArrayList<>();

    public List<String> getItems() {
        return items;
    }

    public void load() {
        items = List.of("alpha", "bravo", "charlie");
    }

    public void clear() {
        items = new ArrayList<>();
    }

}
