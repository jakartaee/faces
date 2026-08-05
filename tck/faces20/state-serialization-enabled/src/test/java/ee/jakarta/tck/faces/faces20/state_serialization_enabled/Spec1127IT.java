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

package ee.jakarta.tck.faces.faces20.state_serialization_enabled;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * This module pins {@code jakarta.faces.SERIALIZE_SERVER_STATE} to {@code true}, which the reactor otherwise never
 * exercises. Its counterpart pinning it to {@code false} lives in {@code faces20/state-serialization-disabled}; the two
 * assert opposite outcomes of the same spec issue and therefore cannot share a module.
 */
class Spec1127IT extends BaseITNG {

    /**
     * With server side state saving and serialization switched on, the state really is serialized, so a component
     * attribute which refuses to serialize fails the request rather than silently succeeding.
     *
     * @see https://github.com/jakartaee/faces/issues/1127
     */
    @Test
    void nonSerializableStateFailsWhenSerializationIsEnabled() {
        WebPage page = getPage("spec1127.xhtml");

        page.findElement(By.id("button")).click();

        assertEquals(500, page.getResponseStatus(),
                "With state serialization a non serializable attribute must fail the request.");
    }
}
