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

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

/**
 * Backs a page whose c:forEach derives each component's id from the item value. Replacing the list with a same-size
 * list of different values must regenerate the ids, so it exercises the case where the iteration count is unchanged
 * but the per-element build-time attributes are not.
 */
@Named
@ViewScoped
public class ForEachContentIdBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<String> items = new ArrayList<>(List.of("a", "b", "c"));

    public List<String> getItems() {
        return items;
    }

    /**
     * Replaces the list with three different values - same count, different content.
     */
    public String replace() {
        items = new ArrayList<>(List.of("x", "y", "z"));
        return null;
    }
}
