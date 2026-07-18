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
 * A {@code ui:repeat} nested in a {@code ui:repeat}, with the form inside the inner repeat: one form
 * per cell. Each postback carries a single cell, so every other cell must keep rendering its own
 * previously submitted value while the submitted one is updated.
 */
class NestedRepeatRepeatFormInsideIT extends NestedIterationITBase {

    @Override
    protected String pageName() {
        return "nestedRepeatRepeatFormInside.xhtml";
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
        return formId(outer, inner) + ":input";
    }

    @Override
    protected String submitId(int outer, int inner) {
        return formId(outer, inner) + ":submit";
    }

    private static String formId(int outer, int inner) {
        return "outer:" + outer + ":inner:" + inner + ":form";
    }
}
