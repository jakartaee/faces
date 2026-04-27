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
package ee.jakarta.tck.faces.test.faces20.api.application.statemanagerwrapper;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;

class StateManagerWrapperClientIT extends BaseITNG {

    @Test
    void stateManagerIsSavingStateInClientTest() {
        // WAR declares STATE_SAVING_METHOD=client; wrapper delegates to underlying manager, so "true".
        String body = getResponseBody("StateManagerWrapperTestServlet?testname=stateManagerIsSavingStateInClientTest");
        assertTrue(body.trim().equals("true"),
                "Expected 'true' (client-side state saving) but got:\n" + body);
    }
}
