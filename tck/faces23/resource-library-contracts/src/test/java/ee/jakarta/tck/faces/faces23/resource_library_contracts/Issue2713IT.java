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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * Path-based resource library contract mapping: the /issue2713user subtree
 * resolves to the 'user' contract while everything else resolves to the
 * 'default' contract, as declared in WEB-INF/faces-config.xml.
 */
class Issue2713IT extends BaseITNG {

    /**
     * @see jakarta.faces.application.ResourceLibraryContract
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2713
     */
    @Test
    void testIndex() throws Exception {
        WebPage page = getPage("issue2713.xhtml");
        assertEquals("Default Contract", page.getTitle());
    }

    /**
     * @see jakarta.faces.application.ResourceLibraryContract
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2713
     */
    @Test
    void testPage2() throws Exception {
        WebPage page = getPage("issue2713-page2.xhtml");
        assertEquals("Default Contract", page.getTitle());
    }

    /**
     * @see jakarta.faces.application.ResourceLibraryContract
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2713
     */
    @Test
    void testUserIndex() throws Exception {
        WebPage page = getPage("issue2713user/issue2713.xhtml");
        assertEquals("User area contract", page.getTitle());
    }
}
