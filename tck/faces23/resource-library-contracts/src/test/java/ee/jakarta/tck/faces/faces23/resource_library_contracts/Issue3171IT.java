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

package ee.jakarta.tck.faces.faces23.resource_library_contracts;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;

/**
 * A resource-library-contract template lives under {@code /contracts} and is private to the
 * contract mechanism: referencing it directly from {@code ui:decorate} or {@code ui:include} is
 * denied rather than served.
 */
class Issue3171IT extends BaseITNG {

    /**
     * {@code ui:decorate} pointing directly at a contract template fails rather than rendering it.
     *
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3171
     * @see jakarta.faces.view.facelets.FaceletContext
     */
    @Test
    void decorateDirectContract() {
        assertTrue(getStatusCode("issue3171decorate.xhtml") >= 400, "direct contract decorate is denied");
    }

    /**
     * {@code ui:include} pointing directly at a contract template fails rather than rendering it.
     *
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3171
     * @see jakarta.faces.view.facelets.FaceletContext
     */
    @Test
    void includeDirectContract() {
        assertTrue(getStatusCode("issue3171include.xhtml") >= 400, "direct contract include is denied");
    }
}
