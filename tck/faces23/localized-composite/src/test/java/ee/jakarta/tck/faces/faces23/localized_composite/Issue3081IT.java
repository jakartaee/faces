/*
 * Copyright (c) 2026 Contributors to Eclipse Foundation.
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
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
package ee.jakarta.tck.faces.faces23.localized_composite;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

public class Issue3081IT extends BaseITNG {

    /**
     * Verify #{cc} resolves identically inside the composite component implementation and inside a
     * ui:included file: the clientId and attribute both evaluate to the same value in both contexts,
     * using both bracket and dot notation. The composite is used as <external/> nesting <internal/>,
     * so #{cc.clientId} is "external:internal" in both contexts; the include sits in an f:subview
     * "included", which only prefixes the output element ids, not the #{cc} value.
     *
     * @see jakarta.faces.component.UINamingContainer
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3081
     */
    @Test
    void testCCAndInclude() throws Exception {
        WebPage page = getPage("issue3081.xhtml");

        // #{cc} resolved directly inside the composite implementation (naming container external:internal)
        assertCc(page, "external:internal");

        // #{cc} resolved inside the ui:included file (f:subview "included" prefixes the element ids only)
        assertCc(page, "external:internal:included");
    }

    private void assertCc(WebPage page, String idPrefix) {
        assertEquals("external:internal", page.findElement(By.id(idPrefix + ":ccClientIdBracket")).getText(), "#{cc['clientId']}");
        assertEquals("external:internal", page.findElement(By.id(idPrefix + ":ccClientIdDot")).getText(), "#{cc.clientId}");
        assertEquals("Test string", page.findElement(By.id(idPrefix + ":ccAttrsBracket")).getText(), "#{cc['attrs'].param}");
        assertEquals("Test string", page.findElement(By.id(idPrefix + ":ccAttrsDot")).getText(), "#{cc.attrs.param}");
    }
}
