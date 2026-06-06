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

package ee.jakarta.tck.faces.faces22.phase_listener;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;
import jakarta.faces.event.PhaseListener;

class Spec762IT extends BaseITNG {

    private static final String PHASES_IN_ORDER =
            "(?s).*beforePhase\\s+RESTORE_VIEW\\s+1"
            + "\\s+beforePhase\\s+APPLY_REQUEST_VALUES\\s+2"
            + "\\s+beforePhase\\s+PROCESS_VALIDATIONS\\s+3"
            + "\\s+beforePhase\\s+UPDATE_MODEL_VALUES\\s+4"
            + "\\s+beforePhase\\s+INVOKE_APPLICATION\\s+5"
            + "\\s+beforePhase\\s+RENDER_RESPONSE\\s+6.*";

    /**
     * With the metadata facet present, all six lifecycle phases fire in order even on the initial GET request.
     *
     * @see PhaseListener
     * @see https://github.com/jakartaee/faces/issues/762
     */
    @Test
    void testMetadataShortCircuit() throws Exception {
        WebPage page = getPage("spec762.xhtml");
        assertTrue(page.getSource().matches(PHASES_IN_ORDER));
    }
}
