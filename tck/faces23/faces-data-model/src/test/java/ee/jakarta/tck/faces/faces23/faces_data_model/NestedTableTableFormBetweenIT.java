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
 * An {@code h:dataTable} nested in an {@code h:dataTable}, with the form between them in the outer
 * column: one form per outer row, wrapping that row's inner table. Each postback therefore carries
 * only one outer row, and the inputs of the other row must still render their own previously
 * submitted value.
 */
class NestedTableTableFormBetweenIT extends NestedIterationITBase {

    @Override
    protected String pageName() {
        return "nestedTableTableFormBetween.xhtml";
    }

    /**
     * @see jakarta.faces.component.UIData
     */
    @Test
    void inputsKeepPerRowStateAcrossPostbacks() {
        assertCellsKeepOwnValueAcrossPostbacks();
    }

    @Override
    protected String inputId(int outer, int inner) {
        return "outer:" + outer + ":form:inner:" + inner + ":input";
    }

    @Override
    protected String submitId(int outer, int inner) {
        return "outer:" + outer + ":form:submit";
    }
}
