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

package ee.jakarta.tck.faces.faces23.facelets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue2972IT extends BaseITNG {

    /**
     * Facelets is an XML view declaration language and must honour XML namespace scoping: an element which
     * redeclares the default namespace for its own subtree does not end the document, and the markup which
     * follows it is compiled and rendered as usual.
     *
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2972
     */
    @Test
    void nestedDefaultNamespaceDeclarationStillYieldsACompleteDocument() {
        WebPage page = getPage("issue2972.xhtml");

        assertEquals(200, page.getResponseStatus(), "The view must render.");
        assertTrue(page.containsSource("</html>"), "The document must be complete.");
        assertEquals("after the nested namespace", page.findElement(By.id("after")).getText(),
                "Markup after the nested namespace declaration must still be rendered.");
    }
}
