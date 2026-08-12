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
package ee.jakarta.tck.faces.faces23.faces_data_model;

import org.junit.jupiter.api.Test;

/**
 * A {@code c:forEach} nested in a {@code c:forEach}, with the form wrapping both and one input per
 * cell. Both iterations run while the view is built, so every cell has its own child instances and
 * no per-row state is involved at all: the cells must round-trip their own values regardless.
 */
class NestedForEachForEachFormOutsideIT extends NestedIterationITBase {

    @Override
    protected String pageName() {
        return "nestedForEachForEachFormOutside.xhtml";
    }

    /**
     * @see jakarta.faces.component.UIInput
     */
    @Test
    void inputsKeepPerRowStateAcrossPostbacks() {
        assertCellsKeepOwnValueAcrossPostbacks();
    }

    @Override
    protected String inputId(int outer, int inner) {
        return "form:input_" + outer + "_" + inner;
    }

    @Override
    protected String submitId(int outer, int inner) {
        return "form:submit";
    }
}
