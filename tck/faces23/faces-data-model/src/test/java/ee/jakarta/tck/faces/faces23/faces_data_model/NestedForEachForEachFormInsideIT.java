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
 * A {@code c:forEach} nested in a {@code c:forEach}, with the form inside the inner one: one form
 * per cell, so each postback carries a single cell. This is the shape the {@code rowStatePreserved}
 * documentation offers as the alternative to a runtime iteration, so it must hold without it.
 */
class NestedForEachForEachFormInsideIT extends NestedIterationITBase {

    @Override
    protected String pageName() {
        return "nestedForEachForEachFormInside.xhtml";
    }

    /**
     * @see jakarta.faces.component.UIForm
     */
    @Test
    void inputsKeepPerRowStateAcrossPostbacks() {
        assertCellsKeepOwnValueAcrossPostbacks();
    }

    @Override
    protected String inputId(int outer, int inner) {
        return "form_" + outer + "_" + inner + ":input";
    }

    @Override
    protected String submitId(int outer, int inner) {
        return "form_" + outer + "_" + inner + ":submit";
    }
}
