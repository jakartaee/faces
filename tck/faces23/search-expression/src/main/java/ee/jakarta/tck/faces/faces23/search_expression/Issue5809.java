/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
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

package ee.jakarta.tck.faces.faces23.search_expression;

import java.util.List;

import jakarta.enterprise.inject.Model;

@Model
public class Issue5809 {

    private boolean listTouched;

    /**
     * Backs the value of an {@code h:dataTable}. Reading it forces row iteration,
     * which happens whenever a tree visit descends into the table - including the
     * search expression resolution visit, which by design does not skip unrendered
     * subtrees.
     */
    public List<String> getList() {
        listTouched = true;
        return List.of("one", "two", "three");
    }

    public boolean isListTouched() {
        return listTouched;
    }
}
