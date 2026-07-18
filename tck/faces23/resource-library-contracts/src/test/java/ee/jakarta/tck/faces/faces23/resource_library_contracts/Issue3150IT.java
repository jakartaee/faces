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
package ee.jakarta.tck.faces.faces23.resource_library_contracts;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;

class Issue3150IT extends BaseITNG {

    /**
     * A ui:composition template referencing an absolute path under /contracts that
     * does not resolve to an active resource library contract must fail to build the
     * view, resulting in a server error rather than a rendered page.
     *
     * @see jakarta.faces.application.ResourceHandler
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3150
     */
    @Test
    void unresolvableContractTemplateFails() {
        assertEquals(500, getStatusCode("issue3150.xhtml"), "unresolvable contract template must fail");
    }
}
