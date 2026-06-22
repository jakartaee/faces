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

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class Issue5809 {

    private boolean listTouched;
    private boolean revealed;
    private String message = "";

    /**
     * Backs the value of an unrendered {@code h:dataTable}. Reading it forces row
     * iteration, which only happens when a tree visit descends into the unrendered
     * subtree - i.e. when the search expression resolution failed to skip it.
     */
    public List<String> getList() {
        listTouched = true;
        return List.of("item");
    }

    public boolean isListTouched() {
        return listTouched;
    }

    /**
     * Reveals a component that is unrendered in the initial request but rendered after
     * the ajax call, and updates the always-rendered target's message.
     */
    public void action() {
        revealed = true;
        message = "updated";
    }

    public boolean isRevealed() {
        return revealed;
    }

    public String getMessage() {
        return message;
    }
}
